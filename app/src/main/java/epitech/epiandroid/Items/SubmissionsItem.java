package epitech.epiandroid.Items;

/**
 * Created by Styve on 14/01/2015.
 */
public class SubmissionsItem {
    private String mProjectName;
    private String mDate;
    private String mIdActivity;
    private int mProgress;

    public SubmissionsItem(String projectName, String idActivity, String date, int progress) {
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
