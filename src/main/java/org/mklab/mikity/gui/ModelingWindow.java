package org.mklab.mikity.gui;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

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
import org.mklab.mikity.jogl.JoglModeler;
import org.mklab.mikity.view.action.AnimationWindowOpenAction;
import org.mklab.mikity.view.action.ConfigDialogOpenAction;
import org.mklab.mikity.view.action.file.FileExitAction;
import org.mklab.mikity.view.action.file.FileImportAction;
import org.mklab.mikity.view.action.file.FileNewAction;
import org.mklab.mikity.view.action.file.FileOpenAction;
import org.mklab.mikity.view.action.file.FileSaveAction;
import org.mklab.mikity.view.action.file.FileSaveAsAction;
import org.mklab.mikity.view.action.toolbar.BoxToolBarAction;
import org.mklab.mikity.view.action.toolbar.ConeToolBarAction;
import org.mklab.mikity.view.action.toolbar.CylinderToolBarAction;
import org.mklab.mikity.view.action.toolbar.QuadPolygonToolBarAction;
import org.mklab.mikity.view.action.toolbar.SphereToolBarAction;
import org.mklab.mikity.view.action.toolbar.TrianglePolygonToolBarAction;
import org.mklab.mikity.xml.JAXBMarshaller;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.JamastConfig;
import org.mklab.mikity.xml.JamastFactory;
import org.mklab.mikity.xml.config.DataUnit;
import org.mklab.mikity.xml.config.ModelUnit;

/**
 * モデリングを行うウィンドウを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.21 $.2004/12/01
 */
public class ModelingWindow extends ApplicationWindow {
  /** */
  Action FILE_NEW_ACTION = new FileNewAction(this);
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
  Action CONFIGDIALOG_OPEN_ACTION = new ConfigDialogOpenAction(this);
  /** */
  Action ANIMATION_WINDOW_OPEN_ACTION = new AnimationWindowOpenAction(this);

  private Action TOOLBAR_BOX_ACTION = new BoxToolBarAction(this);
  private Action TOOLBAR_SPHERE_ACTION = new SphereToolBarAction(this);
  private Action TOOLBAR_CYLINDER_ACTION = new CylinderToolBarAction(this);
  private Action TOOLBAR_CONE_ACTION = new ConeToolBarAction(this);
  private Action TOOLBAR_TRIANGLE_ACTION = new TrianglePolygonToolBarAction(this);
  private Action TOOLBAR_QUAD_ACTION = new QuadPolygonToolBarAction(this);

  private File file;
  
  private Jamast root;
  
  /** */
  Text filePathText;
  
  private boolean dirty;
  private AbstractModeler modeler;

  /**
   * コンストラクター
   * 
   */
  public ModelingWindow() {
    this(null);
  }

  /**
   * コンストラクター
   * 
   * @param shell シェル
   */
  public ModelingWindow(final Shell shell) {
    super(shell);
    this.root = new JamastFactory().createEmptyModel();
    addMenuBar();
    addToolBar(SWT.FLAT);
    addStatusLine();
  }

  /**
   * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite composite) {
    final Composite localComposite = new Composite(composite, SWT.NONE);
    localComposite.setLayout(new GridLayout());
    localComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

    // TODO Java3d or JOGL
    //this.modeler = new Java3dModeler(localComposite, SWT.NONE, this.root);
    this.modeler = new JoglModeler(localComposite, SWT.NONE, this.root);
    
    this.modeler.setLayoutData(new GridData(GridData.FILL_BOTH));
    return localComposite;
  }

  /**
   * シェルの設定を行う
   * 
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText("Modeler"); //$NON-NLS-1$
  }

  /**
   * ファイルを設定します。
   * 
   * @param filePath ファイルパス
   */
  public void setFile(String filePath) {
    this.file = new File(filePath);
    this.modeler.createViewer();
  }

