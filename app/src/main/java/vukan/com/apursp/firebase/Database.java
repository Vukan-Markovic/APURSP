package vukan.com.apursp.firebase;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import vukan.com.apursp.callbacks.CategoriesCallback;
import vukan.com.apursp.callbacks.CategoryCallback;
import vukan.com.apursp.callbacks.CommentsCallback;
import vukan.com.apursp.callbacks.FavoriteCallback;
import vukan.com.apursp.callbacks.FavoritesCallback;
import vukan.com.apursp.callbacks.MessageCallback;
import vukan.com.apursp.callbacks.MessagesCallback;
import vukan.com.apursp.callbacks.ProductCallback;
import vukan.com.apursp.callbacks.ProductImagesCallback;
import vukan.com.apursp.callbacks.ProductsCallback;
import vukan.com.apursp.callbacks.RatingCallback;
import vukan.com.apursp.callbacks.UserCallback;
import vukan.com.apursp.models.Comment;
import vukan.com.apursp.models.Conv;
import vukan.com.apursp.models.FavoriteProduct;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductCategory;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.models.ReportProduct;
import vukan.com.apursp.models.ReportUser;
import vukan.com.apursp.models.User;

public class Database {
    private final FirebaseFirestore firestore;
    private List<Product> products;
    private List<ProductCategory> categories;
    private List<FavoriteProduct> favouritesProducts;
    private List<ProductImage> productImages;
    private List<Product> userProducts;
    private List<Message> userMessages;
    private List<Message> userallMessages;
    private List<Conv> allUserConv;
    private List<Comment> userComments;
    private List<String> listOfsenders;
    private List<String> listOfproductid;
    private final FirebaseUser firebaseUser;
    private final Storage storage;

    public Database() {
        firestore = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
        favouritesProducts = new ArrayList<>();
        productImages = new ArrayList<>();
        userProducts = new ArrayList<>();
        userMessages = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = new Storage();
    }

