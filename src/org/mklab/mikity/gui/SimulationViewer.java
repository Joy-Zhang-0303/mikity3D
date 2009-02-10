package org.mklab.mikity.gui;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.mklab.mikity.MovableGroupManager;
import org.mklab.mikity.resource.ResourceManager;
import org.mklab.mikity.task.AnimationTask;
import org.mklab.mikity.util.MsgUtil;
import org.mklab.mikity.xml.Jamast;
import org.mklab.nfc.Matrix;
import org.mklab.nfc.matx.MatxMatrix;


/*
 * Created on 2004/12/02
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */

/**
 * シミュレーションの描画を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.21 $.2004/12/02
 */

public class SimulationViewer extends ApplicationWindow {

  /** アニメーション用タスク */
  AnimationTask task;
  SliderPositionMoveTask stask;

  /**
   * 
   */
  public static boolean playable = true;

  double endTime;

  private Jamast root;

  double speed = 1.0;

  Timer timer = new Timer();

  Slider timeSlider;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  Matrix data;

  Text filePathText;

  MovableGroupManager manager;

  Label startTimeLabel;
  Label currentTimeLabel;
  Label endTimeLabel;

  private boolean usedDHParam = false;
  private boolean usedLink = false;
  private ParameterInputBox playSpeed;

  /**
   * コンストラクター
   * 
   * @param parentShell
   * @param root
   */
  public SimulationViewer(final Shell parentShell, final Jamast root) {
    super(parentShell);
    this.manager = new MovableGroupManager(root);
    this.root = root;
//    setShellStyle(SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
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
    shell.setText("SimulationViewer"); //$NON-NLS-1$
  }

