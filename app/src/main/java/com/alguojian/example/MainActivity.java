package com.alguojian.example;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.alguojian.customizecamera.StartTakePhoto;
import com.bumptech.glide.Glide;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String mString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.dddd).setOnClickListener(v ->

                StartTakePhoto.startTakePhoto(path -> {
                    mString = path;
                    Glide.with(MainActivity.this)
                            .load(path)
                            .into((ImageView) findViewById(R.id.image));

                }, MainActivity.this));

        findViewById(R.id.image).setOnClickListener(v ->

                StartTakePhoto.lookPicture(path -> {
                    mString = path;
                    Glide.with(MainActivity.this)
                            .load(path)
                            .into((ImageView) findViewById(R.id.image));

                }, mString, true, MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != StartTakePhoto.REQUEST_CODE) {
            return;
        }
        Bitmap bm;
        ContentResolver resolver = getContentResolver();
        if (requestCode == StartTakePhoto.REQUEST_CODE) {
            try {
                Uri originalUri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(originalUri, proj, null, null,
                        null);
                cursor.moveToFirst();
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                Glide.with(MainActivity.this)
                        .load(bm)
                        .into((ImageView) findViewById(R.id.image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
