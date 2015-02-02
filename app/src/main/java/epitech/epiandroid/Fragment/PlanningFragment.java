package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Databases.Planning;
import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.Adapters.PlanningSwipeAdapter;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.PlanningTask;


public class PlanningFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_section_planning, container, false);

		Boolean isMarksDisplayed = Planning.listAll(Planning.class).size() > 0;

		if (!isMarksDisplayed) {
			rootView.findViewById(R.id.planning_progress).setVisibility(View.VISIBLE);
			new PlanningTask(getActivity(), rootView).execute((Void) null);
		} else {
		    rootView.findViewById(R.id.planning_progress).setVisibility(View.VISIBLE);
			List<PlanningItem> items = new ArrayList<>();
			List<Planning> marks = Planning.listAll(Planning.class);

			for (int i = 0; i < marks.size(); ++i) {
				Planning item = marks.get(i);
				items.add(new PlanningItem(item.getTitle(), item.getDates()));
			}

			ListView planning = (ListView) rootView.findViewById(R.id.planning_swipe);
			ListAdapter planningSwipeAdapter = new PlanningSwipeAdapter(rootView.getContext(), R.layout.planning_item, items);

			planning.setAdapter(planningSwipeAdapter);
			rootView.findViewById(R.id.planning_progress).setVisibility(View.GONE);
		}
	    return rootView;
    }
}
