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
	private String codemodule;
	private String codeacti;
	private String codeevent;
	private String codeinstance;
	private String scolaryear;

	public PlanningItem(String title, String dates, String codemodule, String codeacti, String codeevent, String codeinstance, String scolaryear) {
		this.title = title;
		this.dates = dates;
		this.codemodule = codemodule;
		this.codeacti = codeacti;
		this.codeevent = codeevent;
		this.codeinstance = codeinstance;
		this.scolaryear = scolaryear;
	}

	public String getTitle() { return this.title; }
	public String getDates() { return this.dates; }
	public String getCodemodule() { return this.codemodule; }
	public String getCodeacti() { return this.codeacti; }
	public String getCodeevent() { return this.codeevent; }
	public String getCodeinstance() { return this.codeinstance; }
	public String getScolaryear() { return this.scolaryear; }
}
