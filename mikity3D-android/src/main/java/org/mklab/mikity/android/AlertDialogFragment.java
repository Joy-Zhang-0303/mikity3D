/*
 * Created on 2015/01/13
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import roboguice.fragment.RoboDialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


/**
 * 警告を表示するダイアログを表すクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class AlertDialogFragment extends RoboDialogFragment {
  /** ダイアログに表示するメッセージ。 */
  private String message;

  /**
   * {@inheritDoc}
   */
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(this.message);
    builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() { //$NON-NLS-1$
          /**
           * {@inheritDoc}
           */
          public void onClick(DialogInterface dialog, int id) {
            //nothing to do
          }
        });
    return builder.create();
  }

  /**
   * メッセージを設定します。
   * 
   * @param message ダイアログに表示するメッセージ
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
