package vukan.com.apursp.ui.obavestenja;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ObavestenjaViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ObavestenjaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is obavestenje fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}
