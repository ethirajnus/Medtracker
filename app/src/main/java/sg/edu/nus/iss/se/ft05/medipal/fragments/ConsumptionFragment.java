package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateConsumption;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ConsumptionListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.model.Category;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * Created by ethi on 08/03/17.
 */

public class ConsumptionFragment extends Fragment {

    private ConsumptionListAdapter mAdapter;
    private Context context;
    private TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumption_fragment, container, false);
        ((MainActivity) getActivity()).setFloatingActionButtonAction(AddOrUpdateConsumption.class);
        RecyclerView consumptionRecyclerView;
        context = getActivity().getApplicationContext();
//        consumptionRecyclerView = (RecyclerView) view.findViewById(R.id.all_consumption_list_view);
//        // Set layout for the RecyclerView, because it's a list we are using the linear layout
//        consumptionRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//
//        // Get all guest info from the database and save in a cursor
//        Cursor cursor = Consumption.findAll(context);
//
//        // Create an adapter for that cursor to display the data
//        mAdapter = new ConsumptionListAdapter(context, cursor);
//
//        // Link the adapter to the RecyclerView
//        consumptionRecyclerView.setAdapter(mAdapter);
//
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                //do nothing, we only care about swiping
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                //get the id of the item being swiped
//                int id = (int) viewHolder.itemView.getTag();
//                //remove from DB
//                Consumption consumption = Consumption.findById(context, id);
//                consumption.delete(context);
//                //update the list
//                mAdapter.swapCursor(Consumption.findAll(context));
//            }
//
//        }).attachToRecyclerView(consumptionRecyclerView);

        getActivity().setTitle(CONSUMPTION);

        // Setting ViewPager for each Tabs
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        // Set Tabs inside Toolbar
        tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setVisibility(View.VISIBLE);
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


        return view;

    }


    // Add Fragments to Tabs


    static class Adapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        String[] tabTitles = {"Consumption by Category", "Consumption by Medicine", "Measurements"};

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
                    ConsumptionByCategoryTab categories = new ConsumptionByCategoryTab();
                    return  categories;
                case 1:
                    ConsumptionByMedicineTab medicines = new ConsumptionByMedicineTab();
                    return medicines;

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
