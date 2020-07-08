package vukan.com.apursp.ui.obavestenja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import vukan.com.apursp.R;

public class ObavestenjaFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ObavestenjaViewModel obavestenjaViewModel = new ViewModelProvider(this).get(ObavestenjaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_obavestenja, container, false);
        final TextView textView = root.findViewById(R.id.text_obavestenja);
        obavestenjaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.app_name));
    }
}
