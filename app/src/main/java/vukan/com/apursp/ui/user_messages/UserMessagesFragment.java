package vukan.com.apursp.ui.user_messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ConversationAdapter;

public class UserMessagesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ConversationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.conversations));
        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ConversationAdapter();
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        UserMessagesViewModel userMessagesViewModel = new ViewModelProvider(this).get(UserMessagesViewModel.class);

        userMessagesViewModel.getAllUserMessages(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).observe(getViewLifecycleOwner(), conv -> {
            adapter.setConversations(conv);
            recyclerView.setAdapter(adapter);
        });
    }
}