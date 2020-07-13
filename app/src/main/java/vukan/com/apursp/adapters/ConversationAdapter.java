package vukan.com.apursp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vukan.com.apursp.R;
import vukan.com.apursp.models.Conv;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.ui.user_messages.UserMessagesFragmentDirections;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
    private List<Conv> conversations;
    private final SimpleDateFormat sfd;

    public ConversationAdapter() {
        this.conversations = new ArrayList<>();
        this.sfd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    }

    public void setConversations(List<Conv> conversations) {
        this.conversations = conversations;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(view -> Navigation.findNavController(view).navigate(UserMessagesFragmentDirections.obavestenjaToPorukeFragmentAction(conversations.get(position).getLista().toArray(new Message[0]))));
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {
        final TextView senderName;
        final TextView adName;
        final TextView lastMessage;
        final TextView date;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.sender_name);
            adName = itemView.findViewById(R.id.ad_name);
            lastMessage = itemView.findViewById(R.id.last_message);
            date = itemView.findViewById(R.id.date);
        }

        void bind(int index) {
            adName.setText(conversations.get(index).getProductName());
            senderName.setText(conversations.get(index).getUserName());
            lastMessage.setText(conversations.get(index).getLista().get(conversations.get(index).getLista().size() - 1).getContent());
            date.setText(sfd.format(conversations.get(index).getLista().get(conversations.get(index).getLista().size() - 1).getDateTime().toDate()));
        }
    }
}