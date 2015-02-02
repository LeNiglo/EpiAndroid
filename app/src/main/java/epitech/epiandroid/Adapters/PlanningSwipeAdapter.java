package epitech.epiandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.List;

import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.SubscribeTask;

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

		final PlanningItem p = (PlanningItem) getItem(position);
		if (p != null) {
			TextView module = (TextView) v.findViewById(R.id.item_module);
			TextView title = (TextView) v.findViewById(R.id.item_title);
			TextView dates = (TextView) v.findViewById(R.id.item_dates);
            Button button = (Button) v.findViewById(R.id.item_button);
            View square = v.findViewById(R.id.item_square);

			if (module != null) {
				module.setText(p.getCodemodule());
			}
			if (title != null) {
				title.setText(p.getTitle());
			}
			if (dates != null) {
				dates.setText(p.getDates());
			}
            try {
                if (button != null && square != null && p.getEventRegistered() != null) {
                    if (p.getAllowToken() && p.getRegisterStudent() && !p.getEventRegistered().equals("null")) { // on peut valider son token !
                        button.setText(v.getResources().getString(R.string.planning_token));
                        button.setBackgroundColor(Color.parseColor("#66FF66"));
                        square.setBackgroundColor(Color.parseColor("#66FF66"));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "CLICKED FOR : token", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (p.getRegisterStudent() && p.getModuleRegistered() && p.getEventRegistered().equals("null")) { // on peut s'enregistrer
                        button.setText(v.getResources().getString(R.string.planning_register));
                        button.setBackgroundColor(Color.parseColor("#FFFF66"));
                        square.setBackgroundColor(Color.parseColor("#FFFF66"));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "CLICKED FOR : register", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (p.getRegisterStudent() && !p.getEventRegistered().equals("null")) { // on peut se désinscrire
                        button.setText(v.getResources().getString(R.string.planning_unregister));
                        button.setBackgroundColor(Color.parseColor("#FF6666"));
                        square.setBackgroundColor(Color.parseColor("#FF6666"));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "CLICKED FOR : unregister", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else { // on peut rien faire :)
                        button.setText(v.getResources().getString(R.string.planning_nothing));
                        button.setBackgroundColor(Color.parseColor("#E0E0E0"));
                        square.setBackgroundColor(Color.parseColor("#E0E0E0"));
                        button.setClickable(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

		}

		return v;
	}
}
