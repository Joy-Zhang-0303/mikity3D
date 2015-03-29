/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.Iterator;
import java.util.List;

import org.mklab.mikity.model.searcher.GroupLink;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupName;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * リストビューを動的に生成するためのアダプタークラスです。
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class ColumnArrayAdapter extends ArrayAdapter <GroupManager>{
  private Context _context;
  private int _viewResourceId;
  private LayoutInflater layoutInflater_;
  private List<GroupManager> lists;
  /** NavigationDrawer*/
  NavigationDrawerFragment fragment;
  /** 変更するカラムのターゲットリスト*/
  List<Integer> targetColumn;
  /** 現在のリストに登録されているグループの数*/
  int groupNameCount;
  
  /**
   * 新しく生成された<code>ColumnArrayAdapter</code>オブジェクトを初期化します。
   * @param context Context
   * @param resourceId id
   * @param lists GroupManager
   * @param fragment NavigatioDrawerFragment
   * @param targetColumn リンクの中の何番目のターゲットかを知るための変数
   */
  public ColumnArrayAdapter(Context context, int resourceId, List<GroupManager> lists, 
      NavigationDrawerFragment fragment, List<Integer> targetColumn) {
    super(context, resourceId, lists);
    this._context = context;
    this._viewResourceId = resourceId;
    this.layoutInflater_ = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.lists = lists;
    this.fragment = fragment;
    this.targetColumn = targetColumn;
    this.groupNameCount = 0;
    
    int groupCount = 0;
    for(Iterator<GroupManager> itr = lists.iterator(); itr.hasNext();) { 
      if((itr.next()).getClass() == GroupName.class) {
        groupCount++;
      }
    }
    this.groupNameCount = groupCount;
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    View view;
    if (convertView != null) {
      view = convertView;
    } else {
      if(this.lists.get(position).getClass() == GroupName.class) {
        GroupName groupName = (GroupName)this.lists.get(position);
        view = this.layoutInflater_.inflate(this._viewResourceId, null);
        TextView columnText = (TextView)view.findViewById(R.id.groupNameText);
        columnText.setText(groupName.getGroupName());
      } else {
        final GroupLink groupLink = (GroupLink)this.lists.get(position);
        view = this.layoutInflater_.inflate(R.layout.list_link, null);
        TextView targetText = (TextView)view.findViewById(R.id.list_target);
        targetText.setText(groupLink.getTarget());
        final EditText columnText = (EditText)view.findViewById(R.id.list_column); 
        columnText.setText(Integer.toString(groupLink.getColumn()));

        Button minusButton = (Button)view.findViewById(R.id.minusButton2);
        minusButton.setOnClickListener(new OnClickListener() {

          /**
           * @param v  View
           */
          public void onClick(View v) {
            if(2 < groupLink.getColumn()) {
              int column = groupLink.getColumn() - 1;
              groupLink.setColumnNumber(column);
              columnText.setText(Integer.toString(column));
              ColumnArrayAdapter.this.fragment.changeModelColumnNumber(ColumnArrayAdapter.this.targetColumn, position-ColumnArrayAdapter.this.groupNameCount, column);
            }
          }          
        });
        Button plusButton = (Button)view.findViewById(R.id.plusButton2);
        plusButton.setOnClickListener(new OnClickListener() {

          /**
           * @param v  View
           */
          public void onClick(View v) {
            if(groupLink.getColumn() < 99) {
              int column = groupLink.getColumn() + 1;
              groupLink.setColumnNumber(column);
              columnText.setText(Integer.toString(column));
              ColumnArrayAdapter.this.fragment.changeModelColumnNumber(ColumnArrayAdapter.this.targetColumn, position-ColumnArrayAdapter.this.groupNameCount, column);
            }
          }          
        });
      }
    }
    return view;
  }
}
