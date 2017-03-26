package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.managers.MedicineManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateMedicine;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.MedicineListAdapter;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;


/**
 * A placeholder fragment containing a simple view.
 */
public class MedicineFragment extends Fragment {

    private MedicineListAdapter mAdapter;
    private Context context;
    private MedicineManager medicineManager;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.medicine_fragment, container, false);

        ((MainActivity) getActivity()).setFloatingActionButtonAction(AddOrUpdateMedicine.class);
        RecyclerView medicineRecyclerView;
        context = getActivity().getApplicationContext();
        medicineRecyclerView = (RecyclerView) view.findViewById(R.id.all_medicines_list_view);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all medicine info from the database and save in a cursor
        Cursor cursor = MedicineManager.findAll(context);


        // Create an adapter for that cursor to display the data
        mAdapter = new MedicineListAdapter(context, cursor);

        // Link the adapter to the RecyclerView
        medicineRecyclerView.setAdapter(mAdapter);

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //get the id of the item being swiped
                int id = (int) viewHolder.itemView.getTag();
                //remove from DB
                medicineManager = new MedicineManager();
                medicineManager.findById(context, id);
                //update the list
                mAdapter.swapCursor(MedicineManager.findAll(context));

                AlertDialog.Builder warningDialog = new AlertDialog.Builder(getActivity(),R.style.AppTheme_Dialog);
                warningDialog.setTitle(Constants.TITLE_WARNING);
                warningDialog.setMessage(R.string.warning_delete);
                warningDialog.setPositiveButton(Constants.BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        //remove from DB
                        medicineManager.delete(context);
                        ReminderUtils.syncMedicineReminder(context);
                        mAdapter.swapCursor(MedicineManager.findAll(context));
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                });
                warningDialog.setNegativeButton(Constants.BUTTON_NO, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                    }
                });
                warningDialog.show();
            }

        }).attachToRecyclerView(medicineRecyclerView);
        getActivity().setTitle(MEDICINE);

        return view;


    }
}
