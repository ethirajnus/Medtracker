package com.ethigeek.medtracker.adapters;

/**
 * Interface for touch functionality
 * Created by Ashish Katre on 3/17/2017.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
