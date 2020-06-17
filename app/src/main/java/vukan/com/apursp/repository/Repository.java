package vukan.com.apursp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import java.util.List;

import vukan.com.apursp.database.Database;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;

public class Repository {
    private Database database;
    private MutableLiveData<List<Product>> mProducts;
    private MutableLiveData<Product> mProduct;
    private MutableLiveData<List<ProductImage>> mProductImages;

    public Repository() {
        database = new Database();
        mProducts = new MutableLiveData<>();
        mProduct = new MutableLiveData<>();
        mProductImages = new MutableLiveData<>();
    }

    public MutableLiveData<List<Product>> getProducts() {
        database.getProducts(products -> mProducts.setValue(products));
        return mProducts;
    }

    public MutableLiveData<Product> getProductDetails(String id) {
        database.getProductDetails(id, product -> mProduct.setValue(product));
        return mProduct;
    }

    public MutableLiveData<List<ProductImage>> getProductImages(String id) {
        database.getProductImages(id, productImages -> mProductImages.setValue(productImages));
        return mProductImages;
    }
}