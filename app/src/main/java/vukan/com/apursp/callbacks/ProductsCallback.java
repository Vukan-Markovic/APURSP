package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.Product;

public interface ProductsCallback {
    void onCallback(List<Product> products);
}
