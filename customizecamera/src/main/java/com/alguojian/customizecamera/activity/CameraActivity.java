package com.alguojian.customizecamera.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alguojian.customizecamera.InterFace.PreViewImageViewListener;
import com.alguojian.customizecamera.R;
import com.alguojian.customizecamera.view.CameraView;
import com.bumptech.glide.Glide;

public class CameraActivity extends AppCompatActivity {

    private static final int INT = 102;
    private static PreViewImageViewListener sPreViewImageViewListener;
    CameraView libraryCameraView;
    ImageView imageView;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    ImageView frame;
    LinearLayout linearLayout3;
    LinearLayout linearLayout4;
    TextView title;
    TextView cancel;
    Button picture;
    TextView retake;
    FrameLayout control;
    private String mString = null;

    public static void start(Context context, PreViewImageViewListener preViewImageViewListener) {
        Intent starter = new Intent(context, CameraActivity.class);
        sPreViewImageViewListener = preViewImageViewListener;
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        initView();
        libraryCameraView.open(this);
    }

    private void initView() {
        libraryCameraView = findViewById(R.id.library_camera_view);
        imageView = findViewById(R.id.imageView);
        linearLayout1 = findViewById(R.id.linearLayout1);
        linearLayout2 = findViewById(R.id.linearLayout2);
        frame = findViewById(R.id.frame);
        linearLayout3 = findViewById(R.id.linearLayout3);
        linearLayout4 = findViewById(R.id.linearLayout4);
        title = findViewById(R.id.title);
        cancel = findViewById(R.id.cancel);
        picture = findViewById(R.id.picture);
        retake = findViewById(R.id.retake);
        control = findViewById(R.id.control);

        cancel.setVisibility(View.VISIBLE);
        picture.setVisibility(View.VISIBLE);
        picture.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_ffffff));
        title.setText(R.string.please_set_paizi);
        frame.setVisibility(View.VISIBLE);
        linearLayout1.getBackground().setAlpha(INT);
        linearLayout2.getBackground().setAlpha(INT);
        linearLayout3.getBackground().setAlpha(INT);
        linearLayout4.getBackground().setAlpha(INT);

        picture.setOnClickListener(v -> {

            if (getString(R.string.take_photo).contentEquals(picture.getText())) {

                libraryCameraView.takePhoto((PreViewImageViewListener) path -> {
                    mString = path;
                    pictureFinish();
                });
            } else if (getString(R.string.finish_text).contentEquals(picture.getText())) {
                if (sPreViewImageViewListener != null && mString != null) {
                    sPreViewImageViewListener.imagePath(mString);
                }
                finish();
            }
        });

        retake.setOnClickListener(v -> retake());

        cancel.setOnClickListener(v -> finish());
    }

    private void pictureFinish() {

        imageView.setVisibility(View.VISIBLE);
        picture.setText(R.string.finish_text);
        title.setText(R.string.take_photo_finish);
        picture.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_3090ff));
        Glide.with(this).load(mString).into(imageView);
        retake.setVisibility(View.VISIBLE);
    }

    private void retake() {

        picture.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_ffffff));

        retake.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        picture.setText(R.string.take_photo);
        title.setText(R.string.please_set_paizi);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        libraryCameraView.close(this);
    }
}
