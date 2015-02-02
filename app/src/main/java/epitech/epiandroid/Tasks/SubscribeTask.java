package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import epitech.epiandroid.Activity.DrawerActivity;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.MyRequest;

/**
 * Created by Styve on 12/01/2015.
 */

public class SubscribeTask extends AsyncTask<Void, Void, Boolean> {
    private String token;
    private String scolaryear;
    private String codemodule;
    private String codeinstance;
    private String codeacti;
	private String codeevent;
    private Context ctx;
    private String responseString = null;
    private Activity activity;

	public SubscribeTask(String scolaryear, String codemodule, String codeinstance, String codeacti, String codeevent, Context ctx, Activity activity) {
		LoginTable infos = LoginTable.listAll(LoginTable.class).get(0);
		this.token = infos.getToken();
		this.scolaryear = scolaryear;
        this.codemodule = codemodule;
        this.codeinstance = codeinstance;
        this.codeacti = codeacti;
        this.codeevent = codeevent;
        this.ctx = ctx;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        responseString = "";
        try {
            MyRequest.clearFields();
            MyRequest.addField("token", this.token);
			MyRequest.addField("scolaryear", this.scolaryear);
			MyRequest.addField("codemodule", this.codemodule);
			MyRequest.addField("codeinstance", this.codeinstance);
			MyRequest.addField("codeacti", this.codeacti);
			MyRequest.addField("codeevent", this.codeevent);

			MyRequest.CreatePost("token");

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
        Boolean err = true;
        JSONObject json;

		Log.v("Validate Token", responseString);
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

        if (!err) {
			Toast.makeText(this.ctx, "Suscribed FOR : "+this.codeevent, Toast.LENGTH_LONG).show();
		}

        this.cancel(true);
    }
}