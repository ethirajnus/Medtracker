package sg.edu.nus.iss.se.ft05.medipal.listeners;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

import sg.edu.nus.iss.se.ft05.medipal.adapters.ItemTouchHelperAdapter;

/**
 * class for implementing touch functionality
 * Created by ashish katre on 3/17/2017.
 */

public class ContactsTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;
    private int dragfrom = -1;
    private int dragto = -1;

    public ContactsTouchHelperCallback(ItemTouchHelperAdapter adapter) {

        super();
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView contactsListAdapter,
                          RecyclerView.ViewHolder contactsViewHolder,
                          RecyclerView.ViewHolder contactsViewHolderTarget) {

        int fromPosition = contactsViewHolder.getAdapterPosition();
        int toPosition = contactsViewHolderTarget.getAdapterPosition();


        if (dragfrom == -1) {

            dragfrom = fromPosition;
        }

        dragto = toPosition;

        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (dragfrom != -1 && dragto != -1 && dragfrom != dragto) {

            mAdapter.onItemMove(dragfrom, dragto);
        }

        dragfrom = dragto = -1;
    }

/*    @Override
    public boolean isLongPressDragEnabled() {

        return true;
    }*/


    @Override
    public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {

        final int direction = (int) Math.signum(viewSizeOutOfBounds);
        return 10 * direction;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {

        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder contactsViewHolder) {

     /*   int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags, swipeFlags);*/

//        return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
//                ItemTouchHelper.DOWN | ItemTouchHelper.UP);

//        return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.UP | ItemTouchHelper.DOWN) | makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
//                ItemTouchHelper.DOWN | ItemTouchHelper.UP);

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
