package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Fragment.PlanningFragment;
import epitech.epiandroid.Fragment.ProjetsFragment;
import epitech.epiandroid.Items.PlanningItem;
import epitech.epiandroid.MyRequest;

/**
** Created by julien on 02/02/15.
**
*/


public class ProjectTask extends AsyncTask<Void, Void, Boolean> {
    private String responseString = null;
    private Activity activity;
    private View view;
    private ProjetsFragment parent;

    public ProjectTask(Activity activity, View view, ProjetsFragment parent) {
        this.activity = activity;
        this.view = view;
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
            MyRequest.CreatePost("projects");


            if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                responseString = MyRequest.getResponseString();
            } else if (MyRequest.isStatusTimeout()) {
                Toast.makeText(activity, "Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
            } else if (MyRequest.isStatusForbidden()) {
                throw new IOException(MyRequest.getReasonPhrase());
            } else {
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

        ArrayList<JSONObject> list = new ArrayList<>();

        if (success) {
            try {
                try {
                    json = new JSONArray(responseString);
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject c = (JSONObject) json.get(i);
                        if (c.get("type_acti").equals("Projets") || c.get("type_acti").equals("Projet")) {
                            list.add(c);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                return;
            }
        }
        parent.completelistview(view, list, activity.getApplicationContext());
    }
}