  /**
   * コンテンツ作成
   * 
   * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(final Composite parent) {
    SashForm sash = new SashForm(parent, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());

    createViewer(sash);
    createController(sash);

    sash.setWeights(new int[] {70, 30}); // 70%:30%に分割点を設定

    if (this.root.loadConfig(0).loadData() != null) {
      setTimeData(new File(this.root.loadConfig(0).loadData()));
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

    if (this.task != null) {
      this.task.cancel();
    }
    if (this.stask != null) {
      this.stask.cancel();
    }
    if (this.timer != null) {
      this.timer.cancel();
    }

    super.handleShellCloseEvent();
  }

  /**
   * This method initializes viewer 実際にシミュレーションを見る画面
   * 
   * @param comp
   */
  private void createViewer(final Composite comp) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);

    final Composite viewer = new Composite(comp, SWT.EMBEDDED);
    viewer.setLayoutData(gridData);

    // AWTのフレームを作る。
    final Frame awtFrame = SWT_AWT.new_Frame(viewer);
    final ModelCanvas sinsiCanvas = new ModelCanvas(this.root);
    awtFrame.add(sinsiCanvas);
    sinsiCanvas.load();
  }

  /**
   * This method initializes controllerComp コントローラCompositの中身
   * 
   * @param parent
   */
  private void createController(final Composite parent) {
    final Composite controllerComp = new Composite(parent, SWT.NONE);
    controllerComp.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_END));
    controllerComp.setLayout(new GridLayout());

    createFileChooseComp(controllerComp);

    final Composite otherController = new Composite(controllerComp, SWT.NONE);
    otherController.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    otherController.setLayout(new GridLayout());

    final Composite playerComp = new Composite(otherController, SWT.NONE);
    playerComp.setLayout(new GridLayout(4, false));
    final Button playbackButton = new Button(playerComp, SWT.NONE);
    playbackButton.setImage(ResourceManager.getImage(ResourceManager.PLAYBACK));
    final Button stopButton = new Button(playerComp, SWT.NONE);
    stopButton.setImage(ResourceManager.getImage(ResourceManager.STOP));
    final Button slowButton = new Button(playerComp, SWT.NONE);
    slowButton.setImage(ResourceManager.getImage(ResourceManager.SLOW));
    final Button fastButton = new Button(playerComp, SWT.NONE);
    fastButton.setImage(ResourceManager.getImage(ResourceManager.FAST));

    // timeLabel.setText("" + task.getCurrentTime());

    Composite speedComp = new Composite(otherController, SWT.NONE);
    GridLayout speedLayout = new GridLayout();
    speedLayout.numColumns = 2;
    speedComp.setLayout(speedLayout);
    this.playSpeed = new ParameterInputBox(speedComp, SWT.NONE, "再生速度", "1.0");

    createTimeBar(otherController);

    final Composite comp2 = new Composite(controllerComp, SWT.NONE);
    comp2.setLayout(new GridLayout(3, false));

    fastButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        SimulationViewer.this.speed += 0.1;
        if (SimulationViewer.this.task != null) {
          SimulationViewer.this.task.setSpeed(SimulationViewer.this.speed);
        }
        // double型をString型に変更
        String stValue = String.valueOf(SimulationViewer.this.speed);
        // 小数点第二以下を表示しないようにする
        stValue = stValue.substring(0, stValue.indexOf(".") + 2); //$NON-NLS-1$
        SimulationViewer.this.playSpeed.setText(stValue);
      }
    });

    slowButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        SimulationViewer.this.speed -= 0.1;
        if (SimulationViewer.this.task != null) {
          SimulationViewer.this.task.setSpeed(SimulationViewer.this.speed);
        }
        String stValue = String.valueOf(SimulationViewer.this.speed);
        stValue = stValue.substring(0, stValue.indexOf(".") + 2); //$NON-NLS-1$
        SimulationViewer.this.playSpeed.setText(stValue);
      }
    });

    // 再生ボタンによるイベント
    playbackButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        runAnimation();
      }
    });

    // 停止ボタンによるイベント
    stopButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        // スレッドを停止させる。復元不能
        SimulationViewer.this.timer.cancel();
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
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

    this.startTimeLabel = new Label(composite, SWT.NONE | SWT.LEFT);
    this.currentTimeLabel = new Label(composite, SWT.NONE | SWT.CENTER);
    this.currentTimeLabel.setText("0.0"); //$NON-NLS-1$
    this.currentTimeLabel.setLayoutData(gridData);
    this.endTimeLabel = new Label(composite, SWT.NONE | SWT.RIGHT);

    this.timeSlider = new Slider(composite, SWT.NONE);
    gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 3;
    this.timeSlider.setLayoutData(gridData);
    this.timeSlider.setMinimum(0);
    this.timeSlider.setMaximum(0);
    // dataCount スライダーの最大値

    this.timeSlider.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        double t = SimulationViewer.this.timeTable[SimulationViewer.this.timeSlider.getSelection()];
        SimulationViewer.this.manager.processStimulus(t);
        if (SimulationViewer.this.task != null) {
          SimulationViewer.this.task.setCurrentTime(t);
          String st = String.valueOf(t);
          if (st.length() > 5) {
            st = st.substring(0, 4);
          }
          SimulationViewer.this.currentTimeLabel.setText(st);
        }
      }
    });
  }

  /**
   * ファイルを選択するボタン
   * 
   * @param composite
   */
  private void createFileChooseComp(final Composite composite) {
    Composite comp = new Composite(composite, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 6;
    comp.setLayout(layout);
    comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Label label = new Label(comp, SWT.NONE);
    label.setText("ファイル");

    this.filePathText = new Text(comp, SWT.BORDER);
    this.filePathText.setText(""); //$NON-NLS-1$
    this.filePathText.addTraverseListener(new TraverseListener() {

      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          setTimeData(new File(SimulationViewer.this.filePathText.getText()));
        }
      }
    });

    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 4;
    this.filePathText.setLayoutData(gridData);

    final Button refButton = new Button(comp, SWT.BORDER);
    refButton.setText("参照");

    refButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        FileDialog dialog = new FileDialog(composite.getShell());
        // ファイルを選択させる
        String ret = dialog.open();
        if (ret != null) {
          setTimeData(new File(ret));
        }
      }
    });
  }

  private void checkLinkParameterType(org.mklab.mikity.xml.model.Group group) {
    org.mklab.mikity.xml.model.Group[] subGroup = group.loadGroup();
    if (subGroup.length != 0) {
      for (int i = 0; i < subGroup.length; i++) {
        org.mklab.mikity.xml.model.Linkdata[] link = subGroup[i].loadLinkdata();
        for (int j = 0; j < link.length; j++) {
          if (link[j].hasDH()) {
            this.usedDHParam = true;
            this.manager.setDH(this.usedDHParam);
          } else if (link[j].hasLink()) {
            this.usedLink = true;
            this.manager.setLink(this.usedLink);
          } else {
            this.usedDHParam = false;
            this.usedLink = false;
          }
        }
        checkLinkParameterType(subGroup[i]);
      }
    }
  }

  /**
   * 実行時間バーを設定する。
   * 
   * @param file
   */
  public void setTimeData(final File file) {
    try {
      this.data = MatxMatrix.readMatFormat(new FileInputStream(file));

      this.timeSlider.setEnabled(true);
      this.manager.setData(this.data);

      org.mklab.mikity.xml.model.Group group = this.root.loadModel(0).loadGroup(0);
      checkLinkParameterType(group);

      final int dataCount = this.manager.getDataCount();

      this.timeTable = new double[dataCount];

      this.endTime = this.manager.getEndTime();
      this.startTimeLabel.setText("" + this.manager.getStartTime()); //$NON-NLS-1$
      this.endTimeLabel.setText("" + this.endTime); //$NON-NLS-1$
      for (int i = 0; i < this.timeTable.length; i++) {
        this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
      }
      this.timeSlider.setMaximum(dataCount);

      this.filePathText.setText(file.getPath());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 実行時間バーを設定する。
   * 
   * @param data
   */
  public void setTimeData(final Matrix data) {
    this.data = data;

    this.timeSlider.setEnabled(true);
    this.manager.setData(this.data);

    org.mklab.mikity.xml.model.Group group = this.root.loadModel(0).loadGroup(0);
    checkLinkParameterType(group);

    final int dataCount = this.manager.getDataCount();

    this.timeTable = new double[dataCount];

    this.endTime = this.manager.getEndTime();
    this.startTimeLabel.setText("" + this.manager.getStartTime()); //$NON-NLS-1$
    this.endTimeLabel.setText("" + this.endTime); //$NON-NLS-1$
    for (int i = 0; i < this.timeTable.length; i++) {
      this.timeTable[i] = this.endTime * ((double)i / this.timeTable.length);
    }
    this.timeSlider.setMaximum(dataCount);

    this.filePathText.setText("data"); //$NON-NLS-1$
  }

  /**
   * アニメーションを開始します。
   */
  public void runAnimation() {
    if (playable == false) {
      SimulationViewer.this.timer.cancel();
    }
    if (SimulationViewer.this.data == null || SimulationViewer.this.timeTable == null) {
      MsgUtil.showMsg(getShell(), "再生ボタンが押されましたが、データは無いので終了します");
      System.out.println("再生ボタンが押されましたが、データは無いので終了します");
      return;
    }
    playable = false;

    SimulationViewer.this.endTime = SimulationViewer.this.manager.getEndTime();
    SimulationViewer.this.task = new AnimationTask(SimulationViewer.this.endTime, SimulationViewer.this.manager);
    SimulationViewer.this.task.setSpeed(this.playSpeed.getDoubleValue());// スピードの設定
    SimulationViewer.this.task.setCurrentTime(SimulationViewer.this.timeTable[SimulationViewer.this.timeSlider.getSelection()]);
    SimulationViewer.this.stask = new SliderPositionMoveTask(SimulationViewer.this.task, SimulationViewer.this.timeSlider);
    SimulationViewer.this.timer = new Timer();
    SimulationViewer.this.timer.schedule(SimulationViewer.this.task, 0, 10);
    SimulationViewer.this.timer.schedule(SimulationViewer.this.stask, 0, 10);
  }

  private class SliderPositionMoveTask extends TimerTask {

    AnimationTask localTask;
    Slider slider;

    /**
     * コンストラクター
     * 
     * @param task
     * @param slider
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

      Display display = getShell().getDisplay();
      if (display.isDisposed()) {
        return;
      }
      display.syncExec(new Runnable() {

        public void run() {
          double ct = SliderPositionMoveTask.this.localTask.getCurrentTime();
          String st = String.valueOf(ct);
          if (st.length() > 5) {
            st = st.substring(0, 4);
          }
          if (SimulationViewer.this.currentTimeLabel.isDisposed()) {
            return;
          }
          SimulationViewer.this.currentTimeLabel.setText(st);
          for (int i = 0; i < SimulationViewer.this.timeTable.length; i++) {
            if (SimulationViewer.this.timeTable[i] > ct) {
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