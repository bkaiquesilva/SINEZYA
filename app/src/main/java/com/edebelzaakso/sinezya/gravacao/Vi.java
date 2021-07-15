package com.edebelzaakso.sinezya.gravacao;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.edebelzaakso.sinezya.R;
import com.edebelzaakso.sinezya.gravacao.inter.Ie;

public class Vi extends FrameLayout implements OnClickListener {

    private ImageView mDeclineBtnIv;
    private ImageView mAcceptBtnIv;
    private TextView regressiva;

    private SurfaceView mSurfaceView;
    private ImageView mThumbnailIv;
    private TextView mTimerTv;
    private Handler customHandler = new Handler();
    private long startTime = 0L;

    private Ie mRecordingInterface;
    private boolean mShowTimer = true;

    public Vi(Context context) {
        super(context);
        vvv(context);
    }

    public Vi(Context context, AttributeSet attrs) {
        super(context, attrs);
        vvv(context);
    }

    public Vi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        vvv(context);
    }

    private void vvv(Context context) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.AppTheme_NoFLU);
        final View videoCapture = View.inflate(contextThemeWrapper, R.layout.i_viou, this);

        regressiva = videoCapture.findViewById(R.id.regressiva);
        mAcceptBtnIv = videoCapture.findViewById(R.id.btn_aceitar);
        mDeclineBtnIv = videoCapture.findViewById(R.id.btn_descartar);

        mAcceptBtnIv.setOnClickListener(this);
        mDeclineBtnIv.setOnClickListener(this);

        mThumbnailIv = videoCapture.findViewById(R.id.videocapture_preview_iv);
        mSurfaceView = videoCapture.findViewById(R.id.videocapture_preview_sv);

        mTimerTv = videoCapture.findViewById(R.id.videocapture_timer_tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                regressiva.setText("4");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        regressiva.setText("3");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                regressiva.setText("2");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        regressiva.setText("1");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                regressiva.setText("0");
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        regressiva.setVisibility(GONE);
                                                        mRecordingInterface.onRecordButtonClicked();
                                                    }
                                                }, 200);
                                            }
                                        }, 1000);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        }, 1000);
    }

    public void setRecordingButtonInterface(Ie mBtnInterface) {
        this.mRecordingInterface = mBtnInterface;
    }

    public SurfaceHolder getPreviewSurfaceHolder() {
        return mSurfaceView.getHolder();
    }

    public void updateUINotRecording() {
        mAcceptBtnIv.setVisibility(View.GONE);
        mDeclineBtnIv.setVisibility(View.GONE);
        mThumbnailIv.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);

    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            updateRecordingTime(seconds, minutes);
            customHandler.postDelayed(this, 1000);
        }
    };

    public void updateUIRecordingOngoing() {
        mAcceptBtnIv.setVisibility(View.GONE);
        mDeclineBtnIv.setVisibility(View.GONE);
        mThumbnailIv.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);

        if (mShowTimer) {
            mTimerTv.setVisibility(View.VISIBLE);
            startTime = SystemClock.uptimeMillis();
            updateRecordingTime(0, 0);
            customHandler.postDelayed(updateTimerThread, 1000);
        }
    }

    public void updateUIRecordingFinished(Bitmap videoThumbnail) {
        mAcceptBtnIv.setVisibility(View.VISIBLE);
        mDeclineBtnIv.setVisibility(View.VISIBLE);
        mThumbnailIv.setVisibility(View.VISIBLE);
        mSurfaceView.setVisibility(View.GONE);

        if (videoThumbnail != null) {
            mThumbnailIv.setScaleType(ScaleType.CENTER_CROP);
            mThumbnailIv.setImageBitmap(videoThumbnail);
        }
        customHandler.removeCallbacks(updateTimerThread);

    }

    @Override
    public void onClick(View v) {
        if (mRecordingInterface == null) return;

       if (v.getId() == mAcceptBtnIv.getId()) {
            mRecordingInterface.onAcceptButtonClicked();
        } else if (v.getId() == mDeclineBtnIv.getId()) {
            mRecordingInterface.onDeclineButtonClicked();
        }

    }

    public void showTimer(boolean showTimer) {
        this.mShowTimer = showTimer;
    }

    private void updateRecordingTime(int seconds, int minutes) {
        mTimerTv.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }
}