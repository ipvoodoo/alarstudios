package com.example.alarstudios.di.components;

import com.example.alarstudios.MainActivity;
import com.example.alarstudios.di.modules.AppModule;
import com.example.alarstudios.di.modules.NetworkModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

  void inject(MainActivity injector);

}
