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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mklab.mikity.action.AnimationWindowOpenAction;
import org.mklab.mikity.action.ConfigDialogOpenAction;
import org.mklab.mikity.action.ModelEditorOpenAction;
import org.mklab.mikity.action.file.FileExitAction;
import org.mklab.mikity.action.file.FileImportAction;
import org.mklab.mikity.action.file.FileNewAction;
import org.mklab.mikity.action.file.FileOpenAction;
import org.mklab.mikity.action.file.FileSaveAction;
import org.mklab.mikity.action.file.FileSaveAsAction;
import org.mklab.mikity.action.toolbar.BoxToolBarAction;
import org.mklab.mikity.action.toolbar.ConeToolBarAction;
import org.mklab.mikity.action.toolbar.CylinderToolBarAction;
import org.mklab.mikity.action.toolbar.QuadPolygonToolBarAction;
import org.mklab.mikity.action.toolbar.SphereToolBarAction;
import org.mklab.mikity.action.toolbar.TrianglePolygonToolBarAction;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.jogl.JoglModeler;
import org.mklab.mikity.xml.JAXBMarshaller;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.JamastConfig;
import org.mklab.mikity.xml.JamastModel;
import org.mklab.mikity.xml.config.DataUnit;
import org.mklab.mikity.xml.config.ModelUnit;
import org.mklab.mikity.xml.model.Group;

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
  Action ANIMATION_WINDOW_OPEN_ACTION = new AnimationWindowOpenAction(this);
  /** */
  Action FILE_SAVE_ACTION = new FileSaveAction(this);
  /** */
  Action FILE_SAVE_AS_ACTION = new FileSaveAsAction(this);
  /** */
  Action FILE_IMPORT_ACTION = new FileImportAction(this);
  /** */
  Action MODELER_OPEN_ACTION = new ModelEditorOpenAction(this);
  /** */
  Action CONFIGDIALOG_OPEN_ACTION = new ConfigDialogOpenAction(this);
  /** */
  Action FILE_EXIT_ACTION = new FileExitAction(this);

  private Composite comp;
  private CollisionCanceller canceller = new CollisionCanceller(this.comp);

  private Action TOOLBAR_BOX_ACTION = new BoxToolBarAction(this);
  private Action TOOLBAR_SPHERE_ACTION = new SphereToolBarAction(this);
  private Action TOOLBAR_CYLINDER_ACTION = new CylinderToolBarAction(this);
  private Action TOOLBAR_CONE_ACTION = new ConeToolBarAction(this);
  private Action TOOLBAR_TRIANGLE_ACTION = new TrianglePolygonToolBarAction(this);
  private Action TOOLBAR_QUAD_ACTION = new QuadPolygonToolBarAction(this);

  private File file;
  private File loadFile;
  private static Jamast root = ModelingWindow.createEmptyModel();
  /** */
  Text filePathText;
  private Button newModelButton;
  private Button modelerButton;
  private Button configButton;
  private Button simButton;
  private Button saveButton;
  private Button saveAsButton;
  private boolean isDirty;

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
    addMenuBar();
    addToolBar(SWT.FLAT);
    addStatusLine();
  }

  /**
   * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite composite) {
    Composite localComposite = new Composite(composite, SWT.NONE);
    localComposite.setLayout(new GridLayout());
    localComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

    this.comp = localComposite;

    // TODO Java3d or JOGL
    // Java3d or JOGL
    //this.modeler = new Java3dModeler(localComposite, SWT.NONE, root, this.canceller);
    this.modeler = new JoglModeler(localComposite, SWT.NONE, root, this.canceller);
    
    this.modeler.setLayoutData(new GridData(GridData.FILL_BOTH));
    return localComposite;
  }

  /**
   * ファイルを選択するボタン
   * 
   * @param composite コンポジット
   */
  public void createFileChooseComp(final Composite composite) {
    Composite localComposite = new Composite(composite, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 6;
    localComposite.setLayout(layout);
    localComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Label label = new Label(localComposite, SWT.NONE);
    label.setText(Messages.getString("MainWindow.0")); //$NON-NLS-1$

    this.filePathText = new Text(localComposite, SWT.BORDER);
    this.filePathText.setText(""); //$NON-NLS-1$
    this.filePathText.addTraverseListener(new TraverseListener() {

      @Override
      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          setFile(ModelingWindow.this.filePathText.getText());
        }
      }
    });

    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 4;
    this.filePathText.setLayoutData(gridData);

    final Button refButton = new Button(localComposite, SWT.BORDER);
    refButton.setText(Messages.getString("MainWindow.1")); //$NON-NLS-1$
    refButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        ModelingWindow.this.FILE_OPEN_ACTION.run();
      }
    });
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
   * ボタンを作成、配置
   * 
   * @param composite コンポジット
   */
  public void createMainButtonComp(final Composite composite) {
    Composite localComposite = new Composite(composite, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 6;
    localComposite.setLayout(layout);
    localComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    this.newModelButton = new Button(localComposite, SWT.NONE);
    this.newModelButton.setText(Messages.getString("MainWindow.2")); //$NON-NLS-1$

    this.modelerButton = new Button(localComposite, SWT.NONE);
    this.modelerButton.setText(Messages.getString("MainWindow.3")); //$NON-NLS-1$

    this.configButton = new Button(localComposite, SWT.NONE);
    this.configButton.setText(Messages.getString("MainWindow.4")); //$NON-NLS-1$

    this.simButton = new Button(localComposite, SWT.NONE);
    this.simButton.setText(Messages.getString("MainWindow.5")); //$NON-NLS-1$

    this.saveAsButton = new Button(localComposite, SWT.NONE);
    this.saveAsButton.setText(Messages.getString("MainWindow.6")); //$NON-NLS-1$

    this.saveButton = new Button(localComposite, SWT.NONE);
    this.saveButton.setText(Messages.getString("MainWindow.7")); //$NON-NLS-1$

    // 編集できないようにする
    // setEditable(false);

    this.newModelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        ModelingWindow.this.FILE_NEW_ACTION.run();
      }
    });

    // ファイルの上書き保存
    this.saveButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        ModelingWindow.this.FILE_SAVE_ACTION.run();
      }
    });


    // ファイルの別名保存
    this.saveAsButton.addSelectionListener(new SaveAsButtonSelectionListener());

    this.modelerButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        ModelingWindow.this.MODELER_OPEN_ACTION.run();
      }
    });

    this.configButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        ModelingWindow.this.CONFIGDIALOG_OPEN_ACTION.run();
      }
    });

    this.simButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        ModelingWindow.this.ANIMATION_WINDOW_OPEN_ACTION.run();
      }
    });

  }

  /**
   * ファイルの設定を行う
   * 
   * @param filePath ファイルパス
   */
  public void setFile(String filePath) {
    File tmp = new File(filePath);
    this.file = tmp;
    this.modeler.createViewer();
  }

  /**
   * メニューバーを作成する。
   * 
   * @see org.eclipse.jface.window.ApplicationWindow#createMenuManager()
   */
  @Override
  protected MenuManager createMenuManager() {
    MenuManager manager = new MenuManager();

    MenuManager localFile = new MenuManager(Messages.getString("MainWindow.8")); //$NON-NLS-1$
    localFile.add(this.FILE_NEW_ACTION);
    localFile.add(this.FILE_OPEN_ACTION);
    localFile.add(this.FILE_SAVE_ACTION);
    localFile.add(this.FILE_SAVE_AS_ACTION);
    localFile.add(this.FILE_IMPORT_ACTION);
    localFile.add(new Separator());
    localFile.add(this.FILE_EXIT_ACTION);
    manager.add(localFile);

    MenuManager edit = new MenuManager(Messages.getString("MainWindow.9")); //$NON-NLS-1$
    edit.add(this.CONFIGDIALOG_OPEN_ACTION);
    manager.add(edit);

    MenuManager play = new MenuManager(Messages.getString("MainWindow.10")); //$NON-NLS-1$
    play.add(this.ANIMATION_WINDOW_OPEN_ACTION);
    manager.add(play);

    return manager;
  }

  /**
   * ツールバーを作成する。
   * 
   * @return ツールバー
   */
  @Override
  protected ToolBarManager createToolBarManager(int arg0) {
    ToolBarManager toolbar = new ToolBarManager();
    toolbar.add(this.TOOLBAR_BOX_ACTION);
    toolbar.add(this.TOOLBAR_SPHERE_ACTION);
    toolbar.add(this.TOOLBAR_CYLINDER_ACTION);
    toolbar.add(this.TOOLBAR_CONE_ACTION);
    toolbar.add(this.TOOLBAR_TRIANGLE_ACTION);
    toolbar.add(this.TOOLBAR_QUAD_ACTION);
    return toolbar;
  }

  /**
   * 単位の設定
   */
  private void setUnit() {
    JamastConfig config = root.loadConfig(0);

    if (config.loadModelUnit() != null) {
      ModelUnit modelUnit = config.loadModelUnit();
      if (modelUnit.loadAngle() != null) {
        UnitLabel.setModelAngle(modelUnit.loadAngle());
      }
      if (modelUnit.loadLength() != null) {
        UnitLabel.setModelLength(modelUnit.loadLength());
      }
    }
    if (config.loadDataUnit() != null) {
      DataUnit dataUnit = config.loadDataUnit();
      if (dataUnit.loadAngle() != null) {
        UnitLabel.setDataAngle(dataUnit.loadAngle());
      }
      if (dataUnit.loadLength() != null) {
        UnitLabel.setDataLength(dataUnit.loadLength());
      }
    }
  }

  /**
   * シーングラフのルートの取得
   * 
   * @return root
   */
  public static Jamast getRoot() {
    return root;
  }

  final class SaveAsButtonSelectionListener extends SelectionAdapter {

    @Override
    public void widgetSelected(SelectionEvent arg0) {
      ModelingWindow.this.FILE_SAVE_AS_ACTION.run();
    }
  }

  /**
   * ファイルを保存します。
   * @throws IOException ファイルに保存できない場合 
   * @throws JAXBException ファイルに保存できない場合 
   * 
   * @throws IllegalArgumentException 例外
   */
  public void save() throws JAXBException, IOException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.11")); //$NON-NLS-1$
    }
    root.loadJamastXMLData();
    JAXBMarshaller marshaller = new JAXBMarshaller(root);
    marshaller.marshal(this.file);
    setFile(this.file.getPath());
    this.isDirty = false;
  }

  /**
   * ファイルを読み込みます。
   * @throws IOException ファイルに保存できない場合 
   * @throws JAXBException ファイルに保存できない場合 
   */
  public void load() throws IOException, JAXBException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    JAXBMarshaller marshaller = new JAXBMarshaller();
    marshaller.unmarshal(this.file);
    root = marshaller.getRoot();
    if (root == null) {
      root = ModelingWindow.createEmptyModel();
      Group groupBlender = root.loadModel(0).loadGroup(0);
      Group[] polygonGroupList = marshaller.getBlenderGroup().loadGroup();
      for (int i = 0; i < polygonGroupList.length; i++) {
        groupBlender.addGroup(polygonGroupList[i]);
      }
    }
    this.loadFile = marshaller.getLoadFile();
    // setEditable(true);
    SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(getRoot().loadModel(0).loadGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.13")); //$NON-NLS-1$
    this.modeler.setModel(root);
    // setEditable(false);
    this.isDirty = false;
  }

  /**
   * ファイルを読み込みます。
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  public void importFile() throws IOException, JAXBException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.14")); //$NON-NLS-1$
    }

    JAXBMarshaller marshaller = new JAXBMarshaller();
    marshaller.unmarshal(this.file);

    if (marshaller.getRoot() != null) {
      Group importGroup = marshaller.getRoot().loadModel(0).loadGroup(0);

      org.mklab.mikity.xml.model.XMLBox[] box = importGroup.loadXMLBox();
      org.mklab.mikity.xml.model.XMLCone[] cone = importGroup.loadXMLCone();
      org.mklab.mikity.xml.model.XMLCylinder[] cylinder = importGroup.loadXMLCylinder();
      org.mklab.mikity.xml.model.XMLSphere[] sphere = importGroup.loadXMLSphere();
      org.mklab.mikity.xml.model.XMLConnector[] connector = importGroup.loadXMLConnector();
      org.mklab.mikity.xml.model.XMLTrianglePolygon[] triangle = importGroup.loadXMLTrianglePolygon();
      org.mklab.mikity.xml.model.XMLQuadPolygon[] quad = importGroup.loadXMLQuadPolygon();
      org.mklab.mikity.xml.model.Group[] group = importGroup.loadGroup();

      Group rootGroup = root.loadModel(0).loadGroup(0);

      if (box != null) {
        for (int i = 0; i < box.length; i++) {
          rootGroup.addXMLBox(box[i]);
        }
      }
      if (cone != null) {
        for (int i = 0; i < cone.length; i++) {
          rootGroup.addXMLCone(cone[i]);
        }
      }
      if (cylinder != null) {
        for (int i = 0; i < cylinder.length; i++) {
          rootGroup.addXMLCylinder(cylinder[i]);
        }
      }
      if (sphere != null) {
        for (int i = 0; i < sphere.length; i++) {
          rootGroup.addXMLSphere(sphere[i]);
        }
      }
      if (connector != null) {
        for (int i = 0; i < connector.length; i++) {
          rootGroup.addXMLConnector(connector[i]);
        }
      }
      if (triangle != null) {
        for (int i = 0; i < triangle.length; i++) {
          rootGroup.addXMLTrianglePolygon(triangle[i]);
        }
      }
      if (quad != null) {
        for (int i = 0; i < quad.length; i++) {
          rootGroup.addXMLQuadPolygon(quad[i]);
        }
      }
      if (group != null) {
        for (int i = 0; i < group.length; i++) {
          rootGroup.addGroup(group[i]);
        }
      }
    } else {
      Group groupBlender = root.loadModel(0).loadGroup(0);
      Group[] polygonGroupList = marshaller.getBlenderGroup().loadGroup();
      for (int i = 0; i < polygonGroupList.length; i++) {
        groupBlender.addGroup(polygonGroupList[i]);
      }
    }

    // setEditable(true);
    SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(getRoot().loadModel(0).loadGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.15")); //$NON-NLS-1$
    this.modeler.setModel(root);
    // setEditable(false);
    this.isDirty = false;
  }

  /**
   * @return isDirty
   */
  public boolean isDirty() {
    return this.isDirty;
  }

  /**
   * @param dirty 変更されている場合true
   */
  public void setDirty(final boolean dirty) {
    this.isDirty = true;
  }

  @Override
  protected void handleShellCloseEvent() {
    this.FILE_EXIT_ACTION.run();
    super.handleShellCloseEvent();
  }

  /**
   * シーングラフツリーにプリミティブのデータを追加させる。
   */
  public void fillTree() {
    this.modeler.fillTree();
  }

  /**
   * GroupをsinsiCanvasに読み込ませ、Frameにaddする。
   */
  public void createViewer() {
    this.modeler.createViewer();
  }

  /**
   * 読み込みファイルの取得
   * 
   * @return　loadFile　読み込みファイル
   */
  public File getLoadFile() {
    return this.loadFile;
  }

  /**
   * @return root
   */
  public static Jamast createEmptyModel() {
    final Jamast root = new Jamast();
    final JamastConfig jamastConfig = new JamastConfig();
    final JamastModel jamastModel = new JamastModel();
    root.addConfig(jamastConfig);
    root.addModel(jamastModel);
    org.mklab.mikity.xml.model.Group rootGroup = new org.mklab.mikity.xml.model.Group();
    rootGroup.setName(Messages.getString("FileNewAction.5")); //$NON-NLS-1$
    jamastModel.addGroup(rootGroup);
    return root;
  }
}