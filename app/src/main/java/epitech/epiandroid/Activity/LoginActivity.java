package epitech.epiandroid.Activity;
import epitech.epiandroid.MyRequest;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import epitech.epiandroid.R;


public class LoginActivity extends Activity {

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    private Context     sContext;
    private LoginTask   mLoginTask;
    private View        mProgressView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sContext = getApplicationContext();
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText login = (EditText) findViewById(R.id.login);
                EditText mdp = (EditText) findViewById(R.id.mdp);

                if (login.getText().toString().equals("") || mdp.getText().toString().equals(""))
                    Toast.makeText(sContext, "Login or password is missing", Toast.LENGTH_SHORT).show();
                else {
                    ((TextView)(findViewById(R.id.notes))).setText("Connecting...");
                    (findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
                    mLoginTask = new LoginTask(login.getText().toString(), mdp.getText().toString(), sContext.getApplicationContext());
                    mLoginTask.execute((Void) null);
                }
            }
        });
    }

    public class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private String login;
        private String pass;
        private Context ctx;
        private String responseString = null;

        LoginTask(String login, String password, Context ctx) {
            this.login = login;
            this.pass = password;
            this.ctx = ctx;
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
                    mLoginTask.cancel(true);
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
            TextView text = (TextView) findViewById(R.id.notes);
            String token = "null";
            Boolean err = true;
            JSONObject json;

            // check si le retour est bien du json
            try {
                json = new JSONObject(responseString);
                // check si il y a bien un token
                if (json.has("token") || json.has("ip")) {
                    if (json.has("token"))
                        token = json.getString("token");
                    else if (json.has("ip"))
                        token = json.getString("ip");
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
            (findViewById(R.id.progressBar)).setVisibility(View.GONE);

            // TODO : remettre les ifs pour la prod
            if (!err) {
                Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
                i.putExtra("token", token);
                i.putExtra("login", login);
                i.putExtra("pass", pass);
                startActivity(i);
            }

            this.cancel(true);
            mLoginTask = null;
        }
    }
}