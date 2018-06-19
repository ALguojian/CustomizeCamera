package com.alguojian.customizecamera.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alguojian.customizecamera.InterFace.PreViewImageViewListener;
import com.alguojian.customizecamera.R;
import com.alguojian.imagegesture.ImageGesture;
import com.alguojian.imagegesture.view.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.guojian.aldialog.dialog.LoadingDialog;

public class LookPictureActivity extends AppCompatActivity {

    private static boolean mflag;
    private static String mStrin;
    private static PreViewImageViewListener sPreViewImageViewListener;
    private PhotoView previewImage;
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

        final LoadingDialog loadingDialog = new LoadingDialog(LookPictureActivity.this, 2);

        loadingDialog.show();

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        RequestListener<Drawable> bitmapRequestListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                loadingDialog.dismiss();
                Toast.makeText(LookPictureActivity.this,"加载失败",Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                loadingDialog.dismiss();

                return false;
            }
        };

        Glide.with(this)
                .load(mStrin)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(bitmapRequestListener)
                .apply(requestOptions)
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
        previewImage.enable();
        takeAgain = findViewById(R.id.takeAgain);
        finish = findViewById(R.id.finish);
    }
}
