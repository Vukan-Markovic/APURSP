package vukan.com.apursp.ui.novioglasprozor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NovioglasprozorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NovioglasprozorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Novioglasprozor fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}
