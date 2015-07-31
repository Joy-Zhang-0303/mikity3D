package org.mklab.mikity.view.gui;

import java.io.File;

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
import org.mklab.mikity.view.gui.action.AnimationWindowOpenAction;
import org.mklab.mikity.view.gui.action.display.ConfigurationDialogOpenAction;
import org.mklab.mikity.view.gui.action.display.ResetToInitialStateAction;
import org.mklab.mikity.view.gui.action.file.FileExitAction;
import org.mklab.mikity.view.gui.action.file.FileImportAction;
import org.mklab.mikity.view.gui.action.file.FileOpenAction;
import org.mklab.mikity.view.gui.action.file.FileSaveAction;
import org.mklab.mikity.view.gui.action.file.FileSaveAsAction;
import org.mklab.mikity.view.gui.action.toolbar.AbstractToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.BoxToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.ConeToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.CylinderToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.GroupToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.QuadPolygonToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.SphereToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.TrianglePolygonToolBarAction;

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
  private AbstractToolBarAction TOOLBAR_TRIANGLE_ACTION = new TrianglePolygonToolBarAction(this);
  private AbstractToolBarAction TOOLBAR_QUAD_ACTION = new QuadPolygonToolBarAction(this);
  
  /** モデル。 */
  private Mikity3DModel root;
  /** モデラー。 */
  private JoglModeler modeler;
  /** ファイル。 */
  private File file;

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
    this.root = new Mikity3dFactory().createEmptyModel();
    addMenuBar();
    addToolBar(SWT.FLAT);
    addStatusLine();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createContents(Composite composite) {
    final Composite localComposite = new Composite(composite, SWT.NONE);
    localComposite.setLayout(new GridLayout());
    localComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

    this.modeler = new JoglModeler(localComposite, this.root);
    
    ((ConfigurationDialogOpenAction)this.CONFIGURATION_DIALOG_OPEN_ACTION).setModeler(this.modeler);
    ((FileExitAction)this.FILE_EXIT_ACTION).setModeler(this.modeler);
    this.TOOLBAR_GROUP_ACTION.setModeler(this.modeler);
    this.TOOLBAR_BOX_ACTION.setModeler(this.modeler);
    this.TOOLBAR_SPHERE_ACTION.setModeler(this.modeler);
    this.TOOLBAR_CYLINDER_ACTION.setModeler(this.modeler);
    this.TOOLBAR_CONE_ACTION.setModeler(this.modeler);
    this.TOOLBAR_TRIANGLE_ACTION.setModeler(this.modeler);
    this.TOOLBAR_QUAD_ACTION.setModeler(this.modeler);
    this.RESET_TO_INITIAL_STATE_ACTION.setModeler(this.modeler);
      
    return localComposite;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText("Mikity3D Modeler"); //$NON-NLS-1$
  }

  /**
   * ファイルを設定します。
   * 
   * @param file ファイル
   */
  public void setFile(File file) {
    this.file = file;
  }
  
  /**
   * ファイルを返します。
   * @return ファイル
   */
  public File getFile() {
    return this.file;
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
    toolbar.add(this.TOOLBAR_BOX_ACTION);
    toolbar.add(this.TOOLBAR_SPHERE_ACTION);
    toolbar.add(this.TOOLBAR_CYLINDER_ACTION);
    toolbar.add(this.TOOLBAR_CONE_ACTION);
    toolbar.add(this.TOOLBAR_TRIANGLE_ACTION);
    toolbar.add(this.TOOLBAR_QUAD_ACTION);
    toolbar.add(this.TOOLBAR_GROUP_ACTION);
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
   * @throws Mikity3dSerializeDeserializeException ファイルに保存できない場合  
   */
  public void saveFile() throws Mikity3dSerializeDeserializeException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.11")); //$NON-NLS-1$
    }
    final Mikity3DMarshaller marshaller = new Mikity3DMarshaller(this.root);
    marshaller.marshal(this.file);
    this.modeler.setChanged(false);
  }

  /**
   * ファイルを読み込みます。
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合 
   */
  public void loadFile() throws Mikity3dSerializeDeserializeException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    
    this.root = new Mikity3dFactory().loadFile(this.file);

    setUnit();
    setStatus(Messages.getString("MainWindow.13")); //$NON-NLS-1$
    this.modeler.setModel(this.root);
  }

  /**
   * ファイルを読み込み，データをモデルに追加します。
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合  
   */
  public void importFile() throws Mikity3dSerializeDeserializeException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    
    new Mikity3dFactory().importFile(this.file, this.root);

    setStatus(Messages.getString("MainWindow.15")); //$NON-NLS-1$
    this.modeler.setChanged(true);
    this.modeler.fillTree();
    this.modeler.updateRenderer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleShellCloseEvent() {
    this.FILE_EXIT_ACTION.run();
    super.handleShellCloseEvent();
  }
}