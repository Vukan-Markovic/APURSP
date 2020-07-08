package vukan.com.apursp.ui.poruke;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.repository.Repository;

public class PorukeViewModel extends ViewModel {
    private Repository repository;

    public PorukeViewModel() {
        repository = new Repository();
    }

    MutableLiveData<List<Message>> getmMessages(String sender, String receiver) {
        return repository.getUserMessages(sender, receiver);
    }

    void sendMessage(Message m) {
        repository.sendMessage(m);
    }

    MutableLiveData<Product> getProductDetails(String id) {
        return repository.getProductDetails(id);
    }
}
