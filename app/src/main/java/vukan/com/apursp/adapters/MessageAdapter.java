package vukan.com.apursp.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.database.Storage;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.ProductImage;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private Storage storage;
    private List<Message> messages;
    final private MessageAdapter.ListItemClickListener mOnClickListener;

    public void setMessages(List<Message> m){
        this.messages=m;
    }
    public void sendMessage(Message m){this.messages.add(m);}

    public MessageAdapter(List<Message> messages, MessageAdapter.ListItemClickListener listener) {
        this.messages = messages;
        storage = new Storage();
        mOnClickListener = listener;
    }
    class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
               }

        @Override
        public void onClick(View v) {
           }
    }
    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public interface ListItemClickListener {
        void onListItemClick(String imageUrl);
    }
}
