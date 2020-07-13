package vukan.com.apursp.ui.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.MessageAdapter;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.ui.my_ads.MyAdsViewModel;

public class MessagesFragment extends Fragment {
    MessageAdapter adapter;
    private TextView text;
    private String productID = "0";
    private String receiverID = "0";
    List<Message> messages = new ArrayList<>();
    String userName = "";
    String image = "";
    String id = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.messages));
        MessagesViewModel messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        MyAdsViewModel myAdsViewModel = new ViewModelProvider(this).get(MyAdsViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.list_of_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FloatingActionButton sendMess = view.findViewById(R.id.btnSend);
        text = view.findViewById(R.id.messageField);
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        adapter = new MessageAdapter(messages);
        adapter.setMessages(this.messages, userName, image);
        recyclerView.setAdapter(adapter);
        Animation mAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade);
        mAnimation.setDuration(150);

        if (getArguments() != null) {
            if (MessagesFragmentArgs.fromBundle(getArguments()).getMessages() != null) {
                Message[] messages = MessagesFragmentArgs.fromBundle(getArguments()).getMessages();
                Collections.addAll(this.messages, Objects.requireNonNull(messages));
                if (!this.messages.isEmpty()) this.messages.remove(0);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);

                for (Message m : this.messages) {
                    if (m.getReceiverID().equals(Objects.requireNonNull(fire_user).getUid())) {
                        myAdsViewModel.getUser(m.getSenderID()).observe(getViewLifecycleOwner(), user1 -> {
                            this.userName = user1.getUsername();
                            this.image = user1.getImageUrl();
                            adapter.setMessages(this.messages, userName, image);
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                        });
                    }
                }
            }

            productID = MessagesFragmentArgs.fromBundle(getArguments()).getProductId();

            if (!productID.equals("0")) {
                messagesViewModel.getProductDetails(productID).observe(getViewLifecycleOwner(), product -> {
                    receiverID = product.getUserID();

                    messagesViewModel.getmMessages(Objects.requireNonNull(fire_user).getUid(), product.getUserID(), productID).observe(getViewLifecycleOwner(), message -> {
                        this.messages = message;
                        if (!this.messages.isEmpty()) this.messages.remove(0);

                        for (Message m : this.messages) {
                            if (m.getReceiverID().equals(Objects.requireNonNull(fire_user).getUid())) {
                                myAdsViewModel.getUser(m.getSenderID()).observe(getViewLifecycleOwner(), user1 -> {
                                    this.userName = user1.getUsername();
                                    this.image = user1.getImageUrl();
                                    adapter.setMessages(this.messages, userName, image);
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                                });
                            }
                        }

                        adapter.setMessages(this.messages, userName, image);
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    });
                });
            }
        }

        sendMess.setOnClickListener(v -> {
            if (!text.getText().toString().trim().isEmpty()) {
                v.startAnimation(mAnimation);
                Message newMessage = new Message();
                newMessage.setContent(text.getText().toString());
                newMessage.setSenderID(Objects.requireNonNull(fire_user).getUid());
                newMessage.setDateTime(new Timestamp(new Date()));
                text.setText("");

                if (productID.equals("0")) {
                    id = messages.get(0).getReceiverID();
                    if (id.equals(fire_user.getUid())) id = messages.get(0).getSenderID();
                    newMessage.setReceiverID(id);
                    newMessage.setProductID(messages.get(0).getProductID());
                } else {
                    newMessage.setReceiverID(receiverID);
                    newMessage.setProductID(productID);
                }

                adapter.addMessage(newMessage);
                messagesViewModel.sendMessage(newMessage);
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }
}