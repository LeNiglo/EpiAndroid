package epitech.epiandroid.Databases;

import android.text.Html;

import com.orm.SugarRecord;

/**
 * Created by Styve on 26/01/2015.
 */

public class Planning extends SugarRecord<Planning> {
	private String title;
	private String dates;
	private String codemodule;
	private String codeacti;
	private String codeevent;
	private String codeinstance;
	private String scolaryear;

	public Planning() {}

	public String getTitle() { return this.title; }
	public String getDates() { return this.dates; }
	public String getCodemodule() { return this.codemodule; }
	public String getCodeacti() { return this.codeacti; }
	public String getCodeevent() { return this.codeevent; }
	public String getCodeinstance() { return this.codeinstance; }
	public String getScolaryear() { return this.scolaryear; }

	public void setTitle(String i) { this.title = i; }
	public void setDates(String i) { this.dates = i; }
	public void setCodemodule(String i) { this.codemodule = i; }
	public void setCodeacti(String i) { this.codeacti = i; }
	public void setCodeevent(String i) { this.codeevent = i; }
	public void setCodeinstance(String i) { this.codeinstance = i; }
	public void setScolaryear(String i) { this.scolaryear = i; }
}