package vukan.com.apursp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.database.Storage;
import vukan.com.apursp.models.Product;
import vukan.com.apursp.models.ProductImage;

public class ProductImageRecyclerViewAdapter extends RecyclerView.Adapter<ProductImageRecyclerViewAdapter.ProductViewHolder> {
    private Storage storage;
    private List<ProductImage> products;

    public ProductImageRecyclerViewAdapter(List<ProductImage> products) {
        this.products = products;
        storage = new Storage();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder( LayoutInflater.from(parent.getContext()).inflate(
                R.layout.proizvod_slika,
                parent,
                false
        ));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage  = itemView.findViewById(R.id.slika_proizvoda);
        }

        void bind(int index) {
            GlideApp.with(productImage.getContext())
                    .load(storage.getProductImage(products.get(index).getImageUrl()))
                    .into(productImage);
        }
    }
}
