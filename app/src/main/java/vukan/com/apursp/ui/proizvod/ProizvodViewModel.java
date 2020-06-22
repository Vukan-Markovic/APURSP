package vukan.com.apursp.ui.proizvod;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.models.User;
import vukan.com.apursp.repository.Repository;

public class ProizvodViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<ProductImage>> mProductImages;
    private MutableLiveData<Product> mProductDetails;
    private MutableLiveData<User> mProductUser;

    public ProizvodViewModel() {
        repository = new Repository();
        mProductUser = new MutableLiveData<>();
        mProductImages = new MutableLiveData<>();
        mProductDetails = new MutableLiveData<>();
    }

    MutableLiveData<List<ProductImage>> getProductImages(String id) {
        mProductImages = repository.getProductImages(id);
        return mProductImages;
    }

    MutableLiveData<Product> getProductDetails(String id) {
        mProductDetails = repository.getProductDetails(id);
        return mProductDetails;
    }

    MutableLiveData<User> getProductUser(String id) {
        mProductUser = repository.getProductUser(id);
        return mProductUser;
    }

    public void incrementCounter(String id) {
        repository.incrementCounter(id);
    }
}
