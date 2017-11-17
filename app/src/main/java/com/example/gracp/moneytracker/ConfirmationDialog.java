package com.example.gracp.moneytracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;



/**
 * Created by gracp on 16.11.2017.
 */

public class ConfirmationDialog extends DialogFragment{

    private DialogListener listener = null;

    public void setListener(DialogListener listener){
        this.listener = listener;
    };


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getContext()).setCancelable(false).setTitle(R.string.app_name).setMessage("Уверены?").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             listener.onPositiveClick();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             listener.onNegativeClick();
            }
        });

        return builder.create();

    }

}
