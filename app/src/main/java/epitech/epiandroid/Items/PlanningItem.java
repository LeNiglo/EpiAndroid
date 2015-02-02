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
	private Boolean register_student;
	private Boolean allow_token;
	private Boolean module_registered;
	private String event_registered;

	public PlanningItem(String title, String dates, String codemodule, String codeacti, String codeevent, String codeinstance, String scolaryear, Boolean register_student, Boolean allow_token, Boolean module_registered, String event_registered) {
		this.title = title;
		this.dates = dates;
		this.codemodule = codemodule;
		this.codeacti = codeacti;
		this.codeevent = codeevent;
		this.codeinstance = codeinstance;
		this.scolaryear = scolaryear;
		this.register_student = register_student;
		this.allow_token = allow_token;
		this.module_registered = module_registered;
		this.event_registered = event_registered;
	}


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
}
