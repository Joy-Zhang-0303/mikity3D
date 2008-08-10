package org.mklab.mikity;

//**********************************************************************
//             MyTriPolygonsクラス（暫定版）                           *
//**********************************************************************
import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;


/**
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/22
 */
public class MyTriPolygons extends MyTransformGroup {

  /**
     * 
     */
  public static final int RENDERING_FRONT = 0;
  /**
     * 
     */
  public static final int RENDERING_ALL = 1;

  private GeometryArray geom = null;
  private Appearance app = null;
  private double creaseAngle = MyUniverse.creaseAngle;

  /**
   * コンストラクター
   * 
   * @param vertex
   * @param material
   * @param transAttr
   * @param mode
   */
  public MyTriPolygons(Point3d[] vertex, Material material, TransparencyAttributes transAttr, int mode) {
    // 形状の枠組み生成
    Shape3D shape = new Shape3D();

    Point3d[] vertex2 = new Point3d[(vertex.length - 2) * 3];

    for (int i = 0, j = 0; i < (vertex.length - 2); i++, j += 3) {
      vertex2[j] = vertex[0];
      vertex2[j + 1] = vertex[(i + 1) % vertex.length];
      vertex2[j + 2] = vertex[(i + 2) % vertex.length];
    }

    // 形状座標の設定
    shape.setGeometry(geometry(vertex2));

    // 質感の設定
    shape.setAppearance(appearance(material, transAttr, mode));

    // 座標系に物体オブジェクトの接続
    this.addChild(shape);
  }

  /**
   * @param vertex
   * @return geom
   */
  private Geometry geometry(Point3d[] vertex) {
    // 形状の原型設定
    GeometryInfo info = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);

    // 頂点座標の設定
    info.setCoordinates(vertex);

    // 法線の設定
    NormalGenerator normalGen = new NormalGenerator();
    normalGen.setCreaseAngle(this.creaseAngle);
    normalGen.generateNormals(info);

    // 形状の取得
    this.geom = info.getGeometryArray();

    return this.geom;
  }

  /**
   * @param material
   * @param transAttr
   * @param mode
   * @return app
   */
  private Appearance appearance(Material material, TransparencyAttributes transAttr, int mode) {
    // 色の設定
    this.app = new Appearance();
    this.app.setMaterial(material);

    // 透明度の設定
    this.app.setTransparencyAttributes(transAttr);

    // 裏面描画
    if (mode == RENDERING_ALL) {
      PolygonAttributes polAttr = new PolygonAttributes();
      polAttr.setCullFace(PolygonAttributes.CULL_NONE);
      polAttr.setBackFaceNormalFlip(true);
      this.app.setPolygonAttributes(polAttr);
    }

    return this.app;
  }

  /**
   * @see org.mklab.mikity.MyTransformGroup#mulScale(javax.vecmath.Vector3f)
   */
  @Override
  public void mulScale(Vector3f scale) {
    // スケールの座標変換
    super.mulScale(scale);
  }

  /**
   * @see org.mklab.mikity.MyTransformGroup#mulRotation(javax.vecmath.AxisAngle4f)
   */
  @Override
  public void mulRotation(AxisAngle4f angle) {
    // 回転の座標変換
    super.mulRotation(angle);
  }

  /**
   * @see org.mklab.mikity.MyTransformGroup#mulTranslation(javax.vecmath.Vector3f)
   */
  @Override
  public void mulTranslation(Vector3f translation) {
    // 平行移動の座標変換
    super.mulTranslation(translation);
  }
}