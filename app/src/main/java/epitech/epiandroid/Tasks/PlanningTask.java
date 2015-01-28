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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import epitech.epiandroid.Adapters.MarksAdapter;
import epitech.epiandroid.Adapters.PlanningSwipeAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Marks;
import epitech.epiandroid.Databases.Planning;
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

    public PlanningTask(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String TAG = "background";
        responseString = "";
        Log.v(TAG, "launch planning task");
        try {
            LoginTable infos = LoginTable.listAll(LoginTable.class).get(0);
            String token = infos.getToken();
            MyRequest.clearFields();
            MyRequest.addField("token", token);
			String start= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            MyRequest.addField("start", start);
			long theFuture = System.currentTimeMillis() + (86400 * 7 * 1000);
			Date nextWeek = new Date(theFuture);
			String end= new SimpleDateFormat("yyyy-MM-dd").format(nextWeek);
            MyRequest.addField("end", end);
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
                Log.e(TAG, "connexion failed" + responseString);
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
						String dates = tmp.getString("start") + " " + tmp.getString("end");
						userPlanning.add(new PlanningItem(tmp.getString("acti_title"), dates));
						Planning item = new Planning();
                        item.setTitle(tmp.getString("acti_title"));
                        item.setDates(dates);
						item.save();
                        System.out.println(dates);
					}
				}
			} catch (JSONException e) {
                e.printStackTrace();
            }

			ListView planning = (ListView) view.findViewById(R.id.planning_swipe);
			ListAdapter planningSwipeAdapter = new PlanningSwipeAdapter(this.view.getContext(), R.layout.planning_item, userPlanning);
			planning.setAdapter(planningSwipeAdapter);

            //view.findViewById(R.id.marks_progress).setVisibility(View.GONE);
        }
    }
}
