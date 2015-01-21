package epitech.epiandroid.Tasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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

//import com.squareup.picasso.Target;

/**
 * Created by Styve on 12/01/2015.
 */

public class MessagesTask extends AsyncTask<Void, Void, Boolean> {
    private String token;
    private Context ctx;
    private String responseString = null;
    private Fragment fragment;
    private List<MessagesItem> userMessages = new ArrayList<>();
    private String jsonString;
    private Boolean noDL;
    private View view;

    public MessagesTask(String token, Context ctx, Fragment fragment, View act) {
        this.token = token;
        this.ctx = ctx;
        this.fragment = fragment;
        this.noDL = false;
        this.view = act;
    }

    public MessagesTask(Fragment fragment, String jsonString, View act) {
        this.noDL = true;
        this.fragment = fragment;
        this.jsonString = jsonString;
        this.view = act;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        responseString = "";
        if (!noDL) {
            try {
                MyRequest.clearFields();
                MyRequest.addField("token", token);
                MyRequest.CreatePost("messages");

                if (MyRequest.isStatusOk() || MyRequest.isStatusUnauthorized()) {
                    responseString = MyRequest.getResponseString();
                } else if (MyRequest.isStatusTimeout()) {
                    Toast.makeText(ctx, "Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
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
        }
        ((ProfilFragment) fragment).setIsMessagesDisplayed(true);
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        super.onPostExecute(success);
        JSONArray json;

        if (!noDL) {
            try {
                json = new JSONArray(responseString);
                for (int i = 0; i < json.length(); ++i) {
                    JSONObject tmp;
                    if ((tmp = json.getJSONObject(i)) != null) {
                        JSONObject user = tmp.getJSONObject("user");
                        TargetPic targetPic = new TargetPic(tmp.getString("content"), tmp.getString("title"), user.getString("title"), tmp.getString("date"), user.getString("picture"));
                        if (user.getString("picture").equals("null")) {
                            Picasso.with(ctx).load(R.drawable.login_x).into(targetPic);
                        } else {
                            Picasso.with(ctx).load(user.getString("picture")).into(targetPic);
                        }
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(ctx, "Server is down..", Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            try {
                json = new JSONArray(jsonString);
                for (int i = 0; i < json.length(); ++i) {
                    JSONObject tmp = json.getJSONObject(i);
                    TargetPic targetPic = new TargetPic(tmp.getString("content"), tmp.getString("title"), tmp.getString("login"), tmp.getString("date"), tmp.getString("picture"));
                    if (tmp.getString("picture").equals("null")) {
                        Picasso.with(ctx).load(R.drawable.login_x).into(targetPic);
                    } else {
                        Picasso.with(ctx).load(tmp.getString("picture")).into(targetPic);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            ListView messages = (ListView) view.findViewById(R.id.user_messages);
            ListAdapter customAdapter = new MessagesAdapter(ctx, R.layout.profil_message, userMessages);
            messages.setAdapter(customAdapter);
        } catch (Exception e) {
            Log.e("Error", "Couldn't set adapter to listview (messages)");
        }
        view.findViewById(R.id.progress_messages).setVisibility(View.GONE);
    }

    private class TargetPic implements Target {
        private String mContent;
        private String mTitle;
        private String mLogin;
        private String mDate;
        private String mPicUrl;

        public TargetPic(String content, String title, String login, String date, String picUrl) { mContent = content; mTitle = title; mLogin = login; mDate = date; mPicUrl = picUrl; }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            MessagesItem item = new MessagesItem(mContent, mTitle, mLogin, mDate, mPicUrl, (Bitmap) null);
            item.setBitmap(bitmap);
            userMessages.add(item);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            MessagesItem item = new MessagesItem(mContent, mTitle, mLogin, mDate, mPicUrl, (Drawable) null);
            item.setDrawable(errorDrawable);
            userMessages.add(item);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            MessagesItem item = new MessagesItem(mContent, mTitle, mLogin, mDate, mPicUrl, (Drawable) null);
            item.setDrawable(placeHolderDrawable);
            userMessages.add(item);
        }
    }
}