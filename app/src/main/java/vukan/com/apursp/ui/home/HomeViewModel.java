package vukan.com.apursp.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.repository.Repository;

public class HomeViewModel extends ViewModel {
    private final Repository repository;
    private MutableLiveData<List<Product>> mProducts;

    public HomeViewModel() {
        repository = new Repository();
        mProducts = new MutableLiveData<>();
    }

    MutableLiveData<List<Product>> getProducts() {
        mProducts = repository.getProducts();
        return mProducts;
    }

    MutableLiveData<List<Product>> filterProducts(String[] filters) {
        mProducts = repository.filterProducts(filters);
        return mProducts;
    }

    void addProductToFavourites(String productID) {
        repository.addProductToFavourites(productID);
    }

    void removeProductFromFavourites(String productID) {
        repository.removeProductFromFavourites(productID);
    }
}