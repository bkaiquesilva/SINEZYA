package com.edebelzaakso.sinezya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Digite_Senha extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;
    private DatabaseReference mDatabseUsers;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String user_id;
    private TextView verTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digite_senhaa);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Digite_Senha.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        verTexto = findViewById(R.id.verTexto);

        if (currentUser != null) {
            user_id = mAuth.getCurrentUser().getUid();
        }


        hideNavigationBar();

        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
                            hideNavigationBar();
                        } else {

                        }
                    }
                });

    }


    public void ummm(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "1");
    }

    public void deletii(View view) {
        String edert = verTexto.getText().toString().trim();
        String mer = removeLastChar(edert);
        verTexto.setText(mer);

    }

    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    public void envio_senha(View view) {
        String edert = verTexto.getText().toString().trim();
        if (!edert.isEmpty()) {
            eSenha(edert);
        }
    }

    private void eSenha(String edert) {
        if (edert.length() == 4) {
            Toast.makeText(Digite_Senha.this, "Bloqueado", Toast.LENGTH_SHORT).show();
            mDatabseUsers.child(user_id).child("cadeado").setValue(edert).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onBackPressed();
                    }
           });

        }
    }

    public void doiss(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "2");
    }

    public void tress(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "3");
    }

    public void quatroo(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "4");
    }

    public void cinco(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "5");
    }

    public void seiss(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "6");
    }

    public void setee(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "7");
    }

    public void oitoo(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "8");
    }

    public void novee(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "9");
    }

    public void zeroo(View view) {
        String edert = verTexto.getText().toString().trim();
        verTexto.setText(edert + "0");
    }


    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(Digite_Senha.this, PerfilSinezya.class);
            intent.putExtra("lokiio", "si");
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(Digite_Senha.this, PerfilSinezya.class);
            intent.putExtra("lokiio", "si");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    private void hideNavigationBar() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);

        }
    }
}