    public void deleteConversation(Conv conv) {
        for (Message m : conv.getMessages()) {
            firestore.collection("messages").whereEqualTo("productID", m.getProductID()).whereEqualTo("senderID", m.getSenderID()).whereEqualTo("receiverID", m.getReceiverID()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                        document.getReference().delete();
                }
            });
        }
    }

    public void deleteProductImage(String url) {
        firestore.collection("productsImages").whereEqualTo("imageUrl", url).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });
    }

    public void updateProfilePicture(Uri imageUrl) {
        storage.updateProfilePicture(firebaseUser.getUid(), imageUrl);
    }

    public void updateProfilePictureBitmap(Bitmap imageBitmap) {
        storage.updateProfilePictureBitmap(firebaseUser.getUid(), imageBitmap);
    }

    public void deleteUserData(String userID) {
        firestore.collection("products").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });

        firestore.collection("messages").whereEqualTo("receiverID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });

        firestore.collection("messages").whereEqualTo("senderID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });
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
                callback.onCallback(Objects.requireNonNull(task.getResult()).exists());
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
                firestore.collection("users").document(databaseUser.getUserID()).set(databaseUser, SetOptions.merge());
            }

            return null;
        });
    }

    public void sendMessage(Message m) {
        firestore.collection("messages").whereEqualTo("productID", m.getProductID()).whereEqualTo("senderID", m.getSenderID()).whereEqualTo("receiverID", m.getReceiverID()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (Objects.requireNonNull(task.getResult()).isEmpty()) {
                    Message emptyMessage = new Message();
                    emptyMessage.setReceiverID(m.getSenderID());
                    emptyMessage.setProductID(m.getProductID());
                    emptyMessage.setSenderID(m.getReceiverID());
                    emptyMessage.setDateTime(new Timestamp(new Date(1)));
                    emptyMessage.setContent("");
                    firestore.collection("messages").add(emptyMessage);
                }

                firestore.collection("messages").add(m);
            }
        });
    }

    public String addProduct(Product p, String productID) {
        Map<String, Object> product = new HashMap<>();
        DocumentReference newProductRef;

        if (productID.equals("0"))
            newProductRef = firestore.collection("products").document();
        else newProductRef = firestore.collection("products").document(productID);

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
        newProductRef.set(product, SetOptions.merge());
        return newProductRef.getId();
    }

    public void addProductImage(ProductImage pi) {
        if (pi.getImageUrl() != null)
            firestore.collection("productsImages").document(pi.getImageUrl()).set(pi, SetOptions.merge());
    }

    public void getUserMessages(String senderId, String receiverId, String productID, MessageCallback callback) {
        userMessages = new ArrayList<>();

        firestore.collection("messages").orderBy("dateTime").whereEqualTo("productID", productID).whereIn("senderID", Arrays.asList(receiverId, senderId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    if (Objects.requireNonNull(document.getString("senderID")).equals(receiverId)) {
                        if (!(Objects.requireNonNull(document.getString("receiverID")).equals(senderId)))
                            continue;
                    }

                    Message message = new Message();
                    message.setContent(document.getString("content"));
                    message.setDateTime(document.getTimestamp("dateTime"));
                    message.setSenderID(document.getString("senderID"));
                    message.setProductID(document.getString("productID"));
                    message.setReceiverID(document.getString("receiverID"));
                    userMessages.add(message);
                }

                callback.onCallback(userMessages);
            }
        });
    }

    public void getAllUserMessages(String user, MessagesCallback callback) {
        userallMessages = new ArrayList<>();
        allUserConv = new ArrayList<>();
        listOfsenders = new ArrayList<>();
        listOfproductid = new ArrayList<>();

        firestore.collection("messages").orderBy("dateTime").whereEqualTo("receiverID", user).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    String sender;
                    sender = document.getString("senderID");
                    if (!listOfsenders.contains(sender)) listOfsenders.add(sender);
                    String productid;
                    productid = document.getString("productID");
                    if (!listOfproductid.contains(productid)) listOfproductid.add(productid);
                }
            }

            firestore.collection("messages").orderBy("dateTime").get().addOnCompleteListener(taskk -> {
                if (taskk.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(taskk.getResult())) {
                        Message message = new Message();
                        message.setContent(document.getString("content"));
                        message.setDateTime(document.getTimestamp("dateTime"));
                        message.setSenderID(document.getString("senderID"));
                        message.setProductID(document.getString("productID"));
                        message.setReceiverID(document.getString("receiverID"));
                        userallMessages.add(message);
                    }
                }

                for (String prodid : listOfproductid) {
                    for (String sendid : listOfsenders) {
                        Conv jednakonverzacija = new Conv();

                        for (Message message : userallMessages) {
                            if (message.getProductID().equals(prodid) && ((message.getReceiverID().equals(sendid) && message.getSenderID().equals(user)) || (message.getSenderID().equals(sendid) && message.getReceiverID().equals(user))))
                                jednakonverzacija.getMessages().add(message);
                        }

                        if (jednakonverzacija.getMessages().size() > 0)
                            allUserConv.add(jednakonverzacija);
                    }
                }

                for (Conv c : allUserConv) {
                    String id = c.getMessages().get(0).getReceiverID();

                    if (id.equals(Objects.requireNonNull(firebaseUser).getUid()))
                        id = c.getMessages().get(0).getSenderID();

                    firestore.collection("users").whereEqualTo("userID", id).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task1.getResult())) {
                                c.setUserName(document.getString("username"));
                            }

                            firestore.collection("products").whereEqualTo("productID", c.getMessages().get(0).getProductID()).get().addOnCompleteListener(task2 -> {
                                if (task2.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task2.getResult())) {
                                        c.setProductName(document.getString("name"));
                                    }

                                    callback.onCallback(allUserConv);
                                }
                            });
                        }
                    });
                }
            });
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
                    product.setUserID(document.getString("userID"));
                    products.add(product);
                    callback.onCallback(products);
                }

                if (task.getResult().getDocuments().isEmpty()) callback.onCallback(products);
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

        if (filters[0] == null && filters[1] == null && filters[2] == null && filters[3] == null) {
            query = query.orderBy("datetime", Query.Direction.DESCENDING);
        }

        if (filters[0] != null && !filters[0].isEmpty())
            query = query.whereGreaterThanOrEqualTo("price", Double.valueOf(filters[0]));

        if (filters[1] != null && !filters[1].isEmpty()) {
            query = query.whereLessThanOrEqualTo("price", Double.valueOf(filters[1]));
        }

        if (filters[2] != null && !filters[2].isEmpty()) {
            if (filters[2].equals("opadajuce"))
                query = query.orderBy("price", Query.Direction.DESCENDING);
            else query = query.orderBy("price", Query.Direction.ASCENDING);
        }

        if (filters[3] != null && !filters[3].isEmpty())
            query = query.whereEqualTo("categoryID", filters[3]);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Product product = new Product();
                    product.setName(document.getString("name"));
                    product.setHomePhotoUrl(document.getString("homePhotoUrl"));
                    product.setProductID(document.getString("productID"));
                    product.setUserID(document.getString("userID"));
                    products.add(product);
                    callback.onCallback(products);
                }

                if (task.getResult().getDocuments().isEmpty()) callback.onCallback(products);
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
                    product.setHomePhotoUrl(document.getString("homePhotoUrl"));
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

    public void addUserComment(Comment newComment) {
        Map<String, Object> comments = new HashMap<>();
        DocumentReference newCommentRef = firestore.collection("comments").document();
        comments.put("fromUserID", newComment.getFromUserID());
        comments.put("toUserID", newComment.getToUserID());
        comments.put("comment", newComment.getComment());
        comments.put("grade", newComment.getGrade());
        newCommentRef.set(comments);
    }

    public void getUserComments(String userID, CommentsCallback callback) {
        userComments = new ArrayList<>();

        firestore.collection("comments").whereEqualTo("toUserID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Comment comment = new Comment();
                    comment.setToUserID(userID);
                    comment.setFromUserID(document.getString("fromUserID"));
                    comment.setComment(document.getString("comment"));
                    comment.setGrade(Float.valueOf(String.valueOf(document.getDouble("grade"))));
                    userComments.add(comment);
                }

                callback.onCallback(userComments);
            }
        });
    }

    public void getUserRating(String userID, RatingCallback callback) {
        ArrayList<Double> sums = new ArrayList<>();

        firestore.collection("comments").whereEqualTo("toUserID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Double value = document.getDouble("grade");
                    sums.add(value);
                }
            }

            int count = 0;
            double sum = 0.0;

            while (sums.size() > count) {
                sum = sum + sums.get(count);
                count++;
            }

            if (count == 0) callback.onCallback((float) 0);
            float grade = (float) sum / count;
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
        Objects.requireNonNull(firebaseUser).updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getUsername())
                .build());

        firestore.collection("users").document(user.getUserID()).update("phone", user.getPhone(), "username", user.getUsername(), "location", user.getLocation());
    }

    public void reportAd(String productID) {
        ReportProduct reportProduct = new ReportProduct();
        reportProduct.setUserID(firebaseUser.getUid());
        reportProduct.setProductID(productID);
        firestore.collection("reportsProducts").document().set(reportProduct, SetOptions.merge());
    }

    public void reportUser(String userID) {
        ReportUser reportUser = new ReportUser();
        reportUser.setReporterUserID(firebaseUser.getUid());
        reportUser.setReportedUserID(userID);
        firestore.collection("reportsUsers").document().set(reportUser, SetOptions.merge());

        firestore.collection("messages").whereEqualTo("senderID", reportUser.getReportedUserID()).whereEqualTo("receiverID", reportUser.getReporterUserID()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });

        firestore.collection("messages").whereEqualTo("senderID", reportUser.getReporterUserID()).whereEqualTo("receiverID", reportUser.getReportedUserID()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });

        firestore.collection("comments").whereEqualTo("fromUserID", reportUser.getReporterUserID()).whereEqualTo("toUserID", reportUser.getReportedUserID()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });

        firestore.collection("messages").whereEqualTo("fromUserID", reportUser.getReportedUserID()).whereEqualTo("toUserID", reportUser.getReporterUserID()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    document.getReference().delete();
            }
        });

        firestore.collection("products").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    firestore.collection("favouriteProducts").whereEqualTo("userID", firebaseUser.getUid()).whereEqualTo("productID", document.getString("productID")).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            for (QueryDocumentSnapshot document1 : Objects.requireNonNull(task1.getResult()))
                                document1.getReference().delete();
                        }
                    });
                }
            }
        });
    }
}