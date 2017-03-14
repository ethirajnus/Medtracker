package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.Contacts;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.ICEAdditionActivity;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ContactsListAdapter;

/**
 * Created by ashish katre.
 */

public class IceFragment extends Fragment {

    private ContactsListAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set view for ICE Contacts fragment
        View view = inflater.inflate(R.layout.ice_fragment, container, false);

        // Set floating action button
        ((MainActivity) getActivity()).setFloatingActionButtonAction(ICEAdditionActivity.class);

        // retrieving context
        context = getActivity().getApplicationContext();

        RecyclerView iceRecyclerView = (RecyclerView) view.findViewById(R.id.listView_ice_contacts);

        iceRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Get all guest info from the database and save in a cursor
        Cursor cursor = Contacts.findAll(context);

        adapter = new ContactsListAdapter(context, cursor);

        iceRecyclerView.setAdapter(adapter);

        // TODO change

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

                long id = (long) viewHolder.itemView.getTag();

                // COMPLETED (9) call removeGuest and pass through that id
                //remove from DB

                Contacts contacts = Contacts.findById(context, id);
                contacts.delete(context);

                // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list

                adapter.swapCursor(Contacts.findAll(context));
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(iceRecyclerView);

        // TODO Chnage

        getActivity().setTitle("In case of emergency contacts");

        return view;
    }

}
