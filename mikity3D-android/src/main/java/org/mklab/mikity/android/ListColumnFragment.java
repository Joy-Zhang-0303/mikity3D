/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mklab.mikity.model.searcher.GroupLink;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupName;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import roboguice.fragment.RoboFragment;


public class ListColumnFragment extends RoboFragment {
  
  public ListView listView;
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
  
  
  public void configureListView() {
//    int[] resId = { R.id.variousView };
    final View[] buttons = new Button[2];
    List<View> views = new ArrayList<View>();
    for(int i = 0; i<2; i++) {
      buttons[i] = new Button(this.getActivity());
//      buttons[i].setId(resId[0]);
      views.add(buttons[i]);
    }
//    ArrayAdapter<View> adapter = new ArrayAdapter<View>(this.getActivity(), android.R.layout.simple_list_item_1, buttons);
    ColumnArrayAdapter adapter = new ColumnArrayAdapter(this.getActivity(), R.layout.button, views, null);
    this.listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        ListView listView = (ListView)parent;
        final Button button = (Button)listView.getItemAtPosition(1);
        button.setOnTouchListener(new OnTouchListener() {

          public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            button.setText("pushed!!!!!1 ");
            return false;
          }
          
        });
      }
      
    });
//    buttons[1].findViewById(R.id.variousView);
//    buttons[1].setOnTouchListener(new OnTouchListener() {
//
//      public boolean onTouch(View v, MotionEvent event) {
//        ((Button)buttons[1]).setText("pushed!!!!");
//        return false;
//      }
//    });
    
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
  
//  public void sample(List<GroupManager> lists) {
////    View[] views = new View[lists.size()];
//    List<View> views = new ArrayList<View>();
//    for(Iterator itr = lists.iterator(); itr.hasNext();) {
//      GroupManager item = (GroupManager)itr.next();
//      if(item.getClass() == GroupName.class) {
//        Button button = new Button(this.getActivity());
//        button.setText(((GroupName)item).getGroupName());
//      } else {
//        ((GroupLink)item).getColumn();
//        ((GroupLink)item).getTarget();
//      }
//    }
//    
//    View[] buttons = new Button[2];
//    for(int i = 0; i<2; i++) {
//      buttons[i] = new Button(this.getActivity());
//      views.add(buttons[i]);
//    }
////  ArrayAdapter<View> adapter = new ArrayAdapter<View>(this.getActivity(), android.R.layout.simple_list_item_1, buttons);
//    ColumnArrayAdapter adapter = new ColumnArrayAdapter(this.getActivity(), R.layout.button, null, lists);
//    this.listView.setAdapter(adapter);
//  }

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
