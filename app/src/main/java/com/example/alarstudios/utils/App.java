package com.example.alarstudios.utils;

import android.app.Application;
import com.example.alarstudios.di.components.AppComponent;
import com.example.alarstudios.di.components.DaggerAppComponent;
import lombok.Getter;

public class App extends Application {

  @Getter
  private static App INSTANCE;

  @Getter
  private static AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    INSTANCE = this;

    if (appComponent == null){
      appComponent = DaggerAppComponent.builder().build();
    }
  }
}
