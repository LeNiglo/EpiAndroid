package epitech.epiandroid.Items;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;

/**
 * Created by Styve on 14/01/2015.
 */
public class MessagesItem {
    private String mContent;
    private String mTitle;
    private String mLogin;
    private String mDate;
    private String mPicUrl;
    private Drawable mDrawable;
    private Bitmap mBitmap;

    public MessagesItem (String content, String title, String login, String date, String picUrl, Drawable drawable) {
        mContent = content;
        mTitle = title;
        mLogin = login;
        mDate = date;
        mDrawable = drawable;
        mBitmap = null;
        mPicUrl = picUrl;
    }

    public MessagesItem(String content, String title, String login, String date, String picUrl, Bitmap bitmap) {
        mContent = content;
        mTitle = title;
        mLogin = login;
        mDate = date;
        mDrawable = null;
        mBitmap = bitmap;
        mPicUrl = picUrl;
    }

    public void setContent(String content) { mContent = content; }
    public void setTitle(String title) { mTitle = title; }
    public void setLogin(String login) { mLogin = login; }
    public void setDate(String date) { mDate = date; }
    public void setDrawable(Drawable image) { mDrawable = image; }
    public void setBitmap(Bitmap bitmap) { mBitmap = bitmap; }

    public String getContent() { return Html.fromHtml(mContent).toString(); }
    public String getTitle() { return Html.fromHtml(mTitle).toString(); }
    public String getLogin() { return mLogin; }
    public String getDate() { return mDate; }
    public String getPicUrl() { return mPicUrl; }
    public Drawable getDrawable() { return mDrawable; }
    public Bitmap getBitmap() { return mBitmap; }
}
