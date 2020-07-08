package vukan.com.apursp.ui.novioglasprozor;

import androidx.lifecycle.ViewModel;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.repository.Repository;

public class NovioglasprozorViewModel extends ViewModel {
    private Repository repository;

    public NovioglasprozorViewModel() {
        repository = new Repository();
    }

    String addProduct(Product p) {
        return repository.addProduct(p);
    }

    void addProductImage(ProductImage pi) {
        repository.addProductImage(pi);
    }
}