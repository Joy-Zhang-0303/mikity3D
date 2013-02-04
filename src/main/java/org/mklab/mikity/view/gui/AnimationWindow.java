package org.mklab.mikity.view.gui;

import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXBException;

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
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.resource.ImageManager;
import org.mklab.mikity.model.xml.Jamast;
import org.mklab.mikity.model.xml.JamastConfig;
import org.mklab.mikity.model.xml.JamastFactory;
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.LinkData;
import org.mklab.mikity.view.renderer.ModelRenderer;
import org.mklab.mikity.view.renderer.jogl.JoglModelRenderer;
import org.mklab.nfc.matrix.Matrix;
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

  private Jamast root;

  /** */
  double speed = 1.0;

  /** */
  Timer timer = new Timer();

  /** */
  Slider timeSlider;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  /** */
  private Matrix data;

  /** */
  Text filePathText;

  /** */
  MovableGroupManager manager;

  /** */
  private Label startTimeLabel;
  /** */
  Label currentTimeLabel;
  /** */
  private Label endTimeLabel;
  /** */
  ParameterInputBox playSpeed;

  /** ModelCanvas */
  private ModelRenderer modelRenderer;

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param root ルート
   */
  public AnimationWindow(final Shell parentShell, final Jamast root) {
    super(parentShell);
    this.root = root;
    this.manager = new MovableGroupManager(this.root);

    // TODO Java3d or JOGL
    //this.modelRenderer = new Java3dModelRenderer(this.root);
    this.modelRenderer = new JoglModelRenderer();
  }

  /**
   * コンストラクター
   * 
   * @param parentShell 親シェル
   * @param modelFile モデルファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws JAXBException ファイルを読み込めない場合
   */
  public AnimationWindow(final Shell parentShell, File modelFile) throws IOException, JAXBException {
    this(parentShell, new JamastFactory().loadJamastFile(modelFile));
  }

  /**
   * シェルの設定
   * 
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell(final Shell shell) {
    super.configureShell(shell);
    shell.setSize(647, 500);
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

    form.setWeights(new int[] {70, 30}); // 70%:30%に分割点を設定

    if (this.root.getConfig(0).getData() != null) {
      setTimeData(new File(this.root.getConfig(0).getData()));
    }
    return parent;
  }

  /**
   * シェルを閉じるときにこのメソッドが呼ばれる
   * 
   * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
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
   * @param parent
   */
  private void createView(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.EMBEDDED);
    final GridData gridData = new GridData(GridData.FILL_BOTH);
    composite.setLayoutData(gridData);

    // AWTのフレームを作る。
    final Frame frame = SWT_AWT.new_Frame(composite);
    frame.add((Component)this.modelRenderer);
    
    final Group[] children = this.root.getModel(0).getGroups();
    this.modelRenderer.setChildren(children);

    final JamastConfig configuration = this.root.getConfig(0);
    this.modelRenderer.setConfiguration(configuration);
  }

  /**
   * This method initializes controllerComp
   * 
   * コントローラCompositの中身
   * 
   * @param parent
   */
  private void createController(final Composite parent) {
    final Composite controllerComposite = new Composite(parent, SWT.NONE);
    controllerComposite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_END));
    controllerComposite.setLayout(new GridLayout());

    createFileChooseComposite(controllerComposite);

    final Composite controller = new Composite(controllerComposite, SWT.NONE);
    controller.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    controller.setLayout(new GridLayout());

    final Composite buttonComposite = new Composite(controller, SWT.NONE);
    buttonComposite.setLayout(new GridLayout(4, false));
    final Button playbackButton = new Button(buttonComposite, SWT.NONE);
    playbackButton.setImage(ImageManager.getImage(ImageManager.PLAYBACK));
    final Button stopButton = new Button(buttonComposite, SWT.NONE);
    stopButton.setImage(ImageManager.getImage(ImageManager.STOP));
    final Button slowerButton = new Button(buttonComposite, SWT.NONE);
    slowerButton.setImage(ImageManager.getImage(ImageManager.SLOW));
    final Button fasterButton = new Button(buttonComposite, SWT.NONE);
    fasterButton.setImage(ImageManager.getImage(ImageManager.FASTER));

    // timeLabel.setText("" + task.getCurrentTime());

    final Composite speedComposite = new Composite(controller, SWT.NONE);
    final GridLayout speedLayout = new GridLayout();
    speedLayout.numColumns = 2;
    speedComposite.setLayout(speedLayout);
    this.playSpeed = new ParameterInputBox(speedComposite, SWT.NONE, Messages.getString("SimulationViewer.0"), "1.0"); //$NON-NLS-1$ //$NON-NLS-2$

    createTimeBar(controller);

    //final Composite composite2 = new Composite(controllerComposite, SWT.NONE);
    //composite2.setLayout(new GridLayout(3, false));

    fasterButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        AnimationWindow.this.speed += 0.1;
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setSpeedScale(AnimationWindow.this.speed);
        }
        String value = String.valueOf(AnimationWindow.this.speed);
        value = value.substring(0, value.indexOf(".") + 2); //$NON-NLS-1$
        AnimationWindow.this.playSpeed.setText(value);
      }
    });

    slowerButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        AnimationWindow.this.speed -= 0.1;
        if (AnimationWindow.this.animationTask != null) {
          AnimationWindow.this.animationTask.setSpeedScale(AnimationWindow.this.speed);
        }
        String value = String.valueOf(AnimationWindow.this.speed);
        value = value.substring(0, value.indexOf(".") + 2); //$NON-NLS-1$
        AnimationWindow.this.playSpeed.setText(value);
      }
    });

    playbackButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        runAnimation();
      }
    });

    stopButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        // スレッドを停止させる。復元不能
        AnimationWindow.this.timer.cancel();
        playable = true;
      }
    });
  }

  /**
   * 実行時間バーを生成する。
   * 
   * @param parent
   */
  private void createTimeBar(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(3, true));
    final GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);

    this.startTimeLabel = new Label(composite, SWT.NONE | SWT.LEFT);
    this.currentTimeLabel = new Label(composite, SWT.NONE | SWT.CENTER);
    this.currentTimeLabel.setText("0.0"); //$NON-NLS-1$
    this.currentTimeLabel.setLayoutData(gridData1);
    this.endTimeLabel = new Label(composite, SWT.NONE | SWT.RIGHT);

    this.timeSlider = new Slider(composite, SWT.NONE);

    final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
    gridData2.horizontalSpan = 3;
    this.timeSlider.setLayoutData(gridData2);
    this.timeSlider.setMinimum(0);
    this.timeSlider.setMaximum(0);

    this.timeSlider.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        final double t = AnimationWindow.this.timeTable[AnimationWindow.this.timeSlider.getSelection()];
        AnimationWindow.this.manager.updateMovableGroupsWithCoordinateParameter(t);
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
   * ファイルを選択するボタン
   * 
   * @param parent
   */
  private void createFileChooseComposite(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 6;
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Label label = new Label(composite, SWT.NONE);
    label.setText(Messages.getString("SimulationViewer.2")); //$NON-NLS-1$

    this.filePathText = new Text(composite, SWT.BORDER);
    this.filePathText.setText(""); //$NON-NLS-1$
    this.filePathText.addTraverseListener(new TraverseListener() {

      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          setTimeData(new File(AnimationWindow.this.filePathText.getText()));
        }
      }
    });

    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 4;
    this.filePathText.setLayoutData(gridData);

    final Button refButton = new Button(composite, SWT.BORDER);
    refButton.setText(Messages.getString("SimulationViewer.3")); //$NON-NLS-1$

    refButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        final FileDialog dialog = new FileDialog(parent.getShell());
        // ファイルを選択させる
        final String filePath = dialog.open();
        if (filePath != null) {
          setTimeData(new File(filePath));
        }
      }
    });
  }

  private void checkLinkParameterType(Group parent) {
    final Group[] groups = parent.getGroups();
    for (final Group group : groups) {
      final LinkData[] links = group.getLinkData();
      for (final LinkData link : links) {
        if (link.hasDHParameter()) {
          this.manager.setHasDHParameter(true);
        } else if (link.hasCoordinateParameter()) {
          this.manager.setHasCoordinateParameter(true);
        }
      }
      checkLinkParameterType(group);
    }
  }

  /**
   * 実行時間バーを設定する。
   * 
   * @param file ファイル
   */
  public void setTimeData(final File file) {
    try {
      final FileInputStream input = new FileInputStream(file);
      this.data = MatxMatrix.readMatFormat(input);
      input.close();

      this.timeSlider.setEnabled(true);
      //this.manager.setData(this.data);
      this.manager.setData(this.data);

      final Group rootGroup = this.root.getModel(0).getGroup(0);
      checkLinkParameterType(rootGroup);

      final int dataSize = this.manager.getDataSize();

      this.timeTable = new double[dataSize];

      this.endTime = this.manager.getEndTime();
      this.startTimeLabel.setText("" + this.manager.getStartTime()); //$NON-NLS-1$
      this.endTimeLabel.setText("" + this.endTime); //$NON-NLS-1$
      for (int i = 0; i < this.timeTable.length; i++) {
        this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
      }
      this.timeSlider.setMaximum(dataSize);

      this.filePathText.setText(file.getPath());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 実行時間を設定します。
   * 
   * @param data データ
   */
  public void setTimeData(final Matrix data) {
    this.data = data;

    this.timeSlider.setEnabled(true);
    //this.manager.setData(this.data);
    this.manager.setData(this.data);

    final Group rootGroup = this.root.getModel(0).getGroup(0);
    checkLinkParameterType(rootGroup);

    final int dataSize = this.manager.getDataSize();

    this.timeTable = new double[dataSize];

    this.endTime = this.manager.getEndTime();
    this.startTimeLabel.setText("" + this.manager.getStartTime()); //$NON-NLS-1$
    this.endTimeLabel.setText("" + this.endTime); //$NON-NLS-1$
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
    }
    this.timeSlider.setMaximum(dataSize);

    this.filePathText.setText("data"); //$NON-NLS-1$
  }

  /**
   * アニメーションを開始します。
   */
  public void runAnimation() {
    if (playable == false) {
      this.timer.cancel();
    }
    
    if (this.data == null || this.timeTable == null) {
      MessagegUtil.show(getShell(), Messages.getString("SimulationViewer.4")); //$NON-NLS-1$
      System.out.println(Messages.getString("SimulationViewer.5")); //$NON-NLS-1$
      return;
    }
    
    playable = false;

    this.endTime = this.manager.getEndTime();
    this.animationTask = new AnimationTask(0, this.endTime, this.manager, this.modelRenderer);
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
     * コンストラクター
     * 
     * @param task タスク
     * @param slider スライダー
     */
    public SliderPositionMoveTask(AnimationTask task, Slider slider) {
      this.localTask = task;
      this.slider = slider;
    }

    /**
     * @see java.util.TimerTask#run()
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