package org.mklab.mikity.gui;


//import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

//import org.mklab.mikity.picker.DataPicker;
import org.mklab.mikity.MyDirectionalLight;
import org.mklab.mikity.MyTransformGroup;
import org.mklab.mikity.MyViewpoint;
import org.mklab.mikity.PrimitiveFactory;
import org.mklab.mikity.util.ColorConstant;
import org.mklab.mikity.xml.config.Light;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.Config;
import org.mklab.mikity.xml.Jamast;

import saiga.MyMouse;
import saiga.MyPlaneBackground;
import com.sun.j3d.utils.universe.SimpleUniverse;
//import com.sun.j3d.utils.universe.ViewingPlatform;


/**
 * キャンバスに関するクラス
 * @author Miki Koga+ Shogo Morimune
 * @version $Revision: 1.6 $.2004/12/16
 */
public class ModelCanvas extends Canvas3D {

  /** */
  private static final long serialVersionUID = 1L;
  
  /**
   * マウス操作の状態の表す数値
   */
  public int mouseOperationType =0;

  /** 読み込んだファイルのルート*/
  private Jamast root;

  private TransformGroup transform;
  /** トップブランチグループ*/
  private BranchGroup topGroup;
  /** ユニバース */
  private SimpleUniverse universe;
  /** ブランチ */
  private BranchGroup bg;
  
  private Color3f backgroundColor;
  private Vector3f lightLocation = new Vector3f(0.2f, -0.8f, -0.8f);

  //private Canvas3D canvas;

  //private Bounds bounds;
  
  /**
   * 
   */
  public static int scale = 1;
  
  /**
   * 
   */
  public static boolean radian = false;
  
  /**
   * 
   * コンストラクター universe --> BranchGroup --> TransformGroup --> topGroup
   * @param root
   */
  public ModelCanvas(Jamast root) {
    super(SimpleUniverse.getPreferredConfiguration());
    this.root = root;

    
    //シンプルユニバースを設定
    universe = new SimpleUniverse(this);

    //ブランチグループを設定
    bg = new BranchGroup();
    bg.setCapability(BranchGroup.ALLOW_DETACH);

    getParameter(root);
    
    //平行光線の設定
    MyDirectionalLight light = new MyDirectionalLight(new Color3f(1.0f, 1.0f, 1.0f), lightLocation);

    //背景色の設定
    MyPlaneBackground background = new MyPlaneBackground(backgroundColor);
    
    bg.addChild(light);
    bg.addChild(background);

    //基準座標系の設定
    MyTransformGroup tg = new MyTransformGroup();
    
    new MyMouse(tg);

    //TransformGroupに子の書き込み、読み込み、追加を許可
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_WRITE);
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_READ);
    tg.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_EXTEND);

    //BranchGroupにTransformGroupをAddする
    bg.addChild(tg);
    
    transform = new TransformGroup();
    transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    bg.addChild(transform);
    
    //BranchGroupにグループの追加の許可
    topGroup = new BranchGroup();
    topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_EXTEND);
    
    //そのBGをルートとするシーングラフをアクティブにし、作成、キャッシュする
    topGroup.setCapability(BranchGroup.ALLOW_DETACH);
    topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_READ);
    topGroup.setCapability(javax.media.j3d.Group.ALLOW_CHILDREN_WRITE);
    
    tg.addChild(topGroup);

