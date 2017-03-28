package com.ethigeek.medtracker.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ethigeek.medtracker.utils.Constants;
import com.ethigeek.medtracker.managers.CategoryManager;
import com.ethigeek.medtracker.managers.MedicineManager;
import com.ethigeek.medtracker.R;
import com.ethigeek.medtracker.utils.ColorGenerator;
import com.ethigeek.medtracker.utils.InitialDrawable;
import com.ethigeek.medtracker.utils.ReminderUtils;
import com.ethigeek.medtracker.activities.AddOrUpdateMedicineActivity;
import com.ethigeek.medtracker.activities.ShowMedicineActivity;
import com.ethigeek.medtracker.daoutils.DBHelper;

import static com.ethigeek.medtracker.utils.Constants.*;


/**
 * class for medicine list processing
 *
 * @author Ethiraj Srinivasan
 */
public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MedicineViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private Activity mActivity;
    MedicineManager medicineManager;

    private TextView noMedicine;
    private RecyclerView medicineRecyclerView;

    public MedicineListAdapter(Context context, Activity activity, Cursor cursor, RecyclerView medicineRecyclerView, TextView noMedicine) {
        this.mContext = context;
        this.mActivity = activity;
        this.mCursor = cursor;
        this.medicineRecyclerView = medicineRecyclerView;
        this.noMedicine = noMedicine;
    }

    /**
     * Method execution while creating UI
     *
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
     *
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
                AlertDialog.Builder warningDialog = new AlertDialog.Builder(mActivity,R.style.AppTheme_Dialog);
                warningDialog.setTitle(Constants.TITLE_WARNING);
                warningDialog.setMessage(R.string.warning_delete);
                warningDialog.setPositiveButton(Constants.BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        //remove from DB
                        medicineManager = new MedicineManager();
                        medicineManager.findById(mContext, id);
                        new DeleteMedicine().execute();
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

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrUpdateMedicineActivity.class);
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
                Intent intent = new Intent(mContext, ShowMedicineActivity.class);
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

    private class DeleteMedicine extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return medicineManager.delete(mContext) == -1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            ReminderUtils.syncMedicineReminder(mContext);
            //update the list
            swapCursor(MedicineManager.findAll(mContext));
            if(!result)
                Toast.makeText(mContext, R.string.delete_success, Toast.LENGTH_SHORT).show();
        }


    }

    /**
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

        noMedicine.setVisibility((this.getItemCount() == 0) ? View.VISIBLE : View.GONE);
        medicineRecyclerView.setVisibility((this.getItemCount() == 0) ? View.GONE : View.VISIBLE);
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
