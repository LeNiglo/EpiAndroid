package epitech.epiandroid.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.Adapters.PlanningSwipeAdapter;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;


public class PlanningFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_section_planning, container, false);
		rootView.findViewById(R.id.planning_progress).setVisibility(View.VISIBLE);
		new PlanningTask(getActivity(), rootView).execute((Void) null);
		return rootView;
    }

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
					for (int i = 0; i < json.length(); ++i) {
						JSONObject tmp;
						if ((tmp = json.getJSONObject(i)) != null) {
							if (tmp.getBoolean("module_registered") == true)
								userPlanning.add(new PlanningItem(tmp.getString("acti_title"), tmp.getString("start") + " " + tmp.getString("end"), tmp.getBoolean("module_registered")));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				ListView planning = (ListView) view.findViewById(R.id.planning_swipe);
				ListAdapter planningSwipeAdapter = new PlanningSwipeAdapter(this.view.getContext(), R.layout.planning_item, userPlanning);
				planning.setAdapter(planningSwipeAdapter);

				view.findViewById(R.id.planning_progress).setVisibility(View.GONE);
			}
		}
	}

}
