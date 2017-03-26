package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.fragments.HelpFragment;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.HomeFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MeasurementFragment;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.fragments.AppointmentFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.CategoryFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.ConsumptionFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.HealthBioFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.IceFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MedicineFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.ReportFragment;
import sg.edu.nus.iss.se.ft05.medipal.managers.PrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String currentFragment;

    Context context;
    TabLayout tabLayout;

    static final int FIRST_RUN_REQUEST = 0;
   // SharedPreferences settings;
    TextView mUserName;

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        // Checking for first time app launch
        prefManager = new PrefManager(context);
        if (prefManager.isFirstTimeLaunch()) {
            Intent intent = new Intent(MainActivity.this, PersonalBioActivity.class);
            Bundle b = new Bundle();
            b.putString(Constants.ACTION, Constants.NEW);
            intent.putExtras(b);
            startActivityForResult(intent,FIRST_RUN_REQUEST);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        setFloatingActionButtonAction(AddOrUpdateMedicine.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        mUserName = (TextView) header.findViewById(R.id.tv_personName);
        mUserName.setText(prefManager.getUsername());

        if (findViewById(R.id.fragment_container) != null) {
            if (currentFragment == null) {
                setFragment(new HomeFragment());
            } else {
                updateFragment(currentFragment);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int userId = 0;
        String userName = "";
        if(requestCode == FIRST_RUN_REQUEST && resultCode == RESULT_OK){
            userId = data.getIntExtra("personId",0);
            userName = data.getStringExtra("personName");
            prefManager.setFirstTimeLaunch(false);
            prefManager.setUserName(userName);
            prefManager.setUserId(userId);
            mUserName.setText(prefManager.getUsername());
            Intent i = new Intent(this,AddOrUpdateHealthBioActivity.class);
            i.putExtra("firstRun",true);
            startActivity(i);
        } else
            finish();
    }

    public void viewPersonalBio(View v){
        Intent intent = new Intent(MainActivity.this, PersonalBioActivity.class);
        Bundle b = new Bundle();
        b.putString(Constants.ACTION, Constants.VIEW);
        b.putInt("userId",prefManager.getUserId());
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

    public void setFloatingActionButtonAction(final Class activityClass) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), activityClass);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        boolean isChecked = prefManager.isShowHelpScreens();
        MenuItem enableHelpItem = menu.findItem(R.id.action_enable_help);
        enableHelpItem.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_enable_help) {
            item.setChecked(!item.isChecked());
            prefManager.setShowHelpScreens(item.isChecked());
            return true;
        }
        else if(id == R.id.action_help) {
            setFragment(new HelpFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateFragment(String fragmentType) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fragmentType);
            setFragment((Fragment) clazz.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    public void updateTitle(String title){
//        collapsingToolbarLayout.setTitle(title);
//    }

    public void setFragment(Fragment fragment) {
        tabLayout.setVisibility(View.GONE);
        tabLayout.removeAllTabs();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
        currentFragment = fragment.getClass().getName();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.category) {
            setFragment(new CategoryFragment());

        } else if (id == R.id.medicine) {
            setFragment(new MedicineFragment());

        } else if (id == R.id.consumption) {
            setFragment(new ConsumptionFragment());

        } else if (id == R.id.heathbio) {
            setFragment(new HealthBioFragment());

        } else if (id == R.id.appointment) {
            setFragment(new AppointmentFragment());

        } else if (id == R.id.ice) {
            setFragment(new IceFragment());

        } else if (id == R.id.measure) {
            setFragment(new MeasurementFragment());

        } else if (id == R.id.help) {
            setFragment(new HelpFragment());
        } else if (id == R.id.report) {
            setFragment(new ReportFragment());
        }
        else if (id == R.id.home) {
            setFragment(new HomeFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}