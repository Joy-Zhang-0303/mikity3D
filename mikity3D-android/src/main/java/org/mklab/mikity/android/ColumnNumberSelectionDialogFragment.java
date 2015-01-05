/*
 * Created on 2015/01/05
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;


public class ColumnNumberSelectionDialogFragment extends DialogFragment {
  
  public CanvasActivity activity;
  public int groupPosition;
  public int childPosition;
  
  public ColumnNumberSelectionDialogFragment(CanvasActivity activity, int groupPosition, int childPosition) {
    this.activity = activity;
    this.groupPosition = groupPosition;
    this.childPosition = childPosition;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    CharSequence[] items = {"1", "2", "3", "4", "5"};
   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
   builder.setItems(items, new DialogInterface.OnClickListener() {
       public void onClick(DialogInterface dialog, int which) {
           switch (which) {
               case 0:
                   Toast.makeText(activity, "1が押された", Toast.LENGTH_LONG).show();
                   ColumnNumberSelectionDialogFragment.this.activity.mcnFragment.changeModelColumnNumber(
                       ColumnNumberSelectionDialogFragment.this.groupPosition, 
                       ColumnNumberSelectionDialogFragment.this.childPosition, 1);
                   break;
               case 1:
                   Toast.makeText(activity, "2が押された", Toast.LENGTH_LONG).show();ColumnNumberSelectionDialogFragment.this.activity.mcnFragment.changeModelColumnNumber(
                       ColumnNumberSelectionDialogFragment.this.groupPosition, 
                       ColumnNumberSelectionDialogFragment.this.childPosition, 2);
                   break;
               case 2:
                   Toast.makeText(activity, "3が押された", Toast.LENGTH_LONG).show();
                   ColumnNumberSelectionDialogFragment.this.activity.mcnFragment.changeModelColumnNumber(
                       ColumnNumberSelectionDialogFragment.this.groupPosition, 
                       ColumnNumberSelectionDialogFragment.this.childPosition, 3);
                   break;
               case 3:
                   Toast.makeText(activity, "4", Toast.LENGTH_LONG).show();
                   ColumnNumberSelectionDialogFragment.this.activity.mcnFragment.changeModelColumnNumber(
                       ColumnNumberSelectionDialogFragment.this.groupPosition, 
                       ColumnNumberSelectionDialogFragment.this.childPosition, 4);
                   break;
               case 4:
                   Toast.makeText(activity, "5", Toast.LENGTH_LONG).show();
                   ColumnNumberSelectionDialogFragment.this.activity.mcnFragment.changeModelColumnNumber(
                       ColumnNumberSelectionDialogFragment.this.groupPosition, 
                       ColumnNumberSelectionDialogFragment.this.childPosition, 5);
                   break;
               default:
                   break;
           }
       }
   });
   return builder.create();
  }
}
