package com.edebelzaakso.sinezya.gravacao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;
import com.edebelzaakso.sinezya.R;
import com.edebelzaakso.sinezya.gravacao.err.Ex;
import com.edebelzaakso.sinezya.gravacao.inter.Ia;
import com.edebelzaakso.sinezya.gravacao.inter.Ie;
import com.edebelzaakso.sinezya.gravacao.utl.Sa;
import com.edebelzaakso.sinezya.gravacao.utl.Se;
import com.edebelzaakso.sinezya.postgif.MeuAlbumActivity;
import com.edebelzaakso.sinezya.postgif.VParaGIFActivity;
import com.edebelzaakso.sinezya.videotogif.VideoToGIFActivity;

import java.io.File;

public class AAAA extends Activity implements Ie, Ia {

    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;
    private boolean mIv = false;
    Va mVa = null;
    private Cg mCg;
    private Vi mVi;
    private Vg mVg;
    private boolean Fr;
    private String retorno;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaaa);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AAAA.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        Bundle extras = getIntent().getExtras();
        retorno = extras.getString("moup");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iee(savedInstanceState);

        mVi = findViewById(R.id.videocapture_videocaptureview_vcv);
        if (mVi == null) return;

        iaa();
    }

    private void iee(final Bundle savedInstanceState) {
        mCg = generateCaptureConfiguration();
        mIv = generateVideoRecorded(savedInstanceState);
        mVa = generateOutputFile(savedInstanceState);
        Fr = generateIsFrontFacingCameraSelected();
    }

    private void iaa() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        mVg = new Vg(this,
                mCg,
                mVa,
                new Sa(new Se(), display.getRotation()),
                mVi.getPreviewSurfaceHolder(),
                Fr);
        mVi.setRecordingButtonInterface(this);

        if (mIv) {
            mVi.updateUIRecordingFinished(getVideoThumbnail());
        } else {
            mVi.updateUINotRecording();
        }
        mVi.showTimer(mCg.getShowTimer());

    }

    @Override
    protected void onPause() {
        if (mVg != null) {
            mVg.stopRecording(null);
        }
        releaseAllResources();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finishCancelled();
    }

    @Override
    public void onRecordButtonClicked() {
        try {
            mVg.toggleRecording();
        } catch (Ex e) {
        }
    }

    @Override
    public void onAcceptButtonClicked() {
        finishCompleted();
    }

    @Override
    public void onDeclineButtonClicked() {
        finishCancelled();
    }

    @Override
    public void onRecordingStarted() {
        mVi.updateUIRecordingOngoing();
    }

    @Override
    public void onSwitchCamera(boolean isFrontFacingSelected) {
    }

    @Override
    public void onRecordingStopped(String message) {
        if (message != null) {
            //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        mVi.updateUIRecordingFinished(getVideoThumbnail());
        releaseAllResources();
    }

    @Override
    public void onRecordingSuccess() {
        mIv = true;
    }

    @Override
    public void onRecordingFailed(String message) {
        finishError(message);
    }

    private void finishCompleted() {
        if (retorno.equals("1")){
            if (transicao) {
                Intent intent2 = new Intent(AAAA.this, VideoToGIFActivity.class);
                intent2.putExtra("videoPath", mVa.getFullPath());
                startActivity(intent2);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }else {
                Intent intent2 = new Intent(AAAA.this, VideoToGIFActivity.class);
                intent2.putExtra("videoPath", mVa.getFullPath());
                startActivity(intent2);
                overridePendingTransition(0, 0);
                finish();
            }
        }else if (retorno.equals("2")){
            if (transicao) {
            Intent intent2 = new Intent(AAAA.this, VParaGIFActivity.class);
            intent2.putExtra("postvideoPath", mVa.getFullPath());
            startActivity(intent2);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            }else {
                Intent intent2 = new Intent(AAAA.this, VParaGIFActivity.class);
                intent2.putExtra("postvideoPath", mVa.getFullPath());
                startActivity(intent2);
                overridePendingTransition(0, 0);
                finish();
            }
        }else if (retorno.equals("3")){
            if (transicao) {
            Intent intent2 = new Intent(AAAA.this, VParaGIFActivity.class);
            intent2.putExtra("postvideoPath", mVa.getFullPath());
            startActivity(intent2);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            }else {
                Intent intent2 = new Intent(AAAA.this, VParaGIFActivity.class);
                intent2.putExtra("postvideoPath", mVa.getFullPath());
                startActivity(intent2);
                overridePendingTransition(0, 0);
                finish();
            }
        }
    }

    private void finishCancelled() {
        ca();
    }

    public void ca() {
        File file = new File(this.mVa.getFullPath());
        if (file.exists()) {
            file.delete();
            AAAA.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
            if (file.exists()) {
                ca();
            }else {
                if (retorno.equals("1")){
                    if (transicao) {
                    Intent result = new Intent(AAAA.this, MainOrkut.class);
                    startActivity(result);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    }else {
                        Intent result = new Intent(AAAA.this, MainOrkut.class);
                        startActivity(result);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }else if (retorno.equals("2")){
                    if (transicao) {
                    Intent innyx = new Intent(AAAA.this, MeuAlbumActivity.class);
                    startActivity(innyx);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    }else {
                        Intent innyx = new Intent(AAAA.this, MeuAlbumActivity.class);
                        startActivity(innyx);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }else if (retorno.equals("3")){
                    if (transicao) {
                    Intent innyx = new Intent(AAAA.this, MainOrkut.class);
                    startActivity(innyx);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    }else {
                        Intent innyx = new Intent(AAAA.this, MainOrkut.class);
                        startActivity(innyx);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }
            }
        }else {
            if (retorno.equals("1")){
                if (transicao) {
                Intent result = new Intent(AAAA.this, MainOrkut.class);
                startActivity(result);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                }else {
                    Intent result = new Intent(AAAA.this, MainOrkut.class);
                    startActivity(result);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }else if (retorno.equals("2")){
                if (transicao) {
                Intent innyx = new Intent(AAAA.this, MeuAlbumActivity.class);
                startActivity(innyx);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                }else {
                    Intent innyx = new Intent(AAAA.this, MeuAlbumActivity.class);
                    startActivity(innyx);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }else if (retorno.equals("3")){
                if (transicao) {
                Intent innyx = new Intent(AAAA.this, MainOrkut.class);
                startActivity(innyx);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                }else {
                    Intent innyx = new Intent(AAAA.this, MainOrkut.class);
                    startActivity(innyx);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        }
    }

    private void finishError(final String message) {
        Toast.makeText(getApplicationContext(), "Aconteceu um erro", Toast.LENGTH_LONG).show();
        if (retorno.equals("1")){
            if (transicao) {
            Intent result = new Intent(AAAA.this, MainOrkut.class);
            startActivity(result);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            }else {
                Intent result = new Intent(AAAA.this, MainOrkut.class);
                startActivity(result);
                overridePendingTransition(0, 0);
                finish();
            }
        }else if (retorno.equals("2")){
            if (transicao) {
            Intent innyx = new Intent(AAAA.this, MeuAlbumActivity.class);
            startActivity(innyx);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            }else {
                Intent innyx = new Intent(AAAA.this, MeuAlbumActivity.class);
                startActivity(innyx);
                overridePendingTransition(0, 0);
                finish();
            }
        }else if (retorno.equals("3")){
            if (transicao) {
            Intent innyx = new Intent(AAAA.this, MainOrkut.class);
            startActivity(innyx);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            }else {
                Intent innyx = new Intent(AAAA.this, MainOrkut.class);
                startActivity(innyx);
                overridePendingTransition(0, 0);
                finish();
            }
        }
    }

    private void releaseAllResources() {
        if (mVg != null) {
            mVg.releaseAllResources();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    protected Cg generateCaptureConfiguration() {
        Cg returnConfiguration = this.getIntent().getParcelableExtra("MECHI_AQUI");
        if (returnConfiguration == null) {
            returnConfiguration = Cg.getDefault();
        }
        return returnConfiguration;
    }

    private boolean generateVideoRecorded(final Bundle savedInstanceState) {
        if (savedInstanceState == null) return false;
        return savedInstanceState.getBoolean("MECHI_AQQUI", false);
    }

    protected Va generateOutputFile(Bundle savedInstanceState) {
        Va returnFile;
        if (savedInstanceState != null) {
            returnFile = new Va(savedInstanceState.getString("MMECHI_AC"));
        } else {
            returnFile = new Va(this.getIntent().getStringExtra("MMECHI_AC"));
        }
        // TODO: add checks to see if outputfile is writeable
        return returnFile;
    }

    private boolean generateIsFrontFacingCameraSelected() {
        return getIntent().getBooleanExtra("ME_AQUI", true);
    }

    public Bitmap getVideoThumbnail() {
        final Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(mVa.getFullPath(),
                Thumbnails.FULL_SCREEN_KIND);
        if (thumbnail == null) {

        }
        return thumbnail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.setResult(resultCode, data);
        finish();
    }
}