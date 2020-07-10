package vukan.com.apursp.ui.messages;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Conv;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.User;
import vukan.com.apursp.repository.Repository;

public class MessagesViewModel extends ViewModel {
    private Repository repository;
  private MutableLiveData<User> mProductUser;


  public MessagesViewModel() {
        repository = new Repository();
    mProductUser = new MutableLiveData<>();

  }

    MutableLiveData<List<Message>> getmMessages( String sender, String receiver,String productID) {
        return repository.getUserMessages(sender, receiver, productID);
    }


  MutableLiveData<User> getProductUser(String id) {
    mProductUser = repository.getProductUser(id);
    return mProductUser;
  }

//  public List<Conv> getAllUserMessages(String sender) {
//    return repository.getAllUserMessages(sender);
//  }



    void sendMessage(Message m) {
        repository.sendMessage(m);
    }

    MutableLiveData<Product> getProductDetails(String id) {
        return repository.getProductDetails(id);
    }
}
