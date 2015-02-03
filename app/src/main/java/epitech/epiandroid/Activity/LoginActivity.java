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
    private Context     sContext;
    private LoginTask   mLoginTask;
    private boolean     pushed;
    private boolean     canLogin;
    private final LoginActivity self = this;

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

        login.setText("");
        mdp.setText("");
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((login.getText().toString().equals("") || mdp.getText().toString().equals("")) && canLogin)
                    Toast.makeText(sContext, getString(R.string.login_empty), Toast.LENGTH_SHORT).show();
                else {
                    ((CircularProgressButton) findViewById(R.id.login_button)).setIndeterminateProgressMode(true);
                    ((CircularProgressButton) findViewById(R.id.login_button)).setProgress(50);
                    canLogin = false;
                    mLoginTask = new LoginTask(login.getText().toString(), mdp.getText().toString(), sContext.getApplicationContext(), self);
                    mLoginTask.execute();
                }
            }
        });

        if (LoginTable.listAll(LoginTable.class).size() > 0) {
            Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        if (!pushed) {
            Toast.makeText(sContext, getString(R.string.login_quit), Toast.LENGTH_SHORT).show();
            pushed = true;
        }
        else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CircularProgressButton) findViewById(R.id.login_button)).setProgress(0);
        pushed = false;
    }

}