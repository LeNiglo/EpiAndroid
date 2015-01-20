package epitech.epiandroid.Tasks;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
//import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Activity.MessagesAdapter;
import epitech.epiandroid.Activity.MessagesItem;
import epitech.epiandroid.Fragment.ProfilFragment;
import epitech.epiandroid.MyRequest;
import epitech.epiandroid.R;

/**
 * Created by Styve on 12/01/2015.
 */

public class MessagesTask extends AsyncTask<Void, Void, Boolean> {
    private String token;
    private Context ctx;
    private String responseString = null;
    private Fragment fragment;
    private List<MessagesItem> userMessages = new ArrayList<>();

    public MessagesTask(String token, Context ctx, Fragment fragment) {
        this.token = token;
        this.ctx = ctx;
        this.fragment = fragment;
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
        ((ProfilFragment) fragment).setIsMessagesDisplayed(true);
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        super.onPostExecute(success);
        JSONArray json;

        try {
            json = new JSONArray(responseString);
            if (json.getJSONObject(0) != null) {
                for (int i = 0; i < json.length() ; ++i) {
                    JSONObject tmp;
                    if ((tmp = json.getJSONObject(i)) != null) {
                        JSONObject user = tmp.getJSONObject("user");
                        TargetPic targetPic = new TargetPic(tmp.getString("content"), tmp.getString("title"), user.getString("title"), tmp.getString("date"));
                        if (user.getString("picture").equals("null")) {
                            Picasso.with(fragment.getActivity().getApplicationContext()).load(R.drawable.login_x).into(targetPic);
                        } else {
                            Picasso.with(fragment.getActivity().getApplicationContext()).load(user.getString("picture")).into(targetPic);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Toast.makeText(ctx, "Server is down..", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }
        ListView messages = (ListView) fragment.getActivity().findViewById(R.id.user_messages);
        ListAdapter customAdapter = new MessagesAdapter(fragment.getActivity().getApplicationContext(), R.layout.profil_message, userMessages);
        messages.setAdapter(customAdapter);
        fragment.getActivity().findViewById(R.id.progress_messages).setVisibility(View.GONE);
    }

    private class TargetPic implements Target {
        private String mContent;
        private String mTitle;
        private String mLogin;
        private String mDate;

        public TargetPic(String content, String title, String login, String date) { mContent = content; mTitle = title; mLogin = login; mDate = date; }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            MessagesItem item = new MessagesItem(mContent, mTitle, mLogin, mDate, (Bitmap) null);
            item.setBitmap(bitmap);
            userMessages.add(item);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            MessagesItem item = new MessagesItem(mContent, mTitle, mLogin, mDate, (Drawable) null);
            item.setDrawable(errorDrawable);
            userMessages.add(item);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            MessagesItem item = new MessagesItem(mContent, mTitle, mLogin, mDate, (Drawable) null);
            item.setDrawable(placeHolderDrawable);
            userMessages.add(item);
        }
    }
}