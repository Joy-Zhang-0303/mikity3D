/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * @author soda
 * @version $Revision$, 2014/10/10
 * デバック時に落ちないようにするために作った、メニューのフラグメントです。
 */
public class MenuFragment extends Fragment implements OnClickListener {
  
  /**
   * @param savedInstanceState Bundle
   * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
    View view = inflater.inflate(R.layout.menu_fragment, container, false);
    
    Button button = (Button)view.findViewById(R.id.button1);
    button.setOnClickListener(this); 
    
    return view;
  }
  
  /**
   * @see android.view.View.OnClickListener#onClick(android.view.View)
   */
  public void onClick(View view) {
    
    if(view.getId() == R.id.button1) {
      Intent intent = new Intent(this.getActivity(), CanvasActivity.class);
      startActivity(intent);
    }
  }
}
