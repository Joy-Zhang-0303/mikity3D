///*
// * Created on 2015/01/07
// * Copyright (C) 2015 Koga Laboratory. All rights reserved.
// *
// */
//package org.mklab.mikity.android;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
////TODO 削除予定
///**
// * カラムナンバーを設定するためのExpandableListAdapterです。
// * @author soda
// * @version $Revision$, 2015/01/16
// */
//public class ColumnNumberExpandableListAdapter extends BaseExpandableListAdapter {
//
//  
//  private List<Map<String, String>> parentList;
//  List<List<Map<String, String>>> allChildList;
//  private List<List<Map<String, String>>> allTargetList;
//  private Context context = null;
//  String columnNumber;
//  int column;
//  ModelColumnNumberFragment mcnFragment;
//  
//  /**
//   * 新しく生成された<code>ColumnNumberExpandableListAdapter</code>オブジェクトを初期化します。
//   * @param ctx context
//   * @param parentList 親のリスト
//   * @param allChildList すべての子のリスト
//   * @param allTargetList すべてのターゲットのリスト
//   * @param mcnFragment ModelColumnNumberFragment
//   */
//  public ColumnNumberExpandableListAdapter(Context ctx, List<Map<String, String>> parentList,
//                                    List<List<Map<String, String>>> allChildList, 
//                                    List<List<Map<String, String>>> allTargetList, ModelColumnNumberFragment mcnFragment) {
//    this.context = ctx;
//    this.parentList = parentList;
//    this.allChildList = allChildList;
//    this.allTargetList = allTargetList;
//    this.mcnFragment = mcnFragment;
//  }
//  
//  /**
//   * ExpandableListViewのレイアウトを返します。
//   * @return view view
//   */
//  public View getGenericView() {
//    View view = LayoutInflater.from(this.context).inflate(R.layout.line_item, null);
//    return view;
//  }
//  
//  /**
//   * ExpandableListViewに設定されているてるtextViewを返します。
//   * @return textView textView
//   */
//  public TextView getGroupGenericView() {
//    AbsListView.LayoutParams param = new AbsListView.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT, 64);
//     
//    TextView textView = new TextView(this.context);
//    textView.setLayoutParams(param);
//     
//    textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//    textView.setPadding(64, 0, 0, 0);
//     
//    return textView;
//}
//  
//  /**
//   * @see android.widget.ExpandableListAdapter#getGroupCount()
//   */
//  public int getGroupCount() {
//    return this.allChildList.size();
//  }
//
//  /**
//   * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
//   */
//  public int getChildrenCount(int groupPosition) {
//    return this.allChildList.get(groupPosition).size();
//  }
//
//  /**
//   * @see android.widget.ExpandableListAdapter#getGroup(int)
//   */
//  public Object getGroup(int groupPosition) {
//    return this.parentList.get(groupPosition).get("groupName"); //$NON-NLS-1$
//  }
//
//  /**
//   * @see android.widget.ExpandableListAdapter#getChild(int, int)
//   */
//  public Object getChild(int groupPosition, int childPosition) {
//    return this.allChildList.get(groupPosition).get(childPosition);
//  }
//
//  /**
//   * @see android.widget.ExpandableListAdapter#getGroupId(int)
//   */
//  public long getGroupId(int groupPosition) {
//    return groupPosition;
//  }
//
//  /**
//   * @param groupPosition 親の場所
//   * @see android.widget.ExpandableListAdapter#getChildId(int, int)
//   */
//  public long getChildId(int groupPosition, int childPosition) {
//    return childPosition;
//  }
//
//  /**
//   * @see android.widget.ExpandableListAdapter#hasStableIds()
//   */
//  public boolean hasStableIds() {
//    return true;
//  }
//
//  /**
//   * @param isExpanded isExpanded
//   * @param convertView view
//   * @param parent viewGroup
//   * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
//   */
//  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//    TextView textView = getGroupGenericView();
//    textView.setText(getGroup(groupPosition).toString());
//    return textView;
//  }
//
//  /**
//   * @param isLastChild isLastChild
//   * @param convertView view
//   * @param parent viewGroup
//   * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
//   */
//  public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//    View childView = getGenericView();
//    TextView targetTextView = (TextView)childView.findViewById(R.id.target_list);
//    targetTextView.setText(this.allTargetList.get(groupPosition).get(childPosition).get("target").toString()); //$NON-NLS-1$
//    final EditText columnEditText = (EditText)childView.findViewById(R.id.column_edit_text);
//    this.columnNumber = this.allChildList.get(groupPosition).get(childPosition).get("columnNumber"); //$NON-NLS-1$
//    columnEditText.setText(this.allChildList.get(groupPosition).get(childPosition).get("columnNumber").toString()); //$NON-NLS-1$
//    this.column = Integer.parseInt(this.columnNumber);
//    
//    Button minusButton = (Button)childView.findViewById(R.id.minusButton);
//    minusButton.setOnClickListener(new View.OnClickListener() {
//      
//      /**
//       * @param v view
//       */
//      public void onClick(View v) {
//        ColumnNumberExpandableListAdapter.this.columnNumber = ColumnNumberExpandableListAdapter.this.allChildList.get(groupPosition).get(childPosition).get("columnNumber"); //$NON-NLS-1$
//        ColumnNumberExpandableListAdapter.this.column = Integer.parseInt(ColumnNumberExpandableListAdapter.this.columnNumber);
//        if (2 < ColumnNumberExpandableListAdapter.this.column) {
//          columnEditText.setText(ColumnNumberExpandableListAdapter.this.allChildList.get(groupPosition).get(childPosition).get("columnNumber").toString()); //$NON-NLS-1$
//          ColumnNumberExpandableListAdapter.this.column--;
//          columnEditText.setText(String.valueOf(ColumnNumberExpandableListAdapter.this.column));
//          ColumnNumberExpandableListAdapter.this.mcnFragment.changeModelColumnNumber(groupPosition, childPosition, ColumnNumberExpandableListAdapter.this.column);
//          Map<String, String> columnData = new HashMap<String, String>();
//          columnData.put("columnNumber", String.valueOf(ColumnNumberExpandableListAdapter.this.column)); //$NON-NLS-1$
//          ColumnNumberExpandableListAdapter.this.allChildList.get(groupPosition).set(childPosition, columnData);
//        }
//      }
//    });
//    
//    Button plusButton = (Button)childView.findViewById(R.id.plusButton);
//    plusButton.setOnClickListener(new View.OnClickListener() {
//      
//      /**
//       * @param v view
//       */
//      public void onClick(View v) {
//        ColumnNumberExpandableListAdapter.this.columnNumber = ColumnNumberExpandableListAdapter.this.allChildList.get(groupPosition).get(childPosition).get("columnNumber"); //$NON-NLS-1$
//        ColumnNumberExpandableListAdapter.this.column = Integer.parseInt(ColumnNumberExpandableListAdapter.this.columnNumber);
//        if((ColumnNumberExpandableListAdapter.this.column < getTimeDataRowSize(ColumnNumberExpandableListAdapter.this.column) || ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.data == null)) {
//          columnEditText.setText(ColumnNumberExpandableListAdapter.this.allChildList.get(groupPosition).get(childPosition).get("columnNumber").toString()); //$NON-NLS-1$
//          ColumnNumberExpandableListAdapter.this.column++;
//          columnEditText.setText(String.valueOf(ColumnNumberExpandableListAdapter.this.column));
//          ColumnNumberExpandableListAdapter.this.mcnFragment.changeModelColumnNumber(groupPosition, childPosition, ColumnNumberExpandableListAdapter.this.column);
//          Map<String, String> columnData = new HashMap<String, String>();
//          columnData.put("columnNumber", String.valueOf(ColumnNumberExpandableListAdapter.this.column)); //$NON-NLS-1$
//          ColumnNumberExpandableListAdapter.this.allChildList.get(groupPosition).set(childPosition, columnData);
//        } else {
//          ColumnNumberExpandableListAdapter.this.mcnFragment.setExceptionDailogFragment("Column number is over.\n" //$NON-NLS-1$
//              + "If you set bigger column number, please push \"×\"button and select time data."); //$NON-NLS-1$
//        }
//      }
//    });
//    
//    return childView;
//  }
//  
//  /**
//   * 条件に応じて読み込んだ時間データのサイズを返します。
//   * @param column1 カラム
//   * @return 以降の処理のための時間データサイズ
//   */
//  public int getTimeDataRowSize(int column1) {
//    if(this.mcnFragment.canvasActivity.canvasFragment.data != null) {
//      if(this.mcnFragment.canvasActivity.canvasFragment.setIllegalTimeData == true) {
//        int newColumn = column1 + 1;
//        return newColumn;
//      }
//      return this.mcnFragment.canvasActivity.canvasFragment.data.getRowSize();
//    }
//    return 0;
//  }
//
//  /**
//   * @param groupPosition 親の場所
//   * @param childPosition 子供の場所
//   * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
//   */
//  public boolean isChildSelectable(int groupPosition, int childPosition) {
//    return true;
//  }
//}
