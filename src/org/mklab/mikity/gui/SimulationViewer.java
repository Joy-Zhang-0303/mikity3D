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


/*
 * Created on 2004/12/02
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */

/**
 * シミュレーションの描画を行うクラス
 * @author miki
 * @version $Revision: 1.21 $.2004/12/02
 */

public class SimulationViewer extends ApplicationWindow {

  /** アニメーション用タスク */
  private AnimationTask task;
  private SliderPositionMoveTask stask;

  /**
   * 
   */
  public static boolean playable = true;

  private double endTime;

  private Jamast root;

  private double speed = 1.0;

  private Timer timer = new Timer();

  private Slider timeSlider;

  /** 等間隔の時間を保存しとく配列 */
  private double[] timeTable;

  private Matrix data;

  private Text filePathText;

  private MovableGroupManager manager;

  private Label startTimeLabel;
  private Label currentTimeLabel;
  private Label endTimeLabel;

  private boolean usedDHParam = false;
  private boolean usedLink = false;
  
  /**
   * コンストラクター
   * 
   * @param parentShell
   * @param root
   */
  public SimulationViewer(final Shell parentShell, final Jamast root) {
    super(parentShell);
    manager = new MovableGroupManager(root);
    this.root = root;
    setShellStyle(SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
  }

  /**
   * シェルの設定
   * 
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  protected void configureShell(final Shell shell) {
    super.configureShell(shell);
    shell.setSize(647, 500);
    shell.setText("SimulationViewer");
  }

  /**
   * コンテンツ作成
   * 
   * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
   */
  protected Control createContents(final Composite parent) {
    SashForm sash = new SashForm(parent, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());

    createViewer(sash);
    createController(sash);

    sash.setWeights(new int[] {70, 30}); //70%:30%に分割点を設定

    if(root.loadConfig(0).loadData()!=null){
      setTimeData(new File(root.loadConfig(0).loadData()));
    }
    return parent;
  }

  /**
   * シェルを閉じるときにこのメソッドが呼ばれる
   * 
   * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
   */
  protected void handleShellCloseEvent() {

    if (task != null) {
      task.cancel();
    }
    if (stask != null) {
      stask.cancel();
    }
    if (timer != null) {
      timer.cancel();
    }

    super.handleShellCloseEvent();
  }

  /**
   * This method initializes viewer 実際にシミュレーションを見る画面
   * @param comp 
   */
  private void createViewer(final Composite comp) {
    final GridData gridData = new GridData(GridData.FILL_BOTH);

    final Composite viewer = new Composite(comp, SWT.EMBEDDED);
    viewer.setLayoutData(gridData);

    //AWTのフレームを作る。
    final Frame awtFrame = SWT_AWT.new_Frame(viewer);
    final ModelCanvas sinsiCanvas = new ModelCanvas(root);
    awtFrame.add(sinsiCanvas);
    sinsiCanvas.load();
  }

  /**
   * This method initializes controllerComp コントローラCompositの中身
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

    //timeLabel.setText("" + task.getCurrentTime());

    Composite speedComp = new Composite(otherController, SWT.NONE);
    GridLayout speedLayout = new GridLayout();
    speedLayout.numColumns = 2;
    speedComp.setLayout(speedLayout);
    final ParameterInputBox playSpeed = new ParameterInputBox(speedComp, SWT.NONE, "再生速度", "1.0");
    //time = new ParameterInputBox(speedComp, SWT.NONE, "時間","0.0");

    createTimeBar(otherController);

    final Composite comp2 = new Composite(controllerComp, SWT.NONE);
    comp2.setLayout(new GridLayout(3, false));

    fastButton.addSelectionListener(new SelectionAdapter() {

      public void widgetSelected(SelectionEvent arg0) {
        speed += 0.1;
        if (task != null) {
          task.setSpeed(speed);
        }
        //double型をString型に変更
        String stValue = String.valueOf(speed);
        //小数点第二以下を表示しないようにする
        stValue = stValue.substring(0, stValue.indexOf(".") + 2);
        playSpeed.setText(stValue);
      }
    });

    slowButton.addSelectionListener(new SelectionAdapter() {

      public void widgetSelected(SelectionEvent arg0) {
        speed -= 0.1;
        if (task != null) {
          task.setSpeed(speed);
        }
        String stValue = String.valueOf(speed);
        stValue = stValue.substring(0, stValue.indexOf(".") + 2);
        playSpeed.setText(stValue);
      }
    });

    //再生ボタンによるイベント
    playbackButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (playable == false) {
          timer.cancel();
        }
        if (data == null || timeTable == null) {
          MsgUtil.showMsg(getShell(), "再生ボタンが押されましたが、データは無いので終了します");
          System.out.println("再生ボタンが押されましたが、データは無いので終了します");
          return;
        }
        playable = false;
        
        endTime = manager.getEndTime();
        task = new AnimationTask(endTime, manager);
        task.setSpeed(playSpeed.getDoubleValue());//スピードの設定
        task.setCurrentTime(timeTable[timeSlider.getSelection()]);
        stask = new SliderPositionMoveTask(task, timeSlider);
        timer = new Timer();
        timer.schedule(task, 0, 10);
        timer.schedule(stask, 0, 10);
      }
    });

    //停止ボタンによるイベント
    stopButton.addSelectionListener(new SelectionAdapter() {

      public void widgetSelected(SelectionEvent arg0) {
        //スレッドを停止させる。復元不能
        timer.cancel();
        playable = true;
      }
    });
  }

  /**
   * 実行時間バーを生成する。
   * @param parent
   */
  private void createTimeBar(final Composite parent) {
    final Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(3, true));
    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

    startTimeLabel = new Label(composite, SWT.NONE | SWT.LEFT);
    currentTimeLabel = new Label(composite, SWT.NONE | SWT.CENTER);
    currentTimeLabel.setText("0.0");
    currentTimeLabel.setLayoutData(gridData);
    endTimeLabel = new Label(composite, SWT.NONE | SWT.RIGHT);

    timeSlider = new Slider(composite, SWT.NONE);
    gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 3;
    timeSlider.setLayoutData(gridData);
    timeSlider.setMinimum(0);
    timeSlider.setMaximum(0);
    //dataCount スライダーの最大値

    timeSlider.addSelectionListener(new SelectionAdapter() {

      public void widgetSelected(SelectionEvent arg0) {
        double t = timeTable[timeSlider.getSelection()];
        manager.processStimulus(t);
        if (task != null) {
          task.setCurrentTime(t);
          String st = String.valueOf(t);
          if (st.length() > 5) {
            st = st.substring(0, 4);
          }
          currentTimeLabel.setText(st);
        }
      }
    });
  }

