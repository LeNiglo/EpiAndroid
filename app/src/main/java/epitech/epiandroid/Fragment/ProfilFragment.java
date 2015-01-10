package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import epitech.epiandroid.R;

public class ProfilFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_section_profil, container, false);



        //TextView text = (TextView) rootView.findViewById(R.id.login);
        //text.setText("login : " + getArguments().getString("login") + " !!");

        //TextView token = (TextView) rootView.findViewById(R.id.token);
        //token.setText(getArguments().getString("token"));

        return rootView;
    }
}
