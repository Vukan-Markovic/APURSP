package vukan.com.apursp.ui.user_messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ConversationAdapter;
import vukan.com.apursp.models.Conv;

public class UserMessagesFragment extends Fragment implements ConversationAdapter.ListItemClickListener {
    private RecyclerView recyclerView;
    private ConversationAdapter adapter;
    private List<Conv> conversations;
    private UserMessagesViewModel userMessagesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.conversations));
        this.conversations = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ConversationAdapter(this);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        userMessagesViewModel = new ViewModelProvider(this).get(UserMessagesViewModel.class);

        userMessagesViewModel.getAllUserMessages(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).observe(getViewLifecycleOwner(), conv -> {
            conversations = conv;
            adapter.setConversations(conversations);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onListItemClick(Conv conv, View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.inflate(R.menu.popup_menu_delete);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete_conversation) {
                Toast.makeText(requireActivity(), R.string.conversation_deleted, Toast.LENGTH_SHORT).show();
                userMessagesViewModel.deleteConversation(conv);
                conversations.remove(conv);
                adapter.setConversations(conversations);
                recyclerView.setAdapter(adapter);
            }

            return true;
        });

        popupMenu.show();
    }
}