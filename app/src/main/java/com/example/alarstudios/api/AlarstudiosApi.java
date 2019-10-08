package com.example.alarstudios.api;

import com.example.alarstudios.model.AuthModel;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlarstudiosApi {

  @GET("auth.cgi")
  Single<AuthModel> authentification(@Query("username") String username, @Query("password") String password);

}
