package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import epitech.epiandroid.Activity.DrawerActivity;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.PhotoClass;
import epitech.epiandroid.R;

/**
 * Created by Styve on 12/01/2015.
 */

public class InfosTask extends AsyncTask<Void, Void, Boolean> {
    private String token;
    private Context ctx;
    private String responseString = null;
    private Fragment fragment;

    public InfosTask(String token, Context ctx, Fragment fragment) {
        this.token = token;
        this.ctx = ctx;
        this.fragment = fragment;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String TAG = "background";
        responseString = "";
        Log.v(TAG, "launch background task");
        try {
            MyRequest.clearFields();
            MyRequest.addField("token", token);
            MyRequest.CreatePost("infos");

            if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                responseString = MyRequest.getResponseString();
            }
            else if (MyRequest.isStatusTimeout()) {
                Toast.makeText(ctx, "Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
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
        //Log.v(TAG, "index=" + responseString);
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        super.onPostExecute(success);
        if (fragment != null && fragment.getActivity() != null) {
        TextView user_name = (TextView) fragment.getActivity().findViewById(R.id.user_name);
        TextView user_surname = (TextView) fragment.getActivity().findViewById(R.id.user_surname);
        TextView user_login = (TextView) fragment.getActivity().findViewById(R.id.user_login);
        TextView user_logtime = (TextView) fragment.getActivity().findViewById(R.id.user_logtime);
        ImageView user_picture = (ImageView) fragment.getActivity().findViewById(R.id.userPic);
        ImageView user_picture2 = (ImageView) fragment.getActivity().findViewById(R.id.user_picture);
        TextView user_semester = (TextView) fragment.getActivity().findViewById(R.id.user_semester);
        JSONObject json;

        // check si le retour est bien du json
        try {
            json = new JSONObject(responseString);
            // check si il y a bien un token
            if (json.has("ip")) {
                Double active_log;
                Double nslog_norm;
                Double nslog_min;

                JSONObject infos = json.getJSONObject("infos");
                JSONObject current = json.getJSONObject("current");
                user_name.setText(infos.getString("lastname").toUpperCase());
                user_surname.setText(infos.getString("firstname"));
                user_login.setText(infos.getString("login"));

                active_log = Double.valueOf(current.getString("active_log"));
                nslog_min = Double.valueOf(current.getString("nslog_min"));
                nslog_norm = Double.valueOf(current.getString("nslog_norm"));
                if (active_log < nslog_min) {
                    user_logtime.setTextColor(Color.parseColor("#FF0000"));
                    user_logtime.setText(active_log.intValue() + " < " + nslog_norm.intValue());
                }
                else if (active_log < nslog_norm) {
                    user_logtime.setTextColor(Color.parseColor("#FFAA00"));
                    user_logtime.setText(active_log.intValue() + " < " + nslog_norm.intValue());
                }
                else {
                    user_logtime.setTextColor(Color.parseColor("#1FA055"));
                    user_logtime.setText(active_log.intValue() + " > " + nslog_norm.intValue());
                }

                user_semester.setText(ctx.getString(R.string.semester) + " " + current.getString("semester_code"));

                new PhotoClass("https://cdn.local.epitech.eu/userprofil/" + infos.getString("picture"), user_picture).execute((Void) null);
                new PhotoClass("https://cdn.local.epitech.eu/userprofil/" + infos.getString("picture"), user_picture2).execute((Void) null);
            } else if (json.has("error")) {
                token = ((JSONObject)json.get("error")).getString("message");
                Toast.makeText(ctx, token, Toast.LENGTH_SHORT).show();
            } else {
                token = "non handled error.";
            }
        } catch (JSONException e) {
            user_name.setText("UNKNOWN");
            user_surname.setText("User");
            user_login.setText("unknow_u");
            Toast.makeText(ctx, "Error while parsing server response", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        }

        //Log.w("response", responseString);
        //(activity.findViewById(R.id.progressBar)).setVisibility(View.GONE);

        this.cancel(true);
    }
}
