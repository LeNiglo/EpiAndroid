package epitech.epiandroid.Activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import epitech.epiandroid.R;


public class MainActivity extends Activity {

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    private Context     sContext;
    private LoginTask   mLoginTask;

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
                    Toast.makeText(sContext, "Connecting as " + login.getText().toString() + "...", Toast.LENGTH_SHORT).show();
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
            try {
                Log.v(TAG, "launch background task");
                HttpClient httpclient  = new DefaultHttpClient();
                httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
                HttpResponse response;
                try {
                    HttpPost post = new HttpPost("http://epitech-api.herokuapp.com/login");
                    post.setHeader("User-Agent", System.getProperty("http.agent"));
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("login", login));
                    nameValuePairs.add(new BasicNameValuePair("password", pass));
                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    response = httpclient.execute(post);
                    StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        Log.v(TAG, "connexion ok");
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        response.getEntity().writeTo(out);
                        out.close();
                        responseString = out.toString();
                    } else if (statusLine.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                        Log.v(TAG, "connexion failed");
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        response.getEntity().writeTo(out);
                        out.close();
                        responseString = out.toString();
                    }  else if (statusLine.getStatusCode()  == HttpStatus.SC_GATEWAY_TIMEOUT || statusLine.getStatusCode()  == HttpStatus.SC_REQUEST_TIMEOUT) {
                        responseString = "null";
                        Toast.makeText(ctx, " Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
                    } else if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN){
                        responseString = "null";
                        mLoginTask.cancel(true);
                        response.getEntity().getContent().close();
                        throw new IOException(statusLine.getReasonPhrase());
                    }
                    else {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        response.getEntity().writeTo(out);
                        out.close();
                        responseString = out.toString();
                        Log.e(TAG, "connexion failed" + responseString);
                        response.getEntity().getContent().close();
                        throw new IOException(statusLine.getReasonPhrase());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                Log.v(TAG, "index=" + responseString);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            super.onPostExecute(success);
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
                    Toast.makeText(ctx, "Connected !", Toast.LENGTH_LONG).show();
                    err = false;
                } else {
                    token = "Invalid login/password combinaison";
                    Toast.makeText(ctx, "Invalid login/password combinaison", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                token = "Internal server error";
                Toast.makeText(ctx, "Internal server error", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            Log.w("response", responseString);

            TextView text = (TextView) findViewById(R.id.notes);
            text.setText(token);

            // TODO : remettre les ifs pour la prod
            if (!err) {
                Intent i = new Intent(getApplicationContext(), SwipeActivity.class);
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