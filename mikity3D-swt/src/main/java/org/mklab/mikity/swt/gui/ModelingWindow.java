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
package org.mklab.mikity.swt.gui;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DMarshaller;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.config.DataUnitModel;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnitModel;
import org.mklab.mikity.swt.gui.action.AnimationWindowOpenAction;
import org.mklab.mikity.swt.gui.action.display.ConfigurationDialogOpenAction;
import org.mklab.mikity.swt.gui.action.display.ResetToInitialStateAction;
import org.mklab.mikity.swt.gui.action.file.FileExitAction;
import org.mklab.mikity.swt.gui.action.file.FileImportAction;
import org.mklab.mikity.swt.gui.action.file.FileOpenAction;
import org.mklab.mikity.swt.gui.action.file.FileSaveAction;
import org.mklab.mikity.swt.gui.action.file.FileSaveAsAction;
import org.mklab.mikity.swt.gui.toolbar.AbstractToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.BoxToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.CapsuleToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.ConeToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.CylinderToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.GroupToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.QuadrangleToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.SphereToolBarAction;
import org.mklab.mikity.swt.gui.toolbar.TriangleToolBarAction;

/**
 * モデリングを行うウィンドウを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.21 $.2004/12/01
 */
public class ModelingWindow extends ApplicationWindow {
  /** */
  Action FILE_OPEN_ACTION = new FileOpenAction(this);
  /** */
  Action FILE_SAVE_ACTION = new FileSaveAction(this);
  /** */
  Action FILE_SAVE_AS_ACTION = new FileSaveAsAction(this);
  /** */
  Action FILE_IMPORT_ACTION = new FileImportAction(this);
  /** */
  Action FILE_EXIT_ACTION = new FileExitAction(this);
  /** */
  Action CONFIGURATION_DIALOG_OPEN_ACTION = new ConfigurationDialogOpenAction(this);
  /** */
  ResetToInitialStateAction RESET_TO_INITIAL_STATE_ACTION = new ResetToInitialStateAction();
  /** */
  Action ANIMATION_WINDOW_OPEN_ACTION = new AnimationWindowOpenAction(this);

  private AbstractToolBarAction TOOLBAR_GROUP_ACTION = new GroupToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_BOX_ACTION = new BoxToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_SPHERE_ACTION = new SphereToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_CYLINDER_ACTION = new CylinderToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_CONE_ACTION = new ConeToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_CAPSULE_ACTION = new CapsuleToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_TRIANGLE_ACTION = new TriangleToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_QUADRANGLE_ACTION = new QuadrangleToolBarAction(this);
  
  /** モデル。 */
  private Mikity3DModel root;
  /** モデラー。 */
  private JoglModeler modeler;
  /** ファイル。 */
  private File modelFile;

  /** アニメーションウィンドウ。 */
  private AnimationWindow animationWindow;
  
  /**
   * 新しく生成された<code>ModelingWindow</code>オブジェクトを初期化します。
   */
  public ModelingWindow() {
    this(null);
  }

  /**
   * 新しく生成された<code>ModelingWindow</code>オブジェクトを初期化します。
   * @param shell シェル
   */
  public ModelingWindow(final Shell shell) {
    super(shell);
    this.root = Mikity3dFactory.getEmptyModel();
    addMenuBar();
    addToolBar(SWT.FLAT);
    addStatusLine();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createContents(Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout());
    composite.setLayoutData(new GridData(GridData.FILL_BOTH));   

    this.modeler = new JoglModeler(composite, this.root);
    
    ((ConfigurationDialogOpenAction)this.CONFIGURATION_DIALOG_OPEN_ACTION).setModeler(this.modeler);
    ((FileExitAction)this.FILE_EXIT_ACTION).setModeler(this.modeler);
    this.TOOLBAR_GROUP_ACTION.setModeler(this.modeler);
    this.TOOLBAR_BOX_ACTION.setModeler(this.modeler);
    this.TOOLBAR_SPHERE_ACTION.setModeler(this.modeler);
    this.TOOLBAR_CYLINDER_ACTION.setModeler(this.modeler);
    this.TOOLBAR_CONE_ACTION.setModeler(this.modeler);
    this.TOOLBAR_CAPSULE_ACTION.setModeler(this.modeler);
    this.TOOLBAR_TRIANGLE_ACTION.setModeler(this.modeler);
    this.TOOLBAR_QUADRANGLE_ACTION.setModeler(this.modeler);
    this.RESET_TO_INITIAL_STATE_ACTION.setModeler(this.modeler);
      
    return composite;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setSize(1300, 800);
    shell.setText("Mikity3D Modeler"); //$NON-NLS-1$
  }

  /**
   * モデルデータのファイルを設定します。
   * 
   * @param file モデルデータのファイル
   */
  public void setModelFile(File file) {
    this.modelFile = file;
  }
  
