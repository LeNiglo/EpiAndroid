package epitech.epiandroid.Databases;

import com.orm.SugarRecord;

/**
 * Created by Styve on 14/01/2015.
 */
public class Submissions extends SugarRecord<ProfilInfos> {
    private String mProjectName;
    private String mDate;
    private String mIdActivity;
    private int mProgress;

    public Submissions() {
        mProgress = 0;
        mDate = null;
        mProjectName = null;
        mIdActivity = null;
    }

    public Submissions(String projectName, String idActivity, String date, int progress) {
        mProjectName = projectName;
        mDate = date;
        mIdActivity = idActivity;
        mProgress = progress;
    }

    public void setIdActivity(String id) { mIdActivity = id; }
    public void setProjectName(String name) { mProjectName = name; }
    public void setDate(String d) { mDate = d; }
    public void setProgress(int progress) { mProgress = progress; }

    public String getIdActivity() { return mIdActivity; }
    public String getProjectName() { return mProjectName; }
    public String getDate() { return mDate; }
    public int getProgress() { return mProgress; }
}
