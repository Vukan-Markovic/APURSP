package vukan.com.apursp.ui.filteri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import vukan.com.apursp.R;

public class FilteriFragment extends Fragment {
    private TextView cenaOd;
    private TextView cenaDo;
    private Button primeni;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filteri, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cenaOd = view.findViewById(R.id.cenaOd);
        cenaDo = view.findViewById(R.id.cenaDo);
        primeni = view.findViewById(R.id.primeni);

        primeni.setOnClickListener(view1 -> {
            if (!cenaOd.getText().toString().isEmpty() && !cenaDo.getText().toString().isEmpty()) {
                String[] filters = new String[2];
                filters[0] = cenaOd.getText().toString();
                filters[1] = cenaDo.getText().toString();
                FilteriFragmentDirections.FilteriToPocetnaFragmentAction action = FilteriFragmentDirections.filteriToPocetnaFragmentAction();
                action.setFilters(filters);
                Navigation.findNavController(requireView()).navigate(action);
            }
        });
    }
}