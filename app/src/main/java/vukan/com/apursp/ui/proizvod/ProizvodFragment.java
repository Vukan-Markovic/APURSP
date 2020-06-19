package vukan.com.apursp.ui.proizvod;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ProductImageRecyclerViewAdapter;

public class ProizvodFragment extends Fragment implements ProductImageRecyclerViewAdapter.ListItemClickListener {
    private ProductImageRecyclerViewAdapter adapter;
    private String productID = "0";
    private TextView nazivProizvoda;
    private TextView opisProizvoda;
    private TextView cenaProizvoda;
    private TextView datumObjavljivanja;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        recyclerView.setAdapter(adapter);
        nazivProizvoda = view.findViewById(R.id.naziv_proizvoda);
        opisProizvoda = view.findViewById(R.id.opis_proizvoda);
        cenaProizvoda = view.findViewById(R.id.cena_proizvoda);
        datumObjavljivanja = view.findViewById(R.id.datum_objavljivanja);

        if (getArguments() != null)
            productID = ProizvodFragmentArgs.fromBundle(getArguments()).getProductId();

        proizvodViewModel.getProductDetails(productID).observe(getViewLifecycleOwner(), product -> {
            nazivProizvoda.setText(product.getName());
            opisProizvoda.setText(product.getDescription());
            cenaProizvoda.setText("Cena: " + product.getPrice().toString() + "RSD");
            datumObjavljivanja.setText("Datum objavljivanja: " + product.getDateTime().toDate().toString());
        });

        proizvodViewModel.getProductImages(productID).observe(getViewLifecycleOwner(), products -> {
            adapter = new ProductImageRecyclerViewAdapter(products, this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onListItemClick(String imageUrl) {
        ProizvodFragmentDirections.ProizvodToSlikaFragmentAction action = ProizvodFragmentDirections.proizvodToSlikaFragmentAction();
        action.setImageUrl(imageUrl);
        Navigation.findNavController(requireView()).navigate(action);
    }
}