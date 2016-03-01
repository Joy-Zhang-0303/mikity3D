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
 * メッセージを表示するダイアログを表すクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class MessageDialogFragment extends DialogFragment {
  /** ダイアログに表示するメッセージ。 */
  private String message;

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    
    final Bundle arguments = getArguments();
    if (arguments != null && arguments.containsKey("message")) { //$NON-NLS-1$
      this.message = arguments.getString("message"); //$NON-NLS-1$
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(this.message);
    builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
          /**
           * {@inheritDoc}
           */
          public void onClick(DialogInterface dialog, int id) {
            //nothing to do
          }
        });
    
    return builder.create();
  }
}
