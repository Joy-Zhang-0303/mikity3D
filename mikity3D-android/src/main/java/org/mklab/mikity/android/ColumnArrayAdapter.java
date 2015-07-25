/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import org.mklab.mikity.model.searcher.GroupAnimation;
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
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class ColumnArrayAdapter extends ArrayAdapter<GroupManager> {

  private Context context;
  private int viewResourceId;
  private LayoutInflater layoutInflater;
  private List<GroupManager> groupManagers;

  /** NavigationDrawer */
  NavigationDrawerFragment fragment;

  /** 変更する番号のターゲットリスト */
  List<Integer> targetNumbers;

  /** 現在のリストに登録されているグループの数 */
  int groupNameCount;

  /**
   * 新しく生成された<code>ColumnArrayAdapter</code>オブジェクトを初期化します。
   * 
   * @param aContext Context
   * @param resourceId id
   * @param localGroupManagers GroupManager
   * @param fragment NavigatioDrawerFragment
   * @param targetNumber リンクの中の何番目のターゲットかを知るための変数
   */
  public ColumnArrayAdapter(Context aContext, int resourceId, List<GroupManager> localGroupManagers, NavigationDrawerFragment fragment, List<Integer> targetNumber) {
    super(aContext, resourceId, localGroupManagers);
    this.context = aContext;
    this.viewResourceId = resourceId;
    this.layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.groupManagers = localGroupManagers;
    this.fragment = fragment;
    this.targetNumbers = targetNumber;
    this.groupNameCount = 0;

    int groupCount = 0;
    for (GroupManager manager : localGroupManagers) {
      if (manager.getClass() == GroupName.class) {
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
    if (convertView != null) {
      return convertView;
    }

    if (this.groupManagers.get(position).getClass() == GroupName.class) {
      final GroupName groupName = (GroupName)this.groupManagers.get(position);
      final View view = this.layoutInflater.inflate(this.viewResourceId, null);
      final TextView columnText = (TextView)view.findViewById(R.id.groupNameText);
      columnText.setText(groupName.getGroupName());
      return view;
    }

    final GroupAnimation groupAnimation = (GroupAnimation)this.groupManagers.get(position);
    final View view = this.layoutInflater.inflate(R.layout.list_link, null);
    final TextView targetText = (TextView)view.findViewById(R.id.list_target);
    targetText.setText(groupAnimation.getTarget());
    final EditText numberText = (EditText)view.findViewById(R.id.list_column);
    numberText.setText(Integer.toString(groupAnimation.getNumber()));

    final Button minusButton = (Button)view.findViewById(R.id.minusButton2);
    minusButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (2 < groupAnimation.getNumber()) {
          final int number = groupAnimation.getNumber() - 1;
          groupAnimation.setNumber(number);
          numberText.setText(Integer.toString(number));
          ColumnArrayAdapter.this.fragment.changeModelNumber(ColumnArrayAdapter.this.targetNumbers, position - ColumnArrayAdapter.this.groupNameCount, number);
        }
      }
    });

    final Button plusButton = (Button)view.findViewById(R.id.plusButton2);
    plusButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (groupAnimation.getNumber() < 99) {
          final int number = groupAnimation.getNumber() + 1;
          groupAnimation.setNumber(number);
          numberText.setText(Integer.toString(number));
          ColumnArrayAdapter.this.fragment.changeModelNumber(ColumnArrayAdapter.this.targetNumbers, position - ColumnArrayAdapter.this.groupNameCount, number);
        }
      }
    });

    return view;
  }
}
