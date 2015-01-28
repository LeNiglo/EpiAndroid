package epitech.epiandroid.Databases;

import android.graphics.drawable.Drawable;

import com.orm.SugarRecord;

/**
 * Created by Styve on 26/01/2015.
 */

public class Marks extends SugarRecord<Marks> {
    String mNote;
    String mProjectName;
    String mModuleName;
    int mModuleImage;
    int mContainerColor;

    public Marks(){
        mNote = null;
        mProjectName = null;
        mModuleName = null;
        mModuleImage = 0;
        mContainerColor = 0;
    }

    public void setNote(String note) { mNote = note; }
    public void setModuleName(String name) { mModuleName = name; }
    public void setProjectName(String name) { mProjectName = name; }
    public void setModuleImage(int d) { mModuleImage = d; }
    public void setContainerColor(int color) { mContainerColor = color; }

    public String getNote() { return mNote; }
    public String getModuleName() { return mModuleName; }
    public String getProjectName() { return mProjectName; }
    public int getModuleImage() { return mModuleImage; }
    public int getContainerColor() { return mContainerColor; }
}