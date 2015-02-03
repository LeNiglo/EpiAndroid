package epitech.epiandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.SubscribeTask;
import epitech.epiandroid.Tasks.TokenTask;
import epitech.epiandroid.Tasks.UnSubscribeTask;

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
                String start = p.getDates().substring(0, 18);
                String end = p.getDates().substring(p.getDates().length() - 19, p.getDates().length() - 1);
                try {
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date MyDate = newDateFormat.parse(start);
                    Date MyDate2 = newDateFormat.parse(end);
                    newDateFormat.applyPattern("EEEE dd MMMM");
                    start = newDateFormat.format(MyDate);
                    newDateFormat.applyPattern("HH:mm");
                    end = newDateFormat.format(MyDate2);
                    start += ": " + v.getResources().getString(R.string.from) + " " + newDateFormat.format(MyDate) + " " + v.getResources().getString(R.string.to) + " " + end;
                } catch (Exception e) {
                    start = "Parsing date error";
                }
                dates.setText(start);
            }
            try {
                if (button != null && square != null) {
                    if (p.getAllowToken() && p.getEventRegistered().equals("registered")) { // on peut valider son token !
                        button.setText(v.getResources().getString(R.string.planning_token));
                        button.setBackgroundColor(Color.parseColor("#FFB266"));
                        square.setBackgroundColor(Color.parseColor("#FFB266"));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final View v2 = v;
								final int layout = R.layout.input_text;
                                new MaterialDialog.Builder(getContext())
                                        .title(R.string.token_title)
                                        .positiveColorRes(R.color.material_blue_500)
                                        .neutralColorRes(R.color.material_blue_500)
                                        .negativeColorRes(R.color.material_blue_500)
                                        .positiveText(R.string.ok)
                                        .customView(layout, true)
                                        .callback(new MaterialDialog.ButtonCallback() {
											@Override
											public void onPositive(MaterialDialog dialog) {
												View view = dialog.getCustomView();
												new TokenTask(p.getScolaryear(), p.getCodemodule(), p.getCodeinstance(), p.getCodeacti(), p.getCodeevent(), ((EditText) view.findViewById(R.id.validate_token)).getText().toString(), view.getContext(), (Activity) view.getContext()).execute((Void) null);
											}
										})
                                        .show();
                            }
                        });
                    } else if (p.getRegisterStudent() && p.getModuleRegistered() && p.getEventRegistered().equals("null")) { // on peut s'enregistrer
                        button.setText(v.getResources().getString(R.string.planning_register));
                        button.setBackgroundColor(Color.parseColor("#FFFF66"));
                        square.setBackgroundColor(Color.parseColor("#FFFF66"));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
								new SubscribeTask(p.getScolaryear(), p.getCodemodule(), p.getCodeinstance(), p.getCodeacti(), p.getCodeevent(), v.getContext(), (Activity) v.getContext()).execute((Void) null);
                            }
                        });
                    } else if (p.getRegisterStudent() && p.getEventRegistered().equals("registered")) { // on peut se désinscrire
                        button.setText(v.getResources().getString(R.string.planning_unregister));
                        button.setBackgroundColor(Color.parseColor("#FF6666"));
                        square.setBackgroundColor(Color.parseColor("#FF6666"));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
								new UnSubscribeTask(p.getScolaryear(), p.getCodemodule(), p.getCodeinstance(), p.getCodeacti(), p.getCodeevent(), v.getContext(), (Activity) v.getContext()).execute((Void) null);
                            }
                        });
                    } else if (p.getEventRegistered().equals("present")) { // on a été présent
                        button.setText(v.getResources().getString(R.string.planning_present));
                        button.setBackgroundColor(Color.parseColor("#66FF66"));
                        square.setBackgroundColor(Color.parseColor("#66FF66"));
                        button.setClickable(false);
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
