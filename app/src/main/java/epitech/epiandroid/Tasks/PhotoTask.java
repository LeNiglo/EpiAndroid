package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import epitech.epiandroid.Activity.DrawerActivity;
import epitech.epiandroid.MyRequest;

/**
 * Created by Styve on 12/01/2015.
 */

public class PhotoTask extends AsyncTask<Void, Void, Boolean> {
    private ImageView mImageView;
    private String responseString = null;
    private String mToken = null;
    private String mLogin = null;
    private Activity activity;

    public PhotoTask(Activity activity, ImageView image, String token, String login) {
        this.mImageView = image;
        this.activity = activity;
        this.mLogin = login;
        this.mToken = token;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String TAG = "background";
        responseString = "";
        Log.v(TAG, "get photo");
        try {
            MyRequest.clearFields();
            MyRequest.addField("token", this.mToken);
            MyRequest.addField("login", this.mLogin);
			MyRequest.CreatePost("photo");

            if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                responseString = MyRequest.getResponseString();
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
        JSONObject json;

        try {
            json = new JSONObject(responseString);
            if (json.has("url")) {
                String url = json.getString("token");
                Picasso.with(activity).load(url).into(mImageView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.cancel(true);
    }
}
