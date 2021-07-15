package com.edebelzaakso.sinezya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class P_POLITICA extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_politica);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(P_POLITICA.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        WebView webView = findViewById(R.id.wv_content);
        webView.loadUrl("file:///android_asset/pprivacidade.html");
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(P_POLITICA.this, Ajustes.class);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(P_POLITICA.this, Ajustes.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
