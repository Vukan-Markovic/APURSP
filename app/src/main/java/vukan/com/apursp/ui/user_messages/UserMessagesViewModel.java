package vukan.com.apursp.ui.user_messages;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import vukan.com.apursp.models.Conv;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.repository.Repository;

public class UserMessagesViewModel extends ViewModel {
  private Repository repository;
  private MutableLiveData<String> mText;

    public UserMessagesViewModel() { repository = new Repository();
        mText = new MutableLiveData<>();
        mText.setValue("Poruke");
    }

  MutableLiveData<List<Conv>> getAllUserMessages(String sender) {
    return repository.getAllUserMessages(sender);
  }

//  public List<Conv> getAllUserMessages(String sender) {
//    return repository.getAllUserMessages(sender);
//  }


  LiveData<String> getText() {
        return mText;
    }
}
