package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.FavoriteProduct;

public interface FavoritesCallback {
    void onCallback(List<FavoriteProduct> products);
}