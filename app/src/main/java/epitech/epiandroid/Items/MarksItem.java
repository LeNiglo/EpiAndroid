package epitech.epiandroid.Items;

import android.graphics.drawable.Drawable;
import android.text.Html;

/**
 * Created by Styve on 14/01/2015.
 */
public class MarksItem {
    private String mNote;
    private String mModuleName;
    private String mProjectName;
    private int mContainerColor;
    private int mModuleImage;

    public MarksItem(String note, String moduleName, String projectName, int containerColor, int moduleImage) {
        mNote = note;
        mModuleName = moduleName;
        mProjectName = projectName;
        mModuleImage = moduleImage;
        mContainerColor = containerColor;
    }

    public void setNote(String note) { mNote = note; }
    public void setModuleName(String name) { mModuleName = name; }
    public void setProjectName(String name) { mProjectName = name; }
    public void setmModuleImage(int d) { mModuleImage = d; }
    public void setContainerColor(int color) { mContainerColor = color; }

    public String getNote() { return mNote; }
    public String getModuleName() { return mModuleName; }
    public String getProjectName() { return mProjectName; }
    public int getModuleImage() { return mModuleImage; }
    public int getContainerColor() { return mContainerColor; }
}
