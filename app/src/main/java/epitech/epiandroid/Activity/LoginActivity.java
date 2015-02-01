package epitech.epiandroid.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.LoginTask;


public class LoginActivity extends Activity {

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    private Context     sContext;
    private LoginTask   mLoginTask;
    private boolean     pushed;
    private boolean     canLogin;

    public void setCanLogin(boolean v) {
        canLogin = v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sContext = getApplicationContext();
        setContentView(R.layout.activity_login);
        pushed = false;
        canLogin = true;

        final EditText login = (EditText) findViewById(R.id.login);
        final EditText mdp = (EditText) findViewById(R.id.mdp);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((login.getText().toString().equals("") || mdp.getText().toString().equals("")) && canLogin)
                    Toast.makeText(sContext, getString(R.string.login_empty), Toast.LENGTH_SHORT).show();
                else {
                    //((TextView)(findViewById(R.id.notes))).setText("Connecting...");
                    ((CircularProgressButton) findViewById(R.id.login_button)).setIndeterminateProgressMode(true);
                    ((CircularProgressButton) findViewById(R.id.login_button)).setProgress(50);
                    canLogin = false;
                    new LoginTask(login.getText().toString(), mdp.getText().toString(), sContext.getApplicationContext(), LoginActivity.this).execute();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!pushed) {
            Toast.makeText(sContext, getString(R.string.login_quit), Toast.LENGTH_SHORT).show();
            pushed = true;
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CircularProgressButton) findViewById(R.id.login_button)).setProgress(0);
        pushed = false;
    }

}