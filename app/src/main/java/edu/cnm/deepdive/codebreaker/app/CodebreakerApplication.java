package edu.cnm.deepdive.codebreaker.app;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class CodebreakerApplication extends Application {

  // When we override onCreate, we MUST invoke super.onCreate();
  // Invoked when application loads. (pre-UI) application wide setup.
  @Override
  public void onCreate() {
    super.onCreate();
  }

}
