package vukan.com.apursp.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.repository.Repository;

public class HomeViewModel extends ViewModel {
    private Repository repository;

    private MutableLiveData<List<Product>> mProducts;

    public HomeViewModel() {
        repository = new Repository();
        mProducts = new MutableLiveData<>();
    }

    MutableLiveData<List<Product>> getProducts() {
        mProducts = repository.getProducts();
        return mProducts;
    }
}
