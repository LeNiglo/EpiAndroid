package epitech.epiandroid.Databases;

import android.text.Html;

import com.orm.SugarRecord;

/**
 * Created by Styve on 26/01/2015.
 */

public class ProfilInfos extends SugarRecord<ProfilInfos> {
    String mFirstName;
    String mLastName;
    String mLogin;
    String mPicUrl;
    String mActiveLog;
    String mNsLogMin;
    String mNsLogNorm;
    String mSemester;
    Integer mLogColor;

    public ProfilInfos(){
        mFirstName = null;
        mLastName = null;
        mLogin = null;
        mPicUrl = null;
        mActiveLog = null;
        mNsLogMin = null;
        mNsLogNorm = null;
        mSemester = null;
        mLogColor = 0;
    }

    public void setFirstName(String value) { this.mFirstName = value; }
    public void setLastName(String value) { this.mLastName = value; }
    public void setLogin(String value) { this.mLogin = value; }
    public void setPicUrl(String value) { this.mPicUrl = value; }
    public void setActiveLog(String value) { this.mActiveLog = value; }
    public void setNsLogMin(String value) { this.mNsLogMin = value; }
    public void setNsLogNorm(String value) { this.mNsLogNorm = value; }
    public void setSemester(String value) { this.mSemester = value; }
    public void setLogColor(Integer value) { this.mLogColor = value; }

    public String getFirstName() { return mFirstName; }
    public String getLastName() { return mLastName; }
    public String getLogin() { return mLogin; }
    public String getPicUrl() { return mPicUrl; }
    public String getActiveLog() { return mActiveLog; }
    public String getNsLogMin() { return mNsLogMin; }
    public String getNsLogNorm() { return mNsLogNorm; }
    public String getSemester() { return mSemester; }
    public Integer getLogColor() { return mLogColor; }
}