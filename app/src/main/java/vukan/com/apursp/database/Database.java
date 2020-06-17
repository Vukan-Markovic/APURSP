package vukan.com.apursp.database;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vukan.com.apursp.callbacks.ProductCallback;
import vukan.com.apursp.callbacks.ProductImageCallback;
import vukan.com.apursp.callbacks.ProductImagesCallback;
import vukan.com.apursp.callbacks.ProductsCallback;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;

public class Database {
    private FirebaseFirestore firestore;
    private List<Product> products;
    private List<ProductImage> productImages;

    public Database() {
        firestore = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
    }

    public void getProducts(ProductsCallback callback) {
        products = new ArrayList<>();

        firestore.collection("products").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Product product = new Product();
                    product.setName(document.getString("name"));
                    product.setHomePhotoUrl(document.getString("homePhotoUrl"));
                    product.setProductID(document.getString("productID"));
                    products.add(product);
                }

                callback.onCallback(products);
            }
        });
    }

    public void getProductImages(String id, ProductImagesCallback callback) {
        productImages = new ArrayList<>();

        firestore.collection("productsImages").whereEqualTo("productID", id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(document.getString("imageUrl"));
                    productImages.add(productImage);
                }

                callback.onCallback(productImages);
            }
        });
    }

    public void getProductDetails(String id, ProductCallback callback) {
        firestore.collection("products").whereEqualTo("productID", id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Product product = new Product();
                    product.setCategoryID(document.getString("categoryID"));
                    product.setDescription(document.getString("description"));
                    product.setName(document.getString("name"));
                    product.setPrice(document.getDouble("price"));
                    product.setProductID(document.getString("productID"));
                    product.setDateTime(document.getTimestamp("datetime"));
                    callback.onCallback(product);
                }
            }
        });
    }
}