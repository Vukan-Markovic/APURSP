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
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

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
        requireActivity().setTitle(getString(R.string.app_name));
        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ConversationAdapter(new ArrayList<>(), getViewLifecycleOwner());
        recyclerView.setAdapter(adapter);
        UserMessagesViewModel userMessagesViewModel = new ViewModelProvider(this).get(UserMessagesViewModel.class);
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();

        //umesto ovog id treba da ide trenutni user id, al morala sam da je zakucam jer nemam u bazi torke da testiram obrnuto
        userMessagesViewModel.getAllUserMessages("dvKPe7wojFQchonZ6GpNZJ1KHgF3").observe(getViewLifecycleOwner(), conv -> {
            adapter.setConversations(conv);
            recyclerView.setAdapter(adapter);
        });
    }
}