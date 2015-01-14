package epitech.epiandroid.Activity;

import android.graphics.drawable.Drawable;

/**
 * Created by Styve on 14/01/2015.
 */
public class MessagesItem {
    private String mContent;
    private String mTitle;
    private String mLogin;
    private String mDate;
    private Drawable mDrawable;

    public MessagesItem (String content, String title, String login, String date, Drawable drawable) {
        mContent = content;
        mTitle = title;
        mLogin = login;
        mDate = date;
        mDrawable = drawable;
    }

    public void setContent(String content) { mContent = content; }
    public void setTitle(String title) { mTitle = title; }
    public void setLogin(String login) { mLogin = login; }
    public void setDate(String date) { mDate = date; }
    public void setDrawable(Drawable image) { mDrawable = image; }

    public String getContent() { return mContent; }
    public String getTitle() { return mTitle; }
    public String getLogin() { return mLogin; }
    public String getDate() { return mDate; }
    public Drawable getDrawable() { return mDrawable; }
}
