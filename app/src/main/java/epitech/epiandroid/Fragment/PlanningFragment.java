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

import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.Adapters.PlanningSwipeAdapter;
import epitech.epiandroid.R;


public class PlanningFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_section_planning, container, false);

		List<PlanningItem> items = new ArrayList<>();

		items.add(new PlanningItem("Yolo Swag #Yolo", "Yolo", "simonn_s", "05/07/58"));

		ListView planning = (ListView) rootView.findViewById(R.id.planning_swipe);
		ListAdapter planningSwipeAdapter = new PlanningSwipeAdapter(this.getActivity().getApplicationContext(), R.layout.planning_item, items);

		planning.setAdapter(planningSwipeAdapter);

        return rootView;
    }
}
