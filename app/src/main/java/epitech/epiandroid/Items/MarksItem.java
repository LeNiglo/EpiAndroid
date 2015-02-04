package epitech.epiandroid.Items;

/**
 * Created by Styve on 14/01/2015.
 */
public class MarksItem {
    private String mNote;
    private String mModuleName;
    private String mProjectName;
    private String mCorrector;
    private int mContainerColor;
    private int mModuleImage;

    public MarksItem(String note, String moduleName, String projectName, String corrector, int containerColor, int moduleImage) {
        mNote = note;
        mModuleName = moduleName;
        mProjectName = projectName;
        mModuleImage = moduleImage;
        mCorrector = corrector;
        mContainerColor = containerColor;
    }

    public String getNote() { return mNote; }
    public String getModuleName() { return mModuleName; }
    public String getProjectName() { return mProjectName; }
    public String getCorrector() { return mCorrector; }
    public int getModuleImage() { return mModuleImage; }
    public int getContainerColor() { return mContainerColor; }
}
