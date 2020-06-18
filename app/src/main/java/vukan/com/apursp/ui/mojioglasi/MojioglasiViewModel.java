package vukan.com.apursp.ui.mojioglasi;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.List;

import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.User;
import vukan.com.apursp.repository.Repository;

public class MojioglasiViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<User>mUser;
    private MutableLiveData<List<Product>>mProduct;

    public MojioglasiViewModel() {
        repository=new Repository();
        mUser=new MutableLiveData<>();
        mProduct=new MutableLiveData<>();
    }

    //fix me
    MutableLiveData<User>getUser(){
        mUser=repository.getUser();
        return  mUser;
    }
    MutableLiveData<List<Product>>getUserProducts(String userID){
        mProduct=repository.getUserProducts(userID);
        return mProduct;
    }
}
