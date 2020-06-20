package vukan.com.apursp.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import vukan.com.apursp.database.Database;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.models.User;

public class Repository {
    private Database database;
    private MutableLiveData<List<Product>> mProducts;
    private MutableLiveData<Product> mProduct;
    private MutableLiveData<List<ProductImage>> mProductImages;
    private MutableLiveData<List<Message>> mMessages;
    private MutableLiveData<User>mUser;
    private MutableLiveData<List<Product>>mUserProducts;

    public Repository() {
        database = new Database();
        mProducts = new MutableLiveData<>();
        mProduct = new MutableLiveData<>();
        mProductImages = new MutableLiveData<>();
        mUser=new MutableLiveData<>();
        mUserProducts=new MutableLiveData<>();
        mMessages = new MutableLiveData<>();
    }
    public void sendMessage(Message m){
        database.sendMessage(m);
    }
    public MutableLiveData<List<Message>> getUserMessages(String sender,String receiver){
        database.getUserMessages(sender,receiver,message -> mMessages.setValue(message));
        return mMessages;
    }
    public MutableLiveData<List<Product>> getProducts() {
        database.getProducts(products -> mProducts.setValue(products));
        return mProducts;
    }

    public MutableLiveData<List<Product>> searchProducts(String query) {
        database.searchProducts(query, products -> mProducts.setValue(products));
        return mProducts;
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

    public MutableLiveData<User>getUser(){
        database.getUser(user->mUser.setValue(user));
        return mUser;
    }

    public MutableLiveData<List<Product>>getUserProducts(String userID){
        database.getUserProducts(userID,products->mUserProducts.setValue(products));
        return mUserProducts;
    }
}