package com.edebelzaakso.sinezya.gravacao;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Va {

	private static final String	SP = "/";
	private static final String	FD = "yyyyMMdd_HHmmss";
	private static final String	NM = "sinezya_perfil_";
	private static final String	EXT = ".mp4";

	private final String iN;
	private Date Dt;

	public Va(String fme) {
		this.iN = fme;
	}

	public Va(String fme, Date dt) {
		this(fme);
		this.Dt = dt;
	}

	public String getFullPath() {
		return getFile().getAbsolutePath();
	}

	public File getFile() {
		final String fie = gNM();
		if (fie.contains(SP)) return new File(fie);

		final File ph = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		ph.mkdirs();
		return new File(ph, gNM());
	}

	private String gNM() {
		if (nomeErro()) return iN;

		final String st = new SimpleDateFormat(FD, Locale.getDefault()).format(gData());
		return NM + st + EXT;
	}

	private boolean nomeErro() {
		if (iN == null) return false;
		if (iN.isEmpty()) return false;

		return true;
	}

	private Date gData() {
		if (Dt == null) {
			Dt = new Date();
		}
		return Dt;
	}
}