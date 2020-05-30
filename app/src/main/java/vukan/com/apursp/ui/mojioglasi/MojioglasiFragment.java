package vukan.com.apursp.ui.mojioglasi;

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

public class MojioglasiFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MojioglasiViewModel mojioglasiViewModel = ViewModelProviders.of(this).get(MojioglasiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mojioglasi, container, false);
        final TextView textView = root.findViewById(R.id.text_mojioglasi);
        mojioglasiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
