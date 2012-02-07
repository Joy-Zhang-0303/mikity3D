package org.mklab.mikity.jogl;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglPrimitiveFactory {
  
  /**
   * グループを作る。
   * 
   * @param group グループ
   * @return tg トランスフォームグループ
   */
  public static JoglTransformGroup create(Group group) {
    
    return JoglTransformGroupFactory.create(group);
  }

  /**
   * 与えられたboxインスタンスのプリミティブを作成する。
   * 
   * @param box ボックス
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLBox box) {
    float x = box.loadXsize()*10f;
    float y = box.loadYsize()*10f;
    float z = box.loadZsize()*10f;
    JoglTransformGroup tg = new JoglTransformGroup();
    tg.setCoordinate(new JoglLocation());
    JoglBox child = new JoglBox();
    child.setSize(x,y,z);
    tg.addChild(child);

    return tg;
  }

}
