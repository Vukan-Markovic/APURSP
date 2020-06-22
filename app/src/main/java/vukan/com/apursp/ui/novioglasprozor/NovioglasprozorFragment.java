package vukan.com.apursp.ui.novioglasprozor;

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
import vukan.com.apursp.ui.poruke.PorukeFragmentArgs;

public class NovioglasprozorFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      NovioglasprozorViewModel novioglasprozorViewModel = ViewModelProviders.of(this).get(NovioglasprozorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_novioglasprozor, container, false);
        final TextView textView = root.findViewById(R.id.text_novioglasprozor);
      novioglasprozorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
    }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    TextView textView = view.findViewById(R.id.text_novioglasprozor);
//    if(getArguments() != null)
//      textView.setText(PorukeFragmentArgs.fromBundle(getArguments()).getId() + "");
  }

}
