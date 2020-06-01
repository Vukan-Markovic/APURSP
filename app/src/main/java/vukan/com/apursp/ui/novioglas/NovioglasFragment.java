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
//        PorukeFragment nextFrag= new PorukeFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//          .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
//          .addToBackStack(null)
//          .commit();
        NovioglasFragmentDirections.NoviOglasToPorukeFragmentAction action = NovioglasFragmentDirections.noviOglasToPorukeFragmentAction();
        action.setId(1);
        Navigation.findNavController(v).navigate(action);

        Toast.makeText(getActivity(), "Odeca", Toast.LENGTH_SHORT).show();
      });

      //obuca
      obuca = (ImageView) root.findViewById(R.id.obuca);
      obuca.setOnClickListener(v -> {
        PorukeFragment nextFrag= new PorukeFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
          .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
          .addToBackStack(null)
          .commit();

        Toast.makeText(getActivity(), "Obuca", Toast.LENGTH_SHORT).show();
      });

      //aksesoari
      aksesoari = (ImageView) root.findViewById(R.id.aksesoari);
      aksesoari.setOnClickListener(v -> {
        PorukeFragment nextFrag= new PorukeFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
          .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
          .addToBackStack(null)
          .commit();

        Toast.makeText(getActivity(), "Aksesoari", Toast.LENGTH_SHORT).show();
      });

      //decije
      decije = (ImageView) root.findViewById(R.id.decije);
      decije.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Decije", Toast.LENGTH_SHORT).show();
        }
      });

      //knjige
      knjige = (ImageView) root.findViewById(R.id.knjige);
      knjige.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Knjige", Toast.LENGTH_SHORT).show();
        }
      });

      //biljke
      biljke = (ImageView) root.findViewById(R.id.biljke);
      biljke.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Biljke", Toast.LENGTH_SHORT).show();
        }
      });

      //ljubimci
      ljubimci = (ImageView) root.findViewById(R.id.ljubimci);
      ljubimci.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Ljubimci", Toast.LENGTH_SHORT).show();
        }
      });

      //uredjenje
      uredjenje = (ImageView) root.findViewById(R.id.uredjenje);
      uredjenje.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Uredjenje", Toast.LENGTH_SHORT).show();
        }
      });

      //bicikl
      bicikl = (ImageView) root.findViewById(R.id.bicikl);
      bicikl.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Bicikl", Toast.LENGTH_SHORT).show();
        }
      });

      //igracke
      igracke = (ImageView) root.findViewById(R.id.igracke);
      igracke.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Igracke", Toast.LENGTH_SHORT).show();
        }
      });

      //sport
      sport = (ImageView) root.findViewById(R.id.sport);
      sport.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Sport", Toast.LENGTH_SHORT).show();
        }
      });

      //muzika
      muzika = (ImageView) root.findViewById(R.id.muzika);
      muzika.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Muzika", Toast.LENGTH_SHORT).show();
        }
      });

      //mobilni
      mobilni = (ImageView) root.findViewById(R.id.mobilni);
      mobilni.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Mobilni", Toast.LENGTH_SHORT).show();
        }
      });

      //kompjuter
      kompjuter = (ImageView) root.findViewById(R.id.kompjuter);
      kompjuter.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Kompjuteri", Toast.LENGTH_SHORT).show();
        }
      });

      //tehnika
      tehnika = (ImageView) root.findViewById(R.id.tehnika);
      tehnika.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Tehnika", Toast.LENGTH_SHORT).show();
        }
      });

      //namestaj
      namestaj = (ImageView) root.findViewById(R.id.namestaj);
      namestaj.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
          PorukeFragment nextFrag= new PorukeFragment();
          getActivity().getSupportFragmentManager().beginTransaction()
            .replace( ((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
            .addToBackStack(null)
            .commit();

          Toast.makeText(getActivity(), "Namestaj", Toast.LENGTH_SHORT).show();
        }
      });

      return root;
    }




}
