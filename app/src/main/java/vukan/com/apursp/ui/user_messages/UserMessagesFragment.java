package vukan.com.apursp.ui.user_messages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vukan.com.apursp.R;
import vukan.com.apursp.adapters.MessageAdapter;
import vukan.com.apursp.adapters.MyAdapter;
import vukan.com.apursp.models.Conv;
import vukan.com.apursp.models.Message;

public class UserMessagesFragment extends Fragment implements MessageAdapter.ListItemClickListener {


  RecyclerView recyclerView;
  ArrayList<Conv> list;
  MyAdapter adapter;

  public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      list = new ArrayList<>();

      UserMessagesViewModel userMessagesViewModel = new ViewModelProvider(this).get(UserMessagesViewModel.class);

      View root = inflater.inflate(R.layout.fragment_user_messages, container, false);

     // recyclerView = view.findViewById(R.id.recview);
      FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();



//umesto ovog id treba da ide trenutni user id, al morala sam da je zakucam jer nemam u bazi torke da testiram obrnuto
    userMessagesViewModel.getAllUserMessages("dvKPe7wojFQchonZ6GpNZJ1KHgF3").observe(getViewLifecycleOwner(), conv -> {
        for (Conv v : conv) {
          list.add(v);
        }

//        adapter=new MyAdapter(list,getContext());
//        recyclerView.setAdapter(adapter);

      LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
      });


      final TextView textView = root.findViewById(R.id.text_obavestenja);
        userMessagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.app_name));
    }

  @Override
  public void onListItemClick(String imageUrl) {

  }
}
