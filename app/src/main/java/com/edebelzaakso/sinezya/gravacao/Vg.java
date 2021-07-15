package com.edebelzaakso.sinezya.gravacao;

import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.view.SurfaceHolder;

import com.edebelzaakso.sinezya.gravacao.err.Ex;
import com.edebelzaakso.sinezya.gravacao.err.Si;
import com.edebelzaakso.sinezya.gravacao.err.So;
import com.edebelzaakso.sinezya.gravacao.inter.Ia;
import com.edebelzaakso.sinezya.gravacao.inter.Io;
import com.edebelzaakso.sinezya.gravacao.utl.Sa;
import com.edebelzaakso.sinezya.gravacao.utl.Su;

import java.io.IOException;

public class Vg implements OnInfoListener, Io {

    private Sa mCameraWrapper;
    private Oz mVideoCapturePreview;

    private final Cg mCaptureConfiguration;
    private final Va mVideoFile;

    private MediaRecorder mRecorder;
    private boolean mRecording = false;
    private final Ia mRecorderInterface;

    public Vg(Ia recorderInterface,
              Cg captureConfiguration,
              Va videoFile,
              Sa cameraWrapper,
              SurfaceHolder previewHolder,
              boolean useFrontFacingCamera) {
        mCaptureConfiguration = captureConfiguration;
        mRecorderInterface = recorderInterface;
        mVideoFile = videoFile;
        mCameraWrapper = cameraWrapper;

        initializeCameraAndPreview(previewHolder, useFrontFacingCamera);
    }

    protected void initializeCameraAndPreview(SurfaceHolder previewHolder, boolean useFrontFacingCamera) {
        try {
            mCameraWrapper.openCamera(useFrontFacingCamera);
        } catch (final Si e) {
            e.printStackTrace();
            mRecorderInterface.onRecordingFailed(e.getMessage());
            return;
        }

        mVideoCapturePreview = new Oz(this, mCameraWrapper, previewHolder);
    }

    public void toggleRecording() throws Ex {
        if (mCameraWrapper == null) {
            throw new Ex();
        }

        if (isRecording()) {
            stopRecording(null);
        } else {
            startRecording();
        }
    }

    protected void startRecording() {
        mRecording = false;

        if (!initRecorder()) return;
        if (!prepareRecorder()) return;
        if (!startRecorder()) return;

        mRecording = true;
        mRecorderInterface.onRecordingStarted();
    }

    public void stopRecording(String message) {
        if (!isRecording()) return;

        try {
            getMediaRecorder().stop();
            mRecorderInterface.onRecordingSuccess();
        } catch (final RuntimeException e) {
        }

        mRecording = false;
        mRecorderInterface.onRecordingStopped(message);
    }

    private boolean initRecorder() {
        try {
            mCameraWrapper.prepareCameraForRecording();
        } catch (final So e) {
            e.printStackTrace();
            mRecorderInterface.onRecordingFailed("Unable to record video");
            return false;
        }

        setMediaRecorder(new MediaRecorder());
        configureMediaRecorder(getMediaRecorder(), mCameraWrapper.getCamera());

        return true;
    }

    @SuppressWarnings("deprecation")
    protected void configureMediaRecorder(final MediaRecorder recorder, android.hardware.Camera camera)
            throws IllegalStateException, IllegalArgumentException {
        recorder.setCamera(camera);
        recorder.setAudioSource(mCaptureConfiguration.getAudioSource());
        recorder.setVideoSource(mCaptureConfiguration.getVideoSource());

        CamcorderProfile baseProfile = mCameraWrapper.getBaseRecordingProfile();
        baseProfile.fileFormat = mCaptureConfiguration.getOutputFormat();

        Su size = mCameraWrapper.getSupportedRecordingSize(mCaptureConfiguration.getVideoWidth(), mCaptureConfiguration.getVideoHeight());
        baseProfile.videoFrameWidth = size.l;
        baseProfile.videoFrameHeight = size.a;
        baseProfile.videoBitRate = mCaptureConfiguration.getVideoBitrate();

        baseProfile.audioCodec = mCaptureConfiguration.getAudioEncoder();
        baseProfile.videoCodec = mCaptureConfiguration.getVideoEncoder();

        recorder.setProfile(baseProfile);
        recorder.setMaxDuration(mCaptureConfiguration.getMaxCaptureDuration());
        recorder.setOutputFile(mVideoFile.getFullPath());
        recorder.setOrientationHint(mCameraWrapper.getRotationCorrection());
        recorder.setVideoFrameRate(mCaptureConfiguration.getVideoFPS());

        try {
            recorder.setMaxFileSize(mCaptureConfiguration.getMaxCaptureFileSize());
        } catch (IllegalArgumentException e) {
        } catch (RuntimeException e2) {
        }
        recorder.setOnInfoListener(this);
    }

    private boolean prepareRecorder() {
        try {
            getMediaRecorder().prepare();
            return true;
        } catch (final IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (final IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean startRecorder() {
        try {
            getMediaRecorder().start();
            return true;
        } catch (final IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (final RuntimeException e2) {
            e2.printStackTrace();
            mRecorderInterface.onRecordingFailed("Unable to record video with given settings");
            return false;
        }
    }

    protected boolean isRecording() {
        return mRecording;
    }

    protected void setMediaRecorder(MediaRecorder recorder) {
        mRecorder = recorder;
    }

    protected MediaRecorder getMediaRecorder() {
        return mRecorder;
    }

    private void releaseRecorderResources() {
        MediaRecorder recorder = getMediaRecorder();
        if (recorder != null) {
            recorder.release();
            setMediaRecorder(null);
        }
    }

    public void releaseAllResources() {
        if (mVideoCapturePreview != null) {
            mVideoCapturePreview.releasePreviewResources();
        }
        if (mCameraWrapper != null) {
            mCameraWrapper.releaseCamera();
            mCameraWrapper = null;
        }
        releaseRecorderResources();
    }

    @Override
    public void onCapturePreviewFailed() {
        mRecorderInterface.onRecordingFailed("Unable to show camera preview");
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        switch (what) {
            case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
                // NOP
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
                stopRecording("Capture stopped - Max duration reached");
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
                stopRecording("Capture stopped - Max file size reached");
                break;
            default:
                break;
        }
    }

}