package vukan.com.apursp.adapters;

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

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {
    private Storage storage;
    private List<Product> products;
    final private ListItemClickListener mOnClickListener;

    public ProductRecyclerViewAdapter(List<Product> products, ListItemClickListener listener) {
        this.products = products;
        storage = new Storage();
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_item,
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

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName;
        ImageView productImage;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productImage = itemView.findViewById(R.id.product_image);
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
            productName.setText(products.get(index).getName());
            GlideApp.with(productImage.getContext())
                    .load(storage.getProductImage(products.get(index).getHomePhotoUrl()))
                    .into(productImage);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(products.get(getAdapterPosition()).getProductID());
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(String productID);
    }
}
