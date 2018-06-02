package com.alguojian.customizecamera.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alguojian.customizecamera.InterFace.PreViewImageViewListener;
import com.alguojian.customizecamera.R;
import com.bumptech.glide.Glide;

public class LookPictureActivity extends AppCompatActivity {

    private static boolean mflag;
    private static String mStrin;
    private static PreViewImageViewListener sPreViewImageViewListener;
    private ImageView previewImage;
    private TextView takeAgain;
    private TextView finish;

    /**
     * @param context
     * @param preViewImageViewListener 回调拍照地址到一个页面，只针对 flag是false
     * @param path                     图片预览地址
     * @param flag                     是否已完成提交，只能预览，不能再次重新拍摄默认true，只能预览
     */
    public static void start(Context context, PreViewImageViewListener preViewImageViewListener, String path, boolean flag) {
        Intent starter = new Intent(context, LookPictureActivity.class);
        mStrin = path;
        mflag = flag;
        sPreViewImageViewListener = preViewImageViewListener;
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_picture);
        initView();

        Glide.with(this)
                .load(mStrin)
                .into(previewImage);

        finish.setOnClickListener(v -> finish());

        if (mflag) {
            takeAgain.setVisibility(View.GONE);
        } else {
            takeAgain.setVisibility(View.VISIBLE);

        }
        takeAgain.setOnClickListener(v -> {
            CameraActivity.start(LookPictureActivity.this, sPreViewImageViewListener);
            finish();
        });
    }

    private void initView() {
        previewImage = findViewById(R.id.previewImage);
        takeAgain = findViewById(R.id.takeAgain);
        finish = findViewById(R.id.finish);
    }
}
