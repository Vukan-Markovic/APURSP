package vukan.com.apursp.firebase;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import vukan.com.apursp.callbacks.CategoriesCallback;
import vukan.com.apursp.callbacks.CategoryCallback;
import vukan.com.apursp.callbacks.FavoriteCallback;
import vukan.com.apursp.callbacks.FavoritesCallback;
import vukan.com.apursp.callbacks.CommentsCallback;
import vukan.com.apursp.callbacks.MessageCallback;
import vukan.com.apursp.callbacks.ProductCallback;
import vukan.com.apursp.callbacks.ProductImagesCallback;
import vukan.com.apursp.callbacks.ProductsCallback;
import vukan.com.apursp.callbacks.RatingCallback;
import vukan.com.apursp.callbacks.UserCallback;
import vukan.com.apursp.models.FavoriteProduct;
import vukan.com.apursp.models.Comment;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductCategory;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.models.User;

import static java.lang.Float.parseFloat;

public class Database {
    private FirebaseFirestore firestore;
    private List<Product> products;
    private List<ProductCategory> categories;
    private List<FavoriteProduct> favouritesProducts;
    private List<ProductImage> productImages;
    private List<Product> userProducts;
    private List<Message> userMessages;
    private List<Comment>userComments;

    public Database() {
        firestore = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
        favouritesProducts = new ArrayList<>();
        productImages = new ArrayList<>();
        userProducts = new ArrayList<>();
        userMessages = new ArrayList<>();
    }

    public void deleteProduct(String id) {
        firestore.collection("products").document(id).delete();
    }