  /**
   * メニューバーを作成する。
   * 
   * @see org.eclipse.jface.window.ApplicationWindow#createMenuManager()
   */
  @Override
  protected MenuManager createMenuManager() {
    final MenuManager fileMenu = new MenuManager(Messages.getString("MainWindow.8")); //$NON-NLS-1$
    fileMenu.add(this.FILE_NEW_ACTION);
    fileMenu.add(this.FILE_OPEN_ACTION);
    fileMenu.add(this.FILE_SAVE_ACTION);
    fileMenu.add(this.FILE_SAVE_AS_ACTION);
    fileMenu.add(this.FILE_IMPORT_ACTION);
    fileMenu.add(new Separator());
    fileMenu.add(this.FILE_EXIT_ACTION);

    final MenuManager editMenu = new MenuManager(Messages.getString("MainWindow.9")); //$NON-NLS-1$
    editMenu.add(this.CONFIGDIALOG_OPEN_ACTION);
    
    final MenuManager playMenu = new MenuManager(Messages.getString("MainWindow.10")); //$NON-NLS-1$
    playMenu.add(this.ANIMATION_WINDOW_OPEN_ACTION);

    final MenuManager manager = new MenuManager();
    manager.add(fileMenu);
    manager.add(editMenu);
    manager.add(playMenu);

    return manager;
  }

  /**
   * ツールバーを作成します。
   * 
   * @return ツールバー
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
    final JamastConfig config = this.root.loadConfig(0);

    if (config.loadModelUnit() != null) {
      final ModelUnit modelUnit = config.loadModelUnit();
      if (modelUnit.loadAngle() != null) {
        UnitLabel.setModelAngle(modelUnit.loadAngle());
      }
      if (modelUnit.loadLength() != null) {
        UnitLabel.setModelLength(modelUnit.loadLength());
      }
    }
    
    if (config.loadDataUnit() != null) {
      final DataUnit dataUnit = config.loadDataUnit();
      if (dataUnit.loadAngle() != null) {
        UnitLabel.setDataAngle(dataUnit.loadAngle());
      }
      if (dataUnit.loadLength() != null) {
        UnitLabel.setDataLength(dataUnit.loadLength());
      }
    }
  }

  /**
   * シーングラフのルートを返します。
   * 
   * @return root
   */
  public Jamast getRoot() {
    return this.root;
  }

  /**
   * ファイルに保存します。
   * @throws IOException ファイルに保存できない場合 
   * @throws JAXBException ファイルに保存できない場合 
   */
  public void saveFile() throws JAXBException, IOException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.11")); //$NON-NLS-1$
    }
    this.root.loadJamastXMLData();
    final JAXBMarshaller marshaller = new JAXBMarshaller(this.root);
    marshaller.marshal(this.file);
    setFile(this.file.getPath());
    this.dirty = false;
  }

  /**
   * ファイルを読み込みます。
   * @throws IOException ファイルを読み込めない場合
   * @throws JAXBException ファイルを読み込めない場合
   */
  public void loadFile() throws IOException, JAXBException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    
    this.root = new JamastFactory().loadJamastFile(this.file);
    
    final SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(this.root.loadModel(0).loadGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.13")); //$NON-NLS-1$
    this.modeler.setModel(this.root);
    this.dirty = false;
  }

  /**
   * ファイルを読み込み，データをモデルに追加します。
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  public void importFile() throws IOException, JAXBException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    
    new JamastFactory().importJavaFile(this.file, this.root);

    final SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(this.root.loadModel(0).loadGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.15")); //$NON-NLS-1$
    this.modeler.setModel(this.root);
    this.dirty = false;
  }

  /**
   * 変更されているか判定します。
   * @return isDirty 変更されている場合true
   */
  public boolean isDirty() {
    return this.dirty;
  }

  /**
   * 変更されているか設定します。
   * @param dirty 変更されている場合true
   */
  public void setDirty(final boolean dirty) {
    this.dirty = dirty;
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
}