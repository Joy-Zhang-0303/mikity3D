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
import org.mklab.mikity.gui.AbstractModeler;
import org.mklab.mikity.gui.SceneGraphTree;
import org.mklab.mikity.xml.JamastModel;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLConnector;


/**
 * 使用するコネクタを選択するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $. 2006/07/04
 */
public class ConnectorSelector {

  private Composite composite;
  private SceneGraphTree tree;
  private AbstractModeler modeler;

  /** コネクタN、Sであるかの真偽 */
  private boolean hasSouth = false;
  private boolean hasNorth = false;

  /** コネクタN */
  private XMLConnector connectorNorth;
  /** コネクタS */
  private XMLConnector connectorSouth;
  
  private XMLConnector connector;

  /** コネクタN,Sを所有するグループ */
  private Group targetGroupNorth;
  /** */
  private Group targetGroupSouth;

  /**
   * コンストラクタ
   * 
   * @param composite コンポジット
   * @param tree キー
   * @param modeler モデラー
   */
  public ConnectorSelector(Composite composite, SceneGraphTree tree, AbstractModeler modeler) {
    this.composite = composite;
    this.tree = tree;
    this.modeler = modeler;
  }

  /**
   * 現在右クリックしている物をコネクタN,Sに設定します。 右クリックしているものがコネクタのときのみ、設定を行います。
   * 
   * @param targetObj 現在右クリックしている物
   * @param root ルート
   * @param xmlTree シーングラフツリー
   * @param targetGroup 現在右クリックしている物を所有するグループ
   */
  public void select(Object targetObj, Group root, Tree xmlTree, Group targetGroup) {
    //
    // ツリー全体からコネクタのフラグの状態を検索する記述
    //
    if (targetObj instanceof XMLConnector) {
      this.connector = (XMLConnector)targetObj;

      if (this.hasNorth == true) {
        if (this.hasSouth == false) {
          String flag = this.connector.loadFlag();
          if (flag == "-") { //$NON-NLS-1$
            selectConnectorSouth(this.connector, root, xmlTree, targetGroup);
          } else if (flag == "N") { //$NON-NLS-1$
            MessageBox mesBox = new MessageBox(this.composite.getShell(), SWT.OK | SWT.ICON_INFORMATION);
            mesBox.setMessage(Messages.getString("ConnectorSelect.0")); //$NON-NLS-1$
            mesBox.setText(Messages.getString("ConnectorSelect.1")); //$NON-NLS-1$
            mesBox.open();
          }
        } else {
          MessageBox mesBox = new MessageBox(this.composite.getShell(), SWT.OK | SWT.ICON_INFORMATION);
          mesBox.setMessage(Messages.getString("ConnectorSelect.2")); //$NON-NLS-1$
          mesBox.setText(Messages.getString("ConnectorSelect.3")); //$NON-NLS-1$
          mesBox.open();
        }
      } else {
        selectConnectorNorth(this.connector, root, xmlTree, targetGroup);
      }
    } else {
      MessageBox mesBox = new MessageBox(this.composite.getShell(), SWT.OK | SWT.ICON_INFORMATION);
      mesBox.setMessage(Messages.getString("ConnectorSelect.4")); //$NON-NLS-1$
      mesBox.setText(Messages.getString("ConnectorSelect.5")); //$NON-NLS-1$
      mesBox.open();
    }
  }

  /**
   * 現在クリックしているコネクタをコネクタNに設定します。
   * 
   * @param cylinder コネクタ
   * @param root ルート
   * @param xmlTree シーングラフツリー
   * @param targetGroup 現在右クリックしているコネクタを所有するグループ
   */
  private void selectConnectorNorth(XMLConnector argConnector, Group root, Tree xmlTree, Group targetGroup) {
    this.hasNorth = true;
    argConnector.setFlag("N"); //$NON-NLS-1$
    this.connectorNorth = argConnector;

    final Group groupN = createNorthConnectorGroup();
    groupN.addXMLConnector(argConnector);
    this.targetGroupNorth = groupN;
    root.removeGroup(targetGroup);
    xmlTree.getSelection()[0].dispose();
    this.tree.fillTree();
    this.modeler.createViewer();
  }

  /**
   * コネクタNを持つグループを生成します。
   * 
   * @return　コネクタNを持つグループ
   */
  private Group createNorthConnectorGroup() {
    //final Jamast root = ModelingWindow.getRoot();
    //final JamastModel model = root.loadModel(0);
    
    final JamastModel model = this.tree.getModel();
    final Group parentGroup = model.loadGroup(0);
    
    final Group group = new Group();
    group.setName("ConnectorN"); //$NON-NLS-1$
    parentGroup.addGroup(group);
    return group;
  }
  
  /**
   * 現在クリックしているコネクタをコネクタSに設定します。
   * 
   * @param argConnector コネクタ
   * @param root ルート
   * @param xmlTree シーングラフツリー
   * @param targetGroup 現在右クリックしているコネクタを所有するグループ
   */
  private void selectConnectorSouth(XMLConnector argConnector, Group root, Tree xmlTree, Group targetGroup) {
    this.hasSouth = true;
    argConnector.setFlag("S"); //$NON-NLS-1$
    this.connectorSouth = argConnector;

    final Group groupS = createSouthConnectorGroup();
    groupS.addXMLConnector(argConnector);
    this.targetGroupSouth = groupS;
    root.removeGroup(targetGroup);
    xmlTree.getSelection()[0].dispose();
    this.tree.fillTree();
    this.modeler.createViewer();
  }
  
  /**
   * コネクタSを持つグループを生成します。
   * 
   * @return　コネクタSを持つグループ
   */
  private Group createSouthConnectorGroup() {   
    //final Jamast root = ModelingWindow.getRoot();
    //final JamastModel model = root.loadModel(0);
    
    final JamastModel model = this.tree.getModel();
    final Group parentGroup = model.loadGroup(0);

    final Group group = new Group();
    group.setName("ConnectorS"); //$NON-NLS-1$
    parentGroup.addGroup(group);
    return group;
  }

  /**
   * コネクタNを選択する
   * 
   * @param connector コネクタ
   */
  public void setConnectorNorth(XMLConnector connector) {
    this.connectorNorth = connector;
    this.hasNorth = true;
  }

  /**
   * コネクタSを選択する
   * 
   * @param connector コネクタ
   */
  public void setConnectorSouth(XMLConnector connector) {
    this.connectorSouth = connector;
    this.hasSouth = true;
  }

  /**
   * コネクタNを返す
   * 
   * @return　connectorN　コネクタN
   */
  public XMLConnector getConnectorN() {
    return this.connectorNorth;
  }

  /**
   * コネクタSを返す
   * 
   * @return　connectorS　コネクタS
   */
  public XMLConnector getConnectorS() {
    return this.connectorSouth;
  }

  /**
   * @return tree connector flag
   */
  public String getTreeConnectorFlag() {
    String flag = "-"; //$NON-NLS-1$
    //
    // ツリー全体からコネクタのフラグの状態を検索する記述
    if (this.hasNorth == false) {
      flag = "N"; //$NON-NLS-1$
    } else if (this.hasNorth == true && this.hasSouth == false) {
      flag = "S"; //$NON-NLS-1$
    }
    return flag;
  }

  /**
   * 選択しているコネクタをデフォルトの状態に戻す
   * 
   * @param root ルート
   */
  public void reset(Group root) {
    root.removeGroup(this.targetGroupNorth);
    root.removeGroup(this.targetGroupSouth);
    this.tree.fillTree();
    this.modeler.createViewer();
    this.hasNorth = false;
    this.hasSouth = false;
  }
}
