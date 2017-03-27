package sg.edu.nus.iss.se.ft05.medipal.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.fragments.HelpFragment;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.fragments.HomeFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MeasurementFragment;
import sg.edu.nus.iss.se.ft05.medipal.utils.Constants;
import sg.edu.nus.iss.se.ft05.medipal.fragments.AppointmentFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.CategoryFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.ConsumptionFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.HealthBioFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.IceFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.MedicineFragment;
import sg.edu.nus.iss.se.ft05.medipal.fragments.ReportFragment;
import sg.edu.nus.iss.se.ft05.medipal.managers.ICEContactsManager;
import sg.edu.nus.iss.se.ft05.medipal.managers.PrefManager;

/**
 * Class for Main Activity
 *
 * @author Ethiraj Srinivasan
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String currentFragment;

    Context context;
    TabLayout tabLayout;

    private static final int ICE_PERMISSIONS_REQUEST_CALL = 1;

    static final int FIRST_RUN_REQUEST = 0;
    // SharedPreferences settings;
    TextView mUserName;

    private PrefManager prefManager;

    /**
     * @param savedInstanceState
     */
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
            startActivityForResult(intent, FIRST_RUN_REQUEST);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        setFloatingActionButtonAction(MainActivity.class);
        setFloatingActionButtonSOSAction();

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

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int userId = 0;
        String userName = "";
        if (requestCode == FIRST_RUN_REQUEST && resultCode == RESULT_OK) {
            userId = data.getIntExtra("personId", 0);
            userName = data.getStringExtra("personName");
            prefManager.setFirstTimeLaunch(false);
            prefManager.setUserName(userName);
            prefManager.setUserId(userId);
            mUserName.setText(prefManager.getUsername());
            Intent i = new Intent(this, AddOrUpdateHealthBioActivity.class);
            i.putExtra("firstRun", true);
            startActivity(i);
        } else
            finish();
    }

    /**
     * View personal bio
     *
     * @param v
     */
    public void viewPersonalBio(View v) {
        Intent intent = new Intent(MainActivity.this, PersonalBioActivity.class);
        Bundle b = new Bundle();
        b.putString(Constants.ACTION, Constants.VIEW);
        b.putInt("userId", prefManager.getUserId());
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

    public void setFloatingActionButtonSOSAction() {
        FloatingActionButton fabSOS = (FloatingActionButton) findViewById(R.id.fabSOS);
        fabSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long phone = ICEContactsManager.findPhone(context);


                if (0 != phone) {

                    if (ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(context, "App does not have Permission to CALL", Toast.LENGTH_SHORT).show();


                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, ICE_PERMISSIONS_REQUEST_CALL);

                    } else {

                        Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone));

                        try {
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(callIntent);

                        } catch (Exception e) {

                            Log.e("Call Failed", e.getMessage());
                        }
                    }
                } else {

                    Toast.makeText(context, "Please add Emergency Contact", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        boolean isChecked = prefManager.isShowHelpScreens();
        MenuItem enableHelpItem = menu.findItem(R.id.action_enable_help);
        enableHelpItem.setChecked(isChecked);
        MenuItem share1 = menu.findItem(R.id.share_consumption);
        if (share1 != null) {
            share1.setVisible(false);
        }
        MenuItem share2 = menu.findItem(R.id.share_measurement);
        if (share2 != null) {
            share2.setVisible(false);
        }
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_enable_help) {
            item.setChecked(!item.isChecked());
            prefManager.setShowHelpScreens(item.isChecked());
            return true;
        } else if (id == R.id.action_help) {
            setFragment(new HelpFragment());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param fragmentType
     */
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

    /**
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        FloatingActionButton fabSOS = (FloatingActionButton) findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.GONE);

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
        } else if (id == R.id.home) {

            setFragment(new HomeFragment());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}