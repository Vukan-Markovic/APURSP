package vukan.com.apursp.ui.poruke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import vukan.com.apursp.R;

public class PorukeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PorukeViewModel porukeViewModel = ViewModelProviders.of(this).get(PorukeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_poruke, container, false);
        final TextView textView = root.findViewById(R.id.text_poruke);
        porukeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
