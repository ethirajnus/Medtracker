package sg.edu.nus.iss.se.ft05.medipal.adapters;

/**
 * Created by Dhruv on 18/3/2017.
 */

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

import sg.edu.nus.iss.se.ft05.medipal.Util.ReminderUtils;
import sg.edu.nus.iss.se.ft05.medipal.managers.AppointmentManager;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.activities.EditAppointment;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.AppointmentViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public AppointmentListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public AppointmentListAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.appointment_list_item, parent, false);
        return new AppointmentListAdapter.AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentListAdapter.AppointmentViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;
        String date = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_DATE));
        String time = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_TIME));
        String clinic = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_CLINIC));
        String test = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_TEST));
        String pre_test = mCursor.getString(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_PRE_TEST));
        final int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_ID));


        holder.itemView.setTag(id);
        holder.date.setText(date);
        holder.time.setText(time);
        holder.clinic.setText(clinic);
        holder.test.setText(test);
        holder.pre_test.setText(pre_test);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppointmentManager appointmentManager = new AppointmentManager();

                appointmentManager.findById(mContext, id);

                appointmentManager.delete(mContext);

                ReminderUtils.syncAppointmentReminder(mContext);
                //update the list
                swapCursor(AppointmentManager.findAll(mContext));
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


    }

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
    }

    class AppointmentViewHolder extends RecyclerView.ViewHolder {
        ImageView delete, edit;
        TextView clinic,test,pre_test,date,time;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            edit = (ImageView) itemView.findViewById(R.id.editIcon);
            delete = (ImageView) itemView.findViewById(R.id.deleteIcon);
            clinic = (TextView) itemView.findViewById(R.id.clinic);
            test= (TextView) itemView.findViewById(R.id.test);
            pre_test= (TextView) itemView.findViewById(R.id.pre_test);
            date= (TextView) itemView.findViewById(R.id.date);
            time= (TextView) itemView.findViewById(R.id.time);

        }
    }
}
