/*
 * Created on 2014/10/09
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;

import org.mklab.mikity.android.slidingmenu.NavDrawerItem;
import org.mklab.mikity.android.slidingmenu.adapter.NavDrawerListAdapter;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Mikity3dのメインとなるアクティビティクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class CanvasActivity extends AppCompatActivity {

	final static int REQUEST_CODE_PICK_MODEL_DATA_FILE = 0;
	final static int REQUEST_CODE_PICK_SOURCE_DATA_FILE = 1;

	/** NavigationDrawer用のアクションバートグル */
	private ActionBarDrawerToggle drawerToggle;

	/** CanvasFragment */
	CanvasFragment canvasFragment;

	/** NavigationDrawerFragment */
	NavigationDrawerFragment ndFragment;

	/** 停止ボタンが押すことができるならばtrue */
	private boolean isStopButtonPushable;

	SensorService sensorService;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_canvas);

		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		createCanvasFragment();
		// createNavigationDrawerFragment();
		createSlideMenu();

		final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.layout_activity_canvas);
		this.drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.string.open, R.string.close);
		this.drawerToggle.syncState();
		drawerLayout.setDrawerListener(this.drawerToggle);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setLogo(R.drawable.icon);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);

		this.sensorService = new SensorService(
				(SensorManager) getSystemService(SENSOR_SERVICE),
				this.canvasFragment);

		startIntentByExternalActivity();



		// ステータスバーを消す
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	private void createSlideMenu() {
		/**
		 * load slide menu items
		 */
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		/**
		 * nav drawer icons from resources
		 */
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		/**
		 * adding nav drawer items to array
		 */
		// 新規モデル作成
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// モデル選択
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// モデル編集
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// 設定
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// アプリについて
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// ヘルプ
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		// ナビゲーションドロワー
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();
		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		// enabling action bar app icon and behaving it as toggle button
		
	}

	/**
	 * Slide menu item click listener
	 */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/**
	 * Displaying fragment view for selected nav drawer list item
	 */
	private void displayView(int position) {
		// update the main content by replacing fragments
		android.support.v4.app.Fragment fragment = null;
		switch (position) {
		case 6:
			fragment = new NavigationDrawerFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_navigation_drawer, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("CanvasActivity", "フラグメントの作成中にエラーが発生しました");
		}
	}

	/**
	 * CanvasFragmentを生成します。
	 */
	private void createCanvasFragment() {
		
		final FragmentManager manager = getSupportFragmentManager();

		if (manager.findFragmentById(R.id.fragment_canvas) != null) {
			this.canvasFragment = (CanvasFragment) manager
					.findFragmentById(R.id.fragment_canvas);
			return;
		}

		this.canvasFragment = new CanvasFragment();

		final FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.fragment_canvas, this.canvasFragment);
		transaction.commit();
	}

	/**
	 * NavigationDrawerFragmentを生成します。
	 */
	 private void createNavigationDrawerFragment() {
	 final FragmentManager manager = getSupportFragmentManager();

	 if (manager.findFragmentById(R.id.fragment_navigation_drawer) != null) {
	 this.ndFragment =
	 (NavigationDrawerFragment)manager.findFragmentById(R.id.fragment_navigation_drawer);
	 return;
	 }

	 this.ndFragment = new NavigationDrawerFragment();

	 final FragmentTransaction transaction = manager.beginTransaction();
	 transaction.add( R.id.fragment_navigation_drawer, this.ndFragment);
	 transaction.show(this.ndFragment);
	 transaction.commit();
	 }

	/**
	 * 外部アクティビティからのモデルパス、データパスを渡されるインテントに対応したメソッドです。
	 */
	private void startIntentByExternalActivity() {
		final String intentPath = "path"; //$NON-NLS-1$
		final String intentDataPath = "data"; //$NON-NLS-1$

		final Intent intent = getIntent();
		if (intent.getBundleExtra(intentPath) == null) {
			return;
		}

		final Bundle bundle = intent.getBundleExtra(intentPath);
		if (bundle.getParcelable(intentPath) == null) {
			return;
		}

		final Uri modelPath = bundle.getParcelable(intentPath);
		loadModelData(modelPath);
		if (bundle.getParcelable(intentDataPath) == null) {
			return;
		}

		final Uri sourcePath = bundle.getParcelable(intentDataPath);
		loadSourceData(sourcePath);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		this.drawerToggle.syncState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		this.drawerToggle.onConfigurationChanged(configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (this.drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		if (item.getItemId() == R.id.menu_play) {
			if (this.canvasFragment.playable) {
				this.isStopButtonPushable = true;
			}
			this.canvasFragment.runAnimation();
		} else if (item.getItemId() == R.id.menu_stop) {
			if (this.isStopButtonPushable) {
				this.isStopButtonPushable = false;
				this.canvasFragment.stopAnimation();
			}
		} else if (item.getItemId() == R.id.menu_pause) {
			if (this.isStopButtonPushable) {
				this.isStopButtonPushable = false;
				this.canvasFragment.pauseAnimation();
			}
		} else if (item.getItemId() == R.id.menu_repeat) {
			if (this.canvasFragment.playable) {
				this.isStopButtonPushable = true;
			}
			this.canvasFragment.repeatAnimation();
		} else if (item.getItemId() == R.id.menu_reset_to_initial_state) {
			this.canvasFragment.resetToInitialState();
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// スクリーンサイズ調整が済んでいない場合は調整する
		if (this.canvasFragment.isInitialScreenSize == false) {
			final GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.glview1);
			final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					glSurfaceView.getWidth(), glSurfaceView.getHeight());
			glSurfaceView.setLayoutParams(params);
		}
		super.onWindowFocusChanged(hasFocus);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResume() {
		this.sensorService.registerSensorListener();

		this.canvasFragment.glView.onResume();
		super.onResume();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPause() {
		this.sensorService.unregisterSensorListener();

		this.canvasFragment.glView.onPause();
		super.onPause();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	/**
	 * This is called after the file manager finished. {@inheritDoc}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CODE_PICK_MODEL_DATA_FILE:
			if (resultCode == RESULT_OK && data != null) {
				final Uri uri = data.getData();
				loadModelData(uri);
			}
			break;
		case REQUEST_CODE_PICK_SOURCE_DATA_FILE:
			if (resultCode == RESULT_OK && data != null) {
				final Uri uri = data.getData();
				loadSourceData(uri);
			}
			break;
		case 3:
			if (resultCode == RESULT_OK && data != null) {
				final Uri uri = data.getData();
				this.ndFragment.unzipFile(uri);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 時間データを読み込みます。
	 * 
	 * @param path
	 *            時間データのパス
	 */
	private void loadSourceData(Uri path) {
		this.ndFragment.loadSourceData(path);
	}

	/**
	 * モデルデータを読み込みます。
	 * 
	 * @param path
	 *            モデルデータのパス
	 */
	private void loadModelData(Uri path) {
		this.ndFragment.loadModelData(path);

		this.ndFragment.sourceId = null;
		this.canvasFragment.objectRenderer.updateDisplay();
	}

	/**
	 * 画面回転時の処理を行います。
	 */
	public void controlRotation() {
		final Configuration configuration = getResources().getConfiguration();
		final int rotation = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay().getRotation();
		boolean isReverse = false;

		if (this.ndFragment.rotationLockButton.isChecked()) {
			switch (rotation) {
			case Surface.ROTATION_180:
			case Surface.ROTATION_270:
				isReverse = true;
				break;
			default:
				isReverse = false;
				break;
			}

			if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
				if (isReverse) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
				} else {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}
			} else {
				if (isReverse) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
				} else {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}
			}
			CanvasActivity.this.canvasFragment.objectRenderer.updateDisplay();
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
		}
	}

	/**
	 * ファイルエクスプローラーにインテントを発行します。
	 * 
	 * @param requestCode
	 *            インテント時のリクエストコード
	 */
	public void sendFileChooseIntent(int requestCode) {
		final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*"); //$NON-NLS-1$
		startActivityForResult(intent, requestCode);
	}
}
