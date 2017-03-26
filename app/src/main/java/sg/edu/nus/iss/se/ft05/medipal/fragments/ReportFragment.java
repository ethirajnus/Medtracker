package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.CONSUMPTION;
import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.REPORT;

/**
 * Created by ethi on 24/03/17.
 */

public class ReportFragment extends Fragment {

    private Context context;
    private TabLayout tabs;
    public ReportFragment() {
        // Required empty public constructor
    }

	@Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.report_fragment, container, false);
        getActivity().setTitle(REPORT);
        context = getActivity().getApplicationContext();

        // Setting ViewPager for each Tabs
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        // Set Tabs inside Toolbar
        tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.VISIBLE);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        ReportFragment.Adapter adapter = new ReportFragment.Adapter(getChildFragmentManager(), tabs.getTabCount());
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


        return view;


    }


    static class Adapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        String[] tabTitles = {"Measurement", "Consumption" };

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
                    MeasurementReportTab measurements = new MeasurementReportTab();
                    return  measurements;
                case 1:
                    ConsumptionReportTab consumptions = new ConsumptionReportTab();
                    return consumptions;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

    }
}
