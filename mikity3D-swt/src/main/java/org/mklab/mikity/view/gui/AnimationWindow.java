package org.mklab.mikity.view.gui;

import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.mklab.mikity.control.AnimationTask;
import org.mklab.mikity.control.AnimationTaskListener;
import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SourceDataModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.gui.dialog.ModifyKeyListener;
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
public class AnimationWindow extends ApplicationWindow implements ModifyKeyListener {

  /** アニメーション用タスク */
  AnimationTask animationTask;
  /** */
  SliderPositionMoveTask sliderTask;

  /** */
  boolean playable = true;
  
  /** 繰り返し再生中ならばtrue。 */
  boolean isRepeating = false;

  /** 現在の時刻。 */
  double currentTime;
  /** 終了の時刻。 */
  private double stopTime;

  private Mikity3DModel root;

  /** */
  int animationSpeedRate = 1000;

  /** */
  Timer timer = new Timer();

  /** */
  Slider timeSlider;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  /** */
  Text modelFilePathText;

  /** */
  Map<String,Text> sourceFilePathText = new HashMap<>();

  /** */
  GroupObjectManager manager;

  /** */
  private Label startTimeLabel;
  /** */
  Label currentTimeLabel;
  /** */
  private Label stopTimeLabel;
  /** */
  ParameterInputBox playSpeed;

  /** Canvas */
  private ObjectRenderer renderer;

  /** */
  Frame frame;

  /** */
  private Composite viewComposite;
  
  /** */
  private Group sourceGroup;
  
  /** モデルファイル */
  private File modelFile;

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
    this.manager = new GroupObjectManager();
    
    final List<SourceDataModel> sourcesInConfiguration = this.root.getConfiguration(0).getSources();
    if (sourcesInConfiguration != null) {
      for (final SourceDataModel source : sourcesInConfiguration) {
        addSource(source.getId(), source.getFilePath());
      }
    }
    
