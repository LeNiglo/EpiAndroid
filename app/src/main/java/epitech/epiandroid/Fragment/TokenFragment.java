package epitech.epiandroid.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import epitech.epiandroid.R;


public class TokenFragment extends android.support.v4.app.Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_section_token, container, false);
        return rootView;
    }
}
