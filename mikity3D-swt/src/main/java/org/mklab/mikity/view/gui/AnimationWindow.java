package org.mklab.mikity.view.gui;

import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.mklab.mikity.control.AnimationTask;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SourceDataModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.renderer.ObjectRenderer;
import org.mklab.mikity.view.renderer.jogl.JoglObjectRenderer;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matx.MatxMatrix;


/**
 * アニメーション描画を行うウィンドウを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.21 $.2004/12/02
 */

public class AnimationWindow extends ApplicationWindow {

  /** アニメーション用タスク */
  AnimationTask animationTask;
  /** */
  private SliderPositionMoveTask sliderTask;

  /** */
  public static boolean playable = true;

  /** */
  private double endTime;

  private Mikity3DModel root;

  /** */
  double speedRate = 1.0;

  /** */
  Timer timer = new Timer();

  /** */
  Slider timeSlider;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

//  /** ソースデータ。 */
//  private Matrix sourceData;

  /** */
  Text modelFilePathText;

  /** */
  Text sourceFilePathText;

  /** */
  ObjectGroupManager manager;

  /** */
  private Label startTimeLabel;
  /** */
  Label currentTimeLabel;
  /** */
  private Label endTimeLabel;
  /** */
  ParameterInputBox playSpeed;

  /** Canvas */
  private ObjectRenderer renderer;

  /** */
  Frame frame;

  /** */
  private Composite viewComposite;
  
  /** モデルファイル */
  private File modelFile;
  
//  /**
//   * 新しく生成された<code>AnimationWindow</code>オブジェクトを初期化します。
//   * 
//   * @param parentShell 親シェル
//   * @param root ルート
//   */
//  private AnimationWindow(final Shell parentShell, final Mikity3DModel root) {
//    super(parentShell);
//    setRoot(root);
//  }

  /**
   * 新しく生成された<code>AnimationWindow</code>オブジェクトを初期化します。
   * 
   * @param parentShell 親シェル
   * @param root ルート
   * @param modelFile ファイルパス
   */
  public AnimationWindow(final Shell parentShell, final Mikity3DModel root, File modelFile) {
    super(parentShell);
    setRoot(root);
    this.modelFile = modelFile;
    this.renderer = new JoglObjectRenderer();
  }

//  /**
//   * 新しく生成された<code>AnimationWindow</code>オブジェクトを初期化します。
//   * 
//   * @param parentShell 親シェル
//   * @param modelFile モデルファイル
//   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
//   */
//  public AnimationWindow(final Shell parentShell, File modelFile) throws Mikity3dSerializeDeserializeException {
//    super(parentShell);
//    setRoot(new Mikity3dFactory().loadFile(modelFile));
//    this.modelFile = modelFile;
//  }

  /**
   * 新しく生成された<code>AnimationWindow</code>オブジェクトを初期化します。
   * @param parentShell 親シェル
   */
  public AnimationWindow(final Shell parentShell) {
    super(parentShell);
    this.renderer = new JoglObjectRenderer();
  }

  /**
   * ルートを設定します。
   * 
   * モデル描画をできるようにします。
   * 
   * @param root ルート
   */
  void setRoot(final Mikity3DModel root) {
    this.root = root;
    this.manager = new ObjectGroupManager();
    
    final List<SourceDataModel> sources = this.root.getConfiguration(0).getSources();
    if (sources != null) {
      for (final SourceDataModel source : sources) {
        addSource(source.getId(), source.getFilePath());
      }
    }
    
    final GroupModel[] rootGroups = this.root.getScene(0).getGroups();
    for (GroupModel rootGroup : rootGroups) {
      checkAnimation(rootGroup);
    }
  }


  /**
   * レンダラーを準備します。
   */
  void prepareRenderer() {
    final GroupModel[] rootGroups = this.root.getScene(0).getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);
    
