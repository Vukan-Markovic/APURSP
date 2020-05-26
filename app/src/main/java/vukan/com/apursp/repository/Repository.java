package vukan.com.apursp.repository;

import androidx.lifecycle.MutableLiveData;
import java.util.List;

import vukan.com.apursp.MyCallback;
import vukan.com.apursp.database.Database;
import vukan.com.apursp.models.Product;

public class Repository {
    private Database database;
    private MutableLiveData<List<Product>> mProducts;

    public Repository() {
        database = new Database();
        mProducts = new MutableLiveData<>();
    }

    public MutableLiveData<List<Product>> getProducts() {
        database.getProducts(products -> mProducts.setValue(products));
        return mProducts;
    }
}