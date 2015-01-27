package epitech.epiandroid.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Messages;
import epitech.epiandroid.Databases.ProfilInfos;
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

    MaterialSection profileSection, projectsSection, planningSection, marksSection, logoutSection;

    @Override
    public void init(Bundle savedInstanceState) {
        allowArrowAnimation();
        // add first account
        MaterialAccount account = new MaterialAccount(this.getResources(), "user_u", "login_x", R.drawable.login_x, R.drawable.background);

        // create sections
        profileSection = this.newSection("Section 2", new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                Toast.makeText(getApplicationContext(), "Section 2 Clicked", Toast.LENGTH_SHORT).show();

                // deselect section when is clicked
                section.unSelect();
            }
        });
        profileSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[0], new ProfilFragment());
        projectsSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[1], new ProjetsFragment());
        planningSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[2], new PlanningFragment());
        marksSection = this.newSection(getResources().getStringArray(R.array.nav_drawer_items)[5], new NotesFragment());

        logoutSection = this.newSection("Logout", new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                Messages.deleteAll(Messages.class);
                ProfilInfos.deleteAll(ProfilInfos.class);
                LoginTable.deleteAll(LoginTable.class);
                finish();
            }
        });

        if (LoginTable.listAll(LoginTable.class).size() != 0) {
            LoginTable infos = LoginTable.listAll(LoginTable.class).get(0);
            if (infos.getLastName() != null) {
                account.setTitle(infos.getLogin());
                account.setSubTitle(infos.getFirstName() + " " + infos.getLastName().toUpperCase());
                Picasso.with(this.getApplicationContext()).load(infos.getPicUrl()).into((ImageView) findViewById(R.id.user_photo));
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
}

