package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        final TextView noImagesTextView = findViewById(R.id.noImageTextView);
        final LinearLayout feedLinearLayout = findViewById(R.id.feedLinearLayout);

        Intent received_intent = getIntent();
        String selectedUser = received_intent.getStringExtra("username");
        setTitle(selectedUser + "'s Feed");

        ParseQuery<ParseObject> query = new ParseQuery<>("Image");
        query.whereEqualTo("username", selectedUser);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null){
                    noImagesTextView.setVisibility(View.GONE);

                    for (ParseObject object:objects){
                        ParseFile file = (ParseFile) object.get("image");

                        //decode image file
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                ImageView imageView = new ImageView(getApplicationContext());
                                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                if (data != null && e == null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);


                                    imageView.setImageBitmap(bitmap);
                                }else {
                                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
                                }
                                feedLinearLayout.addView(imageView);
                            }
                        });
                    }
                }else {
                    Toast.makeText(FeedActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


/*
* LinearLayout feedLinearLayout = findViewById(R.id.feedLinearLayout);

        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.alert_dark_frame));

        feedLinearLayout.addView(imageView);
*/