/*
 * Created on 2015/01/21
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;


/**
 * モデルデータを別のスレッドでダウンロードするためのクラスです。
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class DownloadFileFromURL extends AsyncTask<String, String, InputStream>{

  /** ストリーム*/
  public InputStream input;
  /** NavigationDrawer*/
  public NavigationDrawerFragment fragment;
  
  /**
   * 新しく生成された<code>DownloadFileFromURL</code>オブジェクトを初期化します。
   * @param fragment NavigationDrawer
   */
  public DownloadFileFromURL(NavigationDrawerFragment fragment) {
    this.fragment = fragment;
  }
  @Override
  protected InputStream doInBackground(String... params) {
//    String url = "http://www.mk.ces.kyutech.ac.jp/?page_id=18";
    String url = params[0];
    HttpClient httpClient = new DefaultHttpClient();
//    HttpParams param = httpClient.getParams();
//    HttpConnectionParams.setConnectionTimeout(param, 1000);
//    HttpConnectionParams.setSoTimeout(param, 1000);
//    StringBuilder url= new StringBuilder("http://www.mk.ces.kyutech.ac.jp/?page_id=18");
    HttpGet request = new HttpGet(url.toString());
    
    HttpResponse httpResponse = null;
    try {
        httpResponse = httpClient.execute(request);
    } catch (Exception e) {
        Log.d("HttpSampleActivity", e.getMessage()); //$NON-NLS-1$
    }
    try {
      if(HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
        InputStream inputStream = httpResponse.getEntity().getContent();
        this.input = inputStream;
      }
    } catch (IllegalStateException e) {
        // TODO 自動生成された catch ブロック
      throw new RuntimeException(e);
    } catch (IOException e) {
      // TODO 自動生成された catch ブロック
      throw new RuntimeException(e);
    } catch (NullPointerException e) {
        // show dialog of network connection
    }
    return this.input;
  }
  /**
   * @param strings  String
   */
  @Override
  protected void onProgressUpdate(String...strings) {
    // nothing to do
  }
  
  @Override 
  protected void onPostExecute(InputStream input) {
    this.fragment.input = input;
    Drawable d = Drawable.createFromStream(input, "webimage"); //$NON-NLS-1$
    this.fragment.sampleModelButton.setBackground(d);
  }
}
