package vukan.com.apursp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import vukan.com.apursp.R;
import vukan.com.apursp.models.Conv;
import vukan.com.apursp.repository.Repository;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
    private List<Conv> conversations;
    private Repository repository;
    private SimpleDateFormat sfd;
    private LifecycleOwner lifecycleOwner;

    public ConversationAdapter(List<Conv> conversations, LifecycleOwner lifecycleOwner) {
        this.conversations = conversations;
        this.repository = new Repository();
        this.lifecycleOwner = lifecycleOwner;
        this.sfd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    }

    public void setConversations(List<Conv> conversations) {
        this.conversations = conversations;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView senderName;
        TextView adName;
        TextView lastMessage;
        TextView date;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.sender_name);
            adName = itemView.findViewById(R.id.ad_name);
            lastMessage = itemView.findViewById(R.id.last_message);
            date = itemView.findViewById(R.id.date);
        }

        void bind(int index) {
            repository.getProductDetails(conversations.get(index).getLista().get(0).getProductID()).observe(lifecycleOwner, product -> adName.setText(product.getName()));
            repository.getUser(conversations.get(index).getLista().get(0).getSenderID()).observe(lifecycleOwner, user -> senderName.setText(user.getUsername()));
            lastMessage.setText(conversations.get(index).getLista().get(conversations.get(index).getLista().size() - 1).getContent());
            date.setText(sfd.format(conversations.get(index).getLista().get(conversations.get(index).getLista().size() - 1).getDateTime().toDate()));
        }
    }
}