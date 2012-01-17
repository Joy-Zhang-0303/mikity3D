/*
 * Created on 2004/11/25
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.java3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.mklab.mikity.model.TransformGroupMaker;
import org.mklab.mikity.util.ColorConstant;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;



/**
 * プリミティブおよびポリゴンの生成を行うクラス
 * 
 * @author miki
 * @version $Revision: 1.21 $.2004/11/30
 */
public class PrimitiveFactory {

  /**
   * 単位
   */
  private static int scale = 1;
  private static boolean radian = true;

  /**
   * 透明度の設定
   */
  private static TransparencyAttributes transAttr = new TransparencyAttributes(TransparencyAttributes.NICEST, 0.7f);

  /**
   * グループを作る。
   * 
   * @param group グループ
   * @return tg トランスフォームグループ
   */
  public static MyTransformGroup createGroup(Group group) {
    scale = Java3dModelCanvas.scale;
    radian = Java3dModelCanvas.radian;
    MyTransformGroup tg = new MyTransformGroup();

    Linkdata[] linkdata = group.loadLinkdata();

    myTransformGroupCreater(group, tg, linkdata);

    return tg;
  }

  private static void myTransformGroupCreater(Group group, MyTransformGroup tg, Linkdata[] linkdata) {
    TransformGroupMaker.TransformGroupCreater(group, tg, linkdata);
  }


