package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import epitech.epiandroid.Adapters.MarksAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Marks;
import epitech.epiandroid.Fragment.NotesFragment;
import epitech.epiandroid.Items.MarksItem;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;

/**
 * Created by Styve on 12/01/2015.
 */

public class MarksTask extends AsyncTask<Void, Void, Boolean> {
    private String responseString = null;
    private Activity activity;
    private NotesFragment parent;
    private MarksAdapter adapter;

    public MarksTask(Activity activity, NotesFragment parent, MarksAdapter adapter) {
        this.activity = activity;
        this.parent = parent;
        this.adapter = adapter;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String TAG = "background";
        responseString = "";
        Log.v(TAG, "launch marks task");
        try {
            LoginTable infos = LoginTable.listAll(LoginTable.class).get(0);
            String token = infos.getToken();
            MyRequest.clearFields();
            MyRequest.addField("token", token);
            MyRequest.CreatePost("marks");

            if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                responseString = MyRequest.getResponseString();
            } else if (MyRequest.isStatusTimeout()) {
                Toast.makeText(activity, "Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
            } else if (MyRequest.isStatusForbidden()) {
                throw new IOException(MyRequest.getReasonPhrase());
            } else {
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

        if (success) {
            try {
                json = new JSONObject(responseString);
                if (json.has("notes")) {
                    Marks.deleteAll(Marks.class);
                    JSONArray marks = json.getJSONArray("notes");
                    parent.onMarksDisplayed();
                    for (int i = 0; i < marks.length(); ++i) {
                        JSONObject tmp = marks.getJSONObject(i);
                        final Marks mark = new Marks();
                        Double note = 0.0;
                        try {
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
                        color = Color.rgb(r, g, 70);
                        mark.setNote(tmp.getString("final_note"));
                        mark.setModuleName(tmp.getString("titlemodule"));
                        mark.setProjectName(tmp.getString("title"));
                        mark.setContainerColor(color);
                        mark.setModuleImage(R.drawable.module);
                        mark.save();
                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                adapter.add(new MarksItem(mark.getNote(), mark.getModuleName(), mark.getProjectName(), mark.getContainerColor(), mark.getModuleImage()));
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    LoginTable user = LoginTable.listAll(LoginTable.class).get(0);
                    user.setMarksUpdatedAt(System.currentTimeMillis());
                    user.save();
                } else if (json.has("error")) {
                    token = ((JSONObject) json.get("error")).getString("message");
                    Toast.makeText(activity, token, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
