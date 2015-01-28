package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Activity.DrawerActivity;
import epitech.epiandroid.Activity.LoginActivity;
import epitech.epiandroid.Adapters.MarksAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Marks;
import epitech.epiandroid.Items.MarksItem;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;

/**
 * Created by Styve on 12/01/2015.
 */

public class MarksTask extends AsyncTask<Void, Void, Boolean> {
    private String responseString = null;
    private Activity activity;
    private View view;

    public MarksTask(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String TAG = "background";
        responseString = "";
        Log.v(TAG, "launch login task");
        try {
            LoginTable infos = LoginTable.listAll(LoginTable.class).get(0);
            String token = infos.getToken();
            MyRequest.clearFields();
            MyRequest.addField("token", token);
            MyRequest.CreatePost("marks");

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
        String token;
        JSONObject json;
        List<MarksItem> items = new ArrayList<>();

        if (success) {
            try {
                json = new JSONObject(responseString);
                if (json.has("notes")) {
                    Marks.deleteAll(Marks.class);
                    JSONArray marks = json.getJSONArray("notes");
                    for (int i = 0; i < marks.length(); ++i) {
                        JSONObject tmp = marks.getJSONObject(i);
                        Marks mark = new Marks();
                        Double note = 0.0;
                        try {
                            System.out.println("note string : " + tmp.getString("final_note"));
                            note = Double.valueOf(tmp.getString("final_note"));
                        } catch (Exception e) {
                            Log.e("Note:", e.getMessage());
                        }
                        int color;
                        int r = 181;
                        int g = 0;
                        if (note < 0) {
                            r = 0;
                            g = 0;
                        } else if (note >= 20 && note < 100) {
                            r = 50;
                            g = 181;
                        } else if (note >= 100) {
                            if (note < 400) {
                                r = 0;
                                g = 0;
                            } else {
                                r -= (note - 400) * 0.2316;
                                g += (note - 400) * 0.3016;
                            }
                        } else {
                            r -= note * 7;
                            g += note * 9;
                        }
                        Log.v("Note", note.toString() + " (" + r + "," + g + ",0)");
                        color = Color.rgb(r, g, 70);
                        mark.setNote(tmp.getString("final_note"));
                        mark.setModuleName(tmp.getString("titlemodule"));
                        mark.setProjectName(tmp.getString("title"));
                        mark.setContainerColor(color);
                        mark.setModuleImage(R.drawable.powered_by_google_dark);
                        mark.save();
                        items.add(new MarksItem(tmp.getString("final_note"), tmp.getString("titlemodule"), tmp.getString("title"), color, R.drawable.powered_by_google_dark));
                    }
                } else if (json.has("error")) {
                    token = ((JSONObject) json.get("error")).getString("message");
                    Toast.makeText(activity, token, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListView marksList = (ListView) view.findViewById(R.id.marks_list);
            ListAdapter customAdapter = new MarksAdapter(view.getContext(), R.layout.marks_item, items);
            marksList.setAdapter(customAdapter);
            view.findViewById(R.id.marks_progress).setVisibility(View.GONE);
        }
    }
}
