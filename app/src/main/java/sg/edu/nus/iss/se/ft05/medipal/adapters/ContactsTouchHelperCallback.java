package sg.edu.nus.iss.se.ft05.medipal.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

/**
 * class for implementing touch functionality
 * Created by ashish katre on 3/17/2017.
 */

public class ContactsTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public ContactsTouchHelperCallback(ItemTouchHelperAdapter adapter) {

        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView contactsListAdapter,
                          RecyclerView.ViewHolder contactsViewHolder,
                          RecyclerView.ViewHolder contactsViewHolderTarget) {

        mAdapter.onItemMove(contactsViewHolder.getAdapterPosition(),
                contactsViewHolderTarget.getAdapterPosition());

        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder contactsViewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
