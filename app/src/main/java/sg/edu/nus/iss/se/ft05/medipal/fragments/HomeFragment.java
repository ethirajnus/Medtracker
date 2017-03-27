
package sg.edu.nus.iss.se.ft05.medipal.fragments;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.ICEAdditionActivity;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;


/**
 * Class for Home Fragment operations
 * @author Dhruv Mandan Gopal
 */
public class HomeFragment extends Fragment {
    private String title = "MediPal";
    private TabLayout tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Setting ViewPager for each Tabs
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        ((MainActivity) getActivity()).setFloatingActionButtonAction(ICEAdditionActivity.class);

        // Set Tabs inside Toolbar
        tabs = (TabLayout) getActivity().findViewById(R.id.tabs);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        fabSOS.setVisibility(View.VISIBLE);

        tabs.setVisibility(View.VISIBLE);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        Adapter adapter = new Adapter(getChildFragmentManager(), tabs.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabs.post(new Runnable() {
            @Override
            public void run() {
                tabs.setupWithViewPager(viewPager);
            }
        });
        getActivity().setTitle(title);


        return view;

    }


    // Add Fragments to Tabs


    static class Adapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        String[] tabTitles = {"Appointments", "Consumptions", "Measurements"};

        public Adapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    AppointmentTab appointments = new AppointmentTab();
                    return appointments;
                case 1:
                    ConsumptionTab consumptions = new ConsumptionTab();
                    return consumptions;
                case 2:
                    MeasurementTab measurements = new MeasurementTab();
                    return measurements;
                default:
                    return null;
            }
        }

        /**
         * @return
         */
        @Override
        public int getCount() {
            return mNumOfTabs;
        }

    }


}

