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

import java.util.Arrays;

import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.model.Category;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.ColorGenerator;
import sg.edu.nus.iss.se.ft05.medipal.Util.InitialDrawable;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateConsumption;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * Created by ethi on 11/03/17.
 */

public class ConsumptionListAdapter extends RecyclerView.Adapter<ConsumptionListAdapter.ConsumptionViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    public ConsumptionListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public ConsumptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.consumption_list_item, parent, false);
        return new ConsumptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumptionListAdapter.ConsumptionViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String medicineName = Medicine.findById(mContext,mCursor.getInt(mCursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID))).getName();
        String date = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE));
        String time = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME));
        String dateTime = date + " " + time;
        String quantity = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY));

        final int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID));
        holder.textMedicineName.setText(medicineName);
        holder.textDateTime.setText(dateTime);
        holder.textQuantity.setText(quantity);
        holder.itemView.setTag(id);



        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrUpdateConsumption.class);
                Bundle b = new Bundle();
                b.putString(ACTION, EDIT);
                b.putInt(ID, id);
                intent.putExtras(b);
                mContext.startActivity(intent);

            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Consumption consumption = Consumption.findById(mContext, id);
                consumption.delete(mContext);
                //update the list
                swapCursor(Consumption.findAll(mContext));
            }
        });


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        InitialDrawable drawable = InitialDrawable.builder().buildRound(medicineName.toUpperCase().substring(0, 1), color);

        holder.icon.setImageDrawable(drawable);

    }

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

    class ConsumptionViewHolder extends RecyclerView.ViewHolder {

        TextView textMedicineName,textDateTime,textQuantity;
        ImageView icon, editIcon, deleteIcon;


        public ConsumptionViewHolder(View itemView) {
            super(itemView);
            textMedicineName = (TextView) itemView.findViewById(R.id.consumptionMedicineName);
            textDateTime = (TextView) itemView.findViewById(R.id.consumptionDateTime);
            textQuantity = (TextView) itemView.findViewById(R.id.consumptionQuantity);
            icon = (ImageView) itemView.findViewById(R.id.consumptionImageIcon);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
        }
    }
}
