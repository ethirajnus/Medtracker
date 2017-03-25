package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.se.ft05.medipal.managers.ICEContactsManager;
import sg.edu.nus.iss.se.ft05.medipal.listeners.PhoneCallListener;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.ICEAdditionActivity;
import sg.edu.nus.iss.se.ft05.medipal.activities.MainActivity;
import sg.edu.nus.iss.se.ft05.medipal.adapters.ContactsListAdapter;
import sg.edu.nus.iss.se.ft05.medipal.listeners.ContactsTouchHelperCallback;
import sg.edu.nus.iss.se.ft05.medipal.listeners.OnStartDragListener;

/**
 * Class for ICE Fragment operations
 * Created by ashish katre.
 */
public class IceFragment extends Fragment implements OnStartDragListener {

    public static final String TCE_VIEW_TITLE = "In case of emergency";

    // Holds on to the cursor to display the wait list
    private Context context;
    private ContactsListAdapter adapter;

    private ItemTouchHelper mItemTouchHelper;

    /**
     * method for processing when creating view
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set view for ICE ICEContactsManager fragment
        View view = inflater.inflate(R.layout.ice_fragment, container, false);

        // Set floating action button
        ((MainActivity) getActivity()).setFloatingActionButtonAction(ICEAdditionActivity.class);

        // retrieving context
        context = getActivity().getApplicationContext();

        RecyclerView iceRecyclerView = (RecyclerView) view.findViewById(R.id.listView_ice_contacts);

        iceRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        // Get all guest info from the database and save in a cursor
        Cursor cursor = ICEContactsManager.findAll(context);

        PhoneCallListener phoneListener = new PhoneCallListener(context);
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        adapter = new ContactsListAdapter(context, cursor, this, getActivity());

        iceRecyclerView.setAdapter(adapter);

        // TODO change

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_DRAG, 0) {

            // COMPLETED (4) Override onMove and simply return false inside

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id


                //adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition()) {

                Toast.makeText(context, "priority from : " + viewHolder.getAdapterPosition() + " to : " + target.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                adapter.onItemMove(viewHolder.getAdapterPosition(),
                        target.getAdapterPosition());

                return true;
            }

            // COMPLETED (5) Override onSwiped

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped

                int id = (int) viewHolder.itemView.getTag();

                // COMPLETED (9) call removeGuest and pass through that id
                //remove from DB

                // ICEContactsManager contacts = ICEContactsManager.findById(context, id);
                //contacts.delete(context);

                // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list

                adapter.swapCursor(ICEContactsManager.findAll(context));
            }


            @Override
            public boolean isItemViewSwipeEnabled() {

                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {

                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder contactsViewHolder) {

                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(iceRecyclerView);*/

        ItemTouchHelper.Callback callback = new ContactsTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(iceRecyclerView);

        getActivity().setTitle(TCE_VIEW_TITLE);

        return view;
    }

    /**
     * method for starting drag
     *
     * @param viewHolder
     */
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

        mItemTouchHelper.startDrag(viewHolder);
    }
}
