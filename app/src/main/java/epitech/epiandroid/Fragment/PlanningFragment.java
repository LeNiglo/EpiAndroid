package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Databases.Planning;
import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.Adapters.PlanningSwipeAdapter;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.PlanningTask;


public class PlanningFragment extends Fragment {

    View rootView;
	private static Integer moveWeek = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_planning, container, false);

		rootView.findViewById(R.id.prev_week).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				moveWeek--;
                rootView.findViewById(R.id.planning_progress).setVisibility(View.VISIBLE);
				new PlanningTask(getActivity(), rootView, PlanningFragment.this, moveWeek).execute((Void) null);
			}
		});
		rootView.findViewById(R.id.next_week).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				moveWeek++;
                rootView.findViewById(R.id.planning_progress).setVisibility(View.VISIBLE);
				new PlanningTask(getActivity(), rootView, PlanningFragment.this, moveWeek).execute((Void) null);
			}
		});

		Boolean isMarksDisplayed = Planning.listAll(Planning.class).size() > 0;

		if (!isMarksDisplayed) {
			rootView.findViewById(R.id.planning_progress).setVisibility(View.VISIBLE);
			new PlanningTask(this.getActivity(), rootView, this, moveWeek).execute((Void) null);
		} else {
			this.loadPlanning();
		}
		return rootView;
    }

	public void loadPlanning() {
		rootView.findViewById(R.id.planning_progress).setVisibility(View.VISIBLE);
		List<PlanningItem> items = new ArrayList<>();
		List<Planning> plannings = Planning.listAll(Planning.class);

		for (int i = 0; i < plannings.size(); ++i) {
			Planning item = plannings.get(i);
			items.add(new PlanningItem(item.getTitle(),
                    item.getDates(),
                    item.getCodemodule(),
                    item.getCodeacti(),
                    item.getCodeevent(),
                    item.getCodeinstance(),
                    item.getScolaryear(),
                    item.getRegisterStudent(),
                    item.getAllowToken(),
                    item.getModuleRegistered(),
                    item.getEventRegistered()));
		}

		this.onPlanningLoaded(items);
	}


	public void onPlanningLoaded(List<PlanningItem> items) {
		ListView planning = (ListView) rootView.findViewById(R.id.planning_swipe);
		ListAdapter planningSwipeAdapter = new PlanningSwipeAdapter(rootView.getContext(), R.layout.planning_item, items);

		planning.setAdapter(planningSwipeAdapter);
		rootView.findViewById(R.id.planning_progress).setVisibility(View.INVISIBLE);
	}
}