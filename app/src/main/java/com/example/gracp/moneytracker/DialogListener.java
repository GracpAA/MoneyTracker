package com.example.gracp.moneytracker;

import android.support.v7.view.ActionMode;

/**
 * Created by gracp on 16.11.2017.
 */

public interface DialogListener {
        void removeSelectedItems(ItemsAdapter adapter);
        void finishActionMode(ActionMode actionMode);
        void clearSelections(ItemsAdapter adapter);
}
