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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;


/**
 * モデルデータを別のスレッドでダウンロードするためのクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class DownloadFileFromURL extends AsyncTask<String, String, InputStream> {

  /** ストリーム */
  private InputStream input;
  /** NavigationDrawer */
  private NavigationDrawerFragment fragment;

  /**
   * 新しく生成された<code>DownloadFileFromURL</code>オブジェクトを初期化します。
   * 
   * @param fragment NavigationDrawer
   */
  public DownloadFileFromURL(NavigationDrawerFragment fragment) {
    this.fragment = fragment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected InputStream doInBackground(String... params) {
    final String url = params[0];
    final HttpClient httpClient = new DefaultHttpClient();
    final HttpGet request = new HttpGet(url.toString());

    try {
      final HttpResponse httpResponse = httpClient.execute(request);
      if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
        this.input = httpResponse.getEntity().getContent();
      }
    } catch (ClientProtocolException e1) {
      Log.d("HttpSampleActivity", e1.getMessage()); //$NON-NLS-1$
      throw new RuntimeException(e1);
    } catch (IOException e1) {
      Log.d("HttpSampleActivity", e1.getMessage()); //$NON-NLS-1$
      throw new RuntimeException(e1);
    }
    
    return this.input;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onProgressUpdate(String... strings) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onPostExecute(InputStream anInput) {
    this.fragment.input = anInput;
    final Drawable d = Drawable.createFromStream(anInput, "webimage"); //$NON-NLS-1$
  }
}
