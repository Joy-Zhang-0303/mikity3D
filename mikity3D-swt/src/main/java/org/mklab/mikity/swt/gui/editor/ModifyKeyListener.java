/*
 * Created on 2015/09/13
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.swt.gui.editor;

import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;


/**
 * {@link ModifyListener}と{@link KeyListener}の両方を継承するインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/09/13
 */
public interface ModifyKeyListener extends ModifyListener, KeyListener {
  // 
}
