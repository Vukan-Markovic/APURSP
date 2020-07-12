package vukan.com.apursp.ui.user_messages;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Conv;
import vukan.com.apursp.repository.Repository;

public class UserMessagesViewModel extends ViewModel {
    private Repository repository;

    public UserMessagesViewModel() {
        repository = new Repository();
    }

    MutableLiveData<List<Conv>> getAllUserMessages(String sender) {
        return repository.getAllUserMessages(sender);
    }
}