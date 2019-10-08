package com.example.alarstudios.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {

  private Context appContext;

  public AppModule(Context context) {
    appContext = context;
  }

  @Provides
  @Singleton
  Context provideContext() {
    return appContext;
  }
}
