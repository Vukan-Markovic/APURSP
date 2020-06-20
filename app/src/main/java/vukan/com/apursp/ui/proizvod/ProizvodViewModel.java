package vukan.com.apursp.ui.proizvod;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.repository.Repository;

public class ProizvodViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<ProductImage>> mProductImages;
    private MutableLiveData<Product> mProductDetails;

    public ProizvodViewModel() {
        repository = new Repository();
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

    public void incrementCounter(String id) {
        repository.incrementCounter(id);
    }
}
