package vukan.com.apursp.callbacks;

import java.util.List;

import vukan.com.apursp.models.ProductImage;

public interface ProductImagesCallback {
    void onCallback(List<ProductImage> productImages);
}