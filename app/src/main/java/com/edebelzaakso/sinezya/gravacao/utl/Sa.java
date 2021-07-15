package com.edebelzaakso.sinezya.gravacao.utl;

import android.annotation.TargetApi;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.SurfaceHolder;

import com.edebelzaakso.sinezya.gravacao.err.Si;
import com.edebelzaakso.sinezya.gravacao.err.So;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("deprecation")
public class Sa {

    private final int ioi;
    private Se sii = null;

    public Sa(Se pii, int dii) {
        sii = pii;
        ioi = dii;
    }

    public Camera getCamera() {
        return sii.getNativeCamera();
    }

    public void openCamera(boolean useFrontFacingCamera) throws Si {
        try {
            sii.openNativeCamera(useFrontFacingCamera);
        } catch (final RuntimeException e) {
            e.printStackTrace();
            throw new Si(Si.OpenType.INUSE);
        }

        if (sii.getNativeCamera() == null)
            throw new Si(Si.OpenType.NOCAMERA);
    }

    public void prepareCameraForRecording() throws So {
        try {
            sii.unlockNativeCamera();
        } catch (final RuntimeException e) {
            e.printStackTrace();
            throw new So();
        }
    }

    public void releaseCamera() {
        if (getCamera() == null) return;
        sii.releaseNativeCamera();
    }

    public void startPreview(final SurfaceHolder holder) throws IOException {
        sii.setNativePreviewDisplay(holder);
        sii.startNativePreview();
    }

    public void stopPreview() throws Exception {
        sii.stopNativePreview();
        sii.clearNativePreviewCallback();
    }

    public Su getSupportedRecordingSize(int width, int height) {
        Af recordingSize = getOptimalSize(getSupportedVideoSizes(VERSION.SDK_INT), width, height);
        if (recordingSize == null) {
            return new Su(width, height);
        }
        return new Su(recordingSize.getWidth(), recordingSize.getHeight());
    }

    public CamcorderProfile getBaseRecordingProfile() {
        CamcorderProfile returnProfile;
        if (VERSION.SDK_INT < VERSION_CODES.HONEYCOMB) {
            returnProfile = getDefaultRecordingProfile();
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
            returnProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
            returnProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        } else {
            returnProfile = getDefaultRecordingProfile();
        }
        return returnProfile;
    }

    private CamcorderProfile getDefaultRecordingProfile() {
        CamcorderProfile highProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        if (highProfile != null) {
            return highProfile;
        }
        CamcorderProfile lowProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
        if (lowProfile != null) {
            return lowProfile;
        }
        throw new RuntimeException("No quality level found");
    }

    public void configureForPreview(int viewWidth, int viewHeight) {
        final Parameters params = sii.getNativeCameraParameters();
        final Af previewSize = getOptimalSize(params.getSupportedPreviewSizes(), viewWidth, viewHeight);

        params.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
        params.setPreviewFormat(ImageFormat.NV21);
        sii.updateNativeCameraParameters(params);
        sii.setDisplayOrientation(getRotationCorrection());
    }

    public void enableAutoFocus() {
        final Parameters params = sii.getNativeCameraParameters();
        params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        sii.updateNativeCameraParameters(params);
    }

    public int getRotationCorrection() {
        int displayRotation = ioi * 90;
        if (sii.isFrontFacingCamera()) {
            int mirroredRotation = (sii.getCameraOrientation() + displayRotation) % 360;
            return (360 - mirroredRotation) % 360;
        } else {
            return (sii.getCameraOrientation() - displayRotation + 360) % 360;
        }
    }

    @TargetApi(VERSION_CODES.HONEYCOMB)
    protected List<Size> getSupportedVideoSizes(int currentSdkInt) {
        Parameters params = sii.getNativeCameraParameters();

        List<Size> supportedVideoSizes;
        if (currentSdkInt < VERSION_CODES.HONEYCOMB) {
            supportedVideoSizes = params.getSupportedPreviewSizes();
        } else if (params.getSupportedVideoSizes() == null) {
            supportedVideoSizes = params.getSupportedPreviewSizes();
        } else {
            supportedVideoSizes = params.getSupportedVideoSizes();
        }

        return supportedVideoSizes;
    }

    public Af getOptimalSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        final double targetRatio = (double) w / h;
        if (sizes == null) return null;
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        final int targetHeight = h;
        for (final Size size : sizes) {
            final double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (final Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return new Af(optimalSize.width, optimalSize.height);
    }
}