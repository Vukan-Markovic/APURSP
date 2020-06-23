package vukan.com.apursp.ui.poruke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.adapters.MessageAdapter;
import vukan.com.apursp.database.Database;
import vukan.com.apursp.models.Message;
import vukan.com.apursp.models.User;
import vukan.com.apursp.ui.proizvod.ProizvodFragmentArgs;
import vukan.com.apursp.ui.proizvod.ProizvodViewModel;

public class PorukeFragment extends Fragment implements MessageAdapter.ListItemClickListener{

    ArrayAdapter<Message> adapter;
    private FloatingActionButton sendMess ;
    private TextView text ;
    private String productID = "0";
    private static String receiverId = "";
    private Database database = new Database();;
    ArrayList<Message> messages=new ArrayList<Message>();

    public void setReceiverId(String id){this.receiverId=id;}
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_poruke, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProizvodViewModel proizvodViewModel = new ViewModelProvider(this).get(ProizvodViewModel.class);
        PorukeViewModel porukeViewModel = ViewModelProviders.of(this).get(PorukeViewModel.class);
        ListView recyclerView = view.findViewById(R.id.list_of_messages);
        sendMess = view.findViewById(R.id.btnSend);
        text = view.findViewById(R.id.messageField);
        adapter =new ArrayAdapter<Message>(requireContext(),android.R.layout.simple_list_item_1,messages);

        recyclerView.setAdapter(adapter);
        FirebaseUser fire_user= FirebaseAuth.getInstance().getCurrentUser();
        TextView textView = view.findViewById(R.id.text_poruke);
        if (getArguments() != null)
            productID = PorukeFragmentArgs.fromBundle(getArguments()).getProductId();

        porukeViewModel.getProductDetails(productID).observe(getViewLifecycleOwner(), product -> {
            receiverId = product.getUserID();
        });
        porukeViewModel.getmMessages(fire_user.getUid(),receiverId).observe(getViewLifecycleOwner(),message -> {
            for (Message m: message ){
                MutableLiveData<User> u = porukeViewModel.getUserName(m.getSenderID());
                System.out.println("AAAA" + u.getValue());
               // m.setSenderID(u.getValue().getUsername());
                adapter.add(m);
            }
        });

        recyclerView.setAdapter(adapter);
        sendMess.setOnClickListener(v -> {
            Message newMessage = new Message();
            newMessage.setContent(text.getText().toString());
            //promeniti kada se stavi da se na klik dugmeta za oglas namesti primalac
            newMessage.setReceiverID(receiverId);
            Date date = new Date();
            newMessage.setDateTime(new Timestamp(date));
            newMessage.setSenderID(fire_user.getUid());
            porukeViewModel.sendMessage(newMessage);
            adapter.add(newMessage);
            recyclerView.setAdapter((ListAdapter) adapter);
        });
    }

    @Override
    public void onListItemClick(String imageUrl) {

    }
}