//    if(mouseOperationType == 1){
//      bg = new BranchGroup();
//      
//      BoundingSphere bound_sph = new BoundingSphere( new Point3d(), 100.0 );
//      
//      PickRotateBehavior rotator = new PickRotateBehavior(bg, canvas, bound_sph);
//      rotator.setMode(PickTool.GEOMETRY);
//      bg.addChild(rotator);
//      
//      PickTranslateBehavior translator = new PickTranslateBehavior(bg, canvas, bound_sph);
//      translator.setMode(PickTool.GEOMETRY);
//      bg.addChild(translator);
//      
//      PickZoomBehavior zoomer = new PickZoomBehavior(bg, canvas, bound_sph);
//      zoomer.setMode(PickTool.GEOMETRY);
//      bg.addChild(zoomer);
//      
//      SimplePicking simplepicking = new SimplePicking(bg,canvas,bound_sph,PickTool.GEOMETRY_INTERSECT_INFO,PickResult.PRIMITIVE );
//      simplepicking.setupCallback( new SimplePickingCallback() {
//        public void picked(int type, Node node) {
//          if (node != null) {
//            String data = (String)node.getUserData();
//            System.out.println(data);
//          } else {
//            System.out.println("Error: node is null.");
//          }
//        }
//      });
//      bg.addChild(simplepicking);
//      
// 
//      bg.addChild(tg);
//      
//      TransformGroup gtrans = new TransformGroup();
//      gtrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//      gtrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//      gtrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
//      tg.addChild(gtrans);
//      universe.addBranchGraph(bg);
//    }
    universe.addBranchGraph(bg);
    
  }



  /**
   * 画面から全ての物体を削除(光源設定などはそのまま)
   */
  public void clear() {
//    if (topGroup != null) {
//      topGroup.detach();
//    }
    topGroup.removeAllChildren();
  }

  /**
   * 子を追加する
   * @param node
   */
  public void addChild(MyTransformGroup node){
    BranchGroup tmp = new BranchGroup();
    tmp.setCapability(BranchGroup.ALLOW_DETACH);
    tmp.addChild(node);
    topGroup.addChild(tmp);
  }
  
  /**
   * @param groups
   * いったんトップグループの全てを消してから書き込む
   */
  public void setChild(Group[] groups) {
    topGroup.removeAllChildren();
    BranchGroup branch = new BranchGroup();
    branch.setCapability(BranchGroup.ALLOW_DETACH);
    TransformGroup trans = new TransformGroup();
    trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    for (int i = 0; i < groups.length; i++) {
      branch.addChild(trans);
      trans.addChild(PrimitiveFactory.createGroup(groups[i]));
    }
    topGroup.addChild(branch);
  }
	
  /**
   * fileからXMLを読み込む
   */
  public void load() {
    getParameter(root);
    setChild(root.loadModel(0).loadGroup());
  }

  /**
   * XMLデータからConfigデータを読み込む
   * @param root 
   */
  private void getParameter(Jamast root) {
    Config config = root.loadConfig(0);
   //背景色をセット
    if(config.loadBackground() == null){
      backgroundColor = ColorConstant.getColor("white");
    }else{
      backgroundColor = ColorConstant.getColor(config.loadBackground().loadColor());
    }
    //光源の位置をセット
    if(config.loadLight() != null){
      Light light = config.loadLight();
      lightLocation = new Vector3f(light.loadX(), light.loadY(), light.loadZ());
    }
    //視点の位置、向きをセット
    if(config.loadView() != null){
      new MyViewpoint(universe, config.loadView(),mouseOperationType);
    }else{
      new MyViewpoint(new AxisAngle4f(1.0f,0.0f,0.0f,-0.2f),new Vector3f(0.0f, 0.3f, 1.0f), universe);
    }

    //単位を読み込む
//    int dScale = 1;
//    boolean dRadian = true;
//    if(config.loadModelUnit() != null){
//      if(config.loadModelUnit().loadLength() != null){
//        if(config.loadModelUnit().loadLength().startsWith("cm")){
//          scale = 100;
//        }else if(config.loadModelUnit().loadLength().startsWith("mm")){
//          scale = 1000;
//        }
//      }
//      if(config.loadModelUnit().loadAngle() != null){
//        if(config.loadModelUnit().loadAngle().startsWith("degree")){
//          radian = false;
//        }
//      }
//    }
//    
//    PrimitiveFactory.setScale(scale, radian);
//    
//    if(config.loadDataUnit() != null){
//      if(config.loadDataUnit().loadLength() != null){
//        if(config.loadDataUnit().loadLength().startsWith("cm")){
//          dScale = 100;
//        }else if(config.loadDataUnit().loadLength().startsWith("mm")){
//          dScale = 1000;
//        }
//      }
//      if(config.loadDataUnit().loadAngle() != null){
//        if(config.loadDataUnit().loadAngle().startsWith("degree")){
//          dRadian = false;
//        }
//      }
//    }
//        
//    org.mklab.mikity.picker.DataPicker.setScale(dScale, dRadian, scale, radian);
  }
}