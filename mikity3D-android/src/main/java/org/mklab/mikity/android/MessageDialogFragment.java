/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
