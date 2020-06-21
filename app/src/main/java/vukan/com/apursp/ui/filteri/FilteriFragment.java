package vukan.com.apursp.ui.filteri;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.Timestamp;

import java.util.Calendar;

import vukan.com.apursp.R;

public class FilteriFragment extends Fragment {
    private TextView cenaOd;
    private TextView cenaDo;
    private Button datumOd;
    private Button datumDo;
    private Button primeni;
    private RadioButton opadajuce;
    private RadioButton rastuce;
    public static String[] filters = new String[5];
    private static Calendar c1 = Calendar.getInstance();
    private static Calendar c2 = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filteri, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cenaOd = view.findViewById(R.id.cenaOd);
        cenaDo = view.findViewById(R.id.cenaDo);
        datumOd = view.findViewById(R.id.datumOd);
        datumDo = view.findViewById(R.id.datumDo);
        primeni = view.findViewById(R.id.primeni);
        opadajuce = view.findViewById(R.id.opadajuce);
        rastuce = view.findViewById(R.id.rastuce);

        datumOd.setOnClickListener(view1 -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getParentFragmentManager(), "datumOd");
        });

        datumDo.setOnClickListener(view1 -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getParentFragmentManager(), "datumDo");
        });

        primeni.setOnClickListener(view1 -> {
            filters[0] = cenaOd.getText().toString();
            filters[1] = cenaDo.getText().toString();
            if (opadajuce.isChecked())
                filters[4] = "opadajuce";
            else if (rastuce.isChecked())
                filters[4] = "rastuce";
            FilteriFragmentDirections.FilteriToPocetnaFragmentAction action = FilteriFragmentDirections.filteriToPocetnaFragmentAction();
            action.setFilters(filters);
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year, month, day = 0;

            if (getTag().equals("datumOd")) {
                year = FilteriFragment.c1.get(Calendar.YEAR);
                month = FilteriFragment.c1.get(Calendar.MONTH);
                day = FilteriFragment.c1.get(Calendar.DAY_OF_MONTH);
            } else {
                year = FilteriFragment.c2.get(Calendar.YEAR);
                month = FilteriFragment.c2.get(Calendar.MONTH);
                day = FilteriFragment.c2.get(Calendar.DAY_OF_MONTH);
            }
            return new DatePickerDialog(requireActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (getTag().equals("datumOd")) {
                Timestamp date = new Timestamp(c1.getTime());
                FilteriFragment.c1.set(Calendar.YEAR, year);
                FilteriFragment.c1.set(Calendar.MONTH, month);
                FilteriFragment.c1.set(Calendar.DAY_OF_MONTH, day);
                FilteriFragment.filters[2] = String.valueOf(date.getSeconds());
            } else {
                Timestamp date = new Timestamp(c2.getTime());
                FilteriFragment.c2.set(Calendar.YEAR, year);
                FilteriFragment.c2.set(Calendar.MONTH, month);
                FilteriFragment.c2.set(Calendar.DAY_OF_MONTH, day);
                FilteriFragment.filters[3] = String.valueOf(date.getSeconds());
            }
        }
    }
}