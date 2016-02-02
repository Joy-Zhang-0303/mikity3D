package org.mklab.mikity.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;

/**
 * 設定画面を表すフラグメントです
 * @author Yuri
 *
 */

public class SettingsFragment extends Fragment{
	  private DrawerLayout sDrawerLayout;
	  private ListView SettingsList;
	  
	  public SettingsFragment(){
		  //nothings to do
	  }
	  
	  CanvasActivity canvasActivity;

	  /** 端末の角度を3Dオブジェクトに反映させるならばtrue。 */
	  CompoundButton rotationSensorButton;
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    final View view = inflater.inflate(R.layout.fragment_settings, container, false);

	    this.canvasActivity = (CanvasActivity)getActivity();

	    createSensorComponent(view);

	    return view;
	  }
	  
	  private void createSensorComponent(final View mainView) {
		    this.rotationSensorButton = (CompoundButton)mainView.findViewById(R.id.rotationSensorButton);
		    this.rotationSensorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (SettingsFragment.this.rotationSensorButton.isChecked()) {
		          SettingsFragment.this.canvasActivity.sensorService.useRotationSensor = true;
		        } else {
		          SettingsFragment.this.canvasActivity.sensorService.useRotationSensor = false;
		        }

		      }
		    });

}
}