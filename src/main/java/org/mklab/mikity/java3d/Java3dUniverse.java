package org.mklab.mikity.java3d;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Frame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.View;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;


/**
  * @author miki
  * @version $Revision: 1.7 $.2004/12/22
  */
public class Java3dUniverse {
  /** */
  private Applet applet = null;
  /** */
  private static SimpleUniverse universe = null;
  /** */
  private Canvas3D canvas = null;
  /** */
  private static float fieldOfView = (float)Math.toRadians(45.);
  /** */
  private static float frontDistance = 0.5f;
  /** */
  private static float backDistance = 100.0f;
  /** */
  static float boundsRadius = 100.0f;
  /** */
  static float creaseAngle = (float)Math.toRadians(44.);
  /** */
  private static int division = 30;

  /**
   * コンストラクター
   * 
   * @param app アプレット
   */
  public Java3dUniverse(Applet app) {
    this.applet = app;

    // 画面オブジェクトの生成
    canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // 画面レイアウトの設定
    this.applet.setLayout(new BorderLayout());
    this.applet.add(canvas, BorderLayout.CENTER);

    // シンプルユニバースの生成
    universe = new SimpleUniverse(canvas);
    // 視点位置の設定
    ViewingPlatform vp = universe.getViewingPlatform();
    vp.setNominalViewingTransform();
  }

  /**
   */
  /**
   * 新しく生成された<code>MyUniverse</code>オブジェクトを初期化します。
   * @param frame
   */
  public Java3dUniverse(Frame frame) {
    // 画面オブジェクトの生成
    canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // 画面レイアウトの設定
    frame.setLayout(new BorderLayout());
    frame.add(canvas, BorderLayout.CENTER);

    // シンプルユニバースの生成
    universe = new SimpleUniverse(canvas);

    // 視点位置の設定
    ViewingPlatform vp = universe.getViewingPlatform();
    vp.setNominalViewingTransform();
  }

  /**
   * @param viewAngle 視点
   * @param frontDis 前面の距離
   * @param backDis 構面の距離
   * @param boundsRad 境界の半径
   * @param creaseAng しわの角度
   * @param div 部門
   */
  public static void setEnvParameter(float viewAngle, float frontDis, float backDis, float boundsRad, float creaseAng, int div) {
    // 環境パラメータの設定
    fieldOfView = viewAngle;
    frontDistance = frontDis;
    backDistance = backDis;
    boundsRadius = boundsRad;
    creaseAngle = creaseAng;
    division = div;

    // Viewオブジェクトの取得
    View view = universe.getViewer().getView();

    // 視野角の設定
    view.setWindowEyepointPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
    view.setFieldOfView(fieldOfView);

    // 前方クリッピング距離の設定
    view.setFrontClipPolicy(View.VIRTUAL_EYE);
    view.setFrontClipDistance(frontDistance);

    // 後方クリッピング距離の設定
    view.setBackClipPolicy(View.VIRTUAL_EYE);
    view.setBackClipDistance(backDistance);
  }

  /**
   * @param bg ブランチグループ
   */
  public static void addBranchGraph(BranchGroup bg) {
    // Compiled-Retainモード実行
    bg.compile();

    // ブランチグループのロケールへの接続
    universe.addBranchGraph(bg);
  }
}