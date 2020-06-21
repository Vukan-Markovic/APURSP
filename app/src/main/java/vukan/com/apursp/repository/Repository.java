package vukan.com.apursp.repository;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import vukan.com.apursp.database.Database;
import vukan.com.apursp.models.FavouriteProduct;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.models.User;

public class Repository {
    private Database database;
    private MutableLiveData<List<Product>> mProducts;
    private List<FavouriteProduct> mFavouritesProducts;
    private List<Product> products;
    private MutableLiveData<Product> mProduct;
    private MutableLiveData<List<ProductImage>> mProductImages;
    private MutableLiveData<User> mUser;
    private MutableLiveData<List<Product>> mUserProducts;
    private boolean favourite = false;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public Repository() {
        database = new Database();
        products = new ArrayList<>();
        mFavouritesProducts = new ArrayList<>();
        mProducts = new MutableLiveData<>();
        mProduct = new MutableLiveData<>();
        mProductImages = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mUserProducts = new MutableLiveData<>();
    }

    public void addUser() {
        database.addUser(user);
    }

    public MutableLiveData<List<Product>> getProducts() {
        database.getProducts(products -> mProducts.setValue(products));
        return mProducts;
    }

    public MutableLiveData<List<Product>> getFavouriteProducts() {
        this.products = new ArrayList<>();
        database.getFavouriteProducts(user.getUid(), products -> {
            mFavouritesProducts = products;
            for (FavouriteProduct product : mFavouritesProducts) {
                database.getProduct(product.getProductID(), favouriteProduct -> {
                    this.products.add(favouriteProduct);
                    mProducts.setValue(this.products);
                });
            }
        });

        return mProducts;
    }

    public MutableLiveData<List<Product>> filterProducts(String[] filters) {
        database.searchProducts(filters, products -> mProducts.setValue(products));
        return mProducts;
    }

    public void isFavourite(String productID, View v) {
        database.isFavourite(productID, user.getUid(), favourite -> {
            if (favourite)
                v.setBackground(ContextCompat.getDrawable(v.getContext(), android.R.drawable.star_on));
            else
                v.setBackground(ContextCompat.getDrawable(v.getContext(), android.R.drawable.star_off));
        });
    }

    public void incrementCounter(String id) {
        database.incrementCounter(id);
    }

    public MutableLiveData<Product> getProductDetails(String id) {
        database.getProductDetails(id, product -> mProduct.setValue(product));
        return mProduct;
    }

    public MutableLiveData<List<ProductImage>> getProductImages(String id) {
        database.getProductImages(id, productImages -> mProductImages.setValue(productImages));
        return mProductImages;
    }

    public MutableLiveData<User> getUser() {
        database.getUser(user -> mUser.setValue(user));
        return mUser;
    }

    public MutableLiveData<List<Product>> getUserProducts(String userID) {
        database.getUserProducts(userID, products -> mUserProducts.setValue(products));
        return mUserProducts;
    }

    public void addProductToFavourites(String productID) {
        database.addProductToFavourites(productID, user.getUid());
    }

    public void removeProductFromFavourites(String productID) {
        database.removeProductFromFavourites(productID, user.getUid());
    }
}