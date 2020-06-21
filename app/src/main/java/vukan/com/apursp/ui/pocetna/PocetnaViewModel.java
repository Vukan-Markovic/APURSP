package vukan.com.apursp.ui.pocetna;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.repository.Repository;

public class PocetnaViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Product>> mProducts;

    public PocetnaViewModel() {
        repository = new Repository();
        mProducts = new MutableLiveData<>();
    }

    void addUser() {
        repository.addUser();
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
