package epitech.epiandroid.Tasks;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Adapters.MessagesAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Messages;
import epitech.epiandroid.Fragment.ProfilFragment;
import epitech.epiandroid.Items.MessagesItem;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;

/**
*** Created by Styve on 12/01/2015.
**/
public class MessagesTask extends AsyncTask<Void, Void, Boolean> {
    private String token;
    private String responseString = null;
    private ProfilFragment parent;
    private View view;

    public MessagesTask(View view, ProfilFragment parent) {
        LoginTable user = LoginTable.listAll(LoginTable.class).get(0);
        if (user != null)
            token = user.getToken();
        this.view = view;
        this.parent = parent;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        responseString = "";
        try {
            MyRequest.clearFields();
            MyRequest.addField("token", token);
            MyRequest.CreatePost("messages");

            if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                responseString = MyRequest.getResponseString();
            } else if (MyRequest.isStatusTimeout()) {
                Toast.makeText(view.getContext(), "Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
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
        if (success) {
            try {
                JSONArray json;
                json = new JSONArray(responseString);
                Messages.deleteAll(Messages.class);
                for (int i = 0; i < json.length(); ++i) {
                    JSONObject tmp;
                    if ((tmp = json.getJSONObject(i)) != null) {
                        JSONObject user = tmp.getJSONObject("user");
                        Messages message = new Messages(tmp.getString("content"), tmp.getString("title"), user.getString("title"), tmp.getString("date"), user.getString("picture"));
                        message.save();
                    }
                }
                LoginTable user = LoginTable.listAll(LoginTable.class).get(0);
                user.setMessagesUpdatedAt(System.currentTimeMillis());
                user.save();
            } catch (JSONException e) {
                Toast.makeText(view.getContext(), "Server is down..", Toast.LENGTH_LONG).show();
                return;
            }
            parent.LoadMessages();
        }
    }

}