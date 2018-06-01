package com.alguojian.customizecamera.InterFace;

import android.hardware.Camera;

/**
 * 获得拍照之后的图片对象
 *
 * @author alguojian
 * @date 2018.06.01
 */
public interface PreviewFrameListener {

    void onPreviewFrameListener(byte[] data, Camera camera);
}
