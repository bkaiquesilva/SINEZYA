package com.edebelzaakso.sinezya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Recuper_Senha extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;

    private ImageView button13;
    private EditText editText5;
    private TextView log_respo;

    private FirebaseAuth mAuth;
    private RelativeLayout progressi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_senha);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Recuper_Senha.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        button13 = findViewById(R.id.button13);
        editText5 = findViewById(R.id.editText5);
        progressi = findViewById(R.id.progressi);
        log_respo = findViewById(R.id.log_respo);

        mAuth = FirebaseAuth.getInstance();

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_respo.setVisibility(View.GONE);
                progressi.setVisibility(View.VISIBLE);
                String email = editText5.getText().toString();

                if (email.isEmpty()) {
                    progressi.setVisibility(View.GONE);
                    editText5.setError("Digite seu e-mail");
                } else {
                    hideKeyboard();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressi.setVisibility(View.GONE);
                                log_respo.setText("O e-mail para redefinir a sua senha foi enviado com sucesso.");
                                log_respo.setTextColor(getResources().getColor(R.color.hythyt));
                                log_respo.setVisibility(View.VISIBLE);
                            } else {
                                progressi.setVisibility(View.GONE);
                                log_respo.setText("Erro ao procurar por e-mail.");
                                log_respo.setTextColor(getResources().getColor(R.color.reter));
                                log_respo.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(Recuper_Senha.this, LognOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(Recuper_Senha.this, LognOrkut.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
