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
	private Boolean register_student;
	private Boolean allow_token;
	private Boolean module_registered;
	private String event_registered;

	public Planning() {}

	public String getTitle() { return this.title; }
	public String getDates() { return this.dates; }
	public String getCodemodule() { return this.codemodule; }
	public String getCodeacti() { return this.codeacti; }
	public String getCodeevent() { return this.codeevent; }
	public String getCodeinstance() { return this.codeinstance; }
	public String getScolaryear() { return this.scolaryear; }
	public Boolean getRegisterStudent() { return this.register_student; }
	public Boolean getAllowToken() { return this.allow_token; }
	public Boolean getModuleRegistered() { return this.module_registered; }
	public String getEventRegistered() { return this.event_registered; }

	public void setTitle(String i) { this.title = i; }
	public void setDates(String i) { this.dates = i; }
	public void setCodemodule(String i) { this.codemodule = i; }
	public void setCodeacti(String i) { this.codeacti = i; }
	public void setCodeevent(String i) { this.codeevent = i; }
	public void setCodeinstance(String i) { this.codeinstance = i; }
	public void setScolaryear(String i) { this.scolaryear = i; }
	public void setRegisterStudent(Boolean i) { this.register_student = i; }
	public void setAllowToken(Boolean i) { this.allow_token = i; }
	public void setModuleRegistered(Boolean i) { this.module_registered = i; }
	public void setEventRegistered(String i) { this.event_registered = i; }
}