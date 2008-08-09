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
 * Boxにコネクタを表示させるためのクラス
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class BoxConnector {
	
	/** コネクタの所属するグループ*/
	private ConnectorGroup group;
	
	/** コネクタ1~6*/
	private XMLConnector cnct[] = new XMLConnector[6];
	
	private TurnLocation tl;
	
	/**
	 * コンストラクター
	 */
	public BoxConnector(){
		group = new ConnectorGroup(); 
	}
	
	/**
	 * 直方体プリミティブにコネクタを追加する。
	 * @param xSize 直方体の幅
	 * @param ySize 直方体の高さ
	 * @param zSize 直方体の奥行き
	 * @param loc 直方体の座標
	 * @param rot 直方体の回転
	 */
	public void createBoxConnector(float xSize, float ySize, float zSize,Location loc, Rotation rot) {
		for(int i=0; i<cnct.length; i++){
			cnct[i] = new XMLConnector();
			cnct[i].setNum(i+1);
			if(i == 0 || i ==1){
				cnct[i].checkParameter(ySize);
			}else if(i == 2 || i ==3){
				cnct[i].checkParameter(xSize);
			}else if(i == 4 || i ==5){
				cnct[i].checkParameter(zSize);
			}
		}
		
		cnct[0].setLengthToCenter(ySize*0.5f);
		cnct[1].setLengthToCenter(ySize*0.5f);
		cnct[2].setLengthToCenter(xSize*0.5f);
		cnct[3].setLengthToCenter(xSize*0.5f);
		cnct[4].setLengthToCenter(zSize*0.5f);
		cnct[5].setLengthToCenter(zSize*0.5f);
		
		Group cGroup = group.createConnectorGroup();
		
		cnct[0].setConnectorRotation(rot.loadXrotate(),rot.loadYrotate(),rot.loadZrotate());
		cnct[1].setConnectorRotation(rot.loadXrotate()+180.0f,rot.loadYrotate(),rot.loadZrotate());
		cnct[2].setConnectorRotation(-rot.loadYrotate(),0.0f,rot.loadZrotate()-90.0f);
		cnct[3].setConnectorRotation(rot.loadYrotate(),0.0f,rot.loadZrotate()+90.0f);
		cnct[4].setConnectorRotation(rot.loadXrotate()+90.0f,rot.loadYrotate(),rot.loadZrotate());
		cnct[5].setConnectorRotation(rot.loadXrotate()-90.0f,rot.loadYrotate(),rot.loadZrotate());
		
		tl = new TurnLocation(xSize/2.0f,ySize/2.0f,zSize/2.0f,rot.loadXrotate(),rot.loadYrotate(),rot.loadZrotate());
			
		cnct[0].setConnectorLocation(loc.loadX()+tl.getNewLocation2().loadX(),loc.loadY()+tl.getNewLocation2().loadY(),loc.loadZ()+tl.getNewLocation2().loadZ());
		cnct[1].setConnectorLocation(loc.loadX()-tl.getNewLocation2().loadX(),loc.loadY()-tl.getNewLocation2().loadY(),loc.loadZ()-tl.getNewLocation2().loadZ());
		cnct[2].setConnectorLocation(loc.loadX()+tl.getNewLocation1().loadX(),loc.loadY()+tl.getNewLocation1().loadY(),loc.loadZ()+tl.getNewLocation1().loadZ());
		cnct[3].setConnectorLocation(loc.loadX()-tl.getNewLocation1().loadX(),loc.loadY()-tl.getNewLocation1().loadY(),loc.loadZ()-tl.getNewLocation1().loadZ());
		cnct[4].setConnectorLocation(loc.loadX()+tl.getNewLocation3().loadX(),loc.loadY()+tl.getNewLocation3().loadY(),loc.loadZ()+tl.getNewLocation3().loadZ());
		cnct[5].setConnectorLocation(loc.loadX()-tl.getNewLocation3().loadX(),loc.loadY()-tl.getNewLocation3().loadY(),loc.loadZ()-tl.getNewLocation3().loadZ());
		
		for(int x=0; x<cnct.length; x++){
			cGroup.addXMLConnector(cnct[x]);
		}
	}
}
