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
import org.eclipse.swt.widgets.Text;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3DMarshaller;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.config.DataUnit;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnit;
import org.mklab.mikity.view.gui.action.AnimationWindowOpenAction;
import org.mklab.mikity.view.gui.action.display.ConfigurationDialogOpenAction;
import org.mklab.mikity.view.gui.action.display.InitialStateAction;
import org.mklab.mikity.view.gui.action.file.FileExitAction;
import org.mklab.mikity.view.gui.action.file.FileImportAction;
import org.mklab.mikity.view.gui.action.file.FileOpenAction;
import org.mklab.mikity.view.gui.action.file.FileSaveAction;
import org.mklab.mikity.view.gui.action.file.FileSaveAsAction;
import org.mklab.mikity.view.gui.action.toolbar.BoxToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.ConeToolBarAction;
import org.mklab.mikity.view.gui.action.toolbar.CylinderToolBarAction;
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
  //Action FILE_NEW_ACTION = new FileNewAction(this);
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
  
  Action INITIAL_STATE_ACTION = new InitialStateAction(this);
  
  /** */
  Action ANIMATION_WINDOW_OPEN_ACTION = new AnimationWindowOpenAction(this);

  private Action TOOLBAR_BOX_ACTION = new BoxToolBarAction(this);
  private Action TOOLBAR_SPHERE_ACTION = new SphereToolBarAction(this);
  private Action TOOLBAR_CYLINDER_ACTION = new CylinderToolBarAction(this);
  private Action TOOLBAR_CONE_ACTION = new ConeToolBarAction(this);
  private Action TOOLBAR_TRIANGLE_ACTION = new TrianglePolygonToolBarAction(this);
  private Action TOOLBAR_QUAD_ACTION = new QuadPolygonToolBarAction(this);

  /** ファイル */
  private File file;
  
  /** モデル。 */
  private Mikity3d root;
  
  /** */
  Text filePathText;
  
  /** 変更されていればtrue */
  private boolean isChanged;
  /** モデラー。 */
  private JoglModeler modeler;

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

    this.modeler = new JoglModeler(localComposite, SWT.NONE, this.root);
    this.modeler.setLayoutData(new GridData(GridData.FILL_BOTH));
    return localComposite;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText("Modeler"); //$NON-NLS-1$
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
    //fileMenu.add(this.FILE_NEW_ACTION);
    fileMenu.add(this.FILE_OPEN_ACTION);
    fileMenu.add(this.FILE_SAVE_ACTION);
    fileMenu.add(this.FILE_SAVE_AS_ACTION);
    fileMenu.add(this.FILE_IMPORT_ACTION);
    fileMenu.add(new Separator());
    fileMenu.add(this.FILE_EXIT_ACTION);

    final MenuManager displayMenu = new MenuManager(Messages.getString("MainWindow.9")); //$NON-NLS-1$
    displayMenu.add(this.CONFIGURATION_DIALOG_OPEN_ACTION);
    displayMenu.add(this.INITIAL_STATE_ACTION);
    
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
    return toolbar;
  }

  /**
   * 単位を設定します。
   */
  private void setUnit() {
    final Mikity3dConfiguration config = this.root.getConfiguration(0);

    if (config.getModelUnit() != null) {
      final ModelUnit modelUnit = config.getModelUnit();
      if (modelUnit.getAngleUnit() != null) {
        UnitLabel.setModelAngle(modelUnit.getAngleUnit());
      }
      if (modelUnit.getLengthUnit() != null) {
        UnitLabel.setModelLength(modelUnit.getLengthUnit());
      }
    }
    
    if (config.getDataUnit() != null) {
      final DataUnit dataUnit = config.getDataUnit();
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
  public Mikity3d getRoot() {
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
    this.root.getMikity3dData();
    final Mikity3DMarshaller marshaller = new Mikity3DMarshaller(this.root);
    marshaller.marshal(this.file);
    this.isChanged = false;
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
    
    final SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(this.root.getModel(0).getGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.13")); //$NON-NLS-1$
    this.modeler.setModel(this.root);
    this.isChanged = false;
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

    final SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(this.root.getModel(0).getGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.15")); //$NON-NLS-1$
    this.modeler.setModel(this.root);
    this.isChanged = false;
  }

  /**
   * 変更されているか判定します。
   * @return 変更されている場合true
   */
  public boolean isChanged() {
    return this.isChanged;
  }

  /**
   * 変更されているか設定します。
   * @param isChanged 変更されている場合true
   */
  public void setChanged(final boolean isChanged) {
    this.isChanged = isChanged;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleShellCloseEvent() {
    this.FILE_EXIT_ACTION.run();
    super.handleShellCloseEvent();
  }

  /**
   * シーングラフツリーにプリミティブのデータを追加します。
   */
  public void fillTree() {
    this.modeler.fillTree();
  }

  /**
   * GroupをsinsiCanvasに読み込ませ、Frameに追加します。
   */
  public void createViewer() {
    this.modeler.createViewer();
  }
  
  /**
   * モデルへの操作をリセットし、初期状態に戻します。 
   */
  public void setInitialState() {
    this.modeler.setInitialState();
  }
}