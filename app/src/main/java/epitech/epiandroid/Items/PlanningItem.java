package epitech.epiandroid.Items;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;

/**
 * Created by Styve on 14/01/2015.
 */
public class PlanningItem {
	private String title;
	private String dates;

	public PlanningItem(String title, String dates) {
		this.title = title;
		this.dates = dates;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDates(String date) {
		this.dates = dates;
	}

	public String getTitle() {
		return Html.fromHtml(this.title).toString();
	}

	public String getDates() {
		return this.dates;
	}
}
