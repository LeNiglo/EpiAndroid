package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Activity.MessagesAdapter;
import epitech.epiandroid.Activity.MessagesItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;
import epitech.epiandroid.Utils;

public class ProfilFragment extends Fragment {
    private View rootView;
    private boolean isProfileDisplayed;
    private List<MessagesItem> messages = new ArrayList<>();

    public void setIsProfileDisplayed(boolean value) { isProfileDisplayed = value; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_profil, container, false);
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        if (savedInstanceState != null) {
            isProfileDisplayed = savedInstanceState.getBoolean("profile_displayed");
            if (isProfileDisplayed) {
                ((TextView) rootView.findViewById(R.id.user_name)).setText(savedInstanceState.getString("user_name"));
                ((TextView) rootView.findViewById(R.id.user_surname)).setText(savedInstanceState.getString("user_surname"));
                ((TextView) rootView.findViewById(R.id.user_semester)).setText(savedInstanceState.getString("user_semester"));
                ((TextView) rootView.findViewById(R.id.user_login)).setText(savedInstanceState.getString("user_login"));
                ((TextView) rootView.findViewById(R.id.user_logtime)).setText(savedInstanceState.getString("user_logtime"));
                ((TextView) rootView.findViewById(R.id.user_logtime)).setTextColor(savedInstanceState.getInt("user_logtime_color"));
                Utils.getImgFromCache(getActivity().getApplicationContext(), "profil.bmp", ((ImageView) rootView.findViewById(R.id.user_picture)));
                rootView.findViewById((R.id.progress_picture)).setVisibility(View.GONE);
                return rootView;
            }
        }
        Context sContext = getActivity().getApplicationContext();
        Bundle extras = getActivity().getIntent().getExtras();
        String token = extras.getString("token");

        messages.add(new MessagesItem("Please verify; contact the person who corrected you if you believe that there is an error", "Mark added for activity Soutenance intermediaire du projet UML : diagramme de classes, interfaces et diagrammes de séquence. (Soutenance) by Francois Carrubba.", "Francois Carrubba", "14/01/2015", null));
        messages.add(new MessagesItem("Remember to register to this appointment. See appointments slots ...", "You can now register to the appointments of the activity : Soutenance finale du projet UML.", "Francois Carrubba", "14/01/2015", null));
        messages.add(new MessagesItem("Remember to register to this appointment. See appointments slots ...", "You can now register to the appointments of the activity : Suivi pédagogique", "Julien1 Barouhiel", "14/01/2015", null));
        ListView messageList = (ListView) rootView.findViewById(R.id.user_messages);
        ListAdapter customAdapter = new MessagesAdapter(rootView.getContext(), R.layout.profil_message, messages);
        messageList.setAdapter(customAdapter);
        rootView.findViewById(R.id.progress_picture).setVisibility(View.VISIBLE);
        new InfosTask(token, sContext, ProfilFragment.this).execute((Void) null);

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("profile_displayed", isProfileDisplayed);
        if (isProfileDisplayed) {
            outState.putString("user_name", ((TextView) rootView.findViewById(R.id.user_name)).getText().toString());
            outState.putString("user_surname", ((TextView) rootView.findViewById(R.id.user_surname)).getText().toString());
            outState.putString("user_semester", ((TextView) rootView.findViewById(R.id.user_semester)).getText().toString());
            outState.putString("user_login", ((TextView) rootView.findViewById(R.id.user_login)).getText().toString());
            outState.putString("user_logtime", ((TextView) rootView.findViewById(R.id.user_logtime)).getText().toString());
            outState.putInt("user_logtime_color", ((TextView) rootView.findViewById(R.id.user_logtime)).getCurrentTextColor());
        }
    }
}
