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

		new InfosTask(getActivity()).execute((Void) null);
        return rootView;
    }
}
