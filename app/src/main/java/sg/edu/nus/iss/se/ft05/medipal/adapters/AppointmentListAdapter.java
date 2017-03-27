package sg.edu.nus.iss.se.ft05.medipal.adapters;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import sg.edu.nus.iss.se.ft05.medipal.Util.ColorGenerator;
import sg.edu.nus.iss.se.ft05.medipal.Util.InitialDrawable;
import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.activities.ShowAppointment;
import sg.edu.nus.iss.se.ft05.medipal.managers.AppointmentManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.EditAppointment;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;

/**
 * Class for Appointment list processing
 *
 * @author Dhruv Mandan Gopal
 */
public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.AppointmentViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private AppointmentManager appointmentManager;

    private RecyclerView recyclerView;
    private TextView noAppointments;

    public AppointmentListAdapter(Context context, Cursor cursor, RecyclerView recyclerView, TextView noAppointments) {
        this.mContext = context;
        this.mCursor = cursor;
        this.recyclerView = recyclerView;
        this.noAppointments = noAppointments;
    }

    /**
     * Method execution while creating UI
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public AppointmentListAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.appointment_list_item, parent, false);
        return new AppointmentListAdapter.AppointmentViewHolder(view);
    }

    /**
     * Method execution while binding UI
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(AppointmentListAdapter.AppointmentViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;
        String date = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_DATE));
        String time = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_TIME));
        String clinic = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_CLINIC));
        String description = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION));

        final int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_ID));

        holder.itemView.setTag(id);
        holder.dateTime.setText(date + " " + time);
        holder.clinic.setText(formatText(clinic, ""));
        holder.description.setText(formatText("Description: ", description));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                appointmentManager = new AppointmentManager();

                appointmentManager.findById(mContext, id);

                new DeleteAppointment().execute();

            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditAppointment.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowAppointment.class);
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
        InitialDrawable drawable = InitialDrawable.builder().buildRound(clinic.toUpperCase().substring(0, 1), color);
        holder.icon.setImageDrawable(drawable);
    }

    private class DeleteAppointment extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return appointmentManager.delete(mContext) == -1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            ReminderUtils.syncAppointmentReminder(mContext);
            //update the list
            swapCursor(AppointmentManager.findAll(mContext));
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

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }

        noAppointments.setVisibility((this.getItemCount() == 0) ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility((this.getItemCount() == 0) ? View.GONE : View.VISIBLE);
    }

    class AppointmentViewHolder extends RecyclerView.ViewHolder {
        ImageView delete, edit, icon;
        TextView clinic, description, pre_test, dateTime;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.appointmentImageIcon);
            edit = (ImageView) itemView.findViewById(R.id.editIcon);
            delete = (ImageView) itemView.findViewById(R.id.deleteIcon);
            clinic = (TextView) itemView.findViewById(R.id.clinic);
            description = (TextView) itemView.findViewById(R.id.description);
            dateTime = (TextView) itemView.findViewById(R.id.dateTime);
        }
    }

    private SpannableString formatText(String boldText, String normalText) {
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
