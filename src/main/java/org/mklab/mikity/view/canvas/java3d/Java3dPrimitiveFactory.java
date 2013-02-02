/*
 * Created on 2004/11/25
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.canvas.java3d;

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
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.Location;
import org.mklab.mikity.model.xml.model.Rotation;
import org.mklab.mikity.model.xml.model.XMLBox;
import org.mklab.mikity.model.xml.model.XMLCone;
import org.mklab.mikity.model.xml.model.XMLCylinder;
import org.mklab.mikity.model.xml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.model.XMLSphere;
import org.mklab.mikity.model.xml.model.XMLTrianglePolygon;
import org.mklab.mikity.util.Color3;
import org.mklab.mikity.util.ColorConstant;
import org.mklab.mikity.util.Matrix4;
import org.mklab.mikity.util.Util;
import org.mklab.mikity.util.Vector3;

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

  /** 透明度の設定 */
  private static TransparencyAttributes transAttr = new TransparencyAttributes(TransparencyAttributes.NICEST, 0.7f);

  /**
   * グループを生成します。
   * 
   * @param group グループ
   * @return tg トランスフォームグループ
   */
  public static Java3dTransformGroup create(Group group) {
    return new Java3dTransformGroupFactory().create(group);
  }

  /**
   * 与えられたboxインスタンスのプリミティブを生成します。
   * 
   * @param box ボックス
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLBox box) {
    final int flag = Primitive.GENERATE_NORMALS;
    final Primitive primitive = new Box(box.getXsize() / (Util.scale * 2), box.getYsize() / (Util.scale * 2), box.getZsize() / (Util.scale * 2), flag, null);
    final Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(box.getColor()));
    if (box.hasTransparent()) {
      if (box.getTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    final Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    final Location location = box.getLocation();
    final Rotation rotation = box.getRotation();

    transform(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたcylinderインスタンスのプリミティブを生成します。
   * 
   * @param cylinder シリンダー
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLCylinder cylinder) {
    final int flag = Primitive.GENERATE_NORMALS;
    if (cylinder.getDiv() < 3) {
      cylinder.setDiv(10);
    }
    final Primitive primitive = new Cylinder(cylinder.getRadius(), cylinder.getHeight(), flag, cylinder.getDiv(), cylinder.getDiv(), null);
    final Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(cylinder.getColor()));
    if (cylinder.hasTransparent()) {
      if (cylinder.getTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    final Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    final Location location = cylinder.getLocation();
    final Rotation rotation = cylinder.getRotation();

    transform(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたsphereインスタンスのプリミティブを作成する。
   * 
   * @param sphere スフィア
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLSphere sphere) {
    final int flag = Primitive.GENERATE_NORMALS;
    if (sphere.getDiv() < 3) {
      sphere.setDiv(10);
    }
    final Primitive primitive = new Sphere(sphere.getRadius(), flag, sphere.getDiv(), null);
    final Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(sphere.getColor()));
    if (sphere.hasTransparent()) {
      if (sphere.getTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    final Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    final Location location = sphere.getLocation();
    final Rotation rotation = sphere.getRotation();

    transform(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたconeインスタンスのプリミティブを作成する。
   * 
   * @param cone コーン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLCone cone) {
    final int flag = Primitive.GENERATE_NORMALS;
    if (cone.getDiv() < 3) {
      cone.setDiv(10);
    }
    final Primitive primitive = new Cone(cone.getRadisu(), cone.getHeight(), flag, cone.getDiv(), cone.getDiv(), null);
    final Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(cone.getColor()));
    if (cone.hasTransparent()) {
      if (cone.getTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    primitive.setAppearance(appearance);
    final Java3dTransformGroup tg = new Java3dTransformGroup();
    tg.addChild(primitive);

    final Location location = cone.getLocation();
    final Rotation rotation = cone.getRotation();

    transform(location, rotation, tg);

    return tg;
  }

  /**
   * 与えられたTrianglePolygonインスタンスのプリミティブを作成する。
   * 
   * @param triangle 三角形ポリゴン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLTrianglePolygon クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLTrianglePolygon triangle) {
    final Point3f[] vertices = new Point3f[3];

    for (int i = 0; i < vertices.length; i++) {
      vertices[i] = new Point3f(triangle.loadPointLocationX(i), triangle.loadPointLocationY(i), triangle.loadPointLocationZ(i));
    }

    final Vector3[] normals = triangle.loadNormalVector();
    final Vector3f[] normalsF = new Vector3f[3];
    normalsF[0] = new Vector3f(normals[0].getX(), normals[0].getY(), normals[0].getZ());
    normalsF[1] = new Vector3f(normals[1].getX(), normals[1].getY(), normals[1].getZ());
    normalsF[2] = new Vector3f(normals[2].getX(), normals[2].getY(), normals[2].getZ());

    final int[] indices = {0, 1, 2};
    final Color3[] colors = new Color3[3];
    colors[0] = ColorConstant.getColor(triangle.loadColor());
    colors[1] = ColorConstant.getColor(triangle.loadColor());
    colors[2] = ColorConstant.getColor(triangle.loadColor());
    final Color3f[] colorsF = new Color3f[4];
    colorsF[0] = new Color3f(colors[0].getR(), colors[0].getG(), colors[0].getB());
    colorsF[1] = new Color3f(colors[1].getR(), colors[1].getG(), colors[1].getB());
    colorsF[2] = new Color3f(colors[2].getR(), colors[2].getG(), colors[2].getB());
    colorsF[3] = new Color3f(colors[3].getR(), colors[3].getG(), colors[3].getB());
    
    final int[] colorIndices = {0, 1, 2};

    IndexedTriangleArray geometry = new IndexedTriangleArray(vertices.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS, indices.length);
    geometry.setCoordinates(0, vertices);
    // 法線配列追加
    geometry.setNormals(0, normalsF);
    geometry.setCoordinateIndices(0, indices);
    geometry.setColors(0, colorsF);
    geometry.setColorIndices(0, colorIndices);
    final Java3dTransformGroup tg = new Java3dTransformGroup();
    final Shape3D shape = new Shape3D(geometry);

    final Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(triangle.loadColor()));
    if (triangle.hasTransparent()) {
      if (triangle.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    shape.setAppearance(appearance);

    tg.addChild(shape);
    final Location location = triangle.loadLocation();
    final Rotation rotation = triangle.loadRotation();
    final Matrix4 matrix = triangle.loadMatrix();
    transform(matrix, tg);
    transform(location, rotation, tg);
    return tg;
  }

  /**
   * 与えられたQuadPolygonインスタンスのプリミティブを作成する。
   * 
   * @param quad 四角形ポリゴン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLQuadPolygon クラス名 org.mklab.mikity.xml 変数名)
   */
  public static Java3dTransformGroup create(XMLQuadPolygon quad) {
    final Point3f[] vertices = new Point3f[4];

    for (int i = 0; i < vertices.length; i++) {
      vertices[i] = new Point3f(quad.loadPointLocationX(i), quad.loadPointLocationY(i), quad.loadPointLocationZ(i));
    }

    final Vector3[] normals = quad.loadNormalVector();
    final Vector3f[] normalsF = new Vector3f[4];
    normalsF[0] = new Vector3f(normals[0].getX(), normals[0].getY(), normals[0].getZ());
    normalsF[1] = new Vector3f(normals[1].getX(), normals[1].getY(), normals[1].getZ());
    normalsF[2] = new Vector3f(normals[2].getX(), normals[2].getY(), normals[2].getZ());
    normalsF[3] = new Vector3f(normals[3].getX(), normals[3].getY(), normals[3].getZ());

    final int[] indices = {0, 1, 2, 3};
    final Color3[] colors = new Color3[4];
    colors[0] = ColorConstant.getColor(quad.loadColor());
    colors[1] = ColorConstant.getColor(quad.loadColor());
    colors[2] = ColorConstant.getColor(quad.loadColor());
    colors[3] = ColorConstant.getColor(quad.loadColor());
    final Color3f[] colorsF = new Color3f[4];
    colorsF[0] = new Color3f(colors[0].getR(), colors[0].getG(), colors[0].getB());
    colorsF[1] = new Color3f(colors[1].getR(), colors[1].getG(), colors[1].getB());
    colorsF[2] = new Color3f(colors[2].getR(), colors[2].getG(), colors[2].getB());
    colorsF[3] = new Color3f(colors[3].getR(), colors[3].getG(), colors[3].getB());
    
    final int[] colorIndices = {0, 1, 2, 3};

    final IndexedQuadArray geometry = new IndexedQuadArray(vertices.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS, indices.length);
    geometry.setCoordinates(0, vertices);
    // 法線配列追加
    geometry.setNormals(0, normalsF);
    geometry.setCoordinateIndices(0, indices);
    geometry.setColors(0, colorsF);
    geometry.setColorIndices(0, colorIndices);
    final Java3dTransformGroup tg = new Java3dTransformGroup();
    final Shape3D shape = new Shape3D(geometry);

    final Appearance appearance = new Appearance();
    appearance.setMaterial(getMaterial(quad.loadColor()));
    if (quad.hasTransparent()) {
      if (quad.loadTransparent() == true) {
        appearance.setTransparencyAttributes(transAttr);
      }
    }
    shape.setAppearance(appearance);

    tg.addChild(shape);
    final Location location = quad.loadLocation();
    final Rotation rotation = quad.loadRotation();
    final Matrix4 matrix = quad.loadMatrix();
    transform(matrix, tg);
    transform(location, rotation, tg);
    return tg;
  }

  /**
   * 単位を考慮して、渡されたtgに 回転変換と平行変換を適用します。
   * 
   * @param location 平行移動
   * @param rotation 回転移動
   * @param tg
   */
  private static void transform(Location location, Rotation rotation, Java3dTransformGroup tg) {
    if (rotation != null) {
      if (Util.radian == false) {
        tg.rotate(new AxisAngle4f(1.0f, 0.0f, 0.0f, (float)Math.toRadians(rotation.getXrotation())));
        tg.rotate(new AxisAngle4f(0.0f, 1.0f, 0.0f, (float)Math.toRadians(rotation.getYrotation())));
        tg.rotate(new AxisAngle4f(0.0f, 0.0f, 1.0f, (float)Math.toRadians(rotation.loadZrotate())));
      } else {
        tg.rotate(new AxisAngle4f(1.0f, 0.0f, 0.0f, rotation.getXrotation()));
        tg.rotate(new AxisAngle4f(0.0f, 1.0f, 0.0f, rotation.getYrotation()));
        tg.rotate(new AxisAngle4f(0.0f, 0.0f, 1.0f, rotation.loadZrotate()));
      }
    }
    if (location != null) {
      tg.translate(new Vector3f(location.getX() / Util.scale, location.getY() / Util.scale, location.loadZ() / Util.scale));
    }
  }

  /**
   * 単位を考慮して、渡されたtgに 回転変換と平行変換を適用します。
   * 
   * @param tg
   */
  private static void transform(Matrix4 matrix, Java3dTransformGroup tg) {
    if (matrix != null) {
      final Matrix3f matrix3 = new Matrix3f();
      matrix3.setElement(0, 0, matrix.getElement(0, 3));
      matrix3.setElement(0, 1, matrix.getElement(0, 1));
      matrix3.setElement(0, 2, matrix.getElement(0, 2));
      matrix3.setElement(1, 0, matrix.getElement(1, 0));
      matrix3.setElement(1, 1, matrix.getElement(1, 1));
      matrix3.setElement(1, 2, matrix.getElement(1, 2));
      matrix3.setElement(2, 0, matrix.getElement(2, 0));
      matrix3.setElement(2, 1, matrix.getElement(2, 1));
      matrix3.setElement(2, 2, matrix.getElement(2, 2));
      tg.rotate(matrix3);
      tg.translate(new Vector3f(matrix.getElement(0, 3) / matrix.getScale(), matrix.getElement(1, 3) / matrix.getScale(), matrix.getElement(2, 3) / matrix.getScale()));
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
    final Color3 color = ColorConstant.getColor(colorName);
    final Color3f colorF = new Color3f(color.getR(), color.getG(), color.getB());
    material.setDiffuseColor(colorF);
    return material;
  }
}
