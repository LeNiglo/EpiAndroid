package epitech.epiandroid.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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
import epitech.epiandroid.Databases.Messages;
import epitech.epiandroid.Items.MessagesItem;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;

/**
 * Created by Styve on 12/01/2015.
 */

public class MessagesTask extends AsyncTask<Void, Void, Boolean> {
    private String token;
    private String responseString = null;
    private List<MessagesItem> userMessages = new ArrayList<>();
    private View view;

    public MessagesTask(View view, String token) {
        this.token = token;
        this.view = view;
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
                System.out.println("Création des messages et ajout à la DB");
                json = new JSONArray(responseString);
                // supprime tous les messages de la DB
                Messages.deleteAll(Messages.class);
                for (int i = 0; i < json.length(); ++i) {
                    JSONObject tmp;
                    if ((tmp = json.getJSONObject(i)) != null) {
                        JSONObject user = tmp.getJSONObject("user");
                        userMessages.add(new MessagesItem(tmp.getString("content"), tmp.getString("title"), user.getString("title"), tmp.getString("date"), user.getString("picture")));
                        // ajoute le message dans la db
                        Messages message = new Messages(tmp.getString("content"), tmp.getString("title"), user.getString("title"), tmp.getString("date"), user.getString("picture"));
                        message.save();
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(view.getContext(), "Server is down..", Toast.LENGTH_LONG).show();
                return;
            }
            ListView messages = (ListView) view.findViewById(R.id.user_messages);
            ListAdapter customAdapter = new MessagesAdapter(view.getContext(), R.layout.profil_message, userMessages);
            messages.setAdapter(customAdapter);
            view.findViewById(R.id.progress_messages).setVisibility(View.GONE);
        }
    }

}