package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.FavouriteProduct;

public interface FavouritesCallback {
    void onCallback(List<FavouriteProduct> products);
}
