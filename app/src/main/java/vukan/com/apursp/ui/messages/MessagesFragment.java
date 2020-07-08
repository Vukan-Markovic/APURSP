package vukan.com.apursp.ui.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.MessageAdapter;
import vukan.com.apursp.models.Message;

public class MessagesFragment extends Fragment implements MessageAdapter.ListItemClickListener {
    ArrayAdapter<Message> adapter;
    private TextView text;
    private String productID = "0";
    private static String receiverId = "";
    ArrayList<Message> messages = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.app_name));
        MessagesViewModel messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        ListView recyclerView = view.findViewById(R.id.list_of_messages);
        FloatingActionButton sendMess = view.findViewById(R.id.btnSend);
        text = view.findViewById(R.id.messageField);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, messages);

        recyclerView.setAdapter(adapter);
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();

        if (getArguments() != null)
            productID = PorukeFragmentArgs.fromBundle(getArguments()).getProductId();
        System.out.println("Product: " + productID);

        messagesViewModel.getProductDetails(productID).observe(getViewLifecycleOwner(), product -> receiverId = product.getUserID());

        messagesViewModel.getmMessages(Objects.requireNonNull(fire_user).getUid(), receiverId).observe(getViewLifecycleOwner(), message -> {
            for (Message m : message) {
                m.setSenderID(fire_user.getDisplayName() + ": ");
                adapter.add(m);
            }
        });


        recyclerView.setAdapter(adapter);
        sendMess.setOnClickListener(v -> {

            Message newMessage = new Message();
            Message forAdapter = new Message();
            newMessage.setContent(text.getText().toString());

            //promeniti kada se stavi da se na klik dugmeta za oglas namesti primalac
            newMessage.setReceiverID(receiverId);
            Date date = new Date();
            newMessage.setDateTime(new Timestamp(date));
            forAdapter.setContent(text.getText().toString());
            //forAdapter.setSenderID("Poslato : "); STARA VERZIJA
            forAdapter.setSenderID(fire_user.getDisplayName() + ": ");
            adapter.add(forAdapter);
            newMessage.setSenderID(fire_user.getUid());
            text.setText("");
            messagesViewModel.sendMessage(newMessage);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onListItemClick(String imageUrl) {

    }
}
