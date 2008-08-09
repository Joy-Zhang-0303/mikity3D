/*
 * $Id: ConnectorSelect.java,v 1.3 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.mklab.mikity.gui.Modeler;
import org.mklab.mikity.gui.SceneGraphTree;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLConnector;

/**
 * 使用するコネクタを選択するクラス
 * @author SHOGO
 * @version $Revision: 1.3 $. 2006/07/04
 */
public class ConnectorSelect {

	private Composite composite;
	private SceneGraphTree tree;
	private Modeler modeler;
	
	/**コネクタN、Sであるかの真偽*/
	private boolean _hasS =false;
	private boolean _hasN =false;
	
	/**コネクタN*/
	private XMLConnector connectorN;
	/**コネクタS*/
	private XMLConnector connectorS;
	private XMLConnector connector;
	
	/**コネクタN,Sを所有するグループ*/
	public Group targetGroupN;
	public Group targetGroupS;
	
	/**
	 * コンストラクタ
	 * @param composite
	 * @param tree
	 * @param modeler
	 */
	public ConnectorSelect(Composite composite, SceneGraphTree tree, Modeler modeler){
		this.composite = composite;
		this.tree = tree;
		this.modeler = modeler;
	}
	
	/**
	 * 現在右クリックしている物をコネクタN,Sに設定する。
	 * 右クリックしているものがコネクタのときのみ、設定を行う。
	 * @param targetObj　現在右クリックしている物
	 * @param root　ルート
	 * @param xmlTree　シーングラフツリー
	 * @param targetGroup　現在右クリックしている物を所有するグループ
	 */
	public void select(Object targetObj, Group root,Tree xmlTree, Group targetGroup){
		//
		//ツリー全体からコネクタのフラグの状態を検索する記述
		//
		if(targetObj instanceof XMLConnector){
			connector = (XMLConnector) targetObj;
			

				if(_hasN == true){
					if(_hasS == false){
						String flag = connector.loadFlag();
						if(flag == "-"){
							selectConnectorS(connector,root,xmlTree,targetGroup);	
						}else if(flag == "N"){
							MessageBox mesBox = new MessageBox(composite.getShell(), SWT.OK| SWT.ICON_INFORMATION);
							mesBox.setMessage("そのコネクタはコネクタNに指定されています");
							mesBox.setText("確認");
							mesBox.open();
						}
					}else{
						MessageBox mesBox = new MessageBox(composite.getShell(), SWT.OK| SWT.ICON_INFORMATION);
						mesBox.setMessage("コネクタは既に決定済みです");
						mesBox.setText("確認");
						mesBox.open();
					}	
				}else{
					selectConnectorN(connector,root,xmlTree,targetGroup);
				}
		}else{
			MessageBox mesBox = new MessageBox(composite.getShell(), SWT.OK| SWT.ICON_INFORMATION);
			mesBox.setMessage("これはコネクタではありません");
			mesBox.setText("確認");
			mesBox.open();
		}
	}
	
	/**
	 * 現在クリックしているコネクタをコネクタNに設定する
	 * @param cylinder　コネクタ
	 * @param root　ルート
	 * @param xmlTree　シーングラフツリー
	 * @param targetGroup　現在右クリックしているコネクタを所有するグループ
	 */
	private void selectConnectorN(XMLConnector connector,Group root,Tree xmlTree, Group targetGroup){
		_hasN = true;
		connector.setFlag("N");
		connectorN = connector;
		
		PrimitiveConnector pConnector = new PrimitiveConnector();
		Group groupN = pConnector.createConnectorNGroup();
		groupN.addXMLConnector(connector);
		targetGroupN = groupN;
		root.removeGroup(targetGroup);
		xmlTree.getSelection()[0].dispose();
		tree.fillTree();
		modeler.createViewer();
	}
	
	/**
	 * 現在クリックしているコネクタをコネクタSに設定する
	 * @param cylinder　コネクタ
	 * @param root　ルート
	 * @param xmlTree　シーングラフツリー
	 * @param targetGroup　現在右クリックしているコネクタを所有するグループ
	 */
	private void selectConnectorS(XMLConnector connector, Group root,Tree xmlTree, Group targetGroup){
		_hasS = true;
		connector.setFlag("S");
		connectorS = connector;
		
		PrimitiveConnector pConnector = new PrimitiveConnector();
		Group groupS = pConnector.createConnectorSGroup();
		groupS.addXMLConnector(connector);
		targetGroupS = groupS;
		root.removeGroup(targetGroup);
		xmlTree.getSelection()[0].dispose();
		tree.fillTree();
		modeler.createViewer();
	}
	
	/**
	 * コネクタNを選択する
	 * @param connector　コネクタ
	 */
	public void setConnectorN(XMLConnector connector){
		connectorN = connector;
		_hasN=true;
	}
	
	/**
	 * コネクタSを選択する
	 * @param connector　コネクタ
	 */
	public void setConnectorS(XMLConnector connector){
		connectorS = connector;
		_hasS=true;
	}
	
	/**
	 * コネクタNを返す
	 * @return　connectorN　コネクタN
	 */
	public XMLConnector getConnectorN(){
		return connectorN;
	}
	
	/**
	 * コネクタSを返す
	 * @return　connectorS　コネクタS
	 */
	public XMLConnector getConnectorS(){
		return connectorS;
	}
	
	public String getTreeConnectorFlag(){
		String flag ="-";
		//
		//ツリー全体からコネクタのフラグの状態を検索する記述
		if(_hasN == false){
			flag="N";
		}else if(_hasN == true && _hasS == false){
			flag="S";
		}
		return flag;
	}
	
	/**
	 * 選択しているコネクタをデフォルトの状態に戻す
	 * @param root　ルート
	 */
	public void reset(Group root){
		root.removeGroup(targetGroupN);
		root.removeGroup(targetGroupS);
		tree.fillTree();
		modeler.createViewer();
		_hasN = false;
		_hasS = false;
	}
}
