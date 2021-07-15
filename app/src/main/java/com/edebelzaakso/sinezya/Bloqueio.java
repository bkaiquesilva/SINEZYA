package com.so.debelzaak.evolution.makemake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.hanks.passcodeview.PasscodeView;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

public class Bloqueio extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;
    PasscodeView senha_view;
    private String senhaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloqueio);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Bloqueio.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        Bundle extras = getIntent().getExtras();
        senhaa = extras.getString("cedia");

        senha_view = findViewById(R.id.senha_view);

        senha_view.setPasscodeLength(4)
                .setLocalPasscode(senhaa)
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(getApplicationContext(), "Senha incorreta", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onSuccess(String number) {
                        if (transicao) {
                            Intent intent = new Intent(Bloqueio.this, MainOrkut.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }else {
                            Intent intent = new Intent(Bloqueio.this, MainOrkut.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
