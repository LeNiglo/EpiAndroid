package epitech.epiandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.List;

import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.R;

/**
 * Created by Guillaume on 21/01/2015.
 */
public class PlanningSwipeAdapter extends ArraySwipeAdapter<PlanningItem> {
	public PlanningSwipeAdapter(Context context, int resource, List<PlanningItem> objects) {
		super(context, resource, objects);
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
			TextView title = (TextView) v.findViewById(R.id.item_title);
			TextView dates = (TextView) v.findViewById(R.id.item_dates);
			Button button = (Button) v.findViewById(R.id.item_button);

			if (title != null) {
				title.setText(p.getTitle());
			}
			if (dates != null) {
				dates.setText(p.getDates());
			}

		}

		return v;
	}
}
