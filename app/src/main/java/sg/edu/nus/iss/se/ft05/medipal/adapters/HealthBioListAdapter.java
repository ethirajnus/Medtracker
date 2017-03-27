package sg.edu.nus.iss.se.ft05.medipal.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.ColorGenerator;
import sg.edu.nus.iss.se.ft05.medipal.Util.InitialDrawable;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateHealthBioActivity;
import sg.edu.nus.iss.se.ft05.medipal.constants.Constants;
import sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants;
import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;
import sg.edu.nus.iss.se.ft05.medipal.managers.HealthBioManager;


/**
 * Class for Health bio list processing
 * @author Moushumi Seal
 */
public class HealthBioListAdapter extends RecyclerView.Adapter<HealthBioListAdapter.HealthBioViewHolder> {

    // Holds on to the cursor to display the health bio list
    private Cursor mCursor;
    private Context mContext;
    HealthBioManager healthBioManager;

    private static final String LOG = "HealthBioListAdapter";

    public HealthBioListAdapter(Context context, Cursor cursor) {
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
    public HealthBioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.healthbio_list_item, parent, false);

        return new HealthBioViewHolder(view);
    }

    /**
     * Method execution while binding UI
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HealthBioViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return;

        // Update the view holder with the information needed to display
        String condition = mCursor.getString(mCursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION));
        String conditionType = mCursor.getString(mCursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION_TYPE));
        String startDate = mCursor.getString(mCursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_START_DATE));
        final int id = mCursor.getInt(mCursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID));


        holder.textCondition.setText(condition);
        if(conditionType.equalsIgnoreCase(Constants.CONDITION_TYPE_CONDITION))
            conditionType = Constants.CONDITION;
        else
            conditionType = Constants.ALLERGY;
        holder.textConditionType.setText(Constants.TYPE + conditionType);
        holder.textStartDate.setText(Constants.DIAGNOSED_ON + startDate);
        holder.itemView.setTag(id);

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                healthBioManager = new HealthBioManager();
                healthBioManager.findById(mContext, id);
                new DeleteHealthBio().execute();
            }
        });

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrUpdateHealthBioActivity.class);
                Bundle b = new Bundle();
                b.putString(Constants.ACTION, Constants.EDIT);
                b.putInt("id", id);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        InitialDrawable drawable = InitialDrawable.builder().buildRound(condition.toUpperCase().substring(0, 1), color);
        holder.icon.setImageDrawable(drawable);
    }

    private class DeleteHealthBio extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return healthBioManager.delete(mContext)==-1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //update the list
            swapCursor(HealthBioManager.findAll(mContext));
        }
    }
    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null)
            mCursor.close();

        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class HealthBioViewHolder extends RecyclerView.ViewHolder {

        TextView textCondition, textConditionType, textStartDate;
        ImageView icon, editIcon, deleteIcon;


        public HealthBioViewHolder(View itemView) {
            super(itemView);
            textCondition = (TextView) itemView.findViewById(R.id.condition);
            textConditionType = (TextView) itemView.findViewById(R.id.conditionType);
            textStartDate = (TextView) itemView.findViewById(R.id.startDate);
            icon = (ImageView) itemView.findViewById(R.id.healthbioImageIcon);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
        }
    }
}
