package epitech.epiandroid.Activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Marks;
import epitech.epiandroid.Databases.Messages;
import epitech.epiandroid.Databases.Planning;
import epitech.epiandroid.Databases.ProfilInfos;
import epitech.epiandroid.Databases.Submissions;
import epitech.epiandroid.Databases.Susies;
import epitech.epiandroid.Fragment.ActivitiesFragment;
import epitech.epiandroid.Fragment.NotesFragment;
import epitech.epiandroid.Fragment.PlanningFragment;
import epitech.epiandroid.Fragment.ProfilFragment;
import epitech.epiandroid.Fragment.ProjetsFragment;
import epitech.epiandroid.R;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

/**
 * Created by Styve on 07/01/2015.
 */
public class DrawerActivity extends MaterialNavigationDrawer<Fragment> implements MaterialAccountListener {

    MaterialSection activitiesSection, profileSection, projectsSection, planningSection, marksSection, logoutSection;

    @Override
    public void init(Bundle savedInstanceState) {
        allowArrowAnimation();
        // add first account
        MaterialAccount account = new MaterialAccount(this.getResources(), "", "", R.drawable.login_x, R.drawable.background);

        // create sections
        profileSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[0], new ProfilFragment());
        projectsSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[1], new ProjetsFragment());
        planningSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[2], new PlanningFragment());
        activitiesSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[3], new ActivitiesFragment());
        marksSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[4], new NotesFragment());
        logoutSection = this.newSection("Logout", new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                Messages.deleteAll(Messages.class);
                ProfilInfos.deleteAll(ProfilInfos.class);
                LoginTable.deleteAll(LoginTable.class);
                Marks.deleteAll(Marks.class);
                Planning.deleteAll(Planning.class);
                Submissions.deleteAll(Submissions.class);
                Susies.deleteAll(Susies.class);
                finish();
            }
        });

        if (LoginTable.listAll(LoginTable.class).size() != 0) {
            LoginTable infos = LoginTable.listAll(LoginTable.class).get(0);
            if (infos.getLastName() != null) {
                account.setTitle(infos.getLogin());
                account.setSubTitle(infos.getFirstName() + " " + infos.getLastName().toUpperCase());
                Picasso.with(this.getApplicationContext()).load(infos.getPicUrl()).into((ImageView) findViewById(R.id.user_photo));
                Log.e("Debug", infos.getPicUrl());
                this.notifyAccountDataChanged();
            }
        }

        this.addAccount(account);
        // set listener
        this.setAccountListener(this);

        // add your sections to the drawer
        //this.addSubheader(getApplicationContext().getString(R.string.app_name));
        //this.addDivisor();
        this.addSection(profileSection);
        this.addSection(activitiesSection);
        this.addSection(projectsSection);
        this.addSection(planningSection);
        this.addSection(marksSection);
        this.addBottomSection(logoutSection);

        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {
        // open profile activity
    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {
        // when another account is selected
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.ask_quit));
        alert.setMessage(getResources().getString(R.string.ask_sure));

        alert.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        alert.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

