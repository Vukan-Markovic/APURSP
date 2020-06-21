package vukan.com.apursp.ui.mojioglasi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ProductRecyclerViewAdapter;

public class MojioglasiFragment extends Fragment implements ProductRecyclerViewAdapter.ListItemClickListener {

    private String userID="0";
    private TextView username;
    private TextView location;
    private TextView phone;

    private ProductRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mojioglasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MojioglasiViewModel mojioglasiViewModel = new ViewModelProvider(this).get(MojioglasiViewModel.class);

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        FirebaseUser fire_user= FirebaseAuth.getInstance().getCurrentUser();



        username=view.findViewById(R.id.username);
        location=view.findViewById(R.id.location);
        phone=view.findViewById(R.id.phone);

        mojioglasiViewModel.getUser().observe(getViewLifecycleOwner(),user -> {
            username.setText(user.getUsername());
            location.setText(user.getLocation());
            phone.setText(user.getPhone());
            userID=user.getUserID();
        });

        mojioglasiViewModel.getUserProducts(fire_user.getUid()).observe(getViewLifecycleOwner(), products -> {
            adapter=new ProductRecyclerViewAdapter(products,this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onListItemClick(String productID) {
    }

    @Override
    public void onStarItemClick(String productID, View view) {

    }
}
