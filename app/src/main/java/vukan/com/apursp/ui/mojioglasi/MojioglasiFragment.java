package vukan.com.apursp.ui.mojioglasi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ProductRecyclerViewAdapter;
import vukan.com.apursp.models.User;

public class MojioglasiFragment extends Fragment implements ProductRecyclerViewAdapter.ListItemClickListener {

    private String userID="0";
    private TextView username;
    private TextView location;
    private TextView phone;
    private ImageView avatar;
    private ImageButton edit;
    private EditText edit_username;
    private EditText edit_location;
    private EditText edit_phone;
    private ConstraintLayout edit_layout;
    private Button save;
    private Button cancel;
    private User current_user;

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
        avatar=view.findViewById(R.id.imageAvatar);
        edit=view.findViewById(R.id.editButton);
        edit_username=view.findViewById(R.id.username_input);
        edit_location=view.findViewById(R.id.location_input);
        edit_phone=view.findViewById(R.id.phone_input);
        edit_layout=view.findViewById(R.id.edit_layout);
        save=view.findViewById(R.id.buttonSave);
        cancel=view.findViewById(R.id.buttonCancel);

        mojioglasiViewModel.getUser().observe(getViewLifecycleOwner(),user -> {
            username.setText(user.getUsername());
            location.setText(user.getLocation());
            phone.setText(user.getPhone());
            userID=user.getUserID();
            GlideApp.with(avatar.getContext())
                    .load(user.getImageUrl())
                    .into(avatar);
            current_user=user;
        });

        mojioglasiViewModel.getUserProducts(fire_user.getUid()).observe(getViewLifecycleOwner(), products -> {
            adapter=new ProductRecyclerViewAdapter(products,this);
            recyclerView.setAdapter(adapter);
        });

        edit.setOnClickListener(view1 -> {
            recyclerView.setVisibility(View.INVISIBLE);
            location.setVisibility(View.INVISIBLE);
            username.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);

            edit_username.setText(current_user.getUsername());
            edit_location.setText(current_user.getLocation());
            edit_phone.setText(current_user.getPhone());


            edit_layout.setVisibility(View.VISIBLE);
            edit_username.setVisibility(View.VISIBLE);
            edit_location.setVisibility(View.VISIBLE);
            edit_phone.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        });
        cancel.setOnClickListener(view1 -> {
            edit_username.setVisibility(View.INVISIBLE);
            edit_location.setVisibility(View.INVISIBLE);
            edit_phone.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);

            recyclerView.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        });
        save.setOnClickListener(view1 -> {

            mojioglasiViewModel.editUserInfo(new User(current_user.getUserID(),edit_username.getText().toString(),edit_location.getText().toString(),current_user.getGrade(),edit_phone.getText().toString(),current_user.getImageUrl()));
            edit_username.setVisibility(View.INVISIBLE);
            edit_location.setVisibility(View.INVISIBLE);
            edit_phone.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);

            recyclerView.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            mojioglasiViewModel.getUser().observe(getViewLifecycleOwner(),user -> {
                username.setText(user.getUsername());
                location.setText(user.getLocation());
                phone.setText(user.getPhone());
                userID=user.getUserID();
                GlideApp.with(avatar.getContext())
                        .load(user.getImageUrl())
                        .into(avatar);
                current_user=user;
            });
        });

    }

    @Override
    public void onListItemClick(String productID) {
    }

    @Override
    public void onStarItemClick(String productID, View view) {

    }
}
