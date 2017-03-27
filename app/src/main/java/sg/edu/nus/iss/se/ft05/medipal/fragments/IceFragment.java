package sg.edu.nus.iss.se.ft05.medipal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private Context context;
    private ContactsListAdapter adapter;

    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView iceRecyclerView;
    private TextView noICE;

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

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        FloatingActionButton fabSOS = (FloatingActionButton) getActivity().findViewById(R.id.fabSOS);
        TextView tvSOS = (TextView) getActivity().findViewById(R.id.tv_sos);
        fabSOS.setVisibility(View.GONE);
        tvSOS.setVisibility(View.GONE);

        // Set floating action button
        ((MainActivity) getActivity()).setFloatingActionButtonAction(ICEAdditionActivity.class);

        // retrieving context
        context = getActivity().getApplicationContext();

        iceRecyclerView = (RecyclerView) view.findViewById(R.id.listView_ice_contacts);
        noICE = (TextView) view.findViewById(R.id.tv_noICE);

        iceRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        //hide the share button
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        // Get all guest info from the database and save in a cursor
        Cursor cursor = ICEContactsManager.findAll(context);

        PhoneCallListener phoneListener = new PhoneCallListener(context);
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        adapter = new ContactsListAdapter(context, cursor, this, getActivity(),noICE,iceRecyclerView);

        iceRecyclerView.setAdapter(adapter);
        checkForEmptyList();

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

    private void checkForEmptyList(){
        if(adapter != null ){
            noICE.setVisibility((adapter.getItemCount() == 0)? View.VISIBLE : View.GONE);
            iceRecyclerView.setVisibility((adapter.getItemCount() == 0)? View.GONE : View.VISIBLE);
        }
    }
}
