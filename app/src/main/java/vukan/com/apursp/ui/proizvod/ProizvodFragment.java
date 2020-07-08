package vukan.com.apursp.ui.proizvod;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ProductImageRecyclerViewAdapter;

public class ProizvodFragment extends Fragment implements ProductImageRecyclerViewAdapter.ListItemClickListener {
    private ProductImageRecyclerViewAdapter adapter;
    private String productID = "0";
    private TextView nazivProizvoda;
    private TextView opisProizvoda;
    private TextView cenaProizvoda;
    private TextView vidjeno;
    private TextView lokacija;
    private TextView datumObjavljivanja;
    private Button poruke;
    private Button pozovi;
    private AppCompatImageButton delete;
    private String phoneNumber;
    private String userID;
    private String fixPrice;
    private AdView mAdView;
    private SimpleDateFormat sfd;
    private TextView username;
    private CircleImageView userImage;
    private boolean increment = true;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_proizvod, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProizvodViewModel proizvodViewModel = new ViewModelProvider(this).get(ProizvodViewModel.class);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductImageRecyclerViewAdapter(new ArrayList<>(), this);
        sfd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        recyclerView.setAdapter(adapter);
        nazivProizvoda = view.findViewById(R.id.naziv_proizvoda);
        opisProizvoda = view.findViewById(R.id.opis_proizvoda);
        cenaProizvoda = view.findViewById(R.id.cena_proizvoda);
        datumObjavljivanja = view.findViewById(R.id.datum_objavljivanja);
        vidjeno = view.findViewById(R.id.vidjeno);
        poruke = view.findViewById(R.id.poruke);
        pozovi = view.findViewById(R.id.pozovi);
        userImage = view.findViewById(R.id.userImage);
        lokacija = view.findViewById(R.id.lokacija);
        username = view.findViewById(R.id.userName);
        delete = view.findViewById(R.id.delete);

        delete.setOnClickListener(view1 -> new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_product)
                .setMessage(R.string.confirm)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Toast.makeText(requireActivity(), R.string.deleted, Toast.LENGTH_SHORT).show();
                    proizvodViewModel.deleteProduct(productID);
                    requireActivity().onBackPressed();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_delete)
                .show());

        userImage.setOnClickListener(view1 -> {
            ProizvodFragmentDirections.ProizvodToMojiOglasiFragmentAction action = ProizvodFragmentDirections.proizvodToMojiOglasiFragmentAction();
            action.setUserId(userID);
            Navigation.findNavController(view1).navigate(action);
        });

        MobileAds.initialize(requireContext(), initializationStatus -> {
        });

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        poruke.setOnClickListener(view1 -> {
            ProizvodFragmentDirections.ProizvodToPorukeFragmentAction action = ProizvodFragmentDirections.proizvodToPorukeFragmentAction();
            action.setProductId(productID);
            Navigation.findNavController(requireView()).navigate(action);
        });

        pozovi.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null)
                startActivity(intent);
        });

        if (getArguments() != null) {
            productID = ProizvodFragmentArgs.fromBundle(getArguments()).getProductId();
            if (increment) proizvodViewModel.incrementCounter(productID);
        }

        proizvodViewModel.getProductDetails(productID).observe(getViewLifecycleOwner(), product -> {
            nazivProizvoda.setText(product.getName());
            opisProizvoda.setText(product.getDescription());

            proizvodViewModel.getCategory(product.getCategoryID()).observe(getViewLifecycleOwner(), category -> requireActivity().setTitle(category.getName()));

            if (product.getFixPrice()) fixPrice = getString(R.string.fiksna_cena);
            else fixPrice = getString(R.string.dogovor);

            cenaProizvoda.setText(String.format(getString(R.string.cena) + ": %s %s, %s", product.getPrice().toString(), product.getCurrency(), fixPrice));
            datumObjavljivanja.setText(String.format(getString(R.string.objavljeno) + ": %s", sfd.format(product.getDatetime().toDate())));
            vidjeno.setText(String.format(getString(R.string.vidjeno) + ": %s " + getString(R.string.puta), product.getSeen().toString()));

            if (product.getUserID() != null) {
                userID = product.getUserID();

                proizvodViewModel.getProductUser(userID).observe(getViewLifecycleOwner(), user -> {
                    username.setText(String.format(getString(R.string.objavio) + ": %s", user.getUsername()));
                    phoneNumber = user.getPhone();

                    if (user.getLocation() != null)
                        lokacija.setText(String.format(getString(R.string.lokacija) + ": %s", user.getLocation()));
                    else lokacija.setVisibility(View.INVISIBLE);

                    if (!user.getImageUrl().isEmpty()) {
                        GlideApp.with(userImage.getContext())
                                .load(user.getImageUrl())
                                .into(userImage);
                    }

                    if (phoneNumber == null) pozovi.setVisibility(View.INVISIBLE);
                });

                if (userID.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())) {
                    poruke.setVisibility(View.INVISIBLE);
                    pozovi.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.VISIBLE);
                }
            }
        });

        proizvodViewModel.getProductImages(productID).observe(getViewLifecycleOwner(), products -> {
            adapter = new ProductImageRecyclerViewAdapter(products, this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onListItemClick(String imageUrl) {
        increment = false;
        ProizvodFragmentDirections.ProizvodToSlikaFragmentAction action = ProizvodFragmentDirections.proizvodToSlikaFragmentAction();
        action.setImageUrl(imageUrl);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onPause() {
        if (mAdView != null) mAdView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) mAdView.resume();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) mAdView.destroy();
        super.onDestroy();
    }
}
