package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Adapters.MessagesAdapter;
import epitech.epiandroid.Databases.Messages;
import epitech.epiandroid.Databases.ProfilInfos;
import epitech.epiandroid.Items.MessagesItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;
import epitech.epiandroid.Tasks.MessagesTask;

public class ProfilFragment extends Fragment {
    private View rootView;
    private boolean isProfileDisplayed;
    private boolean isMessagesDisplayed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_profil, container, false);
        super.onCreate(savedInstanceState);

        isMessagesDisplayed = Messages.listAll(Messages.class).size() != 0;
        isProfileDisplayed = ProfilInfos.listAll(ProfilInfos.class).size() != 0;

        System.out.println(Messages.listAll(Messages.class).size() + " messages et " + ProfilInfos.listAll(ProfilInfos.class).size() + " profils");

        if (!isProfileDisplayed) {
            new InfosTask(getActivity()).execute((Void) null);
        } else {
            ProfilInfos infos = ProfilInfos.listAll(ProfilInfos.class).get(0);
            ((TextView) rootView.findViewById(R.id.user_name)).setText(infos.getLastName());
            ((TextView) rootView.findViewById(R.id.user_surname)).setText(infos.getFirstName());
            ((TextView) rootView.findViewById(R.id.user_semester)).setText(infos.getSemester());
            ((TextView) rootView.findViewById(R.id.user_login)).setText(infos.getLogin());
            ((TextView) rootView.findViewById(R.id.user_logtime)).setText(infos.getActiveLog() + " / " + infos.getNsLogNorm());
            ((TextView) rootView.findViewById(R.id.user_logtime)).setTextColor(infos.getLogColor());
            Picasso.with(getActivity().getApplicationContext()).load(infos.getPicUrl()).into(((ImageView) rootView.findViewById(R.id.user_picture)));
        }

        if (!isMessagesDisplayed) {
            rootView.findViewById((R.id.progress_messages)).setVisibility(View.VISIBLE);
            new MessagesTask(rootView).execute((Void) null);
        } else {
            rootView.findViewById(R.id.progress_messages).setVisibility(View.VISIBLE);
            List<MessagesItem> userMessages = new ArrayList<>();
            List<Messages> messages = Messages.listAll(Messages.class);

            for (int i = 0; i < messages.size(); ++i) {
                Messages msg = messages.get(i);
                userMessages.add(new MessagesItem(msg.getContent(), msg.getTitle(), msg.getLogin(), msg.getDate(), msg.getPicUrl()));
            }

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ListView messageList = (ListView) rootView.findViewById(R.id.user_messages);
                ListAdapter customAdapter = new MessagesAdapter(rootView.getContext(), R.layout.profil_message, userMessages);
                messageList.setAdapter(customAdapter);
            } else {
                LinearLayout messageList = (LinearLayout) rootView.findViewById(R.id.user_messages_linear);
                ListAdapter customAdapter = new MessagesAdapter(rootView.getContext(), R.layout.profil_message, userMessages);
                for (int i = 0; i < customAdapter.getCount(); i++) {
                    View item = customAdapter.getView(i, null, null);
                    messageList.addView(item);
                }
            }
            rootView.findViewById(R.id.progress_messages).setVisibility(View.GONE);
        }

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
