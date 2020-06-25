package vukan.com.apursp.database;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
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