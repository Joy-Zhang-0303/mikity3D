package org.mklab.mikity.java3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

/**
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/22
 */
public class MyTriPolygons extends MyTransformGroup {
  /** */
  private static final int RENDERING_ALL = 1;

  private GeometryArray geom = null;
  private Appearance app = null;
  private double creaseAngle = MyUniverse.creaseAngle;

  /**
   * コンストラクター
   * 
   * @param vertex 頂点
   * @param material 素材
   * @param transAttr 透明の属性
   * @param mode モード
   */
  public MyTriPolygons(Point3d[] vertex, Material material, TransparencyAttributes transAttr, int mode) {
    // 形状の枠組み生成
    final Shape3D shape = new Shape3D();
    final Point3d[] vertex2 = new Point3d[(vertex.length - 2) * 3];

    for (int i = 0, j = 0; i < (vertex.length - 2); i++, j += 3) {
      vertex2[j] = vertex[0];
      vertex2[j + 1] = vertex[(i + 1) % vertex.length];
      vertex2[j + 2] = vertex[(i + 2) % vertex.length];
    }

    shape.setGeometry(geometry(vertex2));
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
    final GeometryInfo info = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);

    // 頂点座標の設定
    info.setCoordinates(vertex);

    // 法線の設定
    NormalGenerator generator = new NormalGenerator();
    generator.setCreaseAngle(this.creaseAngle);
    generator.generateNormals(info);

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
      PolygonAttributes attributes = new PolygonAttributes();
      attributes.setCullFace(PolygonAttributes.CULL_NONE);
      attributes.setBackFaceNormalFlip(true);
      this.app.setPolygonAttributes(attributes);
    }

    return this.app;
  }
}