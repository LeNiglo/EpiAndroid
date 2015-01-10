package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import epitech.epiandroid.R;


public class ProjetsFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_section_token, container, false);
        return rootView;
    }
}
