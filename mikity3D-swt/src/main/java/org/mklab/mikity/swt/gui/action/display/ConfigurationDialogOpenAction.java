/*
 * Created on 2005/02/18
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.swt.gui.action.display;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.ModelingWindow;
import org.mklab.mikity.swt.gui.action.Messages;
import org.mklab.mikity.swt.gui.editor.ConfigurationEditor;


/**
 * 各種設定画面を展開するクラスです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.4 $.2005/02/18
 */
public class ConfigurationDialogOpenAction extends Action {
  /** モデリングウィンドウ。 */
  private ModelingWindow window;
  
  /** モデラー。 */
  private JoglModeler modeler;

  /**
   * 新しく生成された<code>ConfigDialogOpenAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public ConfigurationDialogOpenAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("ConfigurationDialogOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("ConfigurationDialogOpenAction.1")); //$NON-NLS-1$
  }

  /**
   * 各種設定画面を展開します。
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final ConfigurationEditor dialog = new ConfigurationEditor(this.window.getShell(), this.modeler.getRoot().getConfiguration(0), this.modeler);
    dialog.open();
  }
  
  /**
   * モデラーを設定します。
   * @param modeler モデラー
   */
  public void setModeler(JoglModeler modeler) {
    this.modeler = modeler;
  }
}