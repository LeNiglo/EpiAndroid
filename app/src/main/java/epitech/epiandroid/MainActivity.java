package epitech.epiandroid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

        public AppSectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    return new LaunchpadSectionFragment();
                case 1 :
                    return new PlanningSectionFragment();


                default:
                    // The other sections of the app are dummy placeholders.
                    android.support.v4.app.Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class LaunchpadSectionFragment extends android.support.v4.app.Fragment {


        private LoginTask mLoginTask;
        View rootView;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView  = inflater.inflate(R.layout.fragment_section_launchpad, container, false);

            // Demonstration of navigating to external activities.
            rootView.findViewById(R.id.login_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           EditText login = (EditText) rootView.findViewById(R.id.login);
                           EditText mdp = (EditText) rootView.findViewById(R.id.mdp);

                            if (login.getText().toString().equals("") || mdp.getText().toString().equals(""))
                                Toast.makeText(getActivity(), "entrez vos ids", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(getActivity(), login.getText().toString(), Toast.LENGTH_SHORT).show();
                                mLoginTask = new LoginTask(login.getText().toString(), mdp.getText().toString(), getActivity().getApplicationContext());
                                mLoginTask.execute((Void) null);
                            }



                        }
                    });

            return rootView;
        }

        public class LoginTask extends AsyncTask<Void, Void, Boolean> {


            private String login;
            private String pass;
            private Context ctx;
            private String responseString = null;

            LoginTask(String login, String password, Context ctx) {
                this.login = login;
                this.pass = password;
                this.ctx = ctx;

            }

            @Override
            protected Boolean doInBackground(Void... params) {
                final String TAG = "background";

                try {
                    Log.v(TAG, "launch background task");
                    HttpClient httpclient  = new DefaultHttpClient();
                    httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
                    HttpResponse response;

                    try {
                        JSONObject json = new JSONObject();

                        HttpPost post = new HttpPost("http://epitech-api.herokuapp.com/login");
                        post.setHeader("User-Agent", System.getProperty("http.agent"));


//                        post.addHeader("Authorization", "Basic " + hashedpass);

//                        post.addHeader("login", login);
//                        post.addHeader("password", pass);

//                        Log.w(TAG, login + pass);
//
//
////
//                        json.put("login", login);
//                        json.put("password", pass);
//
//                        StringEntity se = new StringEntity(json.toString(), HTTP.UTF_8);

                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("login", login));
                        nameValuePairs.add(new BasicNameValuePair("password", pass));

                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        response = httpclient.execute(post);

                        StatusLine statusLine = response.getStatusLine();


                        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                            Log.v(TAG, "connexion ok");
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            response.getEntity().writeTo(out);
                            out.close();
                            responseString = out.toString();
                        }  else if (statusLine.getStatusCode()  == HttpStatus.SC_GATEWAY_TIMEOUT || statusLine.getStatusCode()  == HttpStatus.SC_REQUEST_TIMEOUT) {
                            Toast.makeText(ctx, " Delai d'attente dépassé", Toast.LENGTH_SHORT).show();
                        } else if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN){

                            mLoginTask.cancel(true);
                            response.getEntity().getContent().close();
                            throw new IOException(statusLine.getReasonPhrase());
                        }
                        else {
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            response.getEntity().writeTo(out);
                            out.close();
                            responseString = out.toString();
                            Log.e(TAG, "connexion failed" + responseString);
                            response.getEntity().getContent().close();
                            throw new IOException(statusLine.getReasonPhrase());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                    Log.v(TAG, "index=" + responseString);

                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(final Boolean success) {
                super.onPostExecute(success);

                String notes1 = "null";
                JSONObject json = null;
                try {
                    json = new JSONObject(responseString);
                    notes1 = json.getString("ip");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextView text = (TextView) rootView.findViewById(R.id.notes);
                text.setText(notes1);



                this.cancel(true);
                mLoginTask = null;
            }

        }
    }

    public static class PlanningSectionFragment extends android.support.v4.app.Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_planning, container, false);



            return rootView;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends android.support.v4.app.Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}