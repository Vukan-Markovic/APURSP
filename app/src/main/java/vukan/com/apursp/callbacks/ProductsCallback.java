package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;

public interface ProductsCallback {
    void onCallback(List<Product> products);
}
