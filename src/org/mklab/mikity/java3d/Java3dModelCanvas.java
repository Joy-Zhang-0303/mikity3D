package org.mklab.mikity.java3d;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.mklab.mikity.util.ColorConstant;
import org.mklab.mikity.xml.Config;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.config.Light;
import org.mklab.mikity.xml.model.Group;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * キャンバスに関するクラス
 * 
 * @author Miki Koga
 * @version $Revision: 1.6 $.2004/12/16
 */
public class Java3dModelCanvas extends Canvas3D {
  /** */
  private static final long serialVersionUID = 1L;
  /** マウス操作の状態の表す数値  */
  public int mouseOperationType = 0;
  /** 読み込んだファイルのルート */
  private Jamast root;

  private TransformGroup transform;
  /** トップブランチグループ */
  private BranchGroup topGroup;
  /** ユニバース */
  private SimpleUniverse universe;
  /** ブランチ */
  private BranchGroup bg;

  private Color3f backgroundColor;
  private Vector3f lightLocation = new Vector3f(0.2f, -0.8f, -0.8f);

  /** */
  public static int scale = 1;
  /** */
  public static boolean radian = false;

  /**
   * コンストラクター universe --> BranchGroup --> TransformGroup --> topGroup
   * @param root 
   */
  public Java3dModelCanvas(Jamast root) {
    super(SimpleUniverse.getPreferredConfiguration());
    this.root = root;

    // シンプルユニバースを設定
    this.universe = new SimpleUniverse(this);

    // ブランチグループを設定
    this.bg = new BranchGroup();
    this.bg.setCapability(BranchGroup.ALLOW_DETACH);

    getParameter(root);

    // 平行光線の設定
    MyDirectionalLight light = new MyDirectionalLight(new Color3f(1.0f, 1.0f, 1.0f), this.lightLocation);

    // 背景色の設定
    BranchGroup background = createBackground(this.backgroundColor);

    this.bg.addChild(light);
    this.bg.addChild(background);

    // 基準座標系の設定
    MyTransformGroup tg = new MyTransformGroup();

    initializeMouse(tg);

    // TransformGroupに子の書き込み、読み込み、追加を許可
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_WRITE);
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_READ);
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_EXTEND);

    // BranchGroupにTransformGroupをAddする
    this.bg.addChild(tg);

    this.transform = new TransformGroup();
    this.transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    this.bg.addChild(this.transform);

    // BranchGroupにグループの追加の許可
    this.topGroup = new BranchGroup();
    this.topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_EXTEND);

    // そのBGをルートとするシーングラフをアクティブにし、作成、キャッシュする
    this.topGroup.setCapability(BranchGroup.ALLOW_DETACH);
    this.topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_READ);
    this.topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_WRITE);

    tg.addChild(this.topGroup);

    this.universe.addBranchGraph(this.bg);
  }
  
  /**
   * 背景ノードを作成します。
   * 
   * @param color 背景色
   * @return 背景ノード
   */
  private BranchGroup createBackground(final Color3f color){
    final BranchGroup group = new BranchGroup();
    final double radius = Double.POSITIVE_INFINITY;
    
    Background background = new Background(color);

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), radius);
    background.setApplicationBounds(bounds);

    group.addChild(background);
    return group;
  }
  
  /**
   * 指定されたノードにマウスを作成し追加します。
   * 
   * @param tg 追加対象ノード
   */
  private void initializeMouse(final MyTransformGroup tg){
    final float radius = 100f;
    
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    BoundingSphere bounds=new BoundingSphere(new Point3d(0.0,0.0,0.0),radius);

    MouseZoom zoom=new MouseZoom(tg);
    zoom.setSchedulingBounds(bounds);                 
    tg.addChild(zoom);

    MouseRotate rotate=new MouseRotate(tg);
    rotate.setSchedulingBounds(bounds);               
    tg.addChild(rotate);

    MouseTranslate translate=new MouseTranslate(tg);
    translate.setSchedulingBounds(bounds);
    tg.addChild(translate); 
  }

//  /**
//   * 画面から全ての物体を削除(光源設定などはそのまま)
//   */
//  public void clear() {
//    this.topGroup.removeAllChildren();
//  }

//  /**
//   * 子を追加する
//   * 
//   * @param node
//   */
//  public void addChild(MyTransformGroup node) {
//    BranchGroup tmp = new BranchGroup();
//    tmp.setCapability(BranchGroup.ALLOW_DETACH);
//    tmp.addChild(node);
//    this.topGroup.addChild(tmp);
//  }

  /**
   * @param groups いったんトップグループの全てを消してから書き込む
   */
  public void setChild(Group[] groups) {
    this.topGroup.removeAllChildren();
    BranchGroup branch = new BranchGroup();
    branch.setCapability(BranchGroup.ALLOW_DETACH);
    TransformGroup trans = new TransformGroup();
    trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    
    for (int i = 0; i < groups.length; i++) {
      branch.addChild(trans);
      trans.addChild(PrimitiveFactory.createGroup(groups[i]));
    }
    this.topGroup.addChild(branch);
  }

  /**
   * fileからXMLを読み込む
   */
  public void load() {
    getParameter(this.root);
    setChild(this.root.loadModel(0).loadGroup());
  }

  /**
   * XMLデータからConfigデータを読み込む
   * 
   * @param argRoot
   */
  private void getParameter(Jamast argRoot) {
    Config config = argRoot.loadConfig(0);
    if (config == null) {
      return;
    }
    
    // 背景色をセット
    if (config.loadBackground() == null) {
      this.backgroundColor = ColorConstant.getColor("white"); //$NON-NLS-1$
    } else {
      this.backgroundColor = ColorConstant.getColor(config.loadBackground().loadColor());
    }
    
    // 光源の位置をセット
    if (config.loadLight() != null) {
      Light light = config.loadLight();
      this.lightLocation = new Vector3f(light.loadX(), light.loadY(), light.loadZ());
    }
    
    // 視点の位置、向きをセット
    if (config.loadView() != null) {
      new MyViewpoint(this.universe, config.loadView(), this.mouseOperationType);
    } else {
      new MyViewpoint(new AxisAngle4f(1.0f, 0.0f, 0.0f, -0.2f), new Vector3f(0.0f, 0.3f, 1.0f), this.universe);
    }

  }
}