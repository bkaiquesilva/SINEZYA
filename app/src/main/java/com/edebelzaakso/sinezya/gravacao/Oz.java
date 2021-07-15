package com.edebelzaakso.sinezya.gravacao;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.edebelzaakso.sinezya.gravacao.inter.Io;
import com.edebelzaakso.sinezya.gravacao.utl.Sa;

import java.io.IOException;

public class Oz implements SurfaceHolder.Callback {

	private boolean	uyy	= false;
	private final Io ioo;
	public final Sa saa;

	public Oz(Io io, Sa saaa,
			  SurfaceHolder h) {
		ioo = io;
		saa = saaa;

		iho(h);
	}

	@SuppressWarnings("deprecation")
	private void iho(final SurfaceHolder s) {
		s.removeCallback(this);
		s.addCallback(this);
		s.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(final SurfaceHolder holder) {
	}

	@Override
	public void surfaceChanged(final SurfaceHolder h, final int format, final int width, final int height) {
		if (uyy) {
			try {
				saa.stopPreview();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		try {
			saa.configureForPreview(width, height);
		} catch (final RuntimeException e) {
			e.printStackTrace();
			ioo.onCapturePreviewFailed();
			return;
		}

		try {
			saa.enableAutoFocus();
		} catch (final RuntimeException e) {
			e.printStackTrace();
		}

		try {
			saa.startPreview(h);
			gPre(true);
		} catch (final IOException e) {
			e.printStackTrace();
			ioo.onCapturePreviewFailed();
		} catch (final RuntimeException e) {
			e.printStackTrace();
			ioo.onCapturePreviewFailed();
		}
	}

	@Override
	public void surfaceDestroyed(final SurfaceHolder holder) {
	}

	public void releasePreviewResources() {
		if (uyy) {
			try {
				saa.stopPreview();
				gPre(false);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void gPre(boolean running) {
		uyy = running;
	}

	@SuppressWarnings("deprecation")
	public static boolean isFrontCameraAvailable() {
		int i;
		for (i = 0; i < Camera.getNumberOfCameras(); i++) {
			Camera.CameraInfo newInfo = new Camera.CameraInfo();
			Camera.getCameraInfo(i, newInfo);
			if (newInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				return true;
			}
		}

		return false;
	}


}