/*
 * $Id: ColorComboBox.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.model.xml.simplexml.model.ColorConstants;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;


/**
 * 色を選択するためのボタンを表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class ColorSelectorButton implements ModifyListener {
  /** 親。 */
  private Composite parent;
  
  /** 色。 */
  ColorModel color;
  
  /** 選択器。 */
  ColorSelector selector;

  /**
   * 新しく生成された<code>ColorComboBox</code>オブジェクトを初期化します。
   * @param parent 親
   */
  public ColorSelectorButton(Composite parent, final ModifyListener listener) {
    this.parent = parent;
    this.color = new ColorModel("red"); //$NON-NLS-1$

    this.selector = new ColorSelector(this.parent);
    
    final RGB rgb = new RGB(this.color.getR(), this.color.getG(), this.color.getB());
    this.selector.setColorValue(rgb);
    
    final Button button = this.selector.getButton();
    button.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(@SuppressWarnings("unused") final SelectionEvent e) {
        final RGB selectedRGB = ColorSelectorButton.this.selector.getColorValue();
        final ColorModel selectedColor = new ColorModel(selectedRGB.red, selectedRGB.green, selectedRGB.blue);

        if (ColorConstants.contain(selectedColor)) {
          selectedColor.setName(ColorConstants.getColorName(selectedColor));
        }
        
        if (ColorSelectorButton.this.color.equals(selectedColor)) {
          return; 
        }
        
        setColor(selectedColor);
        
        listener.modifyText(null);
      }
    });
  }
  
  /**
   * 色を返します。
   * 
   * @return 色
   */
  public ColorModel getColor() {
    return this.color;
  }
  
  /**
   * 色を設定します。
   * 
   * @param color 色
   */
  public void setColor(ColorModel color) {
    this.color = color;
    if (this.selector != null) {
      this.selector.setColorValue(new RGB(color.getR(),color.getG(),color.getB()));
    }
  }

  /**
   * {@inheritDoc}
   */
  public void modifyText(ModifyEvent arg0) {
    // TODO 自動生成されたメソッド・スタブ
  }
}
