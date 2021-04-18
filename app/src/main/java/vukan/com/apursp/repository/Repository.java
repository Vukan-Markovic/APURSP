package vukan.com.apursp.repository;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vukan.com.apursp.R;
import vukan.com.apursp.firebase.Database;
import vukan.com.apursp.models.Comment;
import vukan.com.apursp.models.Conv;
import vukan.com.apursp.models.FavoriteProduct;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductCategory;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.models.User;

public class Repository {
    private final Database database;
    private final MutableLiveData<List<Product>> mProducts;
    private List<FavoriteProduct> mFavouritesProducts;
    private final MutableLiveData<List<ProductCategory>> mCategories;
    private final MutableLiveData<Product> mProduct;
    private final MutableLiveData<List<ProductImage>> mProductImages;
    private final MutableLiveData<ProductCategory> mProductCategory;
    private final MutableLiveData<User> mUser;
    private final MutableLiveData<Float> mUserRating;
    private final MutableLiveData<List<Product>> mUserProducts;
    private FirebaseUser user;
    private final MutableLiveData<List<Message>> mMessages;
    private final MutableLiveData<List<Conv>> mConv;
    private final MutableLiveData<User> mProductUser;
    private final MutableLiveData<List<Comment>> mUserComments;

    public Repository() {
        database = new Database();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFavouritesProducts = new ArrayList<>();
        mProductUser = new MutableLiveData<>();
        mProductCategory = new MutableLiveData<>();
        mProducts = new MutableLiveData<>();
        mUserRating = new MutableLiveData<>();
        mCategories = new MutableLiveData<>();
        mProduct = new MutableLiveData<>();
        mProductImages = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mMessages = new MutableLiveData<>();
        mConv = new MutableLiveData<>();
        mUserProducts = new MutableLiveData<>();
        mUserComments = new MutableLiveData<>();
    }

    public void reportUser(String userID) {
        database.reportUser(userID);
    }

    public void deleteConversation(Conv conv) {
        database.deleteConversation(conv);
    }

    public void deleteProductImage(String url) {
        database.deleteProductImage(url);
    }

    public void deleteUserData(String userID) {
        database.deleteUserData(userID);
    }

    public void deleteProduct(String id) {
        database.deleteProduct(id);
    }

    public MutableLiveData<List<ProductCategory>> getCategories() {
        database.getCategories(mCategories::setValue);
        return mCategories;
    }

    public MutableLiveData<User> getProductUser(String id) {
        database.getProductUser(id, mProductUser::setValue);
        return mProductUser;
    }

    public void addUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        database.addUser(Objects.requireNonNull(user));
    }

    public void updateProfilePicture(Uri imageUrl) {
        database.updateProfilePicture(imageUrl);
    }

    public void updateProfilePictureBitmap(Bitmap imageBitmap) {
        database.updateProfilePictureBitmap(imageBitmap);
    }

    public void sendMessage(Message m) {
        database.sendMessage(m);
    }

    public String addProduct(Product p, String productID) {
        return database.addProduct(p, productID);
    }

    public void addProductImage(ProductImage pi) {
        database.addProductImage(pi);
    }

    public MutableLiveData<List<Message>> getUserMessages(String sender, String receiver, String productID) {
        database.getUserMessages(sender, receiver, productID, mMessages::setValue);
        return mMessages;
    }

    public MutableLiveData<List<Conv>> getAllUserMessages(String sender) {
        database.getAllUserMessages(sender, mConv::setValue);
        return mConv;
    }

    public MutableLiveData<List<Product>> getProducts() {
        database.getProducts(mProducts::setValue);
        return mProducts;
    }

    public MutableLiveData<List<Product>> getFavouriteProducts() {
        List<Product> products = new ArrayList<>();
        mProducts.setValue(products);

        database.getFavouriteProducts(user.getUid(), favoritesProducts -> {
            mFavouritesProducts = favoritesProducts;
            for (FavoriteProduct product : mFavouritesProducts) {
                database.getProduct(product.getProductID(), favouriteProduct -> {
                    products.add(favouriteProduct);
                    mProducts.setValue(products);
                });
            }
        });

        return mProducts;
    }

    public MutableLiveData<List<Product>> filterProducts(String[] filters) {
        database.filterProducts(filters, mProducts::setValue);
        return mProducts;
    }

    public void isFavourite(String productID, View v) {
        if (user != null) {
            database.isFavourite(productID, user.getUid(), favourite -> {
                if (favourite)
                    v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_star));
                else
                    v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_star_border));
            });
        }
    }

    public void incrementCounter(String id) {
        database.incrementCounter(id, user.getUid());
    }

    public MutableLiveData<Product> getProductDetails(String id) {
        database.getProductDetails(id, mProduct::setValue);
        return mProduct;
    }

    public MutableLiveData<List<ProductImage>> getProductImages(String id) {
        database.getProductImages(id, mProductImages::setValue);
        return mProductImages;
    }

    public MutableLiveData<User> getUser(String userID) {
        database.getUser(userID, mUser::setValue);
        return mUser;
    }

    public MutableLiveData<Float> getUserRating(String userID) {
        database.getUserRating(userID, mUserRating::setValue);
        return mUserRating;
    }

    public MutableLiveData<ProductCategory> getCategory(String id) {
        database.getCategory(id, mProductCategory::setValue);
        return mProductCategory;
    }


    public MutableLiveData<List<Product>> getUserProducts(String userID) {
        database.getUserProducts(userID, mUserProducts::setValue);
        return mUserProducts;
    }

    public void addNewUserComment(Comment newComment) {
        database.addUserComment(newComment);
    }

    public MutableLiveData<List<Comment>> getUserComments(String userID) {
        database.getUserComments(userID, mUserComments::setValue);
        return mUserComments;
    }

    public void addProductToFavourites(String productID) {
        database.addProductToFavourites(productID, user.getUid());
    }

    public void removeProductFromFavourites(String productID) {
        database.removeProductFromFavourites(productID, user.getUid());
    }

    public void editUserInfo(User user) {
        database.editUserInfo(user);
    }

    public void reportAd(String productID) {
        database.reportAd(productID);
    }
}