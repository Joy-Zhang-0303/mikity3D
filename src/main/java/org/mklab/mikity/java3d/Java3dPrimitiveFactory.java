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

import org.mklab.mikity.util.ColorConstant;
import org.mklab.mikity.xml.model.Group;
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
 * プリミティブおよびポリゴンの生成クラスするクラスです。
 * 
 * @author miki
 * @version $Revision: 1.21 $.2004/11/30
 */
public class Java3dPrimitiveFactory {

  /** 単位 */
  private static int scale = 1;
  private static boolean radian = true;

  /** 透明度の設定 */
  private static TransparencyAttributes transAttr = new TransparencyAttributes(TransparencyAttributes.NICEST, 0.7f);

  /**
   * グループを作る。
   * 
   * @param group グループ
   * @return tg トランスフォームグループ
   */
  public static Java3dTransformGroup create(Group group) {
    scale = Java3dModelCanvas.scale;
    radian = Java3dModelCanvas.radian;

    return Java3dTransformGroupFactory.create(group);
  }

  /**
   * 与えられたboxインスタンスのプリミティブを作成する。
   * 
   * @param box ボックス
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLBox box) {
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
    Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    // 続けると長くなるので、一度Locationを得る
    Location location = box.loadLocation();
    Rotation rotation = box.loadRotation();

    applyLocRot(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたcylinderインスタンスのプリミティブを作成する。
   * 
   * @param cylinder シリンダー
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLCylinder cylinder) {
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
    Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    Location location = cylinder.loadLocation();
    Rotation rotation = cylinder.loadRotation();

    applyLocRot(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたsphereインスタンスのプリミティブを作成する。
   * 
   * @param sphere スフィア
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLSphere sphere) {
    int flag = Primitive.GENERATE_NORMALS;
    if (sphere.loadDiv() < 3) { 
      sphere.setDiv(10);
    }
    Primitive primitive = new Sphere(sphere.loadR(), flag, sphere.loadDiv(), null);
    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(sphere.loadColor()));
    if (sphere.hasTransparent()) {
      if (sphere.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    Location location = sphere.loadLocation();
    Rotation rotation = sphere.loadRotation();

    applyLocRot(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたconeインスタンスのプリミティブを作成する。
   * 
   * @param cone コーン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLCone cone) {
    int flag = Primitive.GENERATE_NORMALS;
    if (cone.loadDiv() < 3) { 
      cone.setDiv(10);
    }
    Primitive primitive = new Cone(cone.loadR(), cone.loadHeight(), flag, cone.loadDiv(), cone.loadDiv(), null);
    Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(cone.loadColor()));
    if (cone.hasTransparent()) {
      if (cone.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    Location location = cone.loadLocation();
    Rotation rotation = cone.loadRotation();

    applyLocRot(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたconnectorインスタンスのプリミティブを作成する。
   * 
   * @param connector コネクタ
   * @return tg 与えられたファイルで出来るプリミティブ (XMLConnector クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLConnector connector) {
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
    Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);
    Location location = connector.loadLocation();
    Rotation rotation = connector.loadRotation();

    applyLocRot(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたTrianglePolygonインスタンスのプリミティブを作成する。
   * 
   * @param triangle 三角形ポリゴン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLTrianglePolygon クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLTrianglePolygon triangle) {
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
    Java3dTransformGroup tg = new Java3dTransformGroup();
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
    Location location = triangle.loadLocation();
    Rotation rotation = triangle.loadRotation();
    Matrix4f matrix = triangle.loadMatrix();
    applyMatrix(matrix, tg);
    applyLocRot(location, rotation, tg);
    return tg;
  }

  /**
   * 与えられたQuadPolygonインスタンスのプリミティブを作成する。
   * 
   * @param quad 四角形ポリゴン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLQuadPolygon クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLQuadPolygon quad) {
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
    Java3dTransformGroup tg = new Java3dTransformGroup();
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
    Location location = quad.loadLocation();
    Rotation rotation = quad.loadRotation();
    Matrix4f matrix = quad.loadMatrix();
    applyMatrix(matrix, tg);
    applyLocRot(location, rotation, tg);
    return tg;
  }

  /**
   * 単位を考慮して、渡されたtgに 回転移動、平行移動を行って返す
   * 
   * @param loc
   * @param rot
   * @param tg
   */
  private static void applyLocRot(Location loc, Rotation rot, Java3dTransformGroup tg) {
    if (rot != null) {
      if (radian == false) {
        // radian表記でない場合
        tg.rotate(new AxisAngle4f(1.0f, 0.0f, 0.0f, (float)Math.toRadians(rot.loadXrotate())));
        tg.rotate(new AxisAngle4f(0.0f, 1.0f, 0.0f, (float)Math.toRadians(rot.loadYrotate())));
        tg.rotate(new AxisAngle4f(0.0f, 0.0f, 1.0f, (float)Math.toRadians(rot.loadZrotate())));
      } else {
        tg.rotate(new AxisAngle4f(1.0f, 0.0f, 0.0f, rot.loadXrotate()));
        tg.rotate(new AxisAngle4f(0.0f, 1.0f, 0.0f, rot.loadYrotate()));
        tg.rotate(new AxisAngle4f(0.0f, 0.0f, 1.0f, rot.loadZrotate()));
      }
    }
    if (loc != null) {
      tg.translate(new Vector3f(loc.loadX() / scale, loc.loadY() / scale, loc.loadZ() / scale));
    }
  }

  /**
   * 単位を考慮して、渡されたtgに 回転移動、平行移動を行って返す
   * 
   * @param loc
   * @param rot
   * @param tg
   */
  private static void applyMatrix(Matrix4f matrix, Java3dTransformGroup tg) {
    if (matrix != null) {
      Matrix3f mat3 = new Matrix3f();
      mat3.setElement(0,0,matrix.getElement(0,3));
      mat3.setElement(0,1,matrix.getElement(0,1));
      mat3.setElement(0,2,matrix.getElement(0,2));
      mat3.setElement(1,0,matrix.getElement(1,0));
      mat3.setElement(1,1,matrix.getElement(1,1));
      mat3.setElement(1,2,matrix.getElement(1,2));
      mat3.setElement(2,0,matrix.getElement(2,0));
      mat3.setElement(2,1,matrix.getElement(2,1));
      mat3.setElement(2,2,matrix.getElement(2,2));
      tg.rotate(mat3);
      tg.translate(new Vector3f(matrix.getElement(0,3) / matrix.getScale(), matrix.getElement(1,33) / matrix.getScale(), matrix.getElement(2,3) / matrix.getScale()));
    }
  }

  /**
   * 色を設定したMaterialを返します。
   * 
   * @param colorName 色名
   * @return Material 色を設定したMaterial
   */
  private static Material getMaterial(String colorName) {
    final Material material = new Material();
    material.setDiffuseColor(new Color3f(ColorConstant.getColor(colorName)));
    return material;
  }

  /**
   * モデルを作成する際のスケールを設定します。
   * 
   * @param scal プリミティブの大きさ
   * @param rad 角度
   */
  public static void setScale(int scal, boolean rad) {
    scale = scal;
    radian = rad;
  }
}
