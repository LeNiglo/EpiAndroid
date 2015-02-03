package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import epitech.epiandroid.Databases.Activities;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.ProfilInfos;
import epitech.epiandroid.Databases.Submissions;
import epitech.epiandroid.Databases.Susies;
import epitech.epiandroid.Fragment.ActivitiesFragment;
import epitech.epiandroid.Fragment.ProfilFragment;
import epitech.epiandroid.Items.SubmissionsItem;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by Styve on 12/01/2015.
 */

public class InfosTask extends AsyncTask<Void, Void, Boolean> {
    private String token;
    private Context ctx;
    private String responseString = null;
    private Fragment parent;

    public InfosTask(Context ctx, Fragment parent) {
        this.ctx = ctx;
        LoginTable user = LoginTable.listAll(LoginTable.class).get(0);
        if (user != null)
            token = user.getToken();
        this.parent = parent;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String TAG = "background";
        responseString = "";
        try {
            MyRequest.clearFields();
            MyRequest.addField("token", token);
            MyRequest.CreatePost("infos");

            if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                responseString = MyRequest.getResponseString();
            } else if (MyRequest.isStatusTimeout()) {
                Toast.makeText(ctx, "Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
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
        if (ctx != null && success) {
            JSONObject json;

            try {
                json = new JSONObject(responseString);
                if (json.has("ip")) {

                    try {
                        JSONObject board = json.getJSONObject("board");
                        try {
                            Submissions.deleteAll(Submissions.class);
                            JSONArray projets = board.getJSONArray("projets");
                            for (int i = 0; i < projets.length(); ++i) {
                                JSONObject tmp = projets.getJSONObject(i);
                                Submissions submission = new Submissions();
                                submission.setProjectName(tmp.getString("title"));
                                submission.setIdActivity(tmp.getString("id_activite"));
                                submission.setProgress(Double.valueOf(tmp.getString("timeline_barre")).intValue());
                                submission.setDate(ctx.getResources().getString(R.string.from) + " " + tmp.getString("timeline_start").substring(0, 10) + " "
                                        + ctx.getResources().getString(R.string.to) + " " + tmp.getString("timeline_end").substring(0, 10));
                                submission.save();
                            }
                        } catch (Exception ignored) {}

                        /*try {
                            Activities.deleteAll(Activities.class);
                            JSONArray activites = board.getJSONArray("activites");
                            for (int i = 0; i < activites.length(); ++i) {
                                JSONObject tmp = activites.getJSONObject(i);
                                Activities activite = new Activities();
                                activite.save();
                            }
                        } catch (Exception ignored) {}*/

                        try {
                            Susies.deleteAll(Susies.class);
                            /*JSONArray susies = board.getJSONArray("susies");
                            for (int i = 0; i < susies.length(); ++i) {
                                JSONObject tmp = susies.getJSONObject(i);
                                Susies susie = new Susies();
                                susie.setTitle(tmp.getString("title"));
                                susie.setStart(tmp.getString("timeline_start"));
                                susie.setSusie(tmp.getString("intervenant"));
                                susie.setType(tmp.getString("type"));
                                susie.save();
                            }*/
                        } catch (Exception ignored) {}

                        JSONObject infos = json.getJSONObject("infos");
                        JSONObject current = json.getJSONObject("current");

                        ProfilInfos.deleteAll(ProfilInfos.class);
                        ProfilInfos myInfos = new ProfilInfos();
                        myInfos.setFirstName(infos.getString("firstname"));
                        myInfos.setLastName(infos.getString("lastname").toUpperCase());
                        myInfos.setLogin(infos.getString("login"));
                        myInfos.setPicUrl("https://cdn.local.epitech.eu/userprofil/" + infos.getString("picture"));
                        myInfos.setActiveLog(Double.valueOf(current.getString("active_log")).toString());
                        myInfos.setNsLogMin(Double.valueOf(current.getString("nslog_min")).toString());
                        myInfos.setNsLogNorm(Double.valueOf(current.getString("nslog_norm")).toString());
                        myInfos.setSemester(current.getString("semester_code"));
                        myInfos.save();

                        LoginTable user = LoginTable.listAll(LoginTable.class).get(0);
                        user.setFirstName(infos.getString("firstname"));
                        user.setLastName(infos.getString("lastname"));
                        user.setPicUrl("https://cdn.local.epitech.eu/userprofil/" + infos.getString("picture"));
                        user.setLogin(infos.getString("login"));
                        user.setInfosUpdatedAt(System.currentTimeMillis());
                        user.save();

                        ((MaterialNavigationDrawer) ctx).getToolbar().setTitle(infos.getString("firstname"));
                        ((MaterialNavigationDrawer) ctx).getToolbar().setTitleTextColor(Color.parseColor("#DEDEDE"));
                        ((MaterialNavigationDrawer) ctx).getCurrentAccount().setTitle(infos.getString("login"));
                        ((MaterialNavigationDrawer) ctx).getCurrentAccount().setSubTitle(infos.getString("firstname") + " " + infos.getString("lastname").toUpperCase());
                        ((MaterialNavigationDrawer) ctx).notifyAccountDataChanged();
                        Picasso.with(ctx).load("https://cdn.local.epitech.eu/userprofil/" + infos.getString("picture")).into((ImageView) ((Activity) ctx).findViewById(R.id.user_photo));

                        try {
                            ((ProfilFragment) parent).onProfileLoaded();
                        } catch (ClassCastException ignored) {
                        }
                        try {
                            ((ActivitiesFragment) parent).LoadSusies();
                            ((ActivitiesFragment) parent).LoadSubmissions();
                        } catch (ClassCastException ignored) {
                        }
                    } catch (ClassCastException ignored) {}
                } else if (json.has("error")) {
                    token = ((JSONObject) json.get("error")).getString("message");
                    Toast.makeText(ctx, token, Toast.LENGTH_SHORT).show();
                } else {
                    token = "non handled error.";
                }
            } catch (JSONException e) {
                Toast.makeText(ctx, "Error while parsing server response", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}
