package com.alguojian.customizecamera.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.alguojian.customizecamera.InterFace.PreViewImageViewListener;
import com.alguojian.customizecamera.InterFace.PreviewFrameListener;
import com.alguojian.customizecamera.R;
import com.alguojian.customizecamera.utils.CUtilts;
import com.alguojian.customizecamera.utils.CameraHelper;
import com.alguojian.customizecamera.utils.CameraUtils;

/**
 * camera操作工具
 *
 * @author alguojian
 * @date 2018.06.01
 */
public class CameraView extends FrameLayout {

    public static int CAMERA_BACK = 0;
    public static int CAMERA_FRONT = 1;
    private View rootView;
    private SurfaceView sfv_camera_view;
    private Activity mActivity;
    private int current_camrea = CAMERA_BACK;
    private Bitmap waterMaskBitmap = null;
    private boolean camera_is_open = false;


    public CameraView(@NonNull Context context) {
        super(context, null);
    }


    public CameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CameraView);
        current_camrea = a.getInt(R.styleable.CameraView_direction, Position.BACK.ordinal());
        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        rootView = View.inflate(context, R.layout.layout_camera_view, this);
        sfv_camera_view = rootView.findViewById(R.id.sfv_camera_view);
    }

    /**
     * 打开相机
     *
     * @param mActivity
     */
    public void open(Activity mActivity) {
        this.mActivity = mActivity;
        CameraUtils.getInstance().openCamera(sfv_camera_view, mActivity, current_camrea);
        camera_is_open = true;
    }

    /**
     * 关闭相机
     *
     * @param mActivity
     */
    public void close(Activity mActivity) {
        if (cameraIsOpen()) {
            CameraUtils.getInstance().closeCamera(sfv_camera_view, mActivity);
        }
    }

    private boolean cameraIsOpen() {
        return camera_is_open;
    }

    /**
     * 切换摄像头（相反切换）
     */
    public void ChangeCamera() {
        if (cameraIsOpen()) {
            CameraUtils.getInstance().changeCamera(sfv_camera_view, mActivity);
        }
    }

    /**
     * 切换摄像头（指定切换）
     */
    public void ChangeCamera(int direction) {
        if (cameraIsOpen()) {
            CameraUtils.getInstance().changeCamera(sfv_camera_view, mActivity, direction);
        }
        current_camrea = direction;
    }

    /**
     * 拍照
     *
     * @param mTakeSuccess
     * @return
     */
    public Bitmap takePhoto(CameraHelper.takeSuccess mTakeSuccess) {
        return CameraHelper.getInstance().takePhoto(mTakeSuccess);
    }

    /**
     * 拍照
     *
     * @return
     */
    public void takePhoto(PreViewImageViewListener preViewImageViewListener) {
        CameraHelper.getInstance().takePhoto(mBitmap ->
                CUtilts.saveBitmap(mBitmap,preViewImageViewListener));
    }

    /**
     * 判断当前相机是否是前置摄像头
     *
     * @return
     */
    public Boolean currentCameraIsFront() {
        return CameraHelper.getInstance().checkCameraIsFront();
    }

    public void setPreviewFrameListener(PreviewFrameListener mPreviewFrameListener) {
        CameraHelper.getInstance().setmPreviewFrameListener(mPreviewFrameListener);
    }

    private enum Position {
        BACK,
        FRONT,
    }

    ;
}
