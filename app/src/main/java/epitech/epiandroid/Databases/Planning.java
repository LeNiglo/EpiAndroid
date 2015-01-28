package epitech.epiandroid.Databases;

import android.text.Html;

import com.orm.SugarRecord;

/**
 * Created by Styve on 26/01/2015.
 */

public class Planning extends SugarRecord<Planning> {
    private String dates;
    private String title;

    public Planning(){
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDates(String date) {
        this.dates = date;
    }

    public String getTitle() { return this.title; }
    public String getDates() { return this.dates; }
}