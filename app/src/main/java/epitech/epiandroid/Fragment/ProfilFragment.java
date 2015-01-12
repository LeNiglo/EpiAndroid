package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;

public class ProfilFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_profil, container, false);
        super.onCreate(savedInstanceState);
        Context sContext = getActivity().getApplicationContext();
        Bundle extras = getActivity().getIntent().getExtras();
        String token = extras.getString("token");
        setRetainInstance(true);

        rootView.findViewById(R.id.progress_picture).setVisibility(View.VISIBLE);
        new InfosTask(token, sContext, ProfilFragment.this).execute((Void) null);


        //TextView text = (TextView) rootView.findViewById(R.id.login);
        //text.setText("login : " + getArguments().getString("login") + " !!");

        //TextView token = (TextView) rootView.findViewById(R.id.token);
        //token.setText(getArguments().getString("token"));

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            System.out.println("RESTORED INSTANCE");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("SAVED INSTANCE");
    }
}
