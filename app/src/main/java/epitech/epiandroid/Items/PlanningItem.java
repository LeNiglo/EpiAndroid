package epitech.epiandroid.Items;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;

/**
 * Created by Styve on 14/01/2015.
 */
public class PlanningItem {
	private String mContent;
	private String mTitle;
	private String mLogin;
	private String mDate;

	public PlanningItem(String content, String title, String login, String date) {
		mContent = content;
		mTitle = title;
		mLogin = login;
		mDate = date;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public void setLogin(String login) {
		mLogin = login;
	}

	public void setDate(String date) {
		mDate = date;
	}

	public String getContent() {
		return Html.fromHtml(mContent).toString();
	}

	public String getTitle() {
		return Html.fromHtml(mTitle).toString();
	}

	public String getLogin() {
		return mLogin;
	}

	public String getDate() {
		return mDate;
	}
}