  /**
   * モデルデータのファイルを返します。
   * @return モデルデータのファイル
   */
  public File getModelFile() {
    return this.modelFile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MenuManager createMenuManager() {
    final MenuManager fileMenu = new MenuManager(Messages.getString("MainWindow.8")); //$NON-NLS-1$
    fileMenu.add(this.FILE_OPEN_ACTION);
    fileMenu.add(this.FILE_SAVE_ACTION);
    fileMenu.add(this.FILE_SAVE_AS_ACTION);
    fileMenu.add(this.FILE_IMPORT_ACTION);
    fileMenu.add(new Separator());
    fileMenu.add(this.FILE_EXIT_ACTION);

    final MenuManager displayMenu = new MenuManager(Messages.getString("MainWindow.9")); //$NON-NLS-1$
    displayMenu.add(this.CONFIGURATION_DIALOG_OPEN_ACTION);
    displayMenu.add(this.RESET_TO_INITIAL_STATE_ACTION);
    
    final MenuManager playMenu = new MenuManager(Messages.getString("MainWindow.10")); //$NON-NLS-1$
    playMenu.add(this.ANIMATION_WINDOW_OPEN_ACTION);

    final MenuManager manager = new MenuManager();
    manager.add(fileMenu);
    manager.add(displayMenu);
    manager.add(playMenu);

    return manager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
protected ToolBarManager createToolBarManager(int arg0) {
    final ToolBarManager toolbar = new ToolBarManager();
    toolbar.add(this.TOOLBAR_GROUP_ACTION);
    toolbar.add(this.TOOLBAR_BOX_ACTION);
    toolbar.add(this.TOOLBAR_CYLINDER_ACTION);
    toolbar.add(this.TOOLBAR_SPHERE_ACTION);
    toolbar.add(this.TOOLBAR_CONE_ACTION);
    toolbar.add(this.TOOLBAR_CAPSULE_ACTION);
    toolbar.add(this.TOOLBAR_TRIANGLE_ACTION);
    toolbar.add(this.TOOLBAR_QUADRANGLE_ACTION);
    return toolbar;
  }

  /**
   * 単位を設定します。
   */
  private void setUnit() {
    final ConfigurationModel configuration = this.root.getConfiguration(0);

    if (configuration.getModelUnit() != null) {
      final ModelUnitModel modelUnit = configuration.getModelUnit();
      if (modelUnit.getAngleUnit() != null) {
        UnitLabel.setModelAngle(modelUnit.getAngleUnit());
      }
      if (modelUnit.getLengthUnit() != null) {
        UnitLabel.setModelLength(modelUnit.getLengthUnit());
      }
    }
    
    if (configuration.getDataUnit() != null) {
      final DataUnitModel dataUnit = configuration.getDataUnit();
      if (dataUnit.getAngle() != null) {
        UnitLabel.setDataAngle(dataUnit.getAngle());
      }
      if (dataUnit.getLength() != null) {
        UnitLabel.setDataLength(dataUnit.getLength());
      }
    }
  }

  /**
   * シーングラフのルートを返します。
   * 
   * @return root
   */
  public Mikity3DModel getRoot() {
    return this.root;
  }

  /**
   * ファイルに保存します。
   * 
   * @throws Mikity3dSerializeDeserializeException ファイルに保存できない場合  
   */
  public void saveModelFile() throws Mikity3dSerializeDeserializeException {
    if (this.modelFile == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.11")); //$NON-NLS-1$
    }
    final Mikity3DMarshaller marshaller = new Mikity3DMarshaller(this.root);
    marshaller.marshal(this.modelFile);
    this.modeler.setIsChanged(false);
  }

  /**
   * ファイルを読み込みます。
   * 
   * @throws IOException ファイルを読み込めない場合 
   */
  public void loadModelFile() throws IOException {
    if (this.modelFile == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    
    this.root = new Mikity3dFactory().loadFile(this.modelFile);
    setStatus(Messages.getString("MainWindow.13")); //$NON-NLS-1$
    
    getShell().setText("Mikity3D Modeler : " + this.modelFile.getName()); //$NON-NLS-1$    

    setUnit();
    this.modeler.setModel(this.root);
  }

  /**
   * モデルデータのファイルを読み込み，データをモデルに追加します。
   * @throws IOException ファイルを読み込めない場合  
   */
  public void importModelFile() throws IOException {
    if (this.modelFile == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    
    new Mikity3dFactory().importFile(this.modelFile, this.root);
    setStatus(Messages.getString("MainWindow.15")); //$NON-NLS-1$
    
    this.modeler.setIsChanged(true);
    this.modeler.bindModelToTree();
    this.modeler.updateRenderer();
    this.modeler.updatePropertyEditor();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleShellCloseEvent() {
    this.FILE_EXIT_ACTION.run();
    
    if (this.animationWindow != null) {
      this.animationWindow.handleShellCloseEvent();
    }
    
    super.handleShellCloseEvent();
  }
  
  /**
   * アニメーションウィンドウを設定します。
   * 
   * @param animationWindow アニメーションウィンドウ
   */
  public void setAnimationWindow(AnimationWindow animationWindow) {
    this.animationWindow = animationWindow;
  }
  
}