package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import epitech.epiandroid.Activity.DrawerActivity;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;

/**
 * Created by Styve on 12/01/2015.
 */

public class LoginTask extends AsyncTask<Void, Void, Boolean> {
    private String login;
    private String pass;
    private Context ctx;
    private String responseString = null;
    private Activity activity;

    public LoginTask(String login, String password, Context ctx, Activity activity) {
        this.login = login;
        this.pass = password;
        this.ctx = ctx;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String TAG = "background";
        responseString = "";
        Log.v(TAG, "launch background task");
        try {
            MyRequest.clearFields();
            MyRequest.addField("login", login);
            MyRequest.addField("password", pass);
            MyRequest.CreatePost("login");

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
        Log.v(TAG, "index=" + responseString);
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        super.onPostExecute(success);
        TextView text = (TextView) activity.findViewById(R.id.notes);
        String token;
        Boolean err = true;
        JSONObject json;

        // check si le retour est bien du json
        try {
            json = new JSONObject(responseString);
            // check si il y a bien un token
            if (json.has("token")) {
                token = json.getString("token");
                err = false;
            } else if (json.has("error")) {
                token = ((JSONObject)json.get("error")).getString("message");
                Toast.makeText(ctx, token, Toast.LENGTH_SHORT).show();
            } else {
                token = "non handled error.";
            }
        } catch (JSONException e) {
            token = "Internal server error";
            Toast.makeText(ctx, "Internal server error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        Log.w("response", responseString);
        text.setText(token);
        (activity.findViewById(R.id.progressBar)).setVisibility(View.GONE);

        if (!err) {
            Intent i = new Intent(activity.getApplicationContext(), DrawerActivity.class);
            i.putExtra("token", token);
            activity.startActivity(i);
        }

        this.cancel(true);
    }
}
