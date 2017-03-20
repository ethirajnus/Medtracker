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

import sg.edu.nus.iss.se.ft05.medipal.model.Category;
import sg.edu.nus.iss.se.ft05.medipal.R;
import sg.edu.nus.iss.se.ft05.medipal.Util.ColorGenerator;
import sg.edu.nus.iss.se.ft05.medipal.Util.InitialDrawable;
import sg.edu.nus.iss.se.ft05.medipal.activities.AddOrUpdateCategory;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.*;

/**
 * Created by ethi on 11/03/17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    public CategoryListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = mCursor.getString(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_CATEGORY));
        String code = mCursor.getString(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_CODE));
        String description = mCursor.getString(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_DESCRIPTION));
        Boolean remind = mCursor.getInt(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_REMIND)) == 1;
        final int id = mCursor.getInt(mCursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID));


        holder.textName.setText(name);
        holder.textCode.setText(CODE+COLON+" " + code.toUpperCase());
        holder.textDescription.setText(description);
        if (Arrays.asList(Category.safeCategoryCodes).contains(code)) {
            holder.switchReminder.setEnabled(false);
        }
        holder.switchReminder.setChecked(remind);
        holder.itemView.setTag(id);

        holder.switchReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Category category = Category.findById(mContext, id);
                category.updateReminder(mContext, isChecked);

            }
        });

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrUpdateCategory.class);
                Bundle b = new Bundle();
                b.putString(ACTION, EDIT);
                b.putInt(ID, id);
                intent.putExtras(b);
                mContext.startActivity(intent);

            }
        });


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        InitialDrawable drawable = InitialDrawable.builder().buildRound(name.toUpperCase().substring(0, 1), color);

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

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textCode, textDescription;
        ImageView icon, editIcon, deleteIcon;
        Switch switchReminder;


        public CategoryViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.categoryName);
            textCode = (TextView) itemView.findViewById(R.id.categoryCode);
            textDescription = (TextView) itemView.findViewById(R.id.categoryDescription);
            switchReminder = (Switch) itemView.findViewById(R.id.categoryReminder);
            icon = (ImageView) itemView.findViewById(R.id.categoryImageIcon);
            editIcon = (ImageView) itemView.findViewById(R.id.editIcon);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
        }
    }
}
