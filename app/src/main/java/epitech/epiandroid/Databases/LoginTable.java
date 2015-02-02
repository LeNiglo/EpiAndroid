package epitech.epiandroid.Databases;

import com.orm.SugarRecord;

/**
 * Created by Styve on 26/01/2015.
 */

public class LoginTable extends SugarRecord<LoginTable> {
    String mFirstName;
    String mLastName;
    String mLogin;
    String mPicUrl;
    String mToken;
    long mInfosUpdatedAt;
    long mMarksUpdatedAt;
    long mMessagesUpdatedAt;
    long mPlanningUpdatedAt;

    public LoginTable(){
        mFirstName = null;
        mLastName = null;
        mLogin = null;
        mPicUrl = null;
        mToken = null;
        mInfosUpdatedAt = 0;
        mMarksUpdatedAt = 0;
        mMessagesUpdatedAt = 0;
        mPlanningUpdatedAt = 0;
    }

    public void setFirstName(String value) { this.mFirstName = value; }
    public void setLastName(String value) { this.mLastName = value; }
    public void setLogin(String value) { this.mLogin = value; }
    public void setPicUrl(String value) { this.mPicUrl = value; }
    public void setToken(String value) { this.mToken = value; }
    public void setInfosUpdatedAt(long value) { this.mInfosUpdatedAt = value; }
    public void setMarksUpdatedAt(long value) { this.mMarksUpdatedAt = value; }
    public void setMessagesUpdatedAt(long value) { this.mMessagesUpdatedAt = value; }
    public void setPlanningUpdatedAt(long value) { this.mPlanningUpdatedAt = value; }

    public String getFirstName() { return mFirstName; }
    public String getLastName() { return mLastName; }
    public String getLogin() { return mLogin; }
    public String getPicUrl() { return mPicUrl; }
    public String getToken() { return mToken; }
    public long getInfosUpdatedAt() { return this.mInfosUpdatedAt; }
    public long getMarksUpdatedAt() { return this.mInfosUpdatedAt; }
    public long getMessagesUpdatedAt() { return this.mMessagesUpdatedAt; }
    public long getPlanningUpdatedAt() { return this.mInfosUpdatedAt; }
}