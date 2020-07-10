package vukan.com.apursp.ui.user_messages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import vukan.com.apursp.R;

public class UserMessagesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UserMessagesViewModel userMessagesViewModel = new ViewModelProvider(this).get(UserMessagesViewModel.class);









      View root = inflater.inflate(R.layout.fragment_user_messages, container, false);

      
      FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
      Log.i("***","****user   " + fire_user);
      userMessagesViewModel.getAllUserMessages("dvKPe7wojFQchonZ6GpNZJ1KHgF3");



      final TextView textView = root.findViewById(R.id.text_obavestenja);
        userMessagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.app_name));
    }
}
