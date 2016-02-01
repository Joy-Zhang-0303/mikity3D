package org.mklab.mikity.android.slidingmenu;

public class NavDrawerItem{
	private String title;
	private int navicon;
	private String count="0";
	
	//boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	
	public NavDrawerItem(){}
	
	public NavDrawerItem(String title, int icon){
		this.title = title;
		this.navicon = icon;
	}
	
	public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count){
		this.title = title;
		this.navicon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.navicon;
	}
	
	public String getCount(){
		return this.count;
	}
	
	public boolean getCounterVisiblity(){
		return this.isCounterVisible;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.navicon = icon;
	}
	
	public void setCount(String count){
		this.count = count;
	}
	
	public void setCounterVisisblity(boolean isCounterVisible){
		this.isCounterVisible = isCounterVisible;
	}
}
