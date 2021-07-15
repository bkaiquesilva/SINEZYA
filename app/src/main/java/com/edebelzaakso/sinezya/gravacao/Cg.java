package com.edebelzaakso.sinezya.gravacao;

import android.media.MediaRecorder;
import android.os.Parcel;
import android.os.Parcelable;

public class Cg implements Parcelable {

    public static final int A = 1024 * 1024;
    public static final int B = 1000;

    public static final int C = -1;

    private int W = Oo.W_720P;
    private int H = Oo.H_720P;
    private int T = Oo.HQ_720P;
    private int Du = 3 * B;
    private int Ta = C;
    private boolean Crono = true;
    private boolean Af = true;
    private int Fra = Oo.FPS_30;     //Default FPS is 30.

    private int FO = MediaRecorder.OutputFormat.MPEG_4;
    private int AC = MediaRecorder.AudioSource.DEFAULT;
    private int AD = MediaRecorder.AudioEncoder.AAC;
    private int VC = MediaRecorder.VideoSource.CAMERA;
    private int VD = MediaRecorder.VideoEncoder.H264;

    public static Cg getDefault() {
        return new Cg();
    }

    private Cg() {
    }

    @Deprecated
    public Cg(Oo.Ijy r, Oo.Uu q) {
        W = r.l;
        H = r.a;
        T = r.getKK(q);
    }

    @Deprecated
    public Cg(Oo.Ijy r, Oo.Uu q, int maxDurationSecs,
              int maxFilesizeMb, boolean showTimer) {
        this(r, q, maxDurationSecs, maxFilesizeMb, showTimer, false);
        this.Crono = showTimer;
    }

    @Deprecated
    public Cg(Oo.Ijy r, Oo.Uu q, int maxDurationSecs,
              int maxFilesizeMb, boolean showTimer, boolean allowFrontFacingCamera) {
        this(r, q, maxDurationSecs, maxFilesizeMb);
        this.Crono = showTimer;
        this.Af = allowFrontFacingCamera;
    }

    @Deprecated
    public Cg(Oo.Ijy r, Oo.Uu q, int maxDurationSecs,
              int maxFilesizeMb, boolean showTimer, boolean allowFrontFacingCamera,
              int videoFPS) {
        this(r, q, maxDurationSecs, maxFilesizeMb, showTimer, allowFrontFacingCamera);
        Fra = videoFPS;
    }

    @Deprecated
    public Cg(Oo.Ijy r, Oo.Uu q, int maxDurationSecs,
              int maxFilesizeMb) {
        this(r, q);
        Du = maxDurationSecs * B;
        Ta = maxFilesizeMb * A;
    }

    @Deprecated
    public Cg(int videoWidth, int videoHeight, int bitrate) {
        this.W = videoWidth;
        this.H = videoHeight;
        this.T = bitrate;
    }

    @Deprecated
    public Cg(int videoWidth, int videoHeight, int bitrate, int maxDurationSecs, int maxFilesizeMb) {
        this(videoWidth, videoHeight, bitrate);
        Du = maxDurationSecs * B;
        Ta = maxFilesizeMb * A;
    }

    /**
     * @return Width of the captured video in pixels
     */
    public int getVideoWidth() {
        return W;
    }

    /**
     * @return Height of the captured video in pixels
     */
    public int getVideoHeight() {
        return H;
    }

    /**
     * @return Bitrate of the captured video in bits per second
     */
    public int getVideoBitrate() {
        return T;
    }

    /**
     * @return Maximum duration of the captured video in milliseconds
     */
    public int getMaxCaptureDuration() {
        return Du;
    }

    /**
     * @return Maximum filesize of the captured video in bytes
     */
    public int getMaxCaptureFileSize() {
        return Ta;
    }

    /**
     * @return If timer must be displayed during video capture
     */
    public boolean getShowTimer() {
        return Crono;
    }

    /**
     * @return If front facing camera toggle must be displayed before capturing video
     */
    public boolean getAllowFrontFacingCamera() {
        return Af;
    }

    public int getOutputFormat() {
        return FO;
    }

    public int getAudioSource() {
        return AC;
    }

    public int getAudioEncoder() {
        return AD;
    }

    public int getVideoSource() {
        return VC;
    }

    public int getVideoEncoder() {
        return VD;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(W);
        dest.writeInt(H);
        dest.writeInt(T);
        dest.writeInt(Du);
        dest.writeInt(Ta);
        dest.writeInt(Fra);
        dest.writeByte((byte) (Crono ? 1 : 0));
        dest.writeByte((byte) (Af ? 1 : 0));

        dest.writeInt(FO);
        dest.writeInt(AC);
        dest.writeInt(AD);
        dest.writeInt(VC);
        dest.writeInt(VD);
    }

    public static final Creator<Cg> CREATOR = new Creator<Cg>() {
        @Override
        public Cg createFromParcel(
                Parcel in) {
            return new Cg(in);
        }

        @Override
        public Cg[] newArray(
                int size) {
            return new Cg[size];
        }
    };

    private Cg(Parcel in) {
        W = in.readInt();
        H = in.readInt();
        T = in.readInt();
        Du = in.readInt();
        Ta = in.readInt();
        Fra = in.readInt();
        Crono = in.readByte() != 0;
        Af = in.readByte() != 0;

        FO = in.readInt();
        AC = in.readInt();
        AD = in.readInt();
        VC = in.readInt();
        VD = in.readInt();
    }

    public int getVideoFPS() {
        return Fra;
    }

    public static class Builder {

        private final Cg configuration;

        public Builder(Oo.Ijy r, Oo.Uu q) {
            configuration = new Cg();
            configuration.W = r.l;
            configuration.H = r.a;
            configuration.T = r.getKK(q);
        }

        public Builder(int width, int height, int bitrate) {
            configuration = new Cg();
            configuration.W = width;
            configuration.H = height;
            configuration.T = bitrate;
        }

        public Cg build() {
            return configuration;
        }

        public Builder maxDuration(int maxDurationSec) {
            configuration.Du = 3 * B;
            return this;
        }

        public Builder maxFileSize(int maxFileSizeMb) {
            configuration.Ta = maxFileSizeMb * A;
            return this;
        }

        public Builder frameRate(int framesPerSec) {
            configuration.Fra = framesPerSec;
            return this;
        }

        public Builder showRecordingTime() {
            configuration.Crono = true;
            return this;
        }

        public Builder noCameraToggle() {
            configuration.Af = false;
            return this;
        }
    }
}