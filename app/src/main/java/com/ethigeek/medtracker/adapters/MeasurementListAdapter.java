package com.ethigeek.medtracker.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ethigeek.medtracker.utils.Constants;
import com.ethigeek.medtracker.managers.MeasurementManager;
import com.ethigeek.medtracker.R;
import com.ethigeek.medtracker.utils.ColorGenerator;
import com.ethigeek.medtracker.utils.InitialDrawable;
import com.ethigeek.medtracker.daoutils.DBHelper;


/**
 * class for measurement list processing
 *
 * @author Aakash Deep Mangalore
 */
public class MeasurementListAdapter extends RecyclerView.Adapter<MeasurementListAdapter.MeasurementViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private Activity mActivity;
    MeasurementManager measurementManager;


    private RecyclerView measurementRecyclerView;
    private TextView noMeasurement;

    public MeasurementListAdapter(Context context, Activity activity, Cursor cursor, RecyclerView measurementRecyclerView, TextView noMeasurement) {
        this.mContext = context;
        this.mActivity = activity;
        this.mCursor = cursor;
        this.measurementRecyclerView = measurementRecyclerView;
        this.noMeasurement = noMeasurement;
    }

    /**
     * Method execution while creating UI
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.measurement_list_item, parent, false);
        return new MeasurementViewHolder(view);
    }

    /**
     * Method execution while binding UI
     *
     * @param holder
     * @param position
     */
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

        if (systolic.equals(String.valueOf(0)) && diastolic.equals(String.valueOf(0))) {
            holder.textSystolic.setText((formatText(Constants.BLOOD_PRESSURE, " - ")));
        } else {
            holder.textSystolic.setText((formatText(Constants.BLOOD_PRESSURE, systolic + "/" + diastolic + Constants.BLOOD_PRESSURE_UNIT)));
        }
        if (pulse.equals(String.valueOf(0))) {
            holder.textPulse.setText((formatText(Constants.PULSE, " - ")));
        } else {
            holder.textPulse.setText(formatText(Constants.PULSE, pulse + Constants.PULSE_UNIT));
        }

        if(temperature.matches(Constants.PATTERN_ZERO)){

            holder.textTemperature.setText(formatText(Constants.TEMPERATURE, " - "));
        } else {
            holder.textTemperature.setText(formatText(Constants.TEMPERATURE, temperature + Constants.TEMPERATURE_UNIT));
        }
        if (weight.equals(String.valueOf(0))) {
            holder.textWeight.setText(formatText(Constants.WEIGHT, " - "));
        } else {
            holder.textWeight.setText(formatText(Constants.WEIGHT, weight + Constants.WEIGHT_UNIT));
        }

        holder.textDate.setText(formatText(Constants.MEASURE_ON, date));
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
                        measurementManager = new MeasurementManager();
                        measurementManager.findById(mContext, id);
                        new DeleteMeasurement().execute();
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

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();
        InitialDrawable drawable = InitialDrawable.builder().buildRound("" + id, color);
        holder.icon.setImageDrawable(drawable);

    }

    private class DeleteMeasurement extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return measurementManager.delete(mContext) == -1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //update the list
            swapCursor(MeasurementManager.findAll(mContext));
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

        noMeasurement.setVisibility((this.getItemCount() == 0) ? View.VISIBLE : View.GONE);
        measurementRecyclerView.setVisibility((this.getItemCount() == 0) ? View.GONE : View.VISIBLE);
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

    /**
     * @param boldText
     * @param normalText
     * @return
     */
    private SpannableString formatText(String boldText, String normalText) {
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
