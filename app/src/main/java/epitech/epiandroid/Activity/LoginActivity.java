package epitech.epiandroid.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.LoginTask;


public class LoginActivity extends Activity {

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    private Context     sContext;
    private LoginTask   mLoginTask;
    private View        mProgressView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sContext = getApplicationContext();
        setContentView(R.layout.activity_login);

        final EditText login = (EditText) findViewById(R.id.login);
        final EditText mdp = (EditText) findViewById(R.id.mdp);

        login.setText("simonn_s");
        mdp.setText("w,h=Jvse");
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (login.getText().toString().equals("") || mdp.getText().toString().equals(""))
                    Toast.makeText(sContext, "Login or password is missing", Toast.LENGTH_SHORT).show();
                else {
                    ((TextView)(findViewById(R.id.notes))).setText("Connecting...");
                    (findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
                    /*
                    ** Le LoginTask est dans le package Tasks car c'est plus simple de faire un fichier
                    ** pour chaque tache de fond, donc pour chaque appel à l'API. Tu peux changer ça pour
                    ** le mettre dans le MyRequest mais ça risque d'être un peu compliqué après.
                    */
                    mLoginTask = new LoginTask(login.getText().toString(), mdp.getText().toString(), sContext.getApplicationContext(), LoginActivity.this);
                    mLoginTask.execute((Void) null);
                }
            }
        });
    }

}