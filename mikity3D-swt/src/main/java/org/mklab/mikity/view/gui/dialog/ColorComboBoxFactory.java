/*
 * $Id: ColorComboBox.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;


/**
 * カラーコンボボックス作成クラス
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class ColorComboBoxFactory {
  /**
   * カラーコンボボックスを生成する。
   * @param parent 親
   * @return カラーコンボボックス
   */
  public static Combo create(Group parent) {
    final Combo combo = new Combo(parent, SWT.READ_ONLY);
    createSelection(combo);
    return combo;
  }
  
  /**
   * カラーコンボボックスを生成する。
   * @param parent 親
   * @return カラーコンボボックス
   */
  public static Combo create(Shell parent) {
    final Combo combo = new Combo(parent, SWT.READ_ONLY);
    createSelection(combo);
    return combo;
  }

  /**
   * @param combo Combo
   */
  private static void createSelection(final Combo combo) {
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    combo.setLayoutData(gridData);
    
    final String[] colors = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
    
    combo.setItems(colors);
    
    combo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        Object source = e.getSource();
        //System.out.println(source);
      }
    });
  }
}
