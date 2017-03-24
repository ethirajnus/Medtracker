package sg.edu.nus.iss.se.ft05.medipal.listeners;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import sg.edu.nus.iss.se.ft05.medipal.adapters.ItemTouchHelperAdapter;

/**
 * class for implementing touch functionality
 * Created by ashish katre on 3/17/2017.
 */
public class ContactsTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;
    public static final int ICE_MINUS_ONE = -1;
    public static final int ICE_TEN = 10;
    public static final int ICE_ZERO = 0;
    private int dragfrom = ICE_MINUS_ONE;
    private int dragto = ICE_MINUS_ONE;

    /**
     * Constructor
     *
     * @param adapter ItemTouchHelperAdapter
     */
    public ContactsTouchHelperCallback(ItemTouchHelperAdapter adapter) {

        super();
        mAdapter = adapter;
    }

    /**
     * onmove of selected item
     *
     * @param contactsListAdapter
     * @param contactsViewHolder
     * @param contactsViewHolderTarget
     * @return boolean
     */
    @Override
    public boolean onMove(RecyclerView contactsListAdapter,
                          RecyclerView.ViewHolder contactsViewHolder,
                          RecyclerView.ViewHolder contactsViewHolderTarget) {

        int fromPosition = contactsViewHolder.getAdapterPosition();
        int toPosition = contactsViewHolderTarget.getAdapterPosition();

        if (ICE_MINUS_ONE == dragfrom) {

            dragfrom = fromPosition;
        }

        dragto = toPosition;

        return true;
    }

    /**
     * after movement completed
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (ICE_MINUS_ONE != dragfrom && ICE_MINUS_ONE != dragto && dragfrom != dragto) {

            mAdapter.onItemMove(dragfrom, dragto);
        }

        dragfrom = dragto = ICE_MINUS_ONE;
    }

    /**
     * interpolate Out Of Bounds Scroll
     *
     * @param recyclerView
     * @param viewSize
     * @param viewSizeOutOfBounds
     * @param totalSize
     * @param msSinceStartScroll
     * @return int
     */
    @Override
    public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {

        final int direction = (int) Math.signum(viewSizeOutOfBounds);
        return (ICE_TEN * direction);
    }

    /**
     * is ItemView Swipe Enabled
     *
     * @return boolean
     */
    @Override
    public boolean isItemViewSwipeEnabled() {

        return true;
    }

    /**
     * get movement flag
     *
     * @param recyclerView
     * @param contactsViewHolder
     * @return int
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder contactsViewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        int swipeFlags = ICE_ZERO;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * process on swiped
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
