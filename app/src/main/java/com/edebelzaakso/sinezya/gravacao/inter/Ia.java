package com.edebelzaakso.sinezya.gravacao.inter;

public interface Ia {

	public abstract void onRecordingStopped(String message);

	public abstract void onRecordingStarted();

	public abstract void onRecordingSuccess();

	public abstract void onRecordingFailed(String message);

}