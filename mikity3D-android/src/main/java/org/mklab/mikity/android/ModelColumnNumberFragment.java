/*
 * Created on 2014/12/11
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ModelColumnNumberFragment extends ListFragment{
  
  View view;
  CanvasActivity canvasActivity;
  NavigationDrawerFragment ndFragment;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.model_column_number_fragment, container, false);
    this.canvasActivity = (CanvasActivity)getActivity();
    this.ndFragment = this.canvasActivity.ndFragment;
    return this.view;
  }
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(
          getActivity(),android.R.layout.simple_list_item_1,
          this.ndFragment.groupNameArray);
    
    setListAdapter(adapter);
  }
}
