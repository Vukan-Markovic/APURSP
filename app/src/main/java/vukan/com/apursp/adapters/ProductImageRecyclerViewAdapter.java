package vukan.com.apursp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.firebase.Storage;
import vukan.com.apursp.models.ProductImage;
import vukan.com.apursp.ui.product.ProductFragmentDirections;

public class ProductImageRecyclerViewAdapter extends SliderViewAdapter<ProductImageRecyclerViewAdapter.ProductViewHolder> {
    private Storage storage;
    private List<ProductImage> products;
    final private ListItemClickListener mOnClickListener;

    public ProductImageRecyclerViewAdapter(List<ProductImage> products, ListItemClickListener listener) {
        this.products = products;
        storage = new Storage();
        mOnClickListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(view -> {
            ProductFragmentDirections.ProizvodToSlikaFragmentAction action = ProductFragmentDirections.proizvodToSlikaFragmentAction();
            action.setImageUrl(products.get(position).getImageUrl());
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public int getCount() {
        return products.size();
    }

    class ProductViewHolder extends SliderViewAdapter.ViewHolder implements View.OnClickListener {
        ImageView productImage;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.slika_proizvoda);
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
            GlideApp.with(productImage.getContext()).load(storage.getProductImage(products.get(index).getImageUrl())).into(productImage);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick();
        }
    }

    public interface ListItemClickListener {
        void onListItemClick();
    }
}