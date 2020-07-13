package vukan.com.apursp.ui.new_ad_window;

import androidx.lifecycle.ViewModel;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.repository.Repository;

public class NewAdWindowViewModel extends ViewModel {
    private final Repository repository;

    public NewAdWindowViewModel() {
        repository = new Repository();
    }

    String addProduct(Product p, String productID) {
        return repository.addProduct(p, productID);
    }

    void addProductImage(ProductImage pi) {
        repository.addProductImage(pi);
    }

    void deleteProductImage(String url) {
        repository.deleteProductImage(url);
    }
}