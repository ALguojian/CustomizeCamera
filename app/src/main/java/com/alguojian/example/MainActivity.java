package com.alguojian.example;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alguojian.customizecamera.CameraActivity;
import com.bumptech.glide.Glide;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.takePhoto).setOnClickListener(v ->

                CameraActivity.start(MainActivity.this, path -> Glide.with(MainActivity.this)
                        .load(path)
                        .into((ImageView) findViewById(R.id.image))));

        findViewById(R.id.searchPhoto).setOnClickListener(v -> {

            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, 10000);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }
        Bitmap bm;
        ContentResolver resolver = getContentResolver();
        if (requestCode == 10000) {
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
