package vukan.com.apursp.ui.novioglas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import vukan.com.apursp.R;

public class NovioglasFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_novioglas, container, false);
        final TextView textView = root.findViewById(R.id.text_novioglas);
        textView.setText(R.string.title_novioglas);

        ImageView odeca = root.findViewById(R.id.odeca);
        odeca.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(1);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView obuca = root.findViewById(R.id.obuca);
        obuca.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(2);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView aksesoari = root.findViewById(R.id.aksesoari);
        aksesoari.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(3);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView decije = root.findViewById(R.id.decije);
        decije.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(4);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView knjige = root.findViewById(R.id.knjige);
        knjige.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(5);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView biljke = root.findViewById(R.id.biljke);
        biljke.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(6);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView ljubimci = root.findViewById(R.id.ljubimci);
        ljubimci.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(7);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView uredjenje = root.findViewById(R.id.uredjenje);
        uredjenje.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(8);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView bicikl = root.findViewById(R.id.bicikl);
        bicikl.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(9);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView igracke = root.findViewById(R.id.igracke);
        igracke.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(10);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView sport = root.findViewById(R.id.sport);
        sport.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(11);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView muzika = root.findViewById(R.id.muzika);
        muzika.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(12);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView mobilni = root.findViewById(R.id.mobilni);
        mobilni.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(13);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView kompjuter = root.findViewById(R.id.kompjuter);
        kompjuter.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(14);
            Navigation.findNavController(v).navigate(action);
        });

        ImageView tehnika = root.findViewById(R.id.tehnika);
        tehnika.setOnClickListener(v -> {
            NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(15);
            Navigation.findNavController(v).navigate(action);
        });

        return root;
    }
}