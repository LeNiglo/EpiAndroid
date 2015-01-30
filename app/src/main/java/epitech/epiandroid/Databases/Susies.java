package epitech.epiandroid.Databases;

import com.orm.SugarRecord;

/**
 * Created by Styve on 14/01/2015.
 */

public class Susies extends SugarRecord<ProfilInfos> {
    private String mTitle;
    private String mStart;
    private String mSusie;
    private String mType;

    public Susies() {
        mTitle = null;
        mStart = null;
        mSusie = null;
        mType = null;
    }

    public void setTitle(String value) { mTitle = value; }
    public void setStart(String value) { mStart = value; }
    public void setSusie(String value) { mSusie = value; }
    public void setType(String value) { mType = value; }

    public String getTitle() { return mTitle; }
    public String getStart() { return mStart; }
    public String getSusie() { return mSusie; }
    public String getType() { return mType; }
}