    this.manager.clearObjectGroups();
    this.renderer.setRootGroups(rootGroups, this.manager);
    this.renderer.setConfiguration(configuration);
  }

  /**
   * ルートを返します。
   * 
   * @return root ルート
   */
  Mikity3DModel getRoot() {
    return this.root;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureShell(final Shell shell) {
    super.configureShell(shell);
    shell.setSize(800, 600);
    shell.setText("Animation Viewer"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createContents(final Composite parent) {
    final SashForm form = new SashForm(parent, SWT.NONE);
    form.setLayoutData(new GridData(GridData.FILL_BOTH));
    form.setLayout(new GridLayout());

    createView(form);
    createController(form);

    form.setWeights(new int[] {60, 40}); // 60:40に分割

    if (this.root != null) {
      prepareRenderer();
      this.modelFilePathText.setText(this.modelFile.getAbsolutePath());     
    }  
    
    return parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleShellCloseEvent() {
    if (this.animationTask != null) {
      this.animationTask.cancel();
    }
    if (this.sliderTask != null) {
      this.sliderTask.cancel();
    }
    if (this.timer != null) {
      this.timer.cancel();
    }

    super.handleShellCloseEvent();
  }

  /**
   * アニメーションを描画するビューを生成します。
   * 
   * @param parent 親コンポジット
   */
  private void createView(final Composite parent) {
    this.viewComposite = new Composite(parent, SWT.EMBEDDED);
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    this.viewComposite.setLayoutData(gridData);

    // AWTのフレームを作る。
    this.frame = SWT_AWT.new_Frame(this.viewComposite);
    this.frame.add((Component)this.renderer);
    this.frame.validate();
  }

  /**
   * コントローラーを生成します。
   * 
   * コントローラCompositeの中身
   * 
   * @param parent 親
   */
  private void createController(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_END));
    composite.setLayout(new GridLayout());

    final Composite controller = new Composite(composite, SWT.NONE);
    controller.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    controller.setLayout(new GridLayout());

    final Composite buttonComposite = new Composite(controller, SWT.NONE);
    buttonComposite.setLayout(new GridLayout(4, false));
    
    final Button playButton = new Button(buttonComposite, SWT.NONE);
    playButton.setImage(ImageManager.getImage(ImageManager.PLAYBACK));
    
    final Button stopButton = new Button(buttonComposite, SWT.NONE);
    stopButton.setImage(ImageManager.getImage(ImageManager.STOP));
    
    final Button slowerButton = new Button(buttonComposite, SWT.NONE);
    slowerButton.setImage(ImageManager.getImage(ImageManager.SLOW));
    
    final Button fasterButton = new Button(buttonComposite, SWT.NONE);
    fasterButton.setImage(ImageManager.getImage(ImageManager.FASTER));

    final Composite speedComposite = new Composite(controller, SWT.NONE);
    final GridLayout speedLayout = new GridLayout();
    speedLayout.numColumns = 2;
    speedComposite.setLayout(speedLayout);
    this.playSpeed = new ParameterInputBox(speedComposite, SWT.NONE, Messages.getString("SimulationViewer.0"), "1.0"); //$NON-NLS-1$ //$NON-NLS-2$

    createTimeBar(controller);
    createModeChooser(composite);
    createSourceChooser(composite);

    playButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        runAnimation();
      }
    });

    stopButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        // スレッドを停止させる。復元不能
        AnimationWindow.this.timer.cancel();
        playable = true;
      }
    });

    fasterButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        AnimationWindow.this.speedRate += 0.1;
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setSpeedScale(AnimationWindow.this.speedRate);
        }
        String value = String.valueOf(AnimationWindow.this.speedRate);
        value = value.substring(0, value.indexOf(".") + 2); //$NON-NLS-1$
        AnimationWindow.this.playSpeed.setText(value);
      }
    });

    slowerButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        AnimationWindow.this.speedRate -= 0.1;
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setSpeedScale(AnimationWindow.this.speedRate);
        }
        String value = String.valueOf(AnimationWindow.this.speedRate);
        value = value.substring(0, value.indexOf(".") + 2); //$NON-NLS-1$
        AnimationWindow.this.playSpeed.setText(value);
      }
    });

  }

  /**
   * 実行時間バーを生成します。
   * 
   * @param parent 親コンポジット
   */
  private void createTimeBar(final Composite parent) {
    final Composite tiemBarComposite = new Composite(parent, SWT.NONE);
    tiemBarComposite.setLayout(new GridLayout(3, true));
    final GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);

    this.startTimeLabel = new Label(tiemBarComposite, SWT.NONE | SWT.LEFT);
    this.currentTimeLabel = new Label(tiemBarComposite, SWT.NONE | SWT.CENTER);
    this.currentTimeLabel.setText("0.0"); //$NON-NLS-1$
    this.currentTimeLabel.setLayoutData(gridData1);
    this.endTimeLabel = new Label(tiemBarComposite, SWT.NONE | SWT.RIGHT);

    this.timeSlider = new Slider(tiemBarComposite, SWT.NONE);

    final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.horizontalSpan = 3;
    this.timeSlider.setLayoutData(gridData2);
    this.timeSlider.setMinimum(0);
    this.timeSlider.setMaximum(0);

    this.timeSlider.addSelectionListener(new SelectionAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        final double t = AnimationWindow.this.timeTable[AnimationWindow.this.timeSlider.getSelection()];
        AnimationWindow.this.manager.updateObjectGroups(t);
        
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setCurrentTime(t);
          String st = String.valueOf(t);
          if (st.length() > 5) {
            st = st.substring(0, 4);
          }
          AnimationWindow.this.currentTimeLabel.setText(st);
        }
      }
    });
  }

  /**
   * モデルを選択すチューザーを生成します。
   * 
   * @param parent 親コンポジット
   */
  public void createModeChooser(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 6;
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Label label = new Label(composite, SWT.NONE);
    label.setText(Messages.getString("AnimationWindow.0")); //$NON-NLS-1$

    this.modelFilePathText = new Text(composite, SWT.BORDER);
    this.modelFilePathText.setText(""); //$NON-NLS-1$
    this.modelFilePathText.addTraverseListener(new TraverseListener() {

      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          final String filePath = AnimationWindow.this.modelFilePathText.getText();
          if (filePath != null && filePath.length() != 0) {
            loadModel(filePath);
            prepareRenderer();
          }
        }
      }
    });

    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 4;
    this.modelFilePathText.setLayoutData(gridData);

    final Button referenceButton = new Button(composite, SWT.BORDER);
    referenceButton.setText(Messages.getString("SimulationViewer.3")); //$NON-NLS-1$

    referenceButton.addSelectionListener(new SelectionAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        final FileDialog dialog = new FileDialog(parent.getShell());
        dialog.setFilterExtensions(new String[] {"*.m3d", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$
        
        final String filePath = dialog.open();
        if (filePath != null) {
          AnimationWindow.this.modelFilePathText.setText(filePath);
          loadModel(filePath);
          prepareRenderer();
        }
      }

    });
  }
  
