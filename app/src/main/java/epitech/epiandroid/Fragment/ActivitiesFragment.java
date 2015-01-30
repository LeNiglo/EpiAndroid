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
import epitech.epiandroid.Databases.Submissions;
import epitech.epiandroid.Databases.Susies;
import epitech.epiandroid.Items.SubmissionsItem;
import epitech.epiandroid.Items.SusiesItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;


public class ActivitiesFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_activities, container, false);

        Boolean isSubmissionsDisplayed = Submissions.listAll(Submissions.class).size() > 0;
        Boolean isSusiesDisplayed = Susies.listAll(Susies.class).size() > 0;

        if (!isSubmissionsDisplayed || !isSusiesDisplayed) {
            //rootView.findViewById(R.id.marks_progress).setVisibility(View.VISIBLE);
            new InfosTask(getActivity()).execute((Void) null);
        } else {
            //rootView.findViewById(R.id.marks_progress).setVisibility(View.VISIBLE);

            List<SubmissionsItem> items = new ArrayList<>();
            List<Submissions> sumbissions = Submissions.listAll(Submissions.class);

            Submissions item = null;
            for (int i = 0; i < sumbissions.size(); ++i) {
                Submissions tmp = sumbissions.get(i);
                if (item == null || tmp.getProgress() > item.getProgress())
                    item = tmp;
            }
            if (item != null)
                items.add(new SubmissionsItem(item.getProjectName(), item.getIdActivity(), item.getDate(), item.getProgress()));

            LinearLayout submissionsList = (LinearLayout) rootView.findViewById(R.id.next_submissions_list);
            ListAdapter sumbissionsAdapter = new SubmissionsAdapter(rootView.getContext(), R.layout.submission_item, items);
            for (int i = 0; i < sumbissionsAdapter.getCount(); i++) {
                View jerusalem = sumbissionsAdapter.getView(i, null, null);
                submissionsList.addView(jerusalem);
            }


            List<SusiesItem> SusieItems = new ArrayList<>();
            List<Susies> susies = Susies.listAll(Susies.class);

            if (susies.size() > 0) {
                rootView.findViewById(R.id.susies_list).setVisibility(View.VISIBLE);
                for (int i = 0; i < susies.size(); ++i) {
                    Susies tmp = susies.get(i);
                    SusieItems.add(new SusiesItem(tmp.getTitle(), tmp.getStart(), tmp.getSusie(), tmp.getType()));
                }

                LinearLayout susiesList = (LinearLayout) rootView.findViewById(R.id.susies_list);
                ListAdapter susiesAdapter = new SusiesAdapter(rootView.getContext(), R.layout.susie_item, SusieItems);
                for (int i = 0; i < susiesAdapter.getCount(); i++) {
                    View oneSusie = susiesAdapter.getView(i, null, null);
                    susiesList.addView(oneSusie);
                }
            } else {
                rootView.findViewById(R.id.susies_list).setVisibility(View.GONE);
                rootView.findViewById(R.id.susie_nothing).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.susie_button).setVisibility(View.VISIBLE);
            }


            //rootView.findViewById(R.id.marks_progress).setVisibility(View.GONE);
        }

        return rootView;
    }
}
