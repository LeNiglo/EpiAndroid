package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Adapters.SubmissionsAdapter;
import epitech.epiandroid.Databases.Submissions;
import epitech.epiandroid.Items.SubmissionsItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.InfosTask;


public class ActivitiesFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_section_activities, container, false);

        Boolean isSubmissionsDisplayed = Submissions.listAll(Submissions.class).size() > 0;

        if (!isSubmissionsDisplayed) {
            //rootView.findViewById(R.id.marks_progress).setVisibility(View.VISIBLE);
            new InfosTask(getActivity()).execute((Void) null);
        } else {
            //rootView.findViewById(R.id.marks_progress).setVisibility(View.VISIBLE);
            List<SubmissionsItem> items = new ArrayList<>();
            List<Submissions> sumbissions = Submissions.listAll(Submissions.class);

            for (int i = 0; i < sumbissions.size(); ++i) {
                Submissions item = sumbissions.get(i);
                items.add(new SubmissionsItem(item.getProjectName(), item.getIdActivity(), item.getDate(), item.getProgress()));
            }

            LinearLayout submissionsList = (LinearLayout) rootView.findViewById(R.id.next_submissions_list);
            ListAdapter sumbissionsAdapter = new SubmissionsAdapter(rootView.getContext(), R.layout.submission_item, items);
            for (int i = 0; i < sumbissionsAdapter.getCount(); i++) {
                View jerusalem = sumbissionsAdapter.getView(i, null, null);
                submissionsList.addView(jerusalem);
            }
            //submissionsList.setAdapter(sumbissionsAdapter);
            //rootView.findViewById(R.id.marks_progress).setVisibility(View.GONE);
        }

        return rootView;
    }
}
