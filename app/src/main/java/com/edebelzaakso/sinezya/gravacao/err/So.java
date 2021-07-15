package com.edebelzaakso.sinezya.gravacao.err;

public class So extends Exception {

	private static final String	MESSAGE = "Erro";

	private static final long serialVersionUID = 6305923762266448674L;

	@Override
	public String getMessage() {
		return MESSAGE;
	}
}