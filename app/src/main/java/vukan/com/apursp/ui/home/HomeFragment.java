package vukan.com.apursp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ProductRecyclerViewAdapter;

public class HomeFragment extends Fragment implements ProductRecyclerViewAdapter.ListItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ProductRecyclerViewAdapter adapter;
    SearchView search;
    Button filters;
    HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.app_name));
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ProductRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        search = view.findViewById(R.id.searchView);
        filters = view.findViewById(R.id.filters);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimary,
                android.R.color.holo_green_dark
        );

        filters.setOnClickListener(view1 ->
                Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.pocetnaToFilteriFragmentAction())
        );

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                recyclerView.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                recyclerView.setAdapter(adapter);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadRecyclerViewData();
        });
    }

    @Override
    public void onListItemClick(String productID) {
        search.setQuery("", false);
        search.clearFocus();
        HomeFragmentDirections.PocetnaToProizvodFragmentAction action = HomeFragmentDirections.pocetnaToProizvodFragmentAction();
        action.setProductId(productID);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onStarItemClick(String productID, View v) {
        CheckBox star = ((CheckBox) v);

        if (star.isChecked()) {
            star.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_border));
            homeViewModel.removeProductFromFavourites(productID);
        } else {
            star.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.ic_star));
            homeViewModel.addProductToFavourites(productID);
        }
    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        mSwipeRefreshLayout.setRefreshing(true);

        if (getArguments() != null && getArguments().getStringArray("filters") != null) {
            homeViewModel.filterProducts(HomeFragmentArgs.fromBundle(getArguments()).getFilters()).observe(getViewLifecycleOwner(), products -> {
                adapter.setProducts(products);
                recyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);

                if (products.isEmpty())
                    Toast.makeText(getContext(), R.string.no_results, Toast.LENGTH_SHORT).show();
            });
        } else {
            homeViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
                adapter.setProducts(products);
                recyclerView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);

                if (products.isEmpty())
                    Toast.makeText(getContext(), R.string.no_results, Toast.LENGTH_SHORT).show();
            });
        }
    }
}