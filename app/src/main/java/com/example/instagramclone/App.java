package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AzHruwejfbujub4HgzRaHY24rFMrwSpxuhsrJ5WM")
                // if defined
                .clientKey("sd3GQmW17mADvqVX0GUvgcN7BXFnKv1btiR8VOF2")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
