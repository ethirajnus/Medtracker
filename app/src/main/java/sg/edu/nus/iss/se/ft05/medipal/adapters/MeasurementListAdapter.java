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

import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.ColorGenerator;
import sg.edu.nus.iss.se.ft05.medipal.Util.InitialDrawable;
import sg.edu.nus.iss.se.ft05.medipal.activities.DisplayMeasurement;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;

/**
 * Created by ashu on 14-03-2017.
 */

public class MeasurementListAdapter extends RecyclerView.Adapter<MeasurementListAdapter.MeasurementViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    public MeasurementListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.measurement_list_item, parent, false);
        return new MeasurementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeasurementViewHolder holder, int position) {

        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String date = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_MEASURED_ON));
        final int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_ID));

        holder.textDate.setText(date);
        holder.itemView.setTag(id);

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                MeasurementManager measurementManager = new MeasurementManager();
                measurementManager.findById(mContext, id);

                measurementManager.delete(mContext);
                //update the list
                swapCursor(MeasurementManager.findAll(mContext));
            }
        });

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DisplayMeasurement.class);
                Bundle b = new Bundle();
                b.putInt(DisplayMeasurement.MEASUREMENT_ID, id);
                intent.putExtras(b);intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        InitialDrawable drawable = InitialDrawable.builder().buildRound("" + id, color);

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
     * @ param newCursor the new cursor that will replace the existing one
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

    class MeasurementViewHolder extends RecyclerView.ViewHolder {

        TextView textDate;
        ImageView icon, deleteIcon;


        public MeasurementViewHolder(View itemView) {

            super(itemView);
            textDate = (TextView) itemView.findViewById(R.id.measurementDate);
            icon = (ImageView) itemView.findViewById(R.id.measurementImageIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.measurementDeleteIcon);
        }
    }
}
