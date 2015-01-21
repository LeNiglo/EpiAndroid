package epitech.epiandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.List;

import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.R;

/**
 * Created by Guillaume on 21/01/2015.
 */
public class PlanningSwipeAdapter extends ArraySwipeAdapter {
	public PlanningSwipeAdapter(Context context, int resource) {
		super(context, resource);
	}

	public PlanningSwipeAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public PlanningSwipeAdapter(Context context, int resource, Object[] objects) {
		super(context, resource, objects);
	}

	public PlanningSwipeAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public PlanningSwipeAdapter(Context context, int resource, List objects) {
		super(context, resource, objects);
	}

	public PlanningSwipeAdapter(Context context, int resource, int textViewResourceId, List objects) {
		super(context, resource, textViewResourceId, objects);
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		return R.id.swipe;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.planning_item, null);
		}

		PlanningItem p = (PlanningItem) getItem(position);
		if (p != null) {

		}

		return v;
	}
}
