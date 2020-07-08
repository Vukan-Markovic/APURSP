package vukan.com.apursp.ui.mojioglasi;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Comment;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.User;
import vukan.com.apursp.repository.Repository;

public class MojioglasiViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<User> mUser;
    private MutableLiveData<Float> mUserRating;
    private MutableLiveData<List<Product>> mProduct;
    private MutableLiveData<List<Comment>> mComments;

    public MojioglasiViewModel() {
        repository = new Repository();
        mUser = new MutableLiveData<>();
        mProduct = new MutableLiveData<>();
        mUserRating = new MutableLiveData<>();
        mComments = new MutableLiveData<>();
    }

    //fix me
    public MutableLiveData<User> getUser(String userID) {
        mUser = repository.getUser(userID);
        return mUser;
    }

    public MutableLiveData<Float> getUserRating(String userID) {
        mUserRating = repository.getUserRating(userID);
        return mUserRating;
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
