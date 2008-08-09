/*
 * Created on 2004/11/24
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.internal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Material;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import org.mklab.mikity.MyTransformGroup;
import org.mklab.mikity.MyTriPolygons;

import com.sun.j3d.utils.geometry.Primitive;


/**
 * プリミティブの生成装置となるクラス
 * @author miki
 * @version $Revision: 1.3 $.2004/11/24
 */
public class PrimitiveGenerator {

  /**
   * プリミティブを取得する。
   * @return null
   */
  public Primitive getPrimitive() {
    return null;
  }

  /**
   * オブジェクトを取得する。
   * @param filename
   * @return tg
   */
  public MyTransformGroup getObject(String filename) {
    MyTransformGroup tg = new MyTransformGroup();

    boolean faceFlag = false;

    List<Point3d> pointList = new ArrayList<Point3d>();

    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

      String line = br.readLine();
      while (line != null) {
        if (line.startsWith("face")) {
          faceFlag = true;
        } else if (line.startsWith("endf")) {
          faceFlag = false;
          Point3d[] p = new Point3d[pointList.size()];
          pointList.toArray(p);
          Material matP = new Material();
          matP.setDiffuseColor(new Color3f(1.0f, 0.5f, 0.5f));
          MyTriPolygons polygon = new MyTriPolygons(p, matP, null, MyTriPolygons.RENDERING_ALL);
          tg.addChild(polygon);
          pointList.clear();
        } else if (faceFlag && !line.startsWith("#")) {
          String[] xyz = line.split("\\s");
          double x = Double.parseDouble(xyz[1]);
          double y = Double.parseDouble(xyz[2]);
          double z = Double.parseDouble(xyz[3]);
          pointList.add(new Point3d(x / 1000, y / 1000, z / 1000));
        }
        line = br.readLine();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return tg;
  }
}