  /**
   * ファイルを選択するボタン
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

    filePathText = new Text(comp, SWT.BORDER);
    filePathText.setText("");
    filePathText.addTraverseListener(new TraverseListener() {

      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_RETURN) {
          setTimeData(new File(filePathText.getText()));
        }
      }
    });

    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 4;
    filePathText.setLayoutData(gridData);

    final Button refButton = new Button(comp, SWT.BORDER);
    refButton.setText("参照");

    refButton.addSelectionListener(new SelectionAdapter() {

      public void widgetSelected(SelectionEvent arg0) {
        FileDialog dialog = new FileDialog(composite.getShell());
        //ファイルを選択させる
        String ret = dialog.open();
        if (ret != null) {
          setTimeData(new File(ret));
        }
      }
    });
  }

  private void checkLinkParameterType(org.mklab.mikity.xml.model.Group group){
	  org.mklab.mikity.xml.model.Group[] subGroup = group.loadGroup();
	  if(subGroup.length != 0){
		  for(int i=0; i<subGroup.length; i++){
			  org.mklab.mikity.xml.model.Linkdata[] link = subGroup[i].loadLinkdata();
			  for(int j=0; j<link.length; j++){
				  if(link[j].hasDH()){
					  usedDHParam=true;
					  manager.setDH(usedDHParam);
				  }else if(link[j].hasLink()){
					  usedLink=true;
					  manager.setLink(usedLink);
				  }else{
					  usedDHParam=false;
					  usedLink=false;
				  }
			  }
			  checkLinkParameterType(subGroup[i]);
		  }
	  }
  }
  
  /**
   * 実行時間バーを設定する。
   * @param file
   */
  public void setTimeData(final File file) {
    try {
      data = Matrix.readMatFile(new FileInputStream(file));

      timeSlider.setEnabled(true);
      manager.setData(data);

      org.mklab.mikity.xml.model.Group group = root.loadModel(0).loadGroup(0);
      checkLinkParameterType(group);
      
      final int dataCount = manager.getDataCount();

      timeTable = new double[dataCount];

      endTime = manager.getEndTime();
      startTimeLabel.setText("" + manager.getStartTime());
      endTimeLabel.setText("" + endTime);
      for (int i = 0; i < timeTable.length; i++) {
        timeTable[i] = endTime * ((double)i / timeTable.length);
      }
      timeSlider.setMaximum(dataCount);

      filePathText.setText(file.getPath());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private class SliderPositionMoveTask extends TimerTask {

    private AnimationTask task;
    private Slider slider;

    /**
     * コンストラクター
     * 
     * @param task
     * @param slider
     */
    public SliderPositionMoveTask(AnimationTask task, Slider slider) {
      this.task = task;
      this.slider = slider;
    }

    /**
     * @see java.util.TimerTask#run()
     */
    public void run() {
      if(getShell() == null){
        return;
      }
        
      Display display = getShell().getDisplay();
      if (display.isDisposed()) {
        return;
      }
      display.syncExec(new Runnable() {

        public void run() {
          double ct = task.getCurrentTime();
          String st = String.valueOf(ct);
          if (st.length() > 5) {
            st = st.substring(0, 4);
          }
          if (currentTimeLabel.isDisposed()) {
            return;
          }
          currentTimeLabel.setText(st);
          for (int i = 0; i < timeTable.length; i++) {
            if (timeTable[i] > ct) {
              if (slider.isDisposed()) {
                return;
              }
              slider.setSelection(i);
              return;
            }
          }
        }
      });
    }
  }
}