package vukan.com.apursp.ui.my_ads;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

import vukan.com.apursp.GlideApp;
import vukan.com.apursp.R;
import vukan.com.apursp.adapters.CommentsAdapter;
import vukan.com.apursp.adapters.ProductRecyclerViewAdapter;
import vukan.com.apursp.models.Comment;
import vukan.com.apursp.models.User;

public class MyAdsFragment extends Fragment implements ProductRecyclerViewAdapter.ListItemClickListener {
    private TextView username;
    private TextView location;
    private TextView phone;
    private ImageView avatar;
    private ImageButton edit;
    private ImageButton rate;
    private EditText edit_username;
    private EditText edit_location;
    private EditText edit_phone;
    private ConstraintLayout edit_layout;
    private Button save;
    private Button cancel;
    private User current_user;
    private RatingBar starGrade;
    private String userID = "";
    private ConstraintLayout comment_layout;
    private Button commentBtn;
    private EditText comment;
    private ProductRecyclerViewAdapter adapter;
    private CommentsAdapter adapter2;
    private RecyclerView recikler;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_ads, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(("My profile"));
        MyAdsViewModel myAdsViewModel = new ViewModelProvider(this).get(MyAdsViewModel.class);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        recikler = view.findViewById(R.id.recikler);
        recikler.setLayoutManager(layoutManager2);
        adapter2 = new CommentsAdapter(new ArrayList<>());
        recikler.setAdapter(adapter2);
        recyclerView.setVisibility(View.VISIBLE);
        recikler.setVisibility(View.INVISIBLE);

        if (getArguments() != null)
            userID = MyAdsFragmentArgs.fromBundle(getArguments()).getUserId();

        if (userID.equals("0")) {
            FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
            userID = Objects.requireNonNull(fire_user).getUid();
        }

        username = view.findViewById(R.id.username);
        location = view.findViewById(R.id.location);
        phone = view.findViewById(R.id.phone);
        avatar = view.findViewById(R.id.imageAvatar);
        edit = view.findViewById(R.id.editButton);
        edit_username = view.findViewById(R.id.username_input);
        edit_location = view.findViewById(R.id.location_input);
        edit_phone = view.findViewById(R.id.phone_input);
        edit_layout = view.findViewById(R.id.edit_layout);
        save = view.findViewById(R.id.buttonSave);
        rate = view.findViewById(R.id.rateButton);
        cancel = view.findViewById(R.id.buttonCancel);
        starGrade = view.findViewById(R.id.starGrades);
        comment_layout = view.findViewById(R.id.commentLayout);
        comment = view.findViewById(R.id.comment);
        commentBtn = view.findViewById(R.id.commentButton);

        myAdsViewModel.getUserRating(userID).observe(getViewLifecycleOwner(), rating -> {
            starGrade.setRating(rating);
            if (current_user != null)
                current_user.setGrade(rating);
        });

        myAdsViewModel.getUser(userID).observe(getViewLifecycleOwner(), user -> {
            username.setText(user.getUsername());
            location.setText(user.getLocation());
            phone.setText(user.getPhone());
            user.setUserID(user.getUserID());

            GlideApp.with(avatar.getContext())
                    .load(user.getImageUrl())
                    .into(avatar);

            if (userID.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                edit.setVisibility(View.VISIBLE);

            rate.setVisibility(View.VISIBLE);
            current_user = user;
        });

        myAdsViewModel.getUserProducts(userID).observe(getViewLifecycleOwner(), products -> {
            adapter = new ProductRecyclerViewAdapter(products, this);
            recyclerView.setAdapter(adapter);
        });
        myAdsViewModel.getUserComments(userID).observe(getViewLifecycleOwner(), userComments -> {
            adapter2 = new CommentsAdapter(userComments);
            recikler.setAdapter(adapter2);
        });

        rate.setOnClickListener(view1 -> {
            rate.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            if (userID.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                starGrade.setIsIndicator(true);
            else {
                comment_layout.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);
                commentBtn.setVisibility(View.VISIBLE);
                starGrade.setRating(0);
                starGrade.setIsIndicator(false);
            }

            recikler.setVisibility(View.VISIBLE);
            recikler.setVisibility(View.VISIBLE);
        });

        edit.setOnClickListener(view1 -> {
            recyclerView.setVisibility(View.INVISIBLE);
            username.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            rate.setVisibility(View.INVISIBLE);
            location.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            starGrade.setVisibility(View.INVISIBLE);
            edit_username.setText(current_user.getUsername());
            edit_location.setText(current_user.getLocation());
            edit_phone.setText(current_user.getPhone());
            edit_layout.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            recikler.setVisibility(View.INVISIBLE);
        });

        cancel.setOnClickListener(view1 -> {
            edit_layout.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            starGrade.setVisibility(View.VISIBLE);
            rate.setVisibility(View.VISIBLE);
        });

        save.setOnClickListener(view1 -> {
            myAdsViewModel.editUserInfo(new User(current_user.getUserID(), edit_username.getText().toString(), edit_location.getText().toString(), current_user.getGrade(), edit_phone.getText().toString(), current_user.getImageUrl()));
            edit_layout.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            starGrade.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);

            myAdsViewModel.getUser(userID).observe(getViewLifecycleOwner(), user -> {
                username.setText(user.getUsername());
                location.setText(user.getLocation());
                phone.setText(user.getPhone());
                user.setUserID(user.getUserID());
                GlideApp.with(avatar.getContext())
                        .load(user.getImageUrl())
                        .into(avatar);
                current_user = user;
            });
        });

        commentBtn.setOnClickListener(view1 -> {
            if (comment.getText().toString().trim().isEmpty())
                Toast.makeText(requireActivity(), R.string.comment_warning, Toast.LENGTH_SHORT).show();
            else {
                FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
                String fromUserID = Objects.requireNonNull(fire_user).getUid();
                Comment newComment = new Comment(fromUserID, userID, comment.getText().toString(), starGrade.getRating());
                myAdsViewModel.addNewUserComment(newComment);
                comment_layout.setVisibility(View.GONE);
                comment.setVisibility(View.GONE);
                commentBtn.setVisibility(View.GONE);
                starGrade.setIsIndicator(true);
            }
        });
    }

    @Override
    public void onListItemClick(String productID) {
        MyAdsFragmentDirections.MojioglasiToProizvodFragmentAction action = MyAdsFragmentDirections.mojioglasiToProizvodFragmentAction();
        action.setProductId(productID);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onStarItemClick(String productID, View view) {
    }
}
