package sg.edu.nus.iss.se.ft05.medipal.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.Contacts;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.ColorGenerator;
import sg.edu.nus.iss.se.ft05.medipal.Util.InitialDrawable;
import sg.edu.nus.iss.se.ft05.medipal.activities.ICEAdditionActivity;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;

/**
 * Created by Ashish Katre on 11/03/17.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactsViewHolder> {

    // Holds on to the cursor to display the wait list
    private Cursor cursor;
    private Context context;

    public ContactsListAdapter(Context context, Cursor cursor) {

        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ice_contacts_list_item, parent, false);

        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {

        // Move the mCursor to the position of the item to be displayed
        if (!cursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_NAME));
        String phone = new String("" + cursor.getLong(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_PHONE)));
        final long id = cursor.getLong(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_ID));

        holder.textName.setText(name);
        holder.textPhone.setText("Phone: " + phone);
        holder.itemView.setTag(id);

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Contacts contacts = Contacts.findById(context, id);
                contacts.delete(context);
                //update the list
                swapCursor(Contacts.findAll(context));
            }
        });

        holder.editIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Toast.makeText(context, new String("hi :"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ICEAdditionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ICEAdditionActivity.ICE_BUNDLE_ACTION, ICEAdditionActivity.ICE_BUNDLE_ACTION_EDIT);
                bundle.putLong(ICEAdditionActivity.ICE_BUNDLE_ACTION_ID, id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        InitialDrawable drawable = InitialDrawable.builder().buildRound(name.toUpperCase().substring(0, 1), color);

        holder.icon.setImageDrawable(drawable);


    }

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
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textPhone;
        ImageView icon;
        ImageView editIcon;
        ImageView deleteIcon;


        public ContactsViewHolder(View itemView) {

            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textView_list_ice_name);
            textPhone = (TextView) itemView.findViewById(R.id.textView_list_ice_phone);
            icon = (ImageView) itemView.findViewById(R.id.imageIcon_list_ice_contacts);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon_list_ice_edit);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon_list_ice_delete);
        }
    }
}
