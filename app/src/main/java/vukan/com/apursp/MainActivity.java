package vukan.com.apursp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import vukan.com.apursp.ui.my_ads.MyAdsViewModel;

public class MainActivity extends AppCompatActivity {
    private final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser mFirebaseUser;
    private MyAdsViewModel myAdsViewModel;
    private BottomNavigationView navView;
    private SharedPreferences sharedPref;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK)
                        myAdsViewModel.addUser();
                    if (IdpResponse.fromResultIntent(result.getData()) == null) finish();
                });

        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        myAdsViewModel = new ViewModelProvider(this).get(MyAdsViewModel.class);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        String language = sharedPref.getString("language", "lang");
        if (language != null && !language.equals("lang")) setLocale(language, false);
        String theme = sharedPref.getString("theme", "light");

        if (theme != null && theme.equals("dark"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_proizvodSlika) {
                Objects.requireNonNull(getSupportActionBar()).hide();
                navView.setVisibility(View.GONE);
            } else {
                Objects.requireNonNull(getSupportActionBar()).show();
                navView.setVisibility(View.VISIBLE);
            }
        });

        myAdsViewModel = new ViewModelProvider(this).get(MyAdsViewModel.class);

        mAuthStateListener = firebaseAuth -> {
            mFirebaseUser = firebaseAuth.getCurrentUser();

            if (mFirebaseUser == null) {
                activityResultLauncher.launch(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false, false)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.TwitterBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setLogo(R.mipmap.ic_launcher)
                        .build());
            }
        };

        navView.getMenu().findItem(R.id.navigation_pocetna).setTitle(getString(R.string.title_pocetna));
        navView.getMenu().findItem(R.id.navigation_omiljeni).setTitle(getString(R.string.omiljeni));
        navView.getMenu().findItem(R.id.navigation_novioglas).setTitle(getString(R.string.title_novioglas));
        navView.getMenu().findItem(R.id.navigation_obavestenja).setTitle(getString(R.string.title_poruke));
        navView.getMenu().findItem(R.id.navigation_mojioglasi).setTitle(getString(R.string.title_mojioglasi));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(Objects.requireNonNull(mAuthStateListener));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.delete_account) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.delete_account)
                    .setMessage(R.string.confirm)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        myAdsViewModel.deleteUserData(mFirebaseUser.getUid());
                        AuthUI.getInstance().delete(this).addOnCompleteListener(task ->
                                Toast.makeText(this, R.string.account_deleted, Toast.LENGTH_SHORT).show()
                        );
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.ic_delete)
                    .show();
        } else if (itemId == R.id.sign_out) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(task ->
                    Toast.makeText(this, R.string.signed_out, Toast.LENGTH_SHORT).show()
            );
        } else if (itemId == R.id.change_language) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.change_language)
                    .setMessage(R.string.choose_language)
                    .setPositiveButton(R.string.serbian, (dialog, which) -> setLocale("sr", true))
                    .setNegativeButton(R.string.english, (dialog, which) -> setLocale("en", true))
                    .setIcon(R.drawable.ic_language)
                    .show();
        } else if (itemId == R.id.change_theme) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.change_theme)
                    .setMessage(R.string.choose_theme)
                    .setPositiveButton(R.string.dark, (dialog, which) -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        saveTheme("dark");
                    })
                    .setNegativeButton(R.string.light, (dialog, which) -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        saveTheme("light");
                    })
                    .setIcon(R.drawable.ic_color)
                    .show();
        } else if (itemId == R.id.privacy_policy) {
            openWebPage("https://sites.google.com/view/saleroom-privacy-policy");
        } else if (itemId == R.id.terms_and_conditions) {
            openWebPage("https://sites.google.com/view/saleroom-terms-conditions");
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void saveTheme(String data) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("theme", data);
        editor.apply();
        recreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String langCode, boolean flag) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("language", langCode);
        editor.apply();
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(langCode));
        res.updateConfiguration(conf, res.getDisplayMetrics());
        if (flag) recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}