package sg.edu.nus.iss.se.ft05.medipal.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.utils.Constants;
import sg.edu.nus.iss.se.ft05.medipal.listeners.OnStartDragListener;
import sg.edu.nus.iss.se.ft05.medipal.managers.ICEContactsManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.ICEAdditionActivity;
import sg.edu.nus.iss.se.ft05.medipal.daoutils.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAOImpl;

import static sg.edu.nus.iss.se.ft05.medipal.utils.Constants.*;

/**
 * Class for Contact List processing
 * Created by Ashish Katre.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactsViewHolder> implements ItemTouchHelperAdapter {

    // logger name
    private static final String LOG = "ContactsListAdapter";
    private static final String PHONE = "Phone: ";
    private static final int ICE_PERMISSIONS_REQUEST_CALL = 1;
    private static final int ICE_PERMISSIONS_REQUEST_SMS = 2;

    private final OnStartDragListener mDragStartListener;
    ICEContactsManager iceContactsManager;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    // Holds on to the cursor to display the wait list
    private Cursor cursor;
    private Context context;
    private Activity activity;
    private RecyclerView iceRecyclerView;
    private TextView noICE;

    /**
     * Constructor
     *
     * @param context
     * @param cursor
     */
    public ContactsListAdapter(Context context, Cursor cursor, OnStartDragListener dragStartListener, Activity activity, TextView noICE, RecyclerView iceRecyclerView) {

        this.mDragStartListener = dragStartListener;
        this.context = context;
        this.cursor = cursor;
        this.activity = activity;
        this.noICE = noICE;
        this.iceRecyclerView = iceRecyclerView;
    }

    /**
     * Method execution while creating UI
     *
     * @param parent
     * @param viewType
     * @return ContactsViewHolder
     */
    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ice_contacts_list_item, parent, false);

        return new ContactsViewHolder(view);
    }

    /**
     * ethod execution while binding UI
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {

        // Move the mCursor to the position of the item to be displayed
        if (cursor.moveToPosition(position)) {

            // Update the view holder with the information needed to display
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_NAME));
            String type = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_TYPE));
            final long phone = cursor.getLong(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_PHONE));
            final int id = cursor.getInt(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_ID));

            holder.textName.setText(name);
            holder.textType.setText(type);
            holder.textPhone.setText(PHONE + phone);
            holder.itemView.setTag(id);

            // delete icon onclick listener
            holder.deleteIcon.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    AlertDialog.Builder warningDialog = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
                    warningDialog.setTitle(Constants.TITLE_WARNING);
                    warningDialog.setMessage(R.string.warning_delete);
                    warningDialog.setPositiveButton(Constants.BUTTON_YES, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface alert, int which) {
                            //remove from DB
                            iceContactsManager = new ICEContactsManager();
                            iceContactsManager.findById(context, id);
                            iceContactsManager.updatePriority(context);
                            new DeleteAppointment().execute();
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
            });

            // delete icon onclick listener
            holder.smsIcon.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, NO_SMS_PERMISSION, Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, ICE_PERMISSIONS_REQUEST_SMS);

                    } else {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(String.valueOf(phone), null, "Sender is in emergency please call immediately", null, null);
                        Toast.makeText(context, SMS_SENT, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // edit icon onclick listener
            holder.editIcon.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(context, ICEAdditionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ICEAdditionActivity.ICE_BUNDLE_ACTION, ICEAdditionActivity.ICE_BUNDLE_ACTION_EDIT);
                    bundle.putInt(ICEAdditionActivity.ICE_BUNDLE_ACTION_ID, id);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            // image icon onclick listener
            holder.imageIcon.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, NO_CALL_PERMISSION, Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, ICE_PERMISSIONS_REQUEST_CALL);

                    } else {
                        Toast.makeText(context, CALLING, Toast.LENGTH_SHORT).show();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone));
                        try {
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(callIntent);

                        } catch (Exception e) {

                            Log.e(LOG, e.getMessage());
                        }
                    }
                }
            });

            // Start a drag whenever the handle view it touched
            holder.itemView.setOnTouchListener(new View.OnTouchListener()

            {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEvent.ACTION_UP == MotionEventCompat.getActionMasked(event) || MotionEvent.ACTION_DOWN == MotionEventCompat.getActionMasked(event)) {

                        mDragStartListener.onStartDrag(holder);
                    }
                    return true;
                }
            });
        }

    }

    private class DeleteAppointment extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return iceContactsManager.delete(context) == -1;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            //update the list
            swapCursor(ICEContactsManager.findAll(context));
            if (!result)
                Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get number of elements in cursor
     *
     * @return int count
     */
    @Override
    public int getItemCount() {

        return cursor.getCount();
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {

        // Always close the previous mCursor first
        if (cursor != null) {

            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {


            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }

        noICE.setVisibility((this.getItemCount() == 0) ? View.VISIBLE : View.GONE);
        iceRecyclerView.setVisibility((this.getItemCount() == 0) ? View.GONE : View.VISIBLE);
    }

    /**
     * Inner class for ContactsViewHolder
     */
    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textType;
        TextView textPhone;
        ImageView imageIcon;
        ImageView editIcon;
        ImageView deleteIcon;
        ImageView smsIcon;

        /**
         * Constructor of ContactsViewHolder
         *
         * @param itemView
         */
        public ContactsViewHolder(View itemView) {

            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textView_list_ice_name);
            textPhone = (TextView) itemView.findViewById(R.id.textView_list_ice_phone);
            textType = (TextView) itemView.findViewById(R.id.textView_list_ice_type);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageIcon_list_ice_contacts);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon_list_ice_edit);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon_list_ice_delete);
            smsIcon = (ImageView) itemView.findViewById(R.id.smsIcon_list_ice_sms);
        }

        public void onItemSelected() {

            itemView.setBackgroundColor(activity.getResources().getColor(R.color.colorControlNormal));
        }

        public void onItemClear() {

            itemView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
        }
    }

    /**
     * @param position
     */
    @Override
    public void onItemDismiss(int position) {

    }

    /**
     * Method on move by touch
     *
     * @param fromPosition
     * @param toPosition
     * @return boolean
     */
    public boolean onItemMove(int fromPosition, int toPosition) {

        Toast.makeText(context, FROM_POSITION + (fromPosition + 1) + TO_POSITION + (toPosition + 1), Toast.LENGTH_SHORT).show();

        ICEContactsDAOImpl iceContactsDAOImpl = new ICEContactsDAOImpl(context);

        fromPosition++;
        toPosition++;

        iceContactsDAOImpl.updatePriority(fromPosition, -1);

        if (fromPosition < toPosition) {

            for (int priority = (fromPosition + 1); priority <= toPosition; priority++) {

                iceContactsDAOImpl.updatePriority(priority, (priority - 1));
                // chnage priority --
            }

        } else {

            for (int priority = (fromPosition - 1); priority >= toPosition; priority--) {

                iceContactsDAOImpl.updatePriority(priority, (priority + 1));
                // chnage priority ++
            }
        }
        iceContactsDAOImpl.updatePriority(-1, toPosition);
        swapCursor(ICEContactsManager.findAll(context));
        return true;
    }
}
