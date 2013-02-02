package org.mklab.mikity.view.canvas.java3d;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.mklab.mikity.model.xml.Jamast;
import org.mklab.mikity.model.xml.JamastConfig;
import org.mklab.mikity.model.xml.config.Light;
import org.mklab.mikity.model.xml.config.View;
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.util.Color3;
import org.mklab.mikity.util.ColorConstant;
import org.mklab.mikity.view.canvas.ModelCanvas;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Java3D用のキャンバスを表すクラスです。
 * 
 * @author Miki Koga
 * @version $Revision: 1.6 $.2004/12/16
 */
public class Java3dModelCanvas extends Canvas3D implements ModelCanvas {
  /** */
  private static final long serialVersionUID = 1L;
  /** マウス操作の状態の表す数値 */
  private int mouseOperationType = 0;
  /** 読み込んだファイルのルート */
  private Jamast root;

  /** トップブランチグループ */
  private BranchGroup topGroup;
  /** ユニバース */
  private SimpleUniverse universe;

  /** 背景色 */
  private Color3 backgroundColor;
  
  /** 光源の位置 */
  private Vector3f lightLocation = new Vector3f(0.2f, -0.8f, -0.8f);
  
  /** 視点の位置と向き */
  private Java3dViewpoint viewPoint;

  /**
   * コンストラクター universe --> BranchGroup --> TransformGroup --> topGroup
   * 
   * @param root ルート
   */
  public Java3dModelCanvas(Jamast root) {
    super(SimpleUniverse.getPreferredConfiguration());
    this.root = root;

    this.universe = new SimpleUniverse(this);

    // ブランチグループを設定
    final BranchGroup bg = new BranchGroup();
    bg.setCapability(BranchGroup.ALLOW_DETACH);

    loadConfigurationFromXML(root);

    // 平行光線の設定
    final Java3dDirectionalLight light = new Java3dDirectionalLight(new Color3f(1.0f, 1.0f, 1.0f), this.lightLocation);

    // 背景色の設定
    final BranchGroup background = createBackground(this.backgroundColor);

    bg.addChild(light);
    bg.addChild(background);

    // 基準座標系の設定
    final Java3dTransformGroup tg = new Java3dTransformGroup();

    initializeMouse(tg);

    // TransformGroupに子の書き込み、読み込み、追加を許可
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_WRITE);
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_READ);
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_EXTEND);

    // BranchGroupにTransformGroupをAddする
    bg.addChild(tg);

    final TransformGroup transform = new TransformGroup();
    transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    bg.addChild(transform);

    // BranchGroupにグループの追加の許可
    this.topGroup = new BranchGroup();
    this.topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_EXTEND);

    // そのBGをルートとするシーングラフをアクティブにし、作成、キャッシュする
    this.topGroup.setCapability(BranchGroup.ALLOW_DETACH);
    this.topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_READ);
    this.topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_WRITE);

    tg.addChild(this.topGroup);

    this.universe.addBranchGraph(bg);
  }

  /**
   * 背景ノードを作成します。
   * 
   * @param color 背景色
   * @return 背景ノード
   */
  private BranchGroup createBackground(final Color3 color) {
    final BranchGroup group = new BranchGroup();
    final double radius = Double.POSITIVE_INFINITY;

    final Color3f colorF = new Color3f(color.getR(), color.getG(), color.getB());
    
    final Background background = new Background(colorF);

    final BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), radius);
    background.setApplicationBounds(bounds);

    group.addChild(background);
    return group;
  }

  /**
   * 指定されたノードにマウスを作成し追加します。
   * 
   * @param tg 追加対象ノード
   */
  private void initializeMouse(final Java3dTransformGroup tg) {
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    final Point3d center = new Point3d(0.0, 0.0, 0.0);
    final float radius = 100f;
    final BoundingSphere bounding = new BoundingSphere(center, radius);

    final MouseZoom zoom = new MouseZoom(tg);
    zoom.setSchedulingBounds(bounding);
    tg.addChild(zoom);

    final MouseRotate rotation = new MouseRotate(tg);
    rotation.setSchedulingBounds(bounding);
    tg.addChild(rotation);

    final MouseTranslate translation = new MouseTranslate(tg);
    translation.setSchedulingBounds(bounding);
    tg.addChild(translation);
  }

  /**
   * {@inheritDoc}
   */
  public void load() {
    loadConfigurationFromXML(this.root);
    final Group[] groups = this.root.loadModel(0).loadGroup();
    setChild(groups);
  }

  /**
   * {@inheritDoc}
   */
  public void setChild(Group[] groups) {
    this.topGroup.removeAllChildren();

    final BranchGroup branchGroup = new BranchGroup();
    branchGroup.setCapability(BranchGroup.ALLOW_DETACH);

    final TransformGroup tg = new TransformGroup();
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    for (final Group group : groups) {
      branchGroup.addChild(tg);
      final Java3dTransformGroup child = Java3dPrimitiveFactory.create(group);
      tg.addChild(child);
    }

    this.topGroup.addChild(branchGroup);
  }
  
  /**
   * XMLデータからConfigデータを読み込みます。
   * 
   * @param aRoot
   */
  private void loadConfigurationFromXML(Jamast aRoot) {
    final JamastConfig configuration = aRoot.loadConfig(0);
    if (configuration == null) {
      return;
    }

    // 背景色をセット
    final org.mklab.mikity.model.xml.config.Background loadedBackground = configuration.loadBackground();
    if (loadedBackground != null) {
      this.backgroundColor = ColorConstant.getColor(loadedBackground.loadColor());
    } else {
      this.backgroundColor = ColorConstant.getColor("white"); //$NON-NLS-1$
    }

    // 光源の位置をセット
    final Light loadedLightLocation = configuration.loadLight();
    if (loadedLightLocation != null) {
      final Light light = loadedLightLocation;
      this.lightLocation = new Vector3f(light.loadX(), light.loadY(), light.loadZ());
    }

    // 視点の位置と方向をセット
    final View loadedViewPoint = configuration.loadView();
    if (loadedViewPoint != null) {
      this.viewPoint = new Java3dViewpoint(this.universe, loadedViewPoint, this.mouseOperationType);
    } else {
      final AxisAngle4f orientationAngle = new AxisAngle4f(1.0f, 0.0f, 0.0f, -0.2f);
      final Vector3f position = new Vector3f(0.0f, 0.3f, 1.0f);
      this.viewPoint = new Java3dViewpoint(orientationAngle, position, this.universe);
    }

  }
}