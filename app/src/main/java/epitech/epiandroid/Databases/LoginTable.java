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

    public LoginTable(){
        mFirstName = null;
        mLastName = null;
        mLogin = null;
        mPicUrl = null;
        mToken = null;
    }

    public void setFirstName(String value) { this.mFirstName = value; }
    public void setLastName(String value) { this.mLastName = value; }
    public void setLogin(String value) { this.mLogin = value; }
    public void setPicUrl(String value) { this.mPicUrl = value; }
    public void setToken(String value) { this.mToken = value; }

    public String getFirstName() { return mFirstName; }
    public String getLastName() { return mLastName; }
    public String getLogin() { return mLogin; }
    public String getPicUrl() { return mPicUrl; }
    public String getToken() { return mToken; }
}