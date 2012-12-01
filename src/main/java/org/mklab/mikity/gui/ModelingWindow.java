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
import org.mklab.mikity.jogl.JoglModeler;
import org.mklab.mikity.xml.JAXBMarshaller;
import org.mklab.mikity.xml.JAXBUnmarshaller;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.JamastConfig;
import org.mklab.mikity.xml.JamastFactory;
import org.mklab.mikity.xml.config.DataUnit;
import org.mklab.mikity.xml.config.ModelUnit;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;

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

  private Action TOOLBAR_BOX_ACTION = new BoxToolBarAction(this);
  private Action TOOLBAR_SPHERE_ACTION = new SphereToolBarAction(this);
  private Action TOOLBAR_CYLINDER_ACTION = new CylinderToolBarAction(this);
  private Action TOOLBAR_CONE_ACTION = new ConeToolBarAction(this);
  private Action TOOLBAR_TRIANGLE_ACTION = new TrianglePolygonToolBarAction(this);
  private Action TOOLBAR_QUAD_ACTION = new QuadPolygonToolBarAction(this);

  private File file;
  
  private Jamast root;
  
  /** */
  private Text filePathText;
  private Button newModelButton;
  private Button modelerButton;
  private Button configButton;
  private Button simButton;
  private Button saveButton;
  private Button saveAsButton;
  
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
   * ファイルを選択するボタン
   * 
   * @param composite コンポジット
   */
  public void createFileChooseComp(final Composite composite) {
    final Composite localComposite = new Composite(composite, SWT.NONE);
    final GridLayout layout = new GridLayout();
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

    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
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
    final Composite localComposite = new Composite(composite, SWT.NONE);
    final GridLayout layout = new GridLayout();
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
    final MenuManager manager = new MenuManager();

    final MenuManager localFile = new MenuManager(Messages.getString("MainWindow.8")); //$NON-NLS-1$
    localFile.add(this.FILE_NEW_ACTION);
    localFile.add(this.FILE_OPEN_ACTION);
    localFile.add(this.FILE_SAVE_ACTION);
    localFile.add(this.FILE_SAVE_AS_ACTION);
    localFile.add(this.FILE_IMPORT_ACTION);
    localFile.add(new Separator());
    localFile.add(this.FILE_EXIT_ACTION);
    manager.add(localFile);

    final MenuManager edit = new MenuManager(Messages.getString("MainWindow.9")); //$NON-NLS-1$
    edit.add(this.CONFIGDIALOG_OPEN_ACTION);
    manager.add(edit);

    final MenuManager play = new MenuManager(Messages.getString("MainWindow.10")); //$NON-NLS-1$
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

  final class SaveAsButtonSelectionListener extends SelectionAdapter {
    @Override
    public void widgetSelected(SelectionEvent arg0) {
      ModelingWindow.this.FILE_SAVE_AS_ACTION.run();
    }
  }

  /**
   * ファイルに保存します。
   * @throws IOException ファイルに保存できない場合 
   * @throws JAXBException ファイルに保存できない場合 
   */
  public void save() throws JAXBException, IOException {
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
  public void load() throws IOException, JAXBException {
    if (this.file == null) {
      throw new IllegalArgumentException(Messages.getString("MainWindow.12")); //$NON-NLS-1$
    }
    
    this.root = new JamastFactory().loadJamastFile(this.file);
    
    // setEditable(true);
    final SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(this.root.loadModel(0).loadGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.13")); //$NON-NLS-1$
    this.modeler.setModel(this.root);
    // setEditable(false);
    this.dirty = false;
  }

  /**
   * ファイルを読み込み，データをモデルに追加します。
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  public void importFile() throws IOException, JAXBException {
    new JamastFactory().importJavaFile(this.file, this.root);

    // setEditable(true);
    final SceneGraphTree tree = new SceneGraphTree();
    tree.setAllTransparent(this.root.loadModel(0).loadGroup(0), false);
    setUnit();
    setStatus(Messages.getString("MainWindow.15")); //$NON-NLS-1$
    this.modeler.setModel(this.root);
    // setEditable(false);
    this.dirty = false;
  }

//  /**
//   * ファイルを読み込み，データをrootに追加します。
//   * @param localFile ファイル
//   * @param localRoot Jamastのroot
//   * @throws JAXBException ファイルを読み込めない場合 
//   * @throws IOException ファイルを読み込めない場合 
//   */
//  private void importJavaFile(File localFile, Jamast localRoot) throws IOException, JAXBException {
//    if (localFile == null) {
//      throw new IllegalArgumentException(Messages.getString("MainWindow.14")); //$NON-NLS-1$
//    }
//
//    final Group group = localRoot.loadModel(0).loadGroup(0);
//    
//    final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
//    unmarshaller.unmarshal(localFile);
//
//    final Jamast newRoot = unmarshaller.getRoot();
//    if (newRoot != null) {
//      final Group newGroup = newRoot.loadModel(0).loadGroup(0);
//
//      final XMLBox[] boxs = newGroup.getXMLBox();
//      final XMLCone[] cones = newGroup.getXMLCone();
//      final XMLCylinder[] cylinders = newGroup.getXMLCylinder();
//      final XMLSphere[] spheres = newGroup.getXMLSphere();
//      final XMLConnector[] connectors = newGroup.getXMLConnector();
//      final XMLTrianglePolygon[] triangles = newGroup.getXMLTrianglePolygon();
//      final XMLQuadPolygon[] quads = newGroup.getXMLQuadPolygon();
//      final Group[] groups = newGroup.getGroups();
//      
//      if (boxs != null) {
//        for (int i = 0; i < boxs.length; i++) {
//          group.addXMLBox(boxs[i]);
//        }
//      }
//      if (cones != null) {
//        for (int i = 0; i < cones.length; i++) {
//          group.addXMLCone(cones[i]);
//        }
//      }
//      if (cylinders != null) {
//        for (int i = 0; i < cylinders.length; i++) {
//          group.addXMLCylinder(cylinders[i]);
//        }
//      }
//      if (spheres != null) {
//        for (int i = 0; i < spheres.length; i++) {
//          group.addXMLSphere(spheres[i]);
//        }
//      }
//      if (connectors != null) {
//        for (int i = 0; i < connectors.length; i++) {
//          group.addXMLConnector(connectors[i]);
//        }
//      }
//      if (triangles != null) {
//        for (int i = 0; i < triangles.length; i++) {
//          group.addXMLTrianglePolygon(triangles[i]);
//        }
//      }
//      if (quads != null) {
//        for (int i = 0; i < quads.length; i++) {
//          group.addXMLQuadPolygon(quads[i]);
//        }
//      }
//      if (groups != null) {
//        for (int i = 0; i < groups.length; i++) {
//          group.addGroup(groups[i]);
//        }
//      }
//    } else {
//      final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
//      for (int i = 0; i < groups.length; i++) {
//        group.addGroup(groups[i]);
//      }
//    }
//  }

  /**
   * @return isDirty 変更されている場合true
   */
  public boolean isDirty() {
    return this.dirty;
  }

  /**
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