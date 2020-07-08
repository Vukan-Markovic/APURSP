package vukan.com.apursp.firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Storage {
    private StorageReference storage;

    public Storage() {
        storage = FirebaseStorage.getInstance().getReference();
    }

    public StorageReference getProductImage(String productID) {
        return storage.child(productID + ".jpg");
    }
}