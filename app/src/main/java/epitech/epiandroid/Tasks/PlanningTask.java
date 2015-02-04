package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import epitech.epiandroid.Adapters.MarksAdapter;
import epitech.epiandroid.Adapters.PlanningSwipeAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Marks;
import epitech.epiandroid.Databases.Planning;
import epitech.epiandroid.Fragment.PlanningFragment;
import epitech.epiandroid.Items.MarksItem;
import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;

/**
 * Created by Styve on 12/01/2015.
 */

public class PlanningTask extends AsyncTask<Void, Void, Boolean> {
    private String responseString = null;
    private Activity activity;
	private List<PlanningItem> userPlanning = new ArrayList<>();
    private View view;
	private Integer moveWeek;
	private PlanningFragment parent;

    public PlanningTask(Activity activity, View view, PlanningFragment parent, Integer moveWeek) {
        this.activity = activity;
        this.view = view;
		this.moveWeek = moveWeek;
		this.parent = parent;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        responseString = "";
        try {
            LoginTable infos = LoginTable.listAll(LoginTable.class).get(0);
            String token = infos.getToken();
            MyRequest.clearFields();
            MyRequest.addField("token", token);


			Calendar c = GregorianCalendar.getInstance(Locale.FRANCE);
			c.add(Calendar.DATE, 7*this.moveWeek);
			c.setFirstDayOfWeek(Calendar.MONDAY);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

			String startDate = df.format(c.getTime());
			c.add(Calendar.DATE, 6);
			String endDate = df.format(c.getTime());

            MyRequest.addField("start", startDate);
            MyRequest.addField("end", endDate);

			MyRequest.CreatePost("planning");

            if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                responseString = MyRequest.getResponseString();
            }
            else if (MyRequest.isStatusTimeout()) {
                Toast.makeText(activity, "Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
            }
            else if (MyRequest.isStatusForbidden()) {
                throw new IOException(MyRequest.getReasonPhrase());
            }
            else {
                responseString = MyRequest.getResponseString();
                throw new IOException(MyRequest.getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        super.onPostExecute(success);
        JSONArray json;

        if (success) {
            try {
				json = new JSONArray(responseString);
				Planning.deleteAll(Planning.class);
				for (int i = 0; i < json.length(); ++i) {
					JSONObject tmp;
					if ((tmp = json.getJSONObject(i)) != null) {
						if (tmp.has("module_registered") && tmp.getBoolean("module_registered")) {
							userPlanning.add(new PlanningItem(tmp.getString("acti_title"),
                                    tmp.getString("start") + " " + tmp.getString("end"),
                                    tmp.getString("codemodule"),
                                    tmp.getString("codeacti"),
                                    tmp.getString("codeevent"),
                                    tmp.getString("codeinstance"),
                                    tmp.getString("scolaryear"),
                                    tmp.getBoolean("register_student"),
                                    tmp.getBoolean("allow_token"),
                                    tmp.getBoolean("module_registered"),
                                    tmp.getString("event_registered")));

							Planning item = new Planning();
							item.setTitle(tmp.getString("acti_title"));
							item.setDates(tmp.getString("start") + " " + tmp.getString("end"));
							item.setCodeacti(tmp.getString("codeacti"));
							item.setCodeinstance(tmp.getString("codeinstance"));
							item.setCodeevent(tmp.getString("codeevent"));
							item.setCodemodule(tmp.getString("codemodule"));
							item.setScolaryear(tmp.getString("scolaryear"));
							item.setRegisterStudent(tmp.getBoolean("register_student"));
							item.setAllowToken(tmp.getBoolean("allow_token"));
							item.setModuleRegistered(tmp.getBoolean("module_registered"));
							item.setEventRegistered(tmp.getString("event_registered"));
							item.save();
						}
					}
				}
			} catch (JSONException e) {
                e.printStackTrace();
            }

			parent.onPlanningLoaded(userPlanning);
        }
    }
}
