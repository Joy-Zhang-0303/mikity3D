/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import org.mklab.mikity.model.searcher.GroupAnimationManager;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupNameManager;

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
public class SourceNumberAdapter extends ArrayAdapter<GroupManager> {
  private int resourceId;
  private LayoutInflater layoutInflater;
  private List<GroupManager> groupManagers;

  /** NavigationDrawer */
  NavigationDrawerFragment fragment;

  /** 変更する番号のターゲットリスト */
  List<Integer> targetNumbers;

  /** 現在のリストに登録されているグループの数 */
  int groupNameCount;

  /**
   * 新しく生成された<code>SourceNumberAdapter</code>オブジェクトを初期化します。
   * 
   * @param context Context
   * @param resourceId id
   * @param groupManagers GroupManager
   * @param fragment NavigatioDrawerFragment
   * @param targetNumber リンクの中の何番目のターゲットかを知るための変数
   */
  public SourceNumberAdapter(Context context, int resourceId, List<GroupManager> groupManagers, NavigationDrawerFragment fragment, List<Integer> targetNumber) {
    super(context, resourceId, groupManagers);
    this.resourceId = resourceId;
    this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.groupManagers = groupManagers;
    this.fragment = fragment;
    this.targetNumbers = targetNumber;

    int groupCount = 0;
    for (GroupManager manager : groupManagers) {
      if (manager.getClass() == GroupNameManager.class) {
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

    if (this.groupManagers.get(position).getClass() == GroupNameManager.class) {
      final GroupNameManager nameManager = (GroupNameManager)this.groupManagers.get(position);
      final View view = this.layoutInflater.inflate(this.resourceId, null);
      final TextView groupName = (TextView)view.findViewById(R.id.groupNameText);
      groupName.setText(nameManager.getGroupName());
      return view;
    }

    final GroupAnimationManager animationManager = (GroupAnimationManager)this.groupManagers.get(position);
    final View view = this.layoutInflater.inflate(R.layout.list_link, null);
    final TextView targetText = (TextView)view.findViewById(R.id.list_target);
    targetText.setText(animationManager.getTarget());
    final EditText numberText = (EditText)view.findViewById(R.id.sourceNumber);
    numberText.setText(Integer.toString(animationManager.getNumber()));

    final Button minusButton = (Button)view.findViewById(R.id.minusButton);
    minusButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (2 < animationManager.getNumber()) {
          final int number = animationManager.getNumber() - 1;
          animationManager.setNumber(number);
          numberText.setText(Integer.toString(number));
          SourceNumberAdapter.this.fragment.changeSourceNumber(SourceNumberAdapter.this.targetNumbers, position - SourceNumberAdapter.this.groupNameCount, number);
        }
      }
    });

    final Button plusButton = (Button)view.findViewById(R.id.plusButton);
    plusButton.setOnClickListener(new OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (animationManager.getNumber() < 99) {
          final int number = animationManager.getNumber() + 1;
          animationManager.setNumber(number);
          numberText.setText(Integer.toString(number));
          SourceNumberAdapter.this.fragment.changeSourceNumber(SourceNumberAdapter.this.targetNumbers, position - SourceNumberAdapter.this.groupNameCount, number);
        }
      }
    });

    return view;
  }
}