  /**
   * 与えられたboxインスタンスのプリミティブを作成する。
   * 
   * @param box ボックス
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static MyTransformGroup create(XMLBox box) {
    int flag = Primitive.GENERATE_NORMALS;
    Primitive primitive = new Box(box.loadXsize() / (scale * 2), box.loadYsize() / (scale * 2), box.loadZsize() / (scale * 2), flag, null);
    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(box.loadColor()));
    if (box.hasTransparent()) {
      // hasTransparent = true ならばプリミティブを半透明にする
      if (box.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    MyTransformGroup tg = new MyTransformGroup();
    tg.addChild(primitive);

    // 続けると長くなるので、一度Locationを得る
    Location loc = box.loadLocation();
    Rotation rot = box.loadRotation();

    setLocRot(loc, rot, tg);

    return tg;
  }

  /**
   * 与えられたcylinderインスタンスのプリミティブを作成する。
   * 
   * @param cylinder シリンダー
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static MyTransformGroup create(XMLCylinder cylinder) {
    int flag = Primitive.GENERATE_NORMALS;
    if (cylinder.loadDiv() < 3) {
      cylinder.setDiv(10);
    }
    Primitive primitive = new Cylinder(cylinder.loadR(), cylinder.loadHeight(), flag, cylinder.loadDiv(), cylinder.loadDiv(), null);
    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(cylinder.loadColor()));
    if (cylinder.hasTransparent()) {
      if (cylinder.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    MyTransformGroup tg = new MyTransformGroup();
    tg.addChild(primitive);

    Location loc = cylinder.loadLocation();
    Rotation rot = cylinder.loadRotation();

    setLocRot(loc, rot, tg);

    return tg;
  }

  /**
   * 与えられたsphereインスタンスのプリミティブを作成する。
   * 
   * @param sphere スフィア
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static MyTransformGroup create(XMLSphere sphere) {
    int flag = Primitive.GENERATE_NORMALS;
    if (sphere.loadDiv() < 3) sphere.setDiv(10);
    Primitive primitive = new Sphere(sphere.loadR(), flag, sphere.loadDiv(), null);
    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(sphere.loadColor()));
    if (sphere.hasTransparent()) {
      if (sphere.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    MyTransformGroup tg = new MyTransformGroup();
    tg.addChild(primitive);

    Location loc = sphere.loadLocation();
    Rotation rot = sphere.loadRotation();

    setLocRot(loc, rot, tg);

    return tg;
  }

  /**
   * 与えられたconeインスタンスのプリミティブを作成する。
   * 
   * @param cone コーン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static MyTransformGroup create(XMLCone cone) {
    int flag = Primitive.GENERATE_NORMALS;
    if (cone.loadDiv() < 3) cone.setDiv(10);
    Primitive primitive = new Cone(cone.loadR(), cone.loadHeight(), flag, cone.loadDiv(), cone.loadDiv(), null);
    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(cone.loadColor()));
    if (cone.hasTransparent()) {
      if (cone.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    MyTransformGroup tg = new MyTransformGroup();
    tg.addChild(primitive);

    Location loc = cone.loadLocation();
    Rotation rot = cone.loadRotation();

    setLocRot(loc, rot, tg);

    return tg;
  }

  /**
   * 与えられたconnectorインスタンスのプリミティブを作成する。
   * 
   * @param connector コネクタ
   * @return tg 与えられたファイルで出来るプリミティブ (XMLConnector クラス名 org.mklab.mikity.xml 変数名)
   */
  public static MyTransformGroup create(XMLConnector connector) {
    int flag = Primitive.GENERATE_NORMALS;
    Primitive primitive = new Cylinder(connector.loadR(), connector.loadHeight(), flag, connector.loadDiv(), connector.loadDiv(), null);
    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(connector.loadColor()));
    if (connector.hasTransparent()) {
      if (connector.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    MyTransformGroup tg = new MyTransformGroup();
    tg.addChild(primitive);
    Location loc = connector.loadLocation();
    Rotation rot = connector.loadRotation();

    setLocRot(loc, rot, tg);

    return tg;
  }

  /**
   * 与えられたTrianglePolygonインスタンスのプリミティブを作成する。
   * 
   * @param triangle 三角形ポリゴン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLTrianglePolygon クラス名 org.mklab.mikity.xml 変数名)
   */
  public static MyTransformGroup create(XMLTrianglePolygon triangle) {
    Point3f[] vertices = new Point3f[3];

    for (int i = 0; i < vertices.length; i++) {
      vertices[i] = new Point3f(triangle.loadPointLocationX(i), triangle.loadPointLocationY(i), triangle.loadPointLocationZ(i));
    }

    Vector3f[] normals = triangle.loadNormalVector();

    int[] indices = {0, 1, 2};
    Color3f[] colors = new Color3f[3];
    colors[0] = ColorConstant.getColor(triangle.loadColor());
    colors[1] = ColorConstant.getColor(triangle.loadColor());
    colors[2] = ColorConstant.getColor(triangle.loadColor());
    int[] colorIndices = {0, 1, 2};

    IndexedTriangleArray geometry = new IndexedTriangleArray(vertices.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS, indices.length);
    geometry.setCoordinates(0, vertices);
    // 法線配列追加
    geometry.setNormals(0, normals);
    geometry.setCoordinateIndices(0, indices);
    geometry.setColors(0, colors);
    geometry.setColorIndices(0, colorIndices);
    MyTransformGroup tg = new MyTransformGroup();
    Shape3D shape = new Shape3D(geometry);

    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(triangle.loadColor()));
    if (triangle.hasTransparent()) {
      if (triangle.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    shape.setAppearance(appearance);

    tg.addChild(shape);
    Location loc = triangle.loadLocation();
    Rotation rot = triangle.loadRotation();
    Matrix4f matrix = triangle.loadMatrix();
    setMatrix(matrix, tg);
    setLocRot(loc, rot, tg);
    return tg;
  }

  /**
   * 与えられたQuadPolygonインスタンスのプリミティブを作成する。
   * 
   * @param quad 四角形ポリゴン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLQuadPolygon クラス名 org.mklab.mikity.xml 変数名)
   */
  public static MyTransformGroup create(XMLQuadPolygon quad) {
    Point3f[] vertices = new Point3f[4];

    for (int i = 0; i < vertices.length; i++) {
      vertices[i] = new Point3f(quad.loadPointLocationX(i), quad.loadPointLocationY(i), quad.loadPointLocationZ(i));
    }

    Vector3f[] normals = quad.loadNormalVector();

    int[] indices = {0, 1, 2, 3};
    Color3f[] colors = new Color3f[4];
    colors[0] = ColorConstant.getColor(quad.loadColor());
    colors[1] = ColorConstant.getColor(quad.loadColor());
    colors[2] = ColorConstant.getColor(quad.loadColor());
    colors[3] = ColorConstant.getColor(quad.loadColor());
    int[] colorIndices = {0, 1, 2, 3};

    IndexedQuadArray geometry = new IndexedQuadArray(vertices.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS, indices.length);
    geometry.setCoordinates(0, vertices);
    // 法線配列追加
    geometry.setNormals(0, normals);
    geometry.setCoordinateIndices(0, indices);
    geometry.setColors(0, colors);
    geometry.setColorIndices(0, colorIndices);
    MyTransformGroup tg = new MyTransformGroup();
    Shape3D shape = new Shape3D(geometry);

    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(quad.loadColor()));
    if (quad.hasTransparent()) {
      if (quad.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    shape.setAppearance(appearance);

    tg.addChild(shape);
    Location loc = quad.loadLocation();
    Rotation rot = quad.loadRotation();
    Matrix4f matrix = quad.loadMatrix();
    setMatrix(matrix, tg);
    setLocRot(loc, rot, tg);
    return tg;
  }

  /**
   * 単位を考慮して、渡されたtgに 回転移動、平行移動を行って返す
   * 
   * @param loc
   * @param rot
   * @param tg
   * @return tg
   */
  private static MyTransformGroup setLocRot(Location loc, Rotation rot, MyTransformGroup tg) {
    if (rot != null) {
      if (radian == false) {
        // radian表記でない場合
        tg.mulRotation(new AxisAngle4f(1.0f, 0.0f, 0.0f, (float)Math.toRadians(rot.loadXrotate())));
        tg.mulRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, (float)Math.toRadians(rot.loadYrotate())));
        tg.mulRotation(new AxisAngle4f(0.0f, 0.0f, 1.0f, (float)Math.toRadians(rot.loadZrotate())));
      } else {
        tg.mulRotation(new AxisAngle4f(1.0f, 0.0f, 0.0f, rot.loadXrotate()));
        tg.mulRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, rot.loadYrotate()));
        tg.mulRotation(new AxisAngle4f(0.0f, 0.0f, 1.0f, rot.loadZrotate()));
      }
    }
    if (loc != null) {
      tg.mulTranslation(new Vector3f(loc.loadX() / scale, loc.loadY() / scale, loc.loadZ() / scale));
    }
    return tg;
  }

  /**
   * 単位を考慮して、渡されたtgに 回転移動、平行移動を行って返す
   * 
   * @param loc
   * @param rot
   * @param tg
   * @return tg
   */
  private static MyTransformGroup setMatrix(Matrix4f matrix, MyTransformGroup tg) {
    if (matrix != null) {
      Matrix3f mat3 = new Matrix3f();
      mat3.setM00(matrix.getM00());
      mat3.setM01(matrix.getM01());
      mat3.setM02(matrix.getM02());
      mat3.setM10(matrix.getM10());
      mat3.setM11(matrix.getM11());
      mat3.setM12(matrix.getM12());
      mat3.setM20(matrix.getM20());
      mat3.setM21(matrix.getM21());
      mat3.setM22(matrix.getM22());
      tg.mulRotation(mat3);
      tg.mulTranslation(new Vector3f(matrix.getM03() / matrix.getScale(), matrix.getM13() / matrix.getScale(), matrix.getM23() / matrix.getScale()));
    }
    return tg;
  }

  /**
   * 色を設定したMaterialを取得する
   * 
   * @param colorName 色名
   * @return Material 色を設定したMaterial
   */
  private static Material getMaterial(String colorName) {
    Material material = new Material();
    material.setDiffuseColor(new Color3f(ColorConstant.getColor(colorName)));
    return material;
  }

  /**
   * モデルを作成する際のスケールを読み込む
   * 
   * @param scal プリミティブの大きさ
   * @param rad 角度
   */
  public static void setScale(int scal, boolean rad) {
    scale = scal;
    radian = rad;
  }
}