    final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();    
    if (hasAnimation(rootGroups)) {
      this.manager.setHasAnimation(true);
    }
  }


  /**
   * レンダラーを準備します。
   */
  void prepareRenderer() {
    final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();
    final ConfigurationModel configuration = this.root.getConfiguration(0);
    
    this.manager.clearGroupObjects();
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
    shell.setSize(950, 600);
    shell.setText("Mikity3D Animation Viewer"); //$NON-NLS-1$
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
    
      final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();
      createSourceChoosers(getAllIds(rootGroups), false);
    }
    
    return parent;
  }

  private Set<String> getAllIds(final List<GroupModel> rootGroups) {
    final List<AnimationModel> allAnimations = getAllAnimation(rootGroups);
    
    final Set<String> ids = new TreeSet<>();
    for (final AnimationModel animation : allAnimations) {
      ids.add(animation.getSource().getId());
    }
    return ids;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleShellCloseEvent() {
    if (this.animationTask != null) {
      this.animationTask.cancel();
      this.animationTask = null;
    }
    if (this.sliderTask != null) {
      this.sliderTask.cancel();
      this.sliderTask = null;
    }
    if (this.timer != null) {
      this.timer.cancel();
      this.timer = null;
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

    final Composite topComposite = new Composite(controller, SWT.NONE);
    topComposite.setLayout(new GridLayout(7, false));
    
    final Button playButton = new Button(topComposite, SWT.NONE);
    playButton.setImage(ImageManager.getImage(ImageManager.PLAYBACK));
    
    final Button stopButton = new Button(topComposite, SWT.NONE);
    stopButton.setImage(ImageManager.getImage(ImageManager.STOP));
    
    final Button repeatButton = new Button(topComposite, SWT.NONE);
    repeatButton.setImage(ImageManager.getImage(ImageManager.PLAYBACK));
    
    final Button slowerButton = new Button(topComposite, SWT.NONE);
    slowerButton.setImage(ImageManager.getImage(ImageManager.SLOW));
    
    final Button fasterButton = new Button(topComposite, SWT.NONE);
    fasterButton.setImage(ImageManager.getImage(ImageManager.FASTER));

    this.playSpeed = new ParameterInputBox(topComposite, this, SWT.NONE, Messages.getString("SimulationViewer.0"), "1.0"); //$NON-NLS-1$ //$NON-NLS-2$

    createTimeBar(controller);
    createModelChooser(composite);
    
    this.sourceGroup = new Group(composite, SWT.None);
    this.sourceGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.sourceGroup.setLayout(new GridLayout());
    this.sourceGroup.setText(Messages.getString("SimulationViewer.2")); //$NON-NLS-1$

    playButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        runAnimation();
      }
    });

    stopButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        stopAnimation();
      }
      
    });
    
    repeatButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        repeatAnimation();
      }
    });
    
    fasterButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        final int step = (int)Math.floor(Math.log10(AnimationWindow.this.animationSpeedRate));
        AnimationWindow.this.animationSpeedRate += (int)Math.pow(10, step);
        if (AnimationWindow.this.animationSpeedRate > 1000000) {
          AnimationWindow.this.animationSpeedRate = 1000000;
        }
        AnimationWindow.this.playSpeed.setValue(String.format("%6.3f", Double.valueOf(AnimationWindow.this.animationSpeedRate/1000.0))); //$NON-NLS-1$                
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setSpeedScale(AnimationWindow.this.animationSpeedRate/1000.0);
        }
      }
    });

    slowerButton.addSelectionListener(new SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        final int step = (int)Math.floor(Math.log10(AnimationWindow.this.animationSpeedRate - 1));
        AnimationWindow.this.animationSpeedRate -= (int)Math.pow(10, step);  
        if (AnimationWindow.this.animationSpeedRate < 0) {
          AnimationWindow.this.animationSpeedRate = 1;
        }
        AnimationWindow.this.playSpeed.setValue(String.format("%6.3f", Double.valueOf(AnimationWindow.this.animationSpeedRate/1000.0))); //$NON-NLS-1$        
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setSpeedScale(AnimationWindow.this.animationSpeedRate/1000.0);
        }
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
    tiemBarComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    tiemBarComposite.setLayout(new GridLayout(3, false));
    
    this.startTimeLabel = new Label(tiemBarComposite, SWT.NONE | SWT.LEFT);
    this.startTimeLabel.setText("0.0"); //$NON-NLS-1$
    this.startTimeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    
    this.currentTime = 0;
    this.currentTimeLabel = new Label(tiemBarComposite, SWT.NONE | SWT.CENTER);
    this.currentTimeLabel.setText("0.0"); //$NON-NLS-1$
    this.currentTimeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    
    this.stopTime = 0;
    this.stopTimeLabel = new Label(tiemBarComposite, SWT.NONE | SWT.RIGHT);
    this.stopTimeLabel.setText("0.0"); //$NON-NLS-1$
    this.stopTimeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    this.timeSlider = new Slider(parent, SWT.NONE);
    this.timeSlider.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.timeSlider.setMinimum(0);
    this.timeSlider.setMaximum(0);

    this.timeSlider.addSelectionListener(new SelectionAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (AnimationWindow.this.timeTable == null) {
          return;
        }
        
        final double time = AnimationWindow.this.timeTable[AnimationWindow.this.timeSlider.getSelection()];
        AnimationWindow.this.manager.updateGroupObjects(time);

        String timeString = String.valueOf(time);
        if (timeString.length() > 5) {
          timeString = timeString.substring(0, 4);
        }
        AnimationWindow.this.currentTime = time;
        AnimationWindow.this.currentTimeLabel.setText(timeString);
        
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setCurrentTime(time);
        }
      }
    });
  }

  /**
   * モデルを選択すチューザーを生成します。
   * 
   * @param parent 親コンポジット
   */
  public void createModelChooser(final Composite parent) {
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

      @Override
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
        dialog.setFilterExtensions(new String[] {"*.m3d", "*.stl", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        
        final String filePath = dialog.open();
        if (filePath != null) {
          AnimationWindow.this.modelFilePathText.setText(filePath);
          loadModel(filePath);
          prepareRenderer();
        }
      }

    });
  }
  
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
      
      final List<GroupModel> rootGroups = this.root.getScene(0).getGroups();
      createSourceChoosers(getAllIds(rootGroups), true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  

  /**
   * ソース選択を生成します。
   * 
   * @param ids ソースのID
   */
  private void createSourceChoosers(final Set<String> ids, boolean isPacking) {
    for (final String id : ids) {
      createSourceChooser(this.sourceGroup, id);
    }
    
    if (isPacking) {
      this.sourceGroup.pack();
    }
  }

  /**
   * ソースを選択するコンポジットを生成します。
   * 
   * @param parent 親コンポジット
   * @param id IO
   */
  public void createSourceChooser(final Composite parent, final String id) {
    final Composite composite = new Composite(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 6;
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Label label = new Label(composite, SWT.NONE);
    label.setText(id + ":"); //$NON-NLS-1$
    
    final Text filePathText = new Text(composite, SWT.BORDER);
    this.sourceFilePathText.put(id,  filePathText);
    
    filePathText.setText(""); //$NON-NLS-1$
    filePathText.addTraverseListener(new TraverseListener() {

      @Override
      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          final String filePath =  AnimationWindow.this.sourceFilePathText.get(id).getText();
          addSource(id, filePath);
          AnimationWindow.this.sourceFilePathText.get(id).setText(filePath);
        }
      }
    });

    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 4;
    filePathText.setLayoutData(gridData);

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
          AnimationWindow.this.sourceFilePathText.get(id).setText(filePath);
          addSource(id, filePath);
        }
      }
    });
  }

  /**
   * アニメーションが含まれるか判定します。
   * 
   * @param group グループ
   * @return アニメーションが含まれればtrue
   */
  private boolean hasAnimation(List<GroupModel> groups) {
    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          return true;
        }
      }
      if (hasAnimation(group.getGroups())) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * 全ての含まれるアニメーソンを返します。
   * 
   * @param groups グループ
   * 
   * @return 全ての含まれるアニメーソン
   */
  private List<AnimationModel> getAllAnimation(List<GroupModel> groups) {
    final List<AnimationModel> allAnimations = new ArrayList<>();
    
    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          allAnimations.add(animation);
        }
      }

      allAnimations.addAll(getAllAnimation(group.getGroups()));        
    }
    
    return allAnimations;
  }

  /**
   * ソースのファイルを設定します。
   * 
   * @param id ソースのID
   * @param file ソースのファイルパス
   */
  void addSource(String id, String filePath) {
    try (FileReader input = new FileReader(filePath);) {
      final DoubleMatrix sourceData;
      if (filePath.toLowerCase().endsWith(".mat")) { //$NON-NLS-1$
        sourceData = (DoubleMatrix)MatxMatrix.readMatFormat(input);
      } else if (filePath.toLowerCase().endsWith(".csv")) { //$NON-NLS-1$
        sourceData = DoubleMatrix.readCsvFormat(input).transpose();
      } else if (filePath.toLowerCase().endsWith(".txt")) { //$NON-NLS-1$
        sourceData = DoubleMatrix.readSsvFormat(input).transpose();
      } else {
        sourceData = DoubleMatrix.readSsvFormat(input).transpose();
      }

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
    this.startTimeLabel.setText("" + this.manager.getStartTime()); //$NON-NLS-1$
    this.stopTimeLabel.setText("" + this.manager.getStopTime()); //$NON-NLS-1$

    final int dataSize = this.manager.getDataSize();
    this.timeTable = new double[dataSize];
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.manager.getStopTime() * ((double)i / this.timeTable.length);
    }
    
    this.timeSlider.setEnabled(true);
    this.timeSlider.setMaximum(dataSize);
  }

  /**
   * アニメーションを停止します。 
   */
  public void stopAnimation() {
    this.timer.cancel();
    this.playable = true;
    this.isRepeating = false;
  }
  
  /**
   * アニメーションを繰り返し再生します。 
   */
  public void repeatAnimation() {
    this.isRepeating = true;
    runAnimation();
  }
  
  /**
   * アニメーションを開始します。
   */
  void runAnimation() {
    if (this.manager == null) {
      MessagegUtil.show(getShell(), Messages.getString("AnimationWindow.1")); //$NON-NLS-1$
      return;
    }
    if (this.manager.isSourceReady() == false) {
      MessagegUtil.show(getShell(), Messages.getString("SimulationViewer.4")); //$NON-NLS-1$
      return;
    }

    if (this.playable == false) {
      this.timer.cancel();
    }
        
    this.manager.prepareMovingGroupObjects();

    prepareSlider();

    this.stopTime = this.manager.getStopTime();
    this.animationTask = new AnimationTask(this.currentTime, this.stopTime, this.manager, this.renderer);
    double animationSpeedScale = this.playSpeed.getDoubleValue();
    this.animationTask.setSpeedScale(animationSpeedScale);
    this.animationTask.addAnimationTaskListener(new AnimationTaskListener() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void tearDownAnimation() {
        AnimationWindow.this.playable = true;
        AnimationWindow.this.animationTask.cancel();
        AnimationWindow.this.sliderTask.cancel();
        
        if (AnimationWindow.this.isRepeating) {
          getShell().getDisplay().asyncExec(new Runnable() {
            public void run() {
              AnimationWindow.this.currentTime = 0;
              runAnimation();
            }
          });
        }
      }

      /**
       * {@inheritDoc}
       */
      public void setUpAnimation() {
        // nothing to do
      }
    });

    this.playable = false;
    this.timer = new Timer();
    this.timer.schedule(this.animationTask, 0, 10);

    this.sliderTask = new SliderPositionMoveTask(this.animationTask, this.timeSlider);    
    this.timer.schedule(this.sliderTask, 0, 10);
  }

  /**
   * スライダーの位置を管理するタスクを表すクラスです。
   * 
   * @author koga
   * @version $Revision$, 2015/08/21
   */
  public class SliderPositionMoveTask extends TimerTask {
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
        @Override
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
          AnimationWindow.this.currentTime = Double.parseDouble(timeString);
          
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
  
  /**
   * {@inheritDoc}
   */
  public void modifyText(ModifyEvent arg0) {
    // nothing to do
  }
  
  /**
   * {@inheritDoc}
   */
  public void keyPressed(KeyEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void keyReleased(KeyEvent e) {
    // nothing to do
  }
}