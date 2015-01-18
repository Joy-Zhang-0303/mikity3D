/*
 * Created on 2015/01/13
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class ExceptionDialogFragment extends DialogFragment {
  
  protected String message;
  /**
   * @param savedInstanceState Bundle
   * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
   */
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(this.message)
            .setPositiveButton("閉じる", new DialogInterface.OnClickListener() { //$NON-NLS-1$
                /**
                 * @param dialog dialog 
                 * @param id id
                 */
                public void onClick(DialogInterface dialog, int id) {
                  //nothing to do
                }
            });
    return builder.create();
  }
  /**
   * メッセージを取得します。
   * @param message ダイアログに表示するメッセージ
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
