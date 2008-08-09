/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLConnector;

/**
 * Coneにコネクタを表示させるためのクラス
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class ConeConnector {
	
	/** コネクタの所属するグループ*/
	private ConnectorGroup group;
	
	/** コネクタ1~2の座標*/
	private XMLConnector cnct[] = new XMLConnector[2];
	
	private TurnLocation tl;
	
	/**
	 * コンストラクター
	 */
	public ConeConnector(){	
		group = new ConnectorGroup();
	}
	
	/**
	 * 円錐プリミティブにコネクタを追加する。
	 * @param r 円錐の半径
	 * @param h 円錐の高さ
	 * @param loc 円錐の座標
	 * @param rot 円錐の回転
	 */
	public void createConeConnector(float r, float h,Location loc, Rotation rot) {
		for(int i=0; i<cnct.length; i++){
			cnct[i] = new XMLConnector();
			cnct[i].setNum(i+1);
			if(i == 0 || i ==1){
				cnct[i].checkParameter(h);
			}else if(i == 2 || i ==3 || i == 4 || i == 5){
				cnct[i].checkParameterR(r);
			}
		}
		
		cnct[0].setLengthToCenter(h*0.5f);
		cnct[1].setLengthToCenter(h*0.5f);
		
		Group cGroup = group.createConnectorGroup();
		
		cnct[0].setConnectorRotation(rot.loadXrotate(),rot.loadYrotate(),rot.loadZrotate());
		cnct[1].setConnectorRotation(rot.loadXrotate()+180.0f,rot.loadYrotate(),rot.loadZrotate());
		
		tl = new TurnLocation(r,h/2.0f,r,rot.loadXrotate(),rot.loadYrotate(),rot.loadZrotate());
		
		cnct[0].setConnectorLocation(loc.loadX()+tl.getNewLocation2().loadX(),loc.loadY()+tl.getNewLocation2().loadY(),loc.loadZ()+tl.getNewLocation2().loadZ());
		cnct[1].setConnectorLocation(loc.loadX()-tl.getNewLocation2().loadX(),loc.loadY()-tl.getNewLocation2().loadY(),loc.loadZ()-tl.getNewLocation2().loadZ());
		
		for(int x=0; x<cnct.length; x++){
			cGroup.addXMLConnector(cnct[x]);
		}
	}
}
