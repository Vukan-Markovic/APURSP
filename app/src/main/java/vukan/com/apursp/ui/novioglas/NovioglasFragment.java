package vukan.com.apursp.ui.novioglas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import vukan.com.apursp.MainActivity;
import vukan.com.apursp.R;
import vukan.com.apursp.ui.obavestenja.ObavestenjaFragment;
import vukan.com.apursp.ui.poruke.PorukeFragment;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class NovioglasFragment extends Fragment  {

  private ImageView odeca,obuca,aksesoari,decije;
  private ImageView knjige,biljke,ljubimci,uredjenje;
  private ImageView bicikl,igracke,sport,muzika;
  private ImageView mobilni,kompjuter,tehnika,namestaj;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    NovioglasViewModel novioglasViewModel = ViewModelProviders.of(this).get(NovioglasViewModel.class);
    View root = inflater.inflate(R.layout.fragment_novioglas, container, false);
    final TextView textView = root.findViewById(R.id.text_novioglas);



    novioglasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        textView.setText(s);
      }
    });



    //odeca
    odeca = (ImageView) root.findViewById(R.id.odeca);
    odeca.setOnClickListener(v -> {

      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(1);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Odeca", Toast.LENGTH_SHORT).show();
    });

    //obuca
    obuca = (ImageView) root.findViewById(R.id.obuca);
    obuca.setOnClickListener(v -> {

      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(2);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Obuca", Toast.LENGTH_SHORT).show();
    });

    //aksesoari
    aksesoari = (ImageView) root.findViewById(R.id.aksesoari);
    aksesoari.setOnClickListener(v -> {

      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(3);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Odeca", Toast.LENGTH_SHORT).show();
    });

    //decije
    decije = (ImageView) root.findViewById(R.id.decije);
    decije.setOnClickListener(v -> {

      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(4);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Decije", Toast.LENGTH_SHORT).show();
    });

    //knjige
    knjige = (ImageView) root.findViewById(R.id.knjige);
    knjige.setOnClickListener(v -> {

      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(5);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Knjige", Toast.LENGTH_SHORT).show();
    });

    //biljke
    biljke = (ImageView) root.findViewById(R.id.biljke);
    biljke.setOnClickListener(v -> {

      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(6);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Biljke", Toast.LENGTH_SHORT).show();
    });

    //ljubimci
    ljubimci = (ImageView) root.findViewById(R.id.ljubimci);
    ljubimci.setOnClickListener(v -> {

      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(7);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Ljubimci", Toast.LENGTH_SHORT).show();
    });

    //uredjenje
    uredjenje = (ImageView) root.findViewById(R.id.uredjenje);
    uredjenje.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(8);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Uredjenje", Toast.LENGTH_SHORT).show();
    });

    //bicikl
    bicikl = (ImageView) root.findViewById(R.id.bicikl);
    bicikl.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(9);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Bicikl", Toast.LENGTH_SHORT).show();
    });

    //igracke
    igracke = (ImageView) root.findViewById(R.id.igracke);
    igracke.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(10);
      Navigation.findNavController(v).navigate(action);


      Toast.makeText(getActivity(), "Igracke", Toast.LENGTH_SHORT).show();
    });

    //sport
    sport = (ImageView) root.findViewById(R.id.sport);
    sport.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(11);
      Navigation.findNavController(v).navigate(action);


      Toast.makeText(getActivity(), "Sport", Toast.LENGTH_SHORT).show();
    });
    //muzika
    muzika = (ImageView) root.findViewById(R.id.muzika);
    muzika.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(12);
      Navigation.findNavController(v).navigate(action);


      Toast.makeText(getActivity(), "Muzika", Toast.LENGTH_SHORT).show();
    });
    //mobilni
    mobilni = (ImageView) root.findViewById(R.id.mobilni);
    mobilni.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(13);
      Navigation.findNavController(v).navigate(action);

      Toast.makeText(getActivity(), "Mobilni", Toast.LENGTH_SHORT).show();
    });

    //kompjuter
    kompjuter = (ImageView) root.findViewById(R.id.kompjuter);
    kompjuter.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(14);
      Navigation.findNavController(v).navigate(action);


      Toast.makeText(getActivity(), "Kompjuter", Toast.LENGTH_SHORT).show();
    });

    //tehnika
    tehnika = (ImageView) root.findViewById(R.id.tehnika);
    tehnika.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(15);
      Navigation.findNavController(v).navigate(action);


      Toast.makeText(getActivity(), "Tehnika", Toast.LENGTH_SHORT).show();
    });
    //namestaj
    namestaj = (ImageView) root.findViewById(R.id.namestaj);
    namestaj.setOnClickListener(v -> {
      NovioglasFragmentDirections.NoviOglasToNovioglasprozorFragmentAction action = NovioglasFragmentDirections.noviOglasToNovioglasprozorFragmentAction();
      action.setId(16);
      Navigation.findNavController(v).navigate(action);


      Toast.makeText(getActivity(), "Namestaj", Toast.LENGTH_SHORT).show();
    });

    return root;
  }




}
