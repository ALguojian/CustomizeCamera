package com.alguojian.customizecamera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import com.alguojian.aldialog.dialog.TakePhotoDialog;
import com.alguojian.customizecamera.InterFace.PreViewImageViewListener;
import com.alguojian.customizecamera.activity.CameraActivity;
import com.alguojian.customizecamera.activity.LookPictureActivity;

/**
 * 开启拍照功能-包括权限申请等
 *
 * @author alguojian
 * @date 2018/6/6
 */
public class StartTakePhoto {

    public static final int REQUEST_CODE = 100;

    private static PreViewImageViewListener mPreViewImageViewListener;

    /**
     * 浏览图片
     *
     * @param path 浏览图片的地址
     * @param flag 是否已完成提交，只能预览，不能再次重新拍摄默认true，只能预览
     */
    public static void lookPicture(PreViewImageViewListener preViewImageViewListener, String path, boolean flag, Activity activity) {

        PermissionHelper permissionHelper = new PermissionHelper(activity);
        permissionHelper.requestPermission(Manifest.permission.CAMERA, () ->

                LookPictureActivity.start(activity, preViewImageViewListener, path, flag));
    }

    /**
     * 直接调用拍照功能
     *
     * @param activity
     */
    public static void startTakePhoto(PreViewImageViewListener preViewImageViewListener, Activity activity) {
        mPreViewImageViewListener = preViewImageViewListener;
        requestForAccess(activity);
    }

    /**
     * 申请权限
     *
     * @param activity
     */
    private static void requestForAccess(Activity activity) {
        PermissionHelper permissionHelper = new PermissionHelper(activity);
        permissionHelper.requestPermission(Manifest.permission.CAMERA, () -> {
            CameraActivity.start(activity, mPreViewImageViewListener);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    /**
     * 弹框选择拍照或者打开相册
     *
     * @param activity
     */
    public static void startTakePhotoAndGallery(PreViewImageViewListener preViewImageViewListener, Activity activity) {
        mPreViewImageViewListener = preViewImageViewListener;

        new TakePhotoDialog(activity).setOnTakePhotoListener(new TakePhotoDialog.TakePhoto() {
            @Override
            public void takePhoto() {
                requestForAccess(activity);
            }

            @Override
            public void searchPhoto() {

                PermissionHelper permissionHelper = new PermissionHelper(activity);
                permissionHelper.requestPermission(Manifest.permission.CAMERA, () -> {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(intent, REQUEST_CODE);
                });
            }
        }).show();
    }
}
