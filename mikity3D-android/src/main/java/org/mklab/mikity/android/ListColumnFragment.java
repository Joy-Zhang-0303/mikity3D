/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import roboguice.fragment.RoboFragment;


public class ListColumnFragment extends RoboFragment {
  
  public ListView listView;
  private final int FP = ViewGroup.LayoutParams.MATCH_PARENT; 
  private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT; 
  private View view;
  private List<List<Object>> columnLists;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.set_column_fragment, container, false);
    this.listView = (ListView)view.findViewById(R.id.list_view_column);
    configureListView();
//    setColumn();
//    setAdapter();
    return view;
  }
  
  public void setColumnData(List<List<Object>> list) {
    this.columnLists = list;
  }
  
  public void createViewLists() {
    if(this.columnLists != null) {
      int parentSize = this.columnLists.size();
      int[] childSize = new int[parentSize];
      for(int i=0; i<parentSize; i++) {
        childSize[i] = this.columnLists.get(i).size();
      }
    }
  }
  public void configureListView() {
//    int[] resId = { R.id.variousView };
    View[] buttons = new Button[2];
    List<View> views = new ArrayList<View>();
    for(int i = 0; i<2; i++) {
      buttons[i] = new Button(this.getActivity());
//      buttons[i].setId(resId[0]);
      views.add(buttons[i]);
    }
//    ArrayAdapter<View> adapter = new ArrayAdapter<View>(this.getActivity(), android.R.layout.simple_list_item_1, buttons);
    ColumnArrayAdapter adapter = new ColumnArrayAdapter(this.getActivity(), R.layout.button, views);
    this.listView.setAdapter(adapter);
  }
//  
//  public void setColumn() {
//    List<ColumnData> datas = new ArrayList<ColumnData>();
//    ColumnData data;
//    View[] buttons = new Button[2];
//    for(int i=0; i<2; i++) {
//      buttons[i] = new Button(getActivity());
//    }
//    for(int i=0; i<2; i++) {
//      data = new ColumnData();
//      View button = new Button(this.getActivity());
//      data.setButton(buttons[i]);
//      datas.add(data);
//    }
//    
//    ColumnArrayAdapter adapter = new ColumnArrayAdapter(getActivity(), R.id.minusButton, datas);
//    this.listView.setAdapter(adapter);
//  }
  
//  public void setAdapter() {
//    ArrayList<Book> books = new ArrayList<Book>();
//    Book book;
//    for (int i = 0; i < 1000; i++) {
//        book = new Book();
//        book.setTitle("Title" + String.valueOf(i));
//        book.setIsbn("ISBN:" + String.valueOf(i));
//        books.add(book);
//    }
//
//    SampleAdapter adapter = new SampleAdapter(getActivity(), R.layout.samplerow, books);
//
//    ListView lv = (ListView) view.findViewById(R.id.list_view_column);
//    lv.setAdapter(adapter);
//  }
}
