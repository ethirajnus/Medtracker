package sg.edu.nus.iss.se.ft05.medipal.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.managers.CategoryManager;
import sg.edu.nus.iss.se.ft05.medipal.managers.MedicineManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.ColorGenerator;
import sg.edu.nus.iss.se.ft05.medipal.Util.InitialDrawable;
import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateMedicine;
import sg.edu.nus.iss.se.ft05.medipal.activities.ShowMedicine;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * Created by ethi on 11/03/17.
 */

/**
 * class for medicine list processing
 */
public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MedicineViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    public MedicineListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    /**
     * Method execution while creating UI
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.medicine_list_item, parent, false);
        return new MedicineViewHolder(view);
    }

    /**
     * Method execution while binding UI
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MedicineListAdapter.MedicineViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_MEDICINE));
        String category = new CategoryManager().findById(mContext, mCursor.getInt(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_CATID))).getCategoryName();
        String description = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_DESCRIPTION));
        Boolean remind = mCursor.getInt(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_REMIND)) == 1;
        String dateIssued = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_DATE_ISSUED));
        String expireFactor = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_EXPIRE_FACTOR));

        final int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.MEDICINE_KEY_ID));


        holder.textName.setText(name);
        holder.textCategory.setText(category);
        holder.textDescription.setText(description);
        holder.switchReminder.setChecked(remind);
        holder.textDateIssued.setText(dateIssued);
        holder.textExpireFactor.setText(expireFactor);
        holder.itemView.setTag(id);


        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MedicineManager medicineManager = new MedicineManager();
                medicineManager.findById(mContext, id);
                medicineManager.delete(mContext);
                ReminderUtils.syncMedicineReminder(mContext);
                //update the list
                swapCursor(MedicineManager.findAll(mContext));
            }
        });

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrUpdateMedicine.class);
                Bundle b = new Bundle();
                b.putString(ACTION, EDIT);
                b.putInt(ID, id);
                intent.putExtras(b);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        holder.switchReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MedicineManager medicineManager = new MedicineManager();
                medicineManager.findById(mContext, id);
                medicineManager.updateReminder(mContext, isChecked);
                ReminderUtils.syncMedicineReminder(mContext);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowMedicine.class);
                Bundle b = new Bundle();
                b.putInt("id", id);
                intent.putExtras(b);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        InitialDrawable drawable = InitialDrawable.builder().buildRound(name.toUpperCase().substring(0, 1), color);

        holder.icon.setImageDrawable(drawable);

    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textDescription, textCategory, textQuantity, textPrescribed, textConsumeQuality, textThreshold, textDateIssued, textExpireFactor;
        ImageView icon, editIcon, deleteIcon;
        Switch switchReminder;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.medicineName);
            icon = (ImageView) itemView.findViewById(R.id.medicineImageIcon);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
            textDescription = (TextView) itemView.findViewById(R.id.medicineDescription);
            textCategory = (TextView) itemView.findViewById(R.id.medicineCategory);
            switchReminder = (Switch) itemView.findViewById(R.id.medicineReminder);
            textDateIssued = (TextView) itemView.findViewById(R.id.medicineDateIssued);
            textExpireFactor = (TextView) itemView.findViewById(R.id.medicineExpireFactor);

        }

    }
}
