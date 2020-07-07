package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.ProductCategory;

public interface CategoriesCallback {
    void onCallback(List<ProductCategory> categories);
}