    public void getProductUser(String id, UserCallback callback) {
        firestore.collection("users").whereEqualTo("userID", id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    User user = new User();
                    user.setUsername(document.getString("username"));
                    user.setImageUrl(document.getString("imageUrl"));
                    user.setPhone(document.getString("phone"));
                    user.setLocation(document.getString("location"));
                    callback.onCallback(user);
                }
            }
        });
    }

    public void isFavourite(String productID, String userID, FavoriteCallback callback) {
        firestore.collection("favouriteProducts").document(productID + userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (Objects.requireNonNull(task.getResult()).exists()) callback.onCallback(true);
                else callback.onCallback(false);
            }
        });
    }

    public void addUser(FirebaseUser user) {
        final DocumentReference doc = firestore.collection("users").document(user.getUid());
        firestore.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentSnapshot snapshot = transaction.get(doc);

            if (!snapshot.exists()) {
                User databaseUser = new User();
                databaseUser.setUsername(user.getDisplayName());
                databaseUser.setUserID(user.getUid());
                databaseUser.setImageUrl(Objects.requireNonNull(user.getPhotoUrl()).toString());
                databaseUser.setGrade(0.0f);
                firestore.collection("users").document(databaseUser.getUserID()).set(databaseUser, SetOptions.merge());
            }

            return null;
        });
    }

    public void sendMessage(Message m) {
        firestore.collection("messages").add(m);
    }

    public String addProduct(Product p) {
        Map<String, Object> product = new HashMap<>();
        DocumentReference newProductRef = firestore.collection("products").document();

        product.put("categoryID", p.getCategoryID());
        product.put("datetime", p.getDatetime());
        product.put("description", p.getDescription());
        product.put("homePhotoUrl", p.getHomePhotoUrl());
        product.put("name", p.getName());
        product.put("price", p.getPrice());
        product.put("productID", newProductRef.getId());
        product.put("seen", p.getSeen());
        product.put("userID", p.getUserID());
        product.put("currency", p.getCurrency());
        product.put("fixPrice", p.getFixPrice());

        newProductRef.set(product);
        return newProductRef.getId();
    }

    public void addProductImage(ProductImage pi) {
        firestore.collection("productsImages").document(pi.getImageUrl()).set(pi);
    }

    public void getUserMessages(String senderId, String receiverId, MessageCallback callback) {
        userMessages = new ArrayList<>();

        firestore.collection("messages").whereEqualTo("senderID", senderId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    System.out.println(document.getString("receiverID"));
                    if (!Objects.requireNonNull(document.getString("receiverID")).equals(receiverId))
                        continue;

                    Message message = new Message();
                    message.setContent(document.getString("content"));
                    message.setSenderID(document.getString("senderID"));
                    userMessages.add(message);
                }

                callback.onCallback(userMessages);
            }
        });
    }

    public void getProducts(ProductsCallback callback) {
        products = new ArrayList<>();

        firestore.collection("products").orderBy("datetime", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Product product = new Product();
                    product.setName(document.getString("name"));
                    product.setDescription(document.getString("description"));
                    product.setHomePhotoUrl(document.getString("homePhotoUrl"));
                    product.setProductID(document.getString("productID"));
                    products.add(product);
                }

                callback.onCallback(products);
            }
        });
    }

    public void getCategories(CategoriesCallback callback) {
        categories = new ArrayList<>();

        firestore.collection("productCategories").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    ProductCategory category = new ProductCategory();
                    category.setName(document.getString("name"));
                    category.setCategoryID(document.getString("categoryID"));
                    categories.add(category);
                }

                callback.onCallback(categories);
            }
        });
    }

    public void getCategory(String categoryID, CategoryCallback callback) {
        firestore.collection("productCategories").whereEqualTo("categoryID", categoryID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    ProductCategory category = new ProductCategory();
                    category.setName(document.getString("name"));
                    category.setCategoryID(document.getString("categoryID"));
                    callback.onCallback(category);
                }
            }
        });
    }

    public void getProduct(String id, ProductCallback callback) {
        firestore.collection("products").whereEqualTo("productID", id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Product product = new Product();
                    product.setName(document.getString("name"));
                    product.setProductID(document.getString("productID"));
                    product.setHomePhotoUrl(document.getString("homePhotoUrl"));
                    callback.onCallback(product);
                }
            }
        });
    }

    public void getFavouriteProducts(String userID, FavoritesCallback callback) {
        favouritesProducts = new ArrayList<>();

        firestore.collection("favouriteProducts").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    FavoriteProduct product = new FavoriteProduct();
                    product.setUserID(userID);
                    product.setProductID(document.getString("productID"));
                    favouritesProducts.add(product);
                }

                callback.onCallback(favouritesProducts);
            }
        });
    }

    public void filterProducts(String[] filters, ProductsCallback callback) {
        products = new ArrayList<>();
        Query query = firestore.collection("products");

        if (!filters[0].isEmpty())
            query = query.whereGreaterThanOrEqualTo("price", Double.valueOf(filters[0]));

        if (!filters[1].isEmpty())
            query = query.whereLessThanOrEqualTo("price", Double.valueOf(filters[1]));

        if (filters[2] != null && !filters[2].isEmpty()) {
            Timestamp date = new Timestamp(Long.parseLong(filters[2]), 0);
            query = query.whereGreaterThanOrEqualTo("datetime", date);
        }

        if (filters[3] != null && !filters[3].isEmpty()) {
            Timestamp date = new Timestamp(Long.parseLong(filters[3]), 0);
            query = query.whereLessThanOrEqualTo("datetime", date);
        }

        if (filters[4] != null && !filters[4].isEmpty()) {
            if (filters[4].equals("opadajuce"))
                query = query.orderBy("price", Query.Direction.DESCENDING);
            else query = query.orderBy("price", Query.Direction.ASCENDING);
        }

        if (filters[5] != null && !filters[5].isEmpty() && !filters[5].equals("Sve")) {
            query = query.whereEqualTo("location", filters[5]);
        }

        if (filters[6] != null && !filters[6].isEmpty()) {
            query = query.whereEqualTo("categoryID", filters[6]);
        }

        query.get().addOnCompleteListener(task -> {
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

    public void incrementCounter(String productID, String id) {
        final DocumentReference doc = firestore.collection("products").document(productID);
        firestore.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentSnapshot snapshot = transaction.get(doc);
            if (!Objects.requireNonNull(snapshot.getString("userID")).equals(id)) {
                Long seen = Objects.requireNonNull(snapshot.getLong("seen")) + 1;
                transaction.update(doc, "seen", seen);
            }
            return null;
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
                    product.setDatetime(document.getTimestamp("datetime"));
                    product.setSeen(document.getLong("seen"));
                    product.setUserID(document.getString("userID"));
                    product.setCurrency(document.getString("currency"));
                    product.setFixPrice(document.getBoolean("fixPrice"));
                    callback.onCallback(product);
                }
            }
        });
    }

    public void getUserName(String id, UserCallback callback) {
        firestore.collection("users").whereEqualTo("userID", id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    User user = new User();
                    user.setUserID(document.getString("userID"));
                    user.setUsername(document.getString("username"));
                    user.setLocation(document.getString("location"));
                    user.setPhone(document.getString("phone"));
                    callback.onCallback(user);
                }
            }
        });
    }

    public void getUser(String userID, UserCallback callback) {
        if (userID != null) {
            firestore.collection("users").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        User user = new User();
                        user.setUserID(document.getString("userID"));
                        user.setUsername(document.getString("username"));
                        user.setLocation(document.getString("location"));
                        user.setPhone(document.getString("phone"));
                        user.setImageUrl(document.getString("imageUrl"));
                        callback.onCallback(user);
                    }
                }
            });
        }
    }

    public void getUserProducts(String userID, ProductsCallback callback) {
        userProducts = new ArrayList<>();

        firestore.collection("products").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Product product = new Product();
                    product.setName(document.getString("name"));
                    product.setHomePhotoUrl(document.getString("homePhotoUrl"));
                    product.setProductID(document.getString("productID"));
                    product.setDatetime(document.getTimestamp("datetime"));
                    userProducts.add(product);
                }

                callback.onCallback(userProducts);
            }
        });
    }

    public void addUserComment(Comment newComment){
        Map<String, Object> comments = new HashMap<>();
        DocumentReference newCommentRef = firestore.collection("comments").document();

        comments.put("fromUserID", newComment.getFromUserID());
        comments.put("toUserID", newComment.getToUserID());
        comments.put("comment", newComment.getComment());
        comments.put("grade", newComment.getGrade());

        newCommentRef.set(comments);
    }

    public void getUserComments(String userID, CommentsCallback callback){

        userComments=new ArrayList<>();

        firestore.collection("comments").whereEqualTo("toUserID",userID).get().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                    Comment comment=new Comment();
                    comment.setToUserID(userID);
                    comment.setFromUserID(document.getString("fromUserID"));
                    comment.setComment(document.getString("comment"));
                    comment.setGrade(new Float(document.getDouble("grade")));
                    userComments.add(comment);
                }
                callback.onCallback(userComments);
            }
        });
    }

    public void getUserRating(String userID, RatingCallback callback){
        ArrayList<Double>sums=new ArrayList<>();
        firestore.collection("comments").whereEqualTo("toUserID",userID).get().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Double value=document.getDouble("grade");
                    sums.add(value);
                }
            }
            int count=0;
            Double sum=0.0;
            while(sums.size()>count){
                sum=sum+sums.get(count);
                count++;
            }
            float grade=sum.floatValue()/count;
            callback.onCallback(grade);
        });
    }

    public void addProductToFavourites(String productID, String userID) {
        FavoriteProduct product = new FavoriteProduct();
        product.setProductID(productID);
        product.setUserID(userID);
        firestore.collection("favouriteProducts").document(productID + userID).set(product);
    }

    public void removeProductFromFavourites(String productID, String userID) {
        firestore.collection("favouriteProducts").document(productID + userID).delete();
    }

    public void editUserInfo(User user) {
        firestore.collection("users").document(user.getUserID()).update("phone", user.getPhone(), "username", user.getUsername(), "location", user.getLocation());
    }
}
