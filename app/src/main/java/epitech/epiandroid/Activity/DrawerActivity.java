package epitech.epiandroid.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Fragment.ModulesFragment;
import epitech.epiandroid.Fragment.NotesFragment;
import epitech.epiandroid.Fragment.PlanningFragment;
import epitech.epiandroid.Fragment.ProfilFragment;
import epitech.epiandroid.Fragment.ProjetsFragment;
import epitech.epiandroid.Fragment.SusieFragment;
import epitech.epiandroid.Fragment.TrombiFragment;
import epitech.epiandroid.R;

/**
 * Created by Styve on 07/01/2015.
 */
public class DrawerActivity extends ActionBarActivity implements DrawerNavigationCallbacks {

    private Toolbar mToolbar;
    private List<Fragment> fragments = new ArrayList<>();
    private DrawerNavigationFragment mNavigationDrawerFragment;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            System.out.println("Restored from Activity");
             fragment = getFragmentManager().getFragment(savedInstanceState, "mContent");
        }
        fragments.add(new ProfilFragment());
        fragments.add(new ProfilFragment());
        fragments.add(new ProjetsFragment());
        fragments.add(new PlanningFragment());
        fragments.add(new SusieFragment());
        fragments.add(new ModulesFragment());
        fragments.add(new NotesFragment());
        fragments.add(new TrombiFragment());
        setContentView(R.layout.activity_main_topdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (DrawerNavigationFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
//        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        // Create a new fragment and specify the planet to show based on position
        if (position == 0)
            fragment = fragments.get(0);
        else
            fragment = fragments.get(position - 1);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        /*mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);*/
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        System.out.println("Saved from Activity");
        getFragmentManager().putFragment(outState, "fragment", fragment);


    }
}

