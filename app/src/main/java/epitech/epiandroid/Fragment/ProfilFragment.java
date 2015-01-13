package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;
import epitech.epiandroid.Utils;

public class ProfilFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_profil, container, false);
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);


        if (savedInstanceState != null) {
            System.out.println("RESTORED INSTANCE");
            ((TextView) rootView.findViewById(R.id.user_name)).setText(savedInstanceState.getString("user_name"));
            ((TextView) rootView.findViewById(R.id.user_surname)).setText(savedInstanceState.getString("user_surname"));
            ((TextView) rootView.findViewById(R.id.user_semester)).setText(savedInstanceState.getString("user_semester"));
            ((TextView) rootView.findViewById(R.id.user_login)).setText(savedInstanceState.getString("user_login"));
            ((TextView) rootView.findViewById(R.id.user_logtime)).setText(savedInstanceState.getString("user_logtime"));
            ((TextView) rootView.findViewById(R.id.user_logtime)).setTextColor(savedInstanceState.getInt("user_logtime_color"));
            Utils.getImgFromCache(getActivity().getApplicationContext(), "profil.bmp", ((ImageView) rootView.findViewById(R.id.user_picture)));
            rootView.findViewById((R.id.progress_picture)).setVisibility(View.GONE);
        } else {
            Context sContext = getActivity().getApplicationContext();
            Bundle extras = getActivity().getIntent().getExtras();
            String token = extras.getString("token");
            rootView.findViewById(R.id.progress_picture).setVisibility(View.VISIBLE);
            new InfosTask(token, sContext, ProfilFragment.this).execute((Void) null);
        }
        //TextView text = (TextView) rootView.findViewById(R.id.login);
        //text.setText("login : " + getArguments().getString("login") + " !!");

        //TextView token = (TextView) rootView.findViewById(R.id.token);
        //token.setText(getArguments().getString("token"));

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("SAVED INSTANCE");
        outState.putString("user_name", ((TextView) rootView.findViewById(R.id.user_name)).getText().toString());
        outState.putString("user_surname", ((TextView) rootView.findViewById(R.id.user_surname)).getText().toString());
        outState.putString("user_semester", ((TextView) rootView.findViewById(R.id.user_semester)).getText().toString());
        outState.putString("user_login", ((TextView) rootView.findViewById(R.id.user_login)).getText().toString());
        outState.putString("user_logtime", ((TextView) rootView.findViewById(R.id.user_logtime)).getText().toString());
        outState.putInt("user_logtime_color", ((TextView) rootView.findViewById(R.id.user_logtime)).getCurrentTextColor());
    }
}
