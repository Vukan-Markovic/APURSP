package vukan.com.apursp.ui.user_messages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserMessagesViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public UserMessagesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Poruke");
    }

    LiveData<String> getText() {
        return mText;
    }
}