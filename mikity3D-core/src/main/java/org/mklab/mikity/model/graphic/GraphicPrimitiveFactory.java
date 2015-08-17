/*
 * Created on 2015/08/17
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;


/**
 * {@link GraphicPrimitive}のファクトリクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/17
 */
public class GraphicPrimitiveFactory {
  /**
   * 与えられたモデルを含むをプリミティブを生成します。
   * 
   * @param model モデル
   * @return 与えられたモデルを含むプリミティブ
   */
  public static GraphicPrimitive create(PrimitiveModel model) {
    if (model instanceof BoxModel) {
      return new BoxPrimitive((BoxModel)model);
    } 
    
    if (model instanceof CylinderModel) {
      return new CylinderPrimitive((CylinderModel)model);
    } 
    
    if (model instanceof ConeModel) {
      return new ConePrimitive((ConeModel)model);
    } 
    
    if (model instanceof SphereModel) {
      return new SpherePrimitive((SphereModel)model);
    } 
    
    if (model instanceof TrianglePolygonModel) {
      return new TrianglePolygonPrimitive((TrianglePolygonModel)model);
    } 

    if (model instanceof QuadPolygonModel) {
      return new QuadPolygonPrimitive((QuadPolygonModel)model);
    } 

    if (model instanceof NullModel) {
      return new NullPrimitive((NullModel)model);
    } 

    throw new IllegalArgumentException(model.getClass().toString());
  }
}
