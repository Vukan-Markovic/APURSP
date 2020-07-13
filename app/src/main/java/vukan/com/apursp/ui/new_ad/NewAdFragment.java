package vukan.com.apursp.ui.new_ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import vukan.com.apursp.R;

public class NewAdFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.new_ad));
        final TextView textView = view.findViewById(R.id.text_novioglas);
        textView.setText(R.string.title_novioglas);
        Animation mAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade);
        mAnimation.setDuration(150);
        ImageView odeca = view.findViewById(R.id.odeca);
        ImageView obuca = view.findViewById(R.id.obuca);
        ImageView aksesoari = view.findViewById(R.id.aksesoari);
        ImageView decije = view.findViewById(R.id.decije);
        ImageView knjige = view.findViewById(R.id.knjige);
        ImageView biljke = view.findViewById(R.id.biljke);
        ImageView ljubimci = view.findViewById(R.id.ljubimci);
        ImageView uredjenje = view.findViewById(R.id.uredjenje);
        ImageView bicikl = view.findViewById(R.id.bicikl);
        ImageView igracke = view.findViewById(R.id.igracke);
        ImageView sport = view.findViewById(R.id.sport);
        ImageView muzika = view.findViewById(R.id.muzika);
        ImageView mobilni = view.findViewById(R.id.mobilni);
        ImageView kompjuter = view.findViewById(R.id.kompjuter);
        ImageView tehnika = view.findViewById(R.id.tehnika);

        odeca.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(1);
            Navigation.findNavController(v).navigate(action);
        });

        obuca.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(2);
            Navigation.findNavController(v).navigate(action);
        });

        aksesoari.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(3);
            Navigation.findNavController(v).navigate(action);
        });

        decije.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(4);
            Navigation.findNavController(v).navigate(action);
        });

        knjige.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(5);
            Navigation.findNavController(v).navigate(action);
        });

        biljke.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(6);
            Navigation.findNavController(v).navigate(action);
        });

        ljubimci.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(7);
            Navigation.findNavController(v).navigate(action);
        });

        uredjenje.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(8);
            Navigation.findNavController(v).navigate(action);
        });

        bicikl.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(9);
            Navigation.findNavController(v).navigate(action);
        });

        igracke.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(10);
            Navigation.findNavController(v).navigate(action);
        });

        sport.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(11);
            Navigation.findNavController(v).navigate(action);
        });

        muzika.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(12);
            Navigation.findNavController(v).navigate(action);
        });

        mobilni.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(13);
            Navigation.findNavController(v).navigate(action);
        });

        kompjuter.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(14);
            Navigation.findNavController(v).navigate(action);
        });

        tehnika.setOnClickListener(v -> {
            v.startAnimation(mAnimation);
            NewAdFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NewAdFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
            action.setId(15);
            Navigation.findNavController(v).navigate(action);
        });
    }
}