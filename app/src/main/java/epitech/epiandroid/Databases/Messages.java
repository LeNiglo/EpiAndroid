package epitech.epiandroid.Databases;

import android.text.Html;

import com.orm.SugarRecord;

/**
 * Created by Styve on 26/01/2015.
 */

public class Messages extends SugarRecord<Messages> {
    String mAuthor;
    String mDate;
    String mTitle;
    String mContent;
    String mPicUrl;

    public Messages(){
    }


    public Messages(String content, String title, String login, String date, String picUrl) {
        mContent = content;
        mTitle = title;
        mAuthor = login;
        mDate = date;
        mPicUrl = picUrl;
    }

    public String getContent() { return Html.fromHtml(mContent).toString(); }
    public String getTitle() { return Html.fromHtml(mTitle).toString(); }
    public String getLogin() { return mAuthor; }
    public String getDate() { return mDate; }
    public String getPicUrl() { return mPicUrl; }
}