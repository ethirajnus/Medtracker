package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateMedicine;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.MedicineListAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class MedicineFragment extends Fragment {

    private MedicineListAdapter mAdapter;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.medicine_fragment, container, false);

        ((MainActivity)getActivity()).setFloatingActionButtonAction(AddOrUpdateMedicine.class);
        RecyclerView medicineRecyclerView;
        context = getActivity().getApplicationContext();
        medicineRecyclerView = (RecyclerView) view.findViewById(R.id.all_medicines_list_view);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all medicine info from the database and save in a cursor
        Cursor cursor = Medicine.findAll(context);


        // Create an adapter for that cursor to display the data
        mAdapter = new MedicineListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        medicineRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // COMPLETED (4) Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // COMPLETED (5) Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                int id = (int) viewHolder.itemView.getTag();
                // COMPLETED (9) call removeGuest and pass through that id
                //remove from DB
                Medicine medicine = Medicine.findById(context, id);
                medicine.delete(context);
                // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list
                mAdapter.swapCursor(Medicine.findAll(context));
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(medicineRecyclerView);
        getActivity().setTitle("Medicine");

        return view;


    }
}
