/*
 * Created on 2015/01/07
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ColumnNumberExpandableListAdapter extends BaseExpandableListAdapter {

  
  private List<Map<String, String>> parentList;
  private List<List<Map<String, String>>> allChildList;
  private List<List<Map<String, String>>> allTargetList;
  private Context context = null;
  private String columnNumber;
  private int column;
  private ModelColumnNumberFragment mcnFragment;
  
  public ColumnNumberExpandableListAdapter(Context ctx, List<Map<String, String>> parentList,
                                    List<List<Map<String, String>>> allChildList, 
                                    List<List<Map<String, String>>> allTargetList, ModelColumnNumberFragment mcnFragment) {
    this.context = ctx;
    this.parentList = parentList;
    this.allChildList = allChildList;
    this.allTargetList = allTargetList;
    this.mcnFragment = mcnFragment;
  }
  
  public View getGenericView() {
    View view = LayoutInflater.from(this.context).inflate(R.layout.line_item, null);
    return view;
  }
  
  public TextView getGroupGenericView() {
    AbsListView.LayoutParams param = new AbsListView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 64);
     
    TextView textView = new TextView(this.context);
    textView.setLayoutParams(param);
     
    textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
    textView.setPadding(64, 0, 0, 0);
     
    return textView;
}
  
  public int getGroupCount() {
    return allChildList.size();
  }

  public int getChildrenCount(int groupPosition) {
    return allChildList.get(groupPosition).size();
  }

  public Object getGroup(int groupPosition) {
    return parentList.get(groupPosition).get("groupName");
  }

  public Object getChild(int groupPosition, int childPosition) {
    return allChildList.get(groupPosition).get(childPosition);
  }

  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  public boolean hasStableIds() {
    return true;
  }

  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    TextView textView = getGroupGenericView();
    textView.setText(getGroup(groupPosition).toString());
    return textView;
  }

  public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    View childView = getGenericView();
    TextView targetTextView = (TextView)childView.findViewById(R.id.target_list);
    targetTextView.setText(allTargetList.get(groupPosition).get(childPosition).get("target").toString());
    final EditText columnEditText = (EditText)childView.findViewById(R.id.column_edit_text);
    this.columnNumber = allChildList.get(groupPosition).get(childPosition).get("columnNumber");
    columnEditText.setText(allChildList.get(groupPosition).get(childPosition).get("columnNumber").toString());
    this.column = Integer.parseInt(columnNumber);
    
    Button minusButton = (Button)childView.findViewById(R.id.minusButton);
    minusButton.setOnClickListener(new View.OnClickListener() {
      
      public void onClick(View v) {
        columnNumber = allChildList.get(groupPosition).get(childPosition).get("columnNumber");
        column = Integer.parseInt(columnNumber);
        if (2 < column) {
          columnEditText.setText(allChildList.get(groupPosition).get(childPosition).get("columnNumber").toString());
          ColumnNumberExpandableListAdapter.this.column--;
          columnEditText.setText(String.valueOf(ColumnNumberExpandableListAdapter.this.column));
          ColumnNumberExpandableListAdapter.this.mcnFragment.changeModelColumnNumber(groupPosition, childPosition, column);
          Map<String, String> columnData = new HashMap<String, String>();
          columnData.put("columnNumber", String.valueOf(column));
          allChildList.get(groupPosition).set(childPosition, columnData);
//          if (ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.data != null) {
////              && ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.setModelCount < 2) {
//            ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.setTimeData();
//          }
        }
      }
    });
    
    Button plusButton = (Button)childView.findViewById(R.id.plusButton);
    plusButton.setOnClickListener(new View.OnClickListener() {
      
      public void onClick(View v) {
        columnNumber = allChildList.get(groupPosition).get(childPosition).get("columnNumber");
        column = Integer.parseInt(columnNumber);
        if((column < getTimeDataRowSize(column) || ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.data == null)) {
          columnEditText.setText(allChildList.get(groupPosition).get(childPosition).get("columnNumber").toString());
          ColumnNumberExpandableListAdapter.this.column++;
          columnEditText.setText(String.valueOf(ColumnNumberExpandableListAdapter.this.column));
          ColumnNumberExpandableListAdapter.this.mcnFragment.changeModelColumnNumber(groupPosition, childPosition, column);
          Map<String, String> columnData = new HashMap<String, String>();
          columnData.put("columnNumber", String.valueOf(column));
          allChildList.get(groupPosition).set(childPosition, columnData);
//          if (ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.data != null) {
////              && ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.setModelCount < 2) {
//            ColumnNumberExpandableListAdapter.this.mcnFragment.canvasActivity.canvasFragment.setTimeData();
//          }
        } else {
          mcnFragment.setExceptionDailogFragment("Column number is over.\n"
              + "If you set bigger column number, please push \"×\"button and select time data.");
        }
      }
    });
    
    return childView;
  }
  
  public int getTimeDataRowSize(int column) {
    if(this.mcnFragment.canvasActivity.canvasFragment.data != null) {
      if(this.mcnFragment.canvasActivity.canvasFragment.setIllegalTimeData == true) {
        return ++column;
      } else {
      return this.mcnFragment.canvasActivity.canvasFragment.data.getRowSize();
      }
    } else {
      return 0;
    }
  }

  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
}
