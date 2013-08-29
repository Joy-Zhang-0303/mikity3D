package org.openintents.intents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.mklab.mikity.android.MainActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;

public class FileManager extends Activity{

  private int  REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  private Activity activity;

  public FileManager(MainActivity mainActivity) {
    this.activity = mainActivity;
  }

  /**
   * Opens the file manager to pick a file.
   */
  public void getFilePath() {

    Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

    try {
      activity.startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY );
    } catch (ActivityNotFoundException e) {
      // No compatible file manager was found.
      Toast.makeText(this, "TEST", Toast.LENGTH_SHORT).show();
    }

  }
  
  
	
}