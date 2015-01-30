package epitech.epiandroid.Items;

/**
 * Created by Styve on 14/01/2015.
 */
public class SusiesItem {
    private String mTitle;
    private String mStart;
    private String mSusie;
    private String mType;

    public SusiesItem(String title, String start, String susie, String type) {
        mTitle = title;
        mStart = start;
        mSusie = susie;
        mType = type;
    }

    public String getTitle() { return mTitle; }
    public String getStart() { return mStart; }
    public String getSusie() { return mSusie; }
    public String getType() { return mType; }
}
