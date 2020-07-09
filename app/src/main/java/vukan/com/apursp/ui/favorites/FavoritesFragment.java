package vukan.com.apursp.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ProductRecyclerViewAdapter;

public class FavoritesFragment extends Fragment implements ProductRecyclerViewAdapter.ListItemClickListener {
    private ProductRecyclerViewAdapter adapter;
    FavoritesViewModel favoritesViewModel;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.app_name));
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recycler_view_omiljeni);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        getProducts();
    }

    @Override
    public void onListItemClick(String productID) {
        FavoritesFragmentDirections.OmiljeniToProizvodFragmentAction action = FavoritesFragmentDirections.omiljeniToProizvodFragmentAction();
        action.setProductId(productID);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onStarItemClick(String productID, View view) {
        favoritesViewModel.removeProductFromFavourites(productID);
        getProducts();
    }

    void getProducts() {
        favoritesViewModel.getFavouriteProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.setProducts(products);
            recyclerView.setAdapter(adapter);
        });
    }
}
