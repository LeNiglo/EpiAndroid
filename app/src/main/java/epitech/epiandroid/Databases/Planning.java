package epitech.epiandroid.Databases;

import android.text.Html;

import com.orm.SugarRecord;

/**
 * Created by Styve on 26/01/2015.
 */

public class Planning extends SugarRecord<Planning> {
    String dates;
    String title;

    public Planning(){
    }


    public Planning(String title, String dates) {
        this.title = title;
        this.dates = dates;
    }

    public String getTitle() { return Html.fromHtml(this.title).toString(); }
    public String getDates() { return this.dates; }
}