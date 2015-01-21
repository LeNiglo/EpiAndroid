package epitech.epiandroid.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import epitech.epiandroid.Fragment.NotesFragment;
import epitech.epiandroid.Fragment.PlanningFragment;
import epitech.epiandroid.Fragment.ProfilFragment;
import epitech.epiandroid.Fragment.ProjetsFragment;
import epitech.epiandroid.R;
import it.neokree.materialnavigationdrawer.MaterialAccount;
import it.neokree.materialnavigationdrawer.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.MaterialSection;
import it.neokree.materialnavigationdrawer.MaterialSectionListener;

/**
 * Created by Styve on 07/01/2015.
 */
public class DrawerActivity extends MaterialNavigationDrawer<Fragment> implements MaterialAccountListener {

    MaterialSection profileSection, projectsSection, planningSection, marksSection, logoutSection;


    @Override
    public void init(Bundle savedInstanceState) {

        // add first account
        MaterialAccount account = new MaterialAccount("simonn_s", "Styve Simonneau", this.getResources().getDrawable(R.drawable.login_x), this.getResources().getDrawable(R.drawable.background));

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
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();

                section.unSelect();
            }
        });

        this.addAccount(account);
        // set listener
        this.setAccountListener(this);

        // add your sections to the drawer
        this.addSubheader(getApplicationContext().getString(R.string.app_name));
        this.addDivisor();
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
        Fragment fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");
        System.out.println("RESTORE");
        if (fragment != null) {
            System.out.println("Le fragment n'est pas nul.");
            getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getFragmentManager().putFragment(outState, "fragment", getFragmentManager().findFragmentById(R.id.frame_container));
        // save :
        //outState.putString("side_user_login", ((TextView) findViewById(R.id.side_user_login)).getText().toString());
        //Toast.makeText(getApplicationContext(), "side_user_login: " + ((TextView) findViewById(R.id.side_user_login)).getText().toString(), Toast.LENGTH_SHORT).show();

        // restore :
        /*
        if (savedInstanceState != null) {
            ((TextView) findViewById(R.id.side_user_login)).setText(savedInstanceState.getString("side_user_login"));
            Utils.getImgFromCache(getApplicationContext(), "profil.bmp", ((ImageView) findViewById(R.id.side_user_picture)));
        }*/
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

