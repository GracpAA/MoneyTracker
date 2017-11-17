package com.example.gracp.moneytracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;


import static com.example.gracp.moneytracker.ItemsFragment.adapter;

/**
 * Created by gracp on 16.11.2017.
 */

public class ConfirmationDialog extends DialogFragment implements DialogListener{

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getContext()).setCancelable(false).setTitle(R.string.app_name).setMessage(getString(R.string.confirmation,adapter.getSelectedItems().size())).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeSelectedItems(ItemsFragment.adapter);
                finishActionMode(ItemsFragment.actionMode);

            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearSelections(ItemsFragment.adapter);
                finishActionMode(ItemsFragment.actionMode);
            }
        });

        return builder.create();

    }

    @Override
    public void removeSelectedItems(ItemsAdapter adapter) {
        for (int i = adapter.getSelectedItems().size() - 1; i >= 0; i--) {
            adapter.remove(adapter.getSelectedItems().get(i));
        }
    }

    @Override
    public void finishActionMode(ActionMode actionMode) {
        actionMode.finish();
    }

    @Override
    public void clearSelections(ItemsAdapter adapter) {
        adapter.clearSelections();
    }


}
