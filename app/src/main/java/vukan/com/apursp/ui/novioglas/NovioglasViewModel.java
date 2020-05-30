package vukan.com.apursp.ui.novioglas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NovioglasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NovioglasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is novioglas fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}