//  /**
//   * モデルデータを読み込みます。
//   * 
//   * @param filePath モデルデータのファイルパス
//   */
//  void loadModelData(final String filePath) {
//    AnimationWindow.this.modelFilePathText.setText(filePath);
//    createRoot(filePath);
//    configureModel();
//  }


  /**
   * ルートを生成します。
   * 
   * @param filePath ファイルのパス
   */
  void loadModel(String filePath) {
    try {
      final File file = new File(filePath);
      this.modelFile = file;
      final Mikity3DModel localRoot = new Mikity3dFactory().loadFile(file);
      setRoot(localRoot);
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * ソースを選択するコンポジットを生成します。
   * 
   * @param parent 親コンポジット
   */
  public void createSourceChooser(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 6;
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Label label = new Label(composite, SWT.NONE);
    label.setText(Messages.getString("SimulationViewer.2")); //$NON-NLS-1$

    final String id = "1"; //$NON-NLS-1$
    
    this.sourceFilePathText = new Text(composite, SWT.BORDER);
    this.sourceFilePathText.setText(""); //$NON-NLS-1$
    this.sourceFilePathText.addTraverseListener(new TraverseListener() {

      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          final String filePath = AnimationWindow.this.sourceFilePathText.getText();
          addSource(id, filePath);
          AnimationWindow.this.sourceFilePathText.setText(filePath);
        }
      }
    });

    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 4;
    this.sourceFilePathText.setLayoutData(gridData);

    final Button referenceButton = new Button(composite, SWT.BORDER);
    referenceButton.setText(Messages.getString("SimulationViewer.3")); //$NON-NLS-1$
    
    referenceButton.addSelectionListener(new SelectionAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        final FileDialog dialog = new FileDialog(parent.getShell());
        dialog.setFilterExtensions(new String[] {"*.mat", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$
        
        final String filePath = dialog.open();
        if (filePath != null) {
          AnimationWindow.this.sourceFilePathText.setText(filePath);
          addSource(id, filePath);
        }
      }
    });
  }

  private void checkAnimation(GroupModel parent) {
    final GroupModel[] groups = parent.getGroups();
    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          this.manager.setHasAnimation(true);
        }
      }
      checkAnimation(group);
    }
  }

  /**
   * ソースのファイルを設定します。
   * 
   * @param id ソースのID
   * @param file ソースのファイルパス
   */
  void addSource(String id, String filePath) {
    try (FileReader input = new FileReader(filePath);) {
      final DoubleMatrix sourceData = (DoubleMatrix)MatxMatrix.readMatFormat(input);
      input.close();
      
      this.manager.addSource(id, sourceData);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * スライダーを準備します。
   */
  private void prepareSlider() {
    this.endTime = this.manager.getEndTime();
    this.startTimeLabel.setText("" + this.manager.getStartTime()); //$NON-NLS-1$
    this.endTimeLabel.setText("" + this.endTime); //$NON-NLS-1$

    final int dataSize = this.manager.getDataSize();
    this.timeTable = new double[dataSize];
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
    }
    
    this.timeSlider.setEnabled(true);
    this.timeSlider.setMaximum(dataSize);
  }

//  /**
//   * 時系列データを設定します。
//   * 
//   * @param data 時系列データ
//   */
//  public void setTimeSeriesData(final Matrix data) {
//    this.sourceData = data;
//
//    this.timeSlider.setEnabled(true);
//    this.manager.setData(this.sourceData);
//
//    final GroupModel rootGroup = this.root.getScene(0).getGroup(0);
//    checkAnimation(rootGroup);
//
//    final int dataSize = this.manager.getDataSize();
//
//    this.timeTable = new double[dataSize];
//
//    this.endTime = this.manager.getEndTime();
//    this.startTimeLabel.setText("" + this.manager.getStartTime()); //$NON-NLS-1$
//    this.endTimeLabel.setText("" + this.endTime); //$NON-NLS-1$
//    for (int i = 0; i < this.timeTable.length; i++) {
//      this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
//    }
//    this.timeSlider.setMaximum(dataSize);
//
//    this.sourceFilePathText.setText("data"); //$NON-NLS-1$
//  }

  /**
   * アニメーションを開始します。
   */
  void runAnimation() {
    if (playable == false) {
      this.timer.cancel();
    }
    
    this.manager.prepareMovingGroups();

    prepareSlider();
    
    if (this.timeTable == null) {
      MessagegUtil.show(getShell(), Messages.getString("SimulationViewer.4")); //$NON-NLS-1$
      return;
    }

    playable = false;

    this.endTime = this.manager.getEndTime();
    this.animationTask = new AnimationTask(0, this.endTime, this.manager, this.renderer);
    this.animationTask.setSpeedScale(this.playSpeed.getDoubleValue());// スピードの設定
    this.animationTask.setCurrentTime(this.timeTable[this.timeSlider.getSelection()]);
    this.animationTask.addAnimationTaskListener(new AnimationTaskListener() {

      /**
       * {@inheritDoc}
       */
      public void tearDownAnimation() {
        playable = true;
      }

      /**
       * {@inheritDoc}
       */
      public void setUpAnimation() {
        // nothing to do
      }
    });

    this.sliderTask = new SliderPositionMoveTask(this.animationTask, this.timeSlider);

    this.timer = new Timer();
    this.timer.schedule(this.animationTask, 0, 10);
    this.timer.schedule(this.sliderTask, 0, 10);
  }

  private class SliderPositionMoveTask extends TimerTask {
    AnimationTask localTask;
    Slider slider;

    /**
     * 新しく生成された<code>SliderPositionMoveTask</code>オブジェクトを初期化します。
     * @param task タスク
     * @param slider スライダー
     */
    public SliderPositionMoveTask(AnimationTask task, Slider slider) {
      this.localTask = task;
      this.slider = slider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
      if (getShell() == null) {
        return;
      }

      final Display display = getShell().getDisplay();
      if (display.isDisposed()) {
        return;
      }
      
      display.syncExec(new Runnable() {
        /**
         * {@inheritDoc}
         */
        public void run() {
          final double time = SliderPositionMoveTask.this.localTask.getCurrentTime();
          String timeString = String.valueOf(time);
          if (timeString.length() > 5) {
            timeString = timeString.substring(0, 4);
          }
          if (AnimationWindow.this.currentTimeLabel.isDisposed()) {
            return;
          }

          AnimationWindow.this.currentTimeLabel.setText(timeString);
          for (int i = 0; i < AnimationWindow.this.timeTable.length; i++) {
            if (AnimationWindow.this.timeTable[i] > time) {
              if (SliderPositionMoveTask.this.slider.isDisposed()) {
                return;
              }
              SliderPositionMoveTask.this.slider.setSelection(i);
              return;
            }
          }
        }
      });
    }
  }
}