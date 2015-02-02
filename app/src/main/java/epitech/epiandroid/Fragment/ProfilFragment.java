package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Adapters.MessagesAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Messages;
import epitech.epiandroid.Databases.ProfilInfos;
import epitech.epiandroid.Items.MessagesItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;
import epitech.epiandroid.Tasks.MessagesTask;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class ProfilFragment extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isProfileDisplayed;
        boolean isMessagesDisplayed;
        long MILISECONDS_BEFORE_FORCE_REFRESH = 60 * 1000; // 60 secondes

        rootView = inflater.inflate(R.layout.fragment_section_profil, container, false);

        isMessagesDisplayed = Messages.listAll(Messages.class).size() != 0;
        isProfileDisplayed = ProfilInfos.listAll(ProfilInfos.class).size() != 0;

        LoginTable user = LoginTable.listAll(LoginTable.class).get(0);

        try {
            rootView.findViewById(R.id.user_name).setVisibility(View.GONE);
            rootView.findViewById(R.id.user_surname).setVisibility(View.GONE);
            rootView.findViewById(R.id.user_login).setVisibility(View.GONE);
            rootView.findViewById(R.id.user_picture).setVisibility(View.GONE);
            rootView.findViewById(R.id.user_semester).setVisibility(View.GONE);
            rootView.findViewById(R.id.user_logtime).setVisibility(View.GONE);
            try {
                rootView.findViewById(R.id.user_messages).setVisibility(View.GONE);
            } catch (Exception ignored) {
            }
            try {
            rootView.findViewById(R.id.user_messages_linear).setVisibility(View.GONE);
            } catch (Exception ignored) {
            }
            rootView.findViewById(R.id.user_progress).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.progress_messages).setVisibility(View.VISIBLE);
        } catch (Exception ignored) {
            Log.e("onCreateView", ignored.getMessage());
        }

        if (!isProfileDisplayed || (user.getInfosUpdatedAt() + MILISECONDS_BEFORE_FORCE_REFRESH) < System.currentTimeMillis()) {
            new InfosTask(getActivity(), this).execute((Void) null);
        } else
            onProfileLoaded();

        if (!isMessagesDisplayed || (user.getMessagesUpdatedAt() + MILISECONDS_BEFORE_FORCE_REFRESH) < System.currentTimeMillis()) {
            new MessagesTask(rootView, this).execute((Void) null);
        } else
            LoadMessages();

        return rootView;
    }

    public void onProfileLoaded() {
        try {
            rootView.findViewById(R.id.user_name).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.user_surname).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.user_login).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.user_picture).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.user_semester).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.user_logtime).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.user_progress).setVisibility(View.GONE);
            ProfilInfos infos = ProfilInfos.listAll(ProfilInfos.class).get(0);
            ((TextView) rootView.findViewById(R.id.user_name)).setText(infos.getLastName());
            ((TextView) rootView.findViewById(R.id.user_surname)).setText(infos.getFirstName());
            ((TextView) rootView.findViewById(R.id.user_semester)).setText(getResources().getString(R.string.semester) + " " + infos.getSemester());
            ((TextView) rootView.findViewById(R.id.user_login)).setText(infos.getLogin());
            ((MaterialNavigationDrawer) this.getActivity()).getToolbar().setTitle(infos.getFirstName());
            ((MaterialNavigationDrawer) this.getActivity()).getToolbar().setTitleTextColor(Color.parseColor("#DEDEDE"));
            Double active_log = Double.valueOf(infos.getActiveLog());
            Double nslog_norm = Double.valueOf(infos.getNsLogNorm());
            Double nslog_min = Double.valueOf(infos.getNsLogMin());
            if (active_log < nslog_min) {
                ((TextView) rootView.findViewById(R.id.user_logtime)).setTextColor(Color.parseColor("#FF0000"));
                ((TextView) rootView.findViewById(R.id.user_logtime)).setText(getResources().getString(R.string.logtime_insufficient_warn1) + " (" + active_log.intValue() + " < " + nslog_min.intValue() + ")");
            } else if (active_log < nslog_norm) {
                ((TextView) rootView.findViewById(R.id.user_logtime)).setTextColor(Color.parseColor("#FFAA00"));
                ((TextView) rootView.findViewById(R.id.user_logtime)).setText(getResources().getString(R.string.logtime_insufficient_warn2) + " (" + active_log.intValue() + " < " + nslog_norm.intValue() + ")");
            } else {
                ((TextView) rootView.findViewById(R.id.user_logtime)).setTextColor(Color.parseColor("#1FA055"));
                ((TextView) rootView.findViewById(R.id.user_logtime)).setText(getResources().getString(R.string.logtime_sufficient) + " (" + active_log.intValue() + " > " + nslog_norm.intValue() + ")");
            }
            Picasso.with(getActivity().getApplicationContext()).load(infos.getPicUrl()).into(((ImageView) rootView.findViewById(R.id.user_picture)));
        } catch (Exception ignored) {
            Log.e("onProfileLoaded", ignored.getMessage());
        }
    }

    public void LoadMessages() {
        try {
            List<MessagesItem> userMessages = new ArrayList<>();
            List<Messages> messages = Messages.listAll(Messages.class);
            for (int i = 0; i < messages.size(); ++i) {
                Messages msg = messages.get(i);
                userMessages.add(new MessagesItem(msg.getContent(), msg.getTitle(), msg.getLogin(), msg.getDate(), msg.getPicUrl()));
            }
            onMessagesLoaded(userMessages);
        } catch (Exception ignored) {
        }
    }

    public void onMessagesLoaded(List<MessagesItem> userMessages) {
        try {
            if (rootView.findViewById(R.id.progress_messages) != null)
                rootView.findViewById(R.id.progress_messages).setVisibility(View.GONE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (rootView.findViewById(R.id.user_messages) != null)
                    rootView.findViewById(R.id.user_messages).setVisibility(View.VISIBLE);
                ListView messageList = (ListView) rootView.findViewById(R.id.user_messages);
                ListAdapter customAdapter = new MessagesAdapter(rootView.getContext(), R.layout.message_item, userMessages);
                messageList.setAdapter(customAdapter);
            } else {
                if (rootView.findViewById(R.id.user_messages_linear) != null)
                    rootView.findViewById(R.id.user_messages_linear).setVisibility(View.VISIBLE);
                LinearLayout messageList = (LinearLayout) rootView.findViewById(R.id.user_messages_linear);
                ListAdapter customAdapter = new MessagesAdapter(rootView.getContext(), R.layout.message_item, userMessages);
                for (int i = 0; i < customAdapter.getCount(); i++) {
                    View item = customAdapter.getView(i, null, null);
                    messageList.addView(item);
                }
            }
        } catch (Exception ignored) {
            Log.e("Error", ignored.getMessage());
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
