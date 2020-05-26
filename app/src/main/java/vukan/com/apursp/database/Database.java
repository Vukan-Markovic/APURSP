package vukan.com.apursp.database;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import vukan.com.apursp.MyCallback;
import vukan.com.apursp.models.Product;

public class Database {
    private FirebaseFirestore firestore;
    private List<Product> products;

    public Database() {
        firestore = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
    }

    public void getProducts(MyCallback myCallback) {
        products = new ArrayList<>();

        firestore.collection("products").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Product product = new Product();
                    product.setCategoryID(document.getString("categoryID"));
                    product.setDescription(document.getString("description"));
                    product.setName(document.getString("name"));
                    product.setPrice(document.getDouble("price"));
                    product.setProductID(document.getString("productID"));
                    products.add(product);
                }
                myCallback.onCallback(products);
            }
        });
    }
}