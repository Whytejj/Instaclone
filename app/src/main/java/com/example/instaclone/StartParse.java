package com.example.instaclone;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StartParse extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("78b15d53eed3127137a2dbb58d15bfc4ebdb3e03")
                // if defined
                .clientKey("fcbff1ae34779eba737441e8ef3fba863b6ea2f6")
                .server("http://18.225.6.94:80/parse/")
                .build()
        );

        /*
        ParseObject object = new ParseObject("ExampleObject");
        object.put("myNumber", "234");
        object.put("myString", "jay");

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException ex) {
                if (ex == null) {
                    Log.i("parsesetup", "Parse Successful!");
                } else {
                    Log.i("parsesetup", "Parse Failed" + ex.toString());
                }
            }
        });
        */



        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}