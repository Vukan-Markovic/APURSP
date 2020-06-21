package vukan.com.apursp.ui.omiljeni;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.repository.Repository;

public class OmiljeniViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Product>> mProducts;

    public OmiljeniViewModel() {
        repository = new Repository();
        mProducts = new MutableLiveData<>();
    }

    MutableLiveData<List<Product>> getFavouriteProducts() {
        mProducts = repository.getFavouriteProducts();
        return mProducts;
    }

    void removeProductFromFavourites(String productID) {
        repository.removeProductFromFavourites(productID);
    }
}
