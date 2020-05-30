package vukan.com.apursp.ui.poruke;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PorukeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PorukeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is poruka fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}
