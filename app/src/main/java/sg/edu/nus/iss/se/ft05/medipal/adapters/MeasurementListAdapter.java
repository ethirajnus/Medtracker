package sg.edu.nus.iss.se.ft05.medipal.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
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
        String systolic = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_SYSTOLIC));
        String diastolic = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_DIASTOLIC));
        String pulse = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_PULSE));
        String temperature = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_TEMPERATURE));
        String weight = mCursor.getString(mCursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_WEIGHT));

        if(systolic.equals(String.valueOf(0)) && diastolic.equals(String.valueOf(0))){
            holder.textSystolic.setText((formatText(Constants.BLOOD_PRESSURE," - ")));
        } else {
            holder.textSystolic.setText((formatText(Constants.BLOOD_PRESSURE,systolic + "/" +diastolic+ Constants.BLOOD_PRESSURE_UNIT)));
        }
        if(pulse.equals(String.valueOf(0))){
            holder.textPulse.setText((formatText(Constants.PULSE," - ")));
        } else {
            holder.textPulse.setText(formatText(Constants.PULSE, pulse + Constants.PULSE_UNIT));
        }
        if(temperature.equals(String.valueOf(0))){
            holder.textTemperature.setText(formatText(Constants.TEMPERATURE, " - "));
        } else {
            holder.textTemperature.setText(formatText(Constants.TEMPERATURE, temperature + Constants.TEMPERATURE_UNIT));
        }
        if(weight.equals(String.valueOf(0))){
            holder.textWeight.setText(formatText(Constants.WEIGHT, " - "));
        } else {
            holder.textWeight.setText(formatText(Constants.WEIGHT, weight + Constants.WEIGHT_UNIT));
        }

        holder.textDate.setText(formatText(Constants.MEASURE_ON, date));
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

        TextView textDate, textSystolic, textDiastolic, textPulse, textTemperature, textWeight;
        ImageView icon, deleteIcon;


        public MeasurementViewHolder(View itemView) {

            super(itemView);
            textDate = (TextView) itemView.findViewById(R.id.measurementDate);
            textSystolic = (TextView) itemView.findViewById(R.id.measurementBloodPressure);
            textPulse = (TextView) itemView.findViewById(R.id.measurementPulse);
            textTemperature = (TextView) itemView.findViewById(R.id.measurementTemperature);
            textWeight = (TextView) itemView.findViewById(R.id.measurementWeight);
            icon = (ImageView) itemView.findViewById(R.id.measurementImageIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.measurementDeleteIcon);
        }
    }

    private SpannableString formatText(String boldText, String normalText){
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return  str;
    }
}
