package com.edebelzaakso.sinezya.gravacao.utl;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;

import java.io.IOException;

public class Se {

    private Camera c = null;
    private boolean eyy = false;
    private Parameters p = null;

    public Camera getNativeCamera() {
        return c;
    }

    public void openNativeCamera(boolean useFrontFacingCamera) throws RuntimeException {
        if (useFrontFacingCamera) {
            if (!hasFrontFacingCamera()) return;
            c = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            eyy = true;
        } else {
            c = Camera.open(CameraInfo.CAMERA_FACING_BACK);
        }
    }

    public void unlockNativeCamera() {
        c.unlock();
    }

    public void releaseNativeCamera() {
        c.release();
    }

    public void setNativePreviewDisplay(SurfaceHolder holder) throws IOException {
        c.setPreviewDisplay(holder);
    }

    public void startNativePreview() {
        c.startPreview();
    }

    public void stopNativePreview() {
        c.stopPreview();
    }

    public void clearNativePreviewCallback() {
        c.setPreviewCallback(null);
    }

    public Parameters getNativeCameraParameters() {
        if (p == null) {
            p = c.getParameters();
        }
        return p;
    }

    public void updateNativeCameraParameters(Parameters params) {
        this.p = params;
        c.setParameters(params);
    }

    public void setDisplayOrientation(int degrees) {
        c.setDisplayOrientation(degrees);
    }

    public int getCameraOrientation() {
        CameraInfo camInfo = new CameraInfo();
        Camera.getCameraInfo(getCurrentCameraId(), camInfo);
        return camInfo.orientation;
    }

    public boolean isFrontFacingCamera() {
        return eyy;
    }

    private int getCurrentCameraId() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == getCurrentCameraFacing()) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private int getCurrentCameraFacing() {
        return eyy ? CameraInfo.CAMERA_FACING_FRONT : CameraInfo.CAMERA_FACING_BACK;
    }

    private boolean hasFrontFacingCamera() {
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            CameraInfo newInfo = new CameraInfo();
            Camera.getCameraInfo(i, newInfo);
            if (newInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }
}