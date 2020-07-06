package vukan.com.apursp.ui.novioglasprozor;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.User;
import vukan.com.apursp.repository.Repository;

public class NovioglasprozorViewModel extends ViewModel {

  private MutableLiveData<String> mText;
  private Repository repository;
  private MutableLiveData<User> mUser;

    public NovioglasprozorViewModel() {
      mText = new MutableLiveData<>();
      mText.setValue("This is Novioglasprozor fragment");
        repository = new Repository();
      }


      void addProduct(Product p){
        repository.addProduct(p);
      }

      MutableLiveData<User> getUserName(String id) {
        return repository.getUserName(id);
      }
  LiveData<String> getText() {
    return mText;
  }

}
