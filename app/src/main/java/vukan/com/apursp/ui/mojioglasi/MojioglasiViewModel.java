package vukan.com.apursp.ui.mojioglasi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MojioglasiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MojioglasiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mojioglasi fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}
