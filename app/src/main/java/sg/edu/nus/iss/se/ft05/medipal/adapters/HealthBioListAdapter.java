package sg.edu.nus.iss.se.ft05.medipal.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.model.HealthBio;

/**
 * @author Moushumi Seal
 */

public class HealthBioListAdapter extends RecyclerView.Adapter<HealthBioListAdapter.HealthBioViewHolder> {

    // Holds on to the cursor to display the health bio list
    private Cursor mCursor;
    private Context mContext;

    private static final String LOG = "HealthBioListAdapter";

    public HealthBioListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public HealthBioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.healthbio_list_item, parent, false);

        return new HealthBioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HealthBioViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return;

        // Update the view holder with the information needed to display
        String condition = mCursor.getString(mCursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION));
        final int id = mCursor.getInt(mCursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID));
        Log.v("Healthbio id", String.valueOf(id));


        holder.textCondition.setText(condition);
        holder.itemView.setTag(id);

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

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class HealthBioViewHolder extends RecyclerView.ViewHolder {

        TextView textCondition;
        ImageView icon, editIcon;


        public HealthBioViewHolder(View itemView) {
            super(itemView);
            textCondition = (TextView) itemView.findViewById(R.id.condition);
            icon = (ImageView) itemView.findViewById(R.id.healthbioImageIcon);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon);
        }
    }
}
