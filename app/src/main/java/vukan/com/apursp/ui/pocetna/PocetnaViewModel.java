package vukan.com.apursp.ui.pocetna;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.repository.Repository;

public class PocetnaViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Product>> mProducts;

    public PocetnaViewModel() {
        repository = new Repository();
        mProducts = new MutableLiveData<>();
    }

    MutableLiveData<List<Product>> getProducts() {
        mProducts = repository.getProducts();
        return mProducts;
    }

    MutableLiveData<List<Product>> searchProducts(String query) {
        mProducts = repository.searchProducts(query);
        return mProducts;
    }
}
