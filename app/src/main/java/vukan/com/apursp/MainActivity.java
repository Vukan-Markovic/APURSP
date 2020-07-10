package vukan.com.apursp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import vukan.com.apursp.ui.my_ads.MyAdsViewModel;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser mFirebaseUser;
    private MyAdsViewModel myAdsViewModel;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

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
                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false, false)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.TwitterBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setLogo(R.mipmap.ic_launcher)
                        .build(), 1);
            } else
                Snackbar.make(findViewById(R.id.container), getString(R.string.dobrodosli) + mFirebaseUser.getDisplayName(), Snackbar.LENGTH_SHORT).show();
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) myAdsViewModel.addUser();
        }

        if (IdpResponse.fromResultIntent(data) == null) finish();
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
        switch (item.getItemId()) {
            case R.id.delete_account:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.delete_account)
                        .setMessage(R.string.confirm)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> mFirebaseUser.delete().addOnCompleteListener(task -> Toast.makeText(this, R.string.account_deleted, Toast.LENGTH_SHORT).show()))
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_delete)
                        .show();
                break;
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> Toast.makeText(this, R.string.signed_out, Toast.LENGTH_SHORT).show());
                break;
            case R.id.change_language:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.change_language)
                        .setMessage(R.string.choose_language)
                        .setPositiveButton(R.string.serbian, (dialog, which) -> mFirebaseUser.delete().addOnCompleteListener(task -> setLocale("sr")))
                        .setNegativeButton(R.string.english, (dialog, which) -> mFirebaseUser.delete().addOnCompleteListener(task -> setLocale("en")))
                        .setIcon(R.drawable.ic_language)
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String langCode) {
        Resources res = getResources();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(langCode));
        res.updateConfiguration(conf, res.getDisplayMetrics());
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}