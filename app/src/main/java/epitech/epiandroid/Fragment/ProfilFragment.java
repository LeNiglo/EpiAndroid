package epitech.epiandroid.Fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epitech.epiandroid.Activity.MessagesItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;
import epitech.epiandroid.Tasks.MessagesTask;

public class ProfilFragment extends Fragment {
    private View rootView;
    private boolean isProfileDisplayed;
    private boolean isMessagesDisplayed;

    public void setIsProfileDisplayed(boolean value) { isProfileDisplayed = value; }
    public void setIsMessagesDisplayed(boolean value) { isMessagesDisplayed = value; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_profil, container, false);
        super.onCreate(savedInstanceState);

        Context sContext = getActivity().getApplicationContext();
        Bundle extras = getActivity().getIntent().getExtras();
        String token = extras.getString("token");

        if (savedInstanceState != null) {
            isProfileDisplayed = savedInstanceState.getBoolean("profile_displayed");
            isMessagesDisplayed = savedInstanceState.getBoolean("messages_displayed");
            if (isProfileDisplayed) {
                ((TextView) rootView.findViewById(R.id.user_name)).setText(savedInstanceState.getString("user_name"));
                ((TextView) rootView.findViewById(R.id.user_surname)).setText(savedInstanceState.getString("user_surname"));
                ((TextView) rootView.findViewById(R.id.user_semester)).setText(savedInstanceState.getString("user_semester"));
                ((TextView) rootView.findViewById(R.id.user_login)).setText(savedInstanceState.getString("user_login"));
                ((TextView) rootView.findViewById(R.id.user_logtime)).setText(savedInstanceState.getString("user_logtime"));
                ((TextView) rootView.findViewById(R.id.user_logtime)).setTextColor(savedInstanceState.getInt("user_logtime_color"));
                Picasso.with(getActivity().getApplicationContext()).load("https://cdn.local.epitech.eu/userprofil/" + savedInstanceState.getString("user_login") + ".bmp").into(((ImageView) rootView.findViewById(R.id.user_picture)));
                rootView.findViewById((R.id.progress_picture)).setVisibility(View.GONE);
            }
            if (isMessagesDisplayed) {
                new MessagesTask(ProfilFragment.this, savedInstanceState.getString("user_messages"), rootView).execute((Void) null);
                rootView.findViewById((R.id.progress_messages)).setVisibility(View.GONE);
            }
        }

        if (!isProfileDisplayed) {
            rootView.findViewById(R.id.progress_picture).setVisibility(View.VISIBLE);
            new InfosTask(token, sContext, ProfilFragment.this).execute((Void) null);
        }
        if (!isMessagesDisplayed) {
            rootView.findViewById((R.id.progress_messages)).setVisibility(View.VISIBLE);
            new MessagesTask(token, sContext, ProfilFragment.this, rootView).execute((Void) null);
        }

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("profile_displayed", isProfileDisplayed);
        outState.putBoolean("messages_displayed", isMessagesDisplayed);
        if (isProfileDisplayed) {
            outState.putString("user_name", ((TextView) rootView.findViewById(R.id.user_name)).getText().toString());
            outState.putString("user_surname", ((TextView) rootView.findViewById(R.id.user_surname)).getText().toString());
            outState.putString("user_semester", ((TextView) rootView.findViewById(R.id.user_semester)).getText().toString());
            outState.putString("user_login", ((TextView) rootView.findViewById(R.id.user_login)).getText().toString());
            outState.putString("user_logtime", ((TextView) rootView.findViewById(R.id.user_logtime)).getText().toString());
            outState.putInt("user_logtime_color", ((TextView) rootView.findViewById(R.id.user_logtime)).getCurrentTextColor());
        }
        if (isMessagesDisplayed) {
            ListView messageList = (ListView) rootView.findViewById(R.id.user_messages);
            JSONArray json = new JSONArray();
            for (int i = 0; i < messageList.getCount(); ++i) {
                MessagesItem item = (MessagesItem) messageList.getItemAtPosition(i);
                try {
                    JSONObject jsonO = new JSONObject();
                    jsonO.put("login", item.getLogin());
                    jsonO.put("title", item.getTitle());
                    jsonO.put("content", item.getContent());
                    jsonO.put("date", item.getDate());
                    jsonO.put("picture", item.getPicUrl());
                    json.put(jsonO);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            outState.putString("user_messages", json.toString());
        }
    }
}
