package vukan.com.apursp.ui.my_ads;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Comment;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.User;
import vukan.com.apursp.repository.Repository;

public class MyAdsViewModel extends ViewModel {
    private final Repository repository;
    private MutableLiveData<User> mUser;
    private MutableLiveData<Float> mUserRating;
    private MutableLiveData<List<Product>> mProduct;
    private MutableLiveData<List<Comment>> mComments;

    public MyAdsViewModel() {
        repository = new Repository();
        mUser = new MutableLiveData<>();
        mProduct = new MutableLiveData<>();
        mUserRating = new MutableLiveData<>();
        mComments = new MutableLiveData<>();
    }

    public void reportUser(String userID) {
        repository.reportUser(userID);
    }

    public void updateProfilePicture(Uri imageUrl) {
        repository.updateProfilePicture(imageUrl);
    }

    public void updateProfilePictureBitmap(Bitmap imageBitmap) {
        repository.updateProfilePictureBitmap(imageBitmap);
    }

    public MutableLiveData<User> getUser(String userID) {
        mUser = repository.getUser(userID);
        return mUser;
    }

    public MutableLiveData<Float> getUserRating(String userID) {
        mUserRating = repository.getUserRating(userID);
        return mUserRating;
    }

    public void deleteUserData(String userID) {
        repository.deleteUserData(userID);
    }

    public void addNewUserComment(Comment newComment) {
        repository.addNewUserComment(newComment);
    }

    public void addUser() {
        repository.addUser();
    }

    MutableLiveData<List<Product>> getUserProducts(String userID) {
        mProduct = repository.getUserProducts(userID);
        return mProduct;
    }

    MutableLiveData<List<Comment>> getUserComments(String userID) {
        mComments = repository.getUserComments(userID);
        return mComments;
    }

    public void editUserInfo(User user) {
        repository.editUserInfo(user);
    }
}