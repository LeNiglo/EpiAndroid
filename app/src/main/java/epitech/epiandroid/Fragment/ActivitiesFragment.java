package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Adapters.SubmissionsAdapter;
import epitech.epiandroid.Adapters.SusiesAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Submissions;
import epitech.epiandroid.Databases.Susies;
import epitech.epiandroid.Items.SubmissionsItem;
import epitech.epiandroid.Items.SusiesItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;


public class ActivitiesFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        long MILISECONDS_BEFORE_FORCE_REFRESH = 60 * 1000; // 60 secondes
        rootView = inflater.inflate(R.layout.fragment_section_activities, container, false);

        Boolean isSubmissionsDisplayed = Submissions.listAll(Submissions.class).size() > 0;
        Boolean isSusiesDisplayed = Susies.listAll(Susies.class).size() > 0;

        LoginTable user = null;
        try {
            user = LoginTable.listAll(LoginTable.class).get(0);
        } catch (Exception e) {
            getActivity().finish();
        }

        try {
            rootView.findViewById(R.id.susies_list).setVisibility(View.GONE);
            rootView.findViewById(R.id.next_submissions_list).setVisibility(View.GONE);
            rootView.findViewById(R.id.submissions_progress).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.susies_progress).setVisibility(View.VISIBLE);
        } catch (Exception ignored) {
        }

        if (!isSubmissionsDisplayed || !isSusiesDisplayed || (user.getInfosUpdatedAt() + MILISECONDS_BEFORE_FORCE_REFRESH) < System.currentTimeMillis()) {
            new InfosTask(getActivity(), this).execute((Void) null);
        } else {
            LoadSubmissions();
            LoadSusies();
        }

        return rootView;
    }

    public void LoadSusies() {
        List<SusiesItem> SusieItems = new ArrayList<>();
        List<Susies> susies = Susies.listAll(Susies.class);

        if (susies.size() > 0) {
            try {
                rootView.findViewById(R.id.susies_list).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.susie_nothing).setVisibility(View.GONE);
            } catch (Exception ignored) {
            }
            for (int i = 0; i < susies.size(); ++i) {
                Susies tmp = susies.get(i);
                SusieItems.add(new SusiesItem(tmp.getTitle(), tmp.getStart(), tmp.getSusie(), tmp.getType()));
            }
            onSusiesLoaded(SusieItems);
        } else {
            try {
                rootView.findViewById(R.id.susies_list).setVisibility(View.GONE);
                rootView.findViewById(R.id.susie_nothing).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.susies_progress).setVisibility(View.GONE);
            } catch (Exception ignored) {
            }
        }
    }

    public void onSusiesLoaded(List<SusiesItem> SusieItems) {
        try {
            LinearLayout susiesList = (LinearLayout) rootView.findViewById(R.id.susies_list);
            ListAdapter susiesAdapter = new SusiesAdapter(rootView.getContext(), R.layout.susie_item, SusieItems);
            for (int i = 0; i < susiesAdapter.getCount(); i++) {
                View oneSusie = susiesAdapter.getView(i, null, null);
                susiesList.addView(oneSusie);
            }
            rootView.findViewById(R.id.susies_list).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.susies_progress).setVisibility(View.GONE);
        } catch (Exception ignored) {
        }
    }

    public void LoadSubmissions() {
        List<SubmissionsItem> SubmissionsItem = new ArrayList<>();
        List<Submissions> sumbissions = Submissions.listAll(Submissions.class);

        Submissions item = null;
        for (int i = 0; i < sumbissions.size(); ++i) {
            Submissions tmp = sumbissions.get(i);
            if (item == null || (tmp.getProgress() > item.getProgress() && tmp.getProgress() != 100))
                item = tmp;
        }
        if (item != null)
            SubmissionsItem.add(new SubmissionsItem(item.getProjectName(), item.getIdActivity(), item.getDate(), item.getProgress()));
        onSubmissionsLoaded(SubmissionsItem);
    }

    public void onSubmissionsLoaded(List<SubmissionsItem> SubmissionsItems) {
        try {
            LinearLayout submissionsList = (LinearLayout) rootView.findViewById(R.id.next_submissions_list);
            ListAdapter sumbissionsAdapter = new SubmissionsAdapter(rootView.getContext(), R.layout.submission_item, SubmissionsItems);
            for (int i = 0; i < sumbissionsAdapter.getCount(); i++) {
                View jerusalem = sumbissionsAdapter.getView(i, null, null);
                submissionsList.addView(jerusalem);
            }
            rootView.findViewById(R.id.next_submissions_list).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.submissions_progress).setVisibility(View.GONE);
        } catch (Exception ignored) {
        }
    }
}
