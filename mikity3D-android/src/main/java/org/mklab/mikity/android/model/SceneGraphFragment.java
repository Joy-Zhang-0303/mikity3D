/*
 * Created on 2016/01/08
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.model;

import java.util.List;

import org.mklab.mikity.android.R;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;


/**
 * モデルツリーのフラグメントを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/08
 */
public class SceneGraphFragment extends Fragment {
  private ListView listView;
  
  FragmentManager fragmentManager;
  
  ModelTreeAdapter adapter;
  
  TreeItem root;
  
  /** シーンモデル。 */
  SceneModel model;
  /** ルートグループ。 */
  GroupModel rootGroup;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.scene_graph_fragment, container, false);
    this.listView = (ListView)view.findViewById(R.id.scene_graph_list_view);
    
    configureListView();

    this.listView.setOnItemClickListener(new OnItemClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final TreeItem treeEntry = (TreeItem)parent.getItemAtPosition(position);
        
        if (treeEntry.isExpanded()) {
          treeEntry.collapse();
        } else {
          if (treeEntry.hasChild()) {
            treeEntry.expand();
          }
        }
        
        SceneGraphFragment.this.adapter.notifyDataSetChanged();
      }
    });
    
    final Button backButton = (Button)view.findViewById(R.id.backButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        SceneGraphFragment.this.fragmentManager.popBackStack();
      }
    });
    
    return view;
  }

  /**
   * フラグメントマネージャを設定します。
   * 
   * @param fragmentManager フラグメントマネージャ
   */
  public void setFragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }
  
  /**
   * モデルを設定します。
   * 
   * @param model モデル
   */
  public void setModel(SceneModel model) {
    this.model = model;
    this.rootGroup = new GroupModel("scene"); //$NON-NLS-1$
    this.rootGroup.setGroups(model.getGroups());
  }
  
  /**
   * リストビューをアダプタに登録し、リストの処理します。
   */
  void configureListView() {
    this.adapter = new ModelTreeAdapter(getActivity());
    this.listView.setAdapter(this.adapter);
    
    createTree();
  }
  
  
  /**
   * ツリーを生成します。
   * 
   * @param parent 親
   */
  private void createTree() {
    this.adapter.clearTree();
    
    this.root = this.adapter.add("scene"); //$NON-NLS-1$
    addItemToTree(this.root, this.model.getGroups());
  }
  
  /**
   * ツリーにオブジェクトを追加します。
   * 
   * @param parent 親
   * @param groups グループ
   */
  private void addItemToTree(TreeItem parent, List<GroupModel> groups) {
    for (final GroupModel group : groups) {
      final TreeItem groupItem;
      if (parent.getText().equals("scene")) { //$NON-NLS-1$
        groupItem = new TreeItem(parent, group);
        groupItem.setText(group.getName());
      } else {
        groupItem = new TreeItem(parent, group);
        groupItem.setText(group.toShortString());
      }
      
//      if (group == this.selectedObject) {
//        this.tree.select(groupItem);
//      }

      final List<ObjectModel> objects = group.getObjects();
      final boolean groupHasAnyObject = objects.size() > 0 && (objects.get(0) instanceof NullModel) == false;
      
      if (groupHasAnyObject) {
        final TreeItem objectItems = new TreeItem(groupItem, group);
        objectItems.setText("object"); //$NON-NLS-1$

        for (final ObjectModel object :  objects) {
          if (object instanceof NullModel) {
            continue;
          }
          
          final TreeItem child = new TreeItem(objectItems, object);
          child.setText(object.toShortString());

//          if (object == this.selectedObject) {
//            this.tree.select(child);
//          }
        }
      }
      
      addItemToTree(groupItem, group.getGroups());
    }
    
    if (parent != null) {
      parent.expand();
    }
  }

}
