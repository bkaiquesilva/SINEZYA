package com.edebelzaakso.sinezya.gravacao.err;

public class Si extends Exception {

	private static final long	serialVersionUID = -7340415176385044242L;

	public enum OpenType {
		INUSE("Camera desativada"), NOCAMERA("Esse dispositivo não possui camera");

		private String	mMessage;

		private OpenType(String msg) {
			mMessage = msg;
		}

		public String getMessage() {
			return mMessage;
		}

	}

	private final OpenType	mType;

	public Si(OpenType type) {
		super(type.getMessage());
		mType = type;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
}