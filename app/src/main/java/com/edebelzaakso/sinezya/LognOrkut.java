package com.edebelzaakso.sinezya;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

public class LognOrkut extends AppCompatActivity {

    private EditText mLoginEmailField;
    private EditText mLoginPasswordField;
    private Button mNewAccount;
    private ImageButton mLoginButton;
    private ProgressDialog mProgressbar;
    private DatabaseReference mDatabaseUsers;

    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private Boolean emailcheck;
    private Typeface sb;
    private TextView toolbar;
    private CheckBox checkbox;
    SharedPreferences checkbox1 = null;
    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orkut_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LognOrkut.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);

        sb = Typeface.createFromAsset(getAssets(), "fonts/frijole.ttf");

        mProgressbar = new ProgressDialog(this);
        checkbox1 = getSharedPreferences("checkbo1", MODE_PRIVATE);

        mLoginEmailField = findViewById(R.id.email);
        mLoginPasswordField = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.signin);
        mNewAccount = findViewById(R.id.signup);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTypeface(sb);
        checkbox = findViewById(R.id.checkbox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkbox.isChecked()){
                    checkbox1.edit().putBoolean("checkbo1", true).apply();
                }else {
                    checkbox1.edit().putBoolean("checkbo1", false).apply();
                }
            }
        });

        mNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transicao) {
                    Intent setupIntent = new Intent(LognOrkut.this, RegiOrkut.class);
                    startActivity(setupIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Intent setupIntent = new Intent(LognOrkut.this, RegiOrkut.class);
                    startActivity(setupIntent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        mProgressbar.setMessage("Processando.....");
        mProgressbar.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth = FirebaseAuth.getInstance();
                            Toast.makeText(LognOrkut.this, "BEM-VINDO!!!", Toast.LENGTH_SHORT).show();
                            checkUserExist();
                            mProgressbar.dismiss();
                        } else {
                            Toast.makeText(LognOrkut.this, "Erro no servidor", Toast.LENGTH_SHORT).show();
                            mProgressbar.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
            }
        }
    }


    private void checkLogin() {
        String email = mLoginEmailField.getText().toString().trim();
        String password = mLoginPasswordField.getText().toString().trim();

        if (!email.isEmpty() || !password.isEmpty()) {
            if (checkbox.isChecked()) {
                SharedPreferences msharedpref = getSharedPreferences("editos", MODE_PRIVATE);
                SharedPreferences.Editor editor = msharedpref.edit();
                editor.putString("emme", email);
                editor.putString("sene", password);
                editor.apply();
            } else {
                SharedPreferences msharedpref = getSharedPreferences("editos", MODE_PRIVATE);
                SharedPreferences.Editor editor = msharedpref.edit();
                editor.clear();
                editor.apply();
            }

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                mProgressbar.setMessage("Processando.....");
                mProgressbar.show();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressbar.dismiss();
                            verificacao();
                        } else {
                            mProgressbar.dismiss();
                            Toast.makeText(LognOrkut.this, "Erro no servidor", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    private void verificacao(){
        FirebaseUser user = mAuth.getCurrentUser();
        emailcheck = user.isEmailVerified();
        if (emailcheck){
            checkUserExist();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(LognOrkut.this);
            builder.setMessage("Entre no e-mail e clique no link de verificação para provar que o e-mail inserido na área de cadastro é real!");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog11 = builder.create();
            alertDialog11.setCanceledOnTouchOutside(false);
            alertDialog11.show();
            mAuth.signOut();
        }
    }

    private void checkUserExist() {
        if (transicao) {
            Intent mainIntent = new Intent(LognOrkut.this, MainOrkut.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }else {
            Intent mainIntent = new Intent(LognOrkut.this, MainOrkut.class);
            startActivity(mainIntent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkbox1.getBoolean("checkbo1", true)) {
            checkbox.setChecked(true);
        }
        SharedPreferences prefs2 = this.getSharedPreferences("editos", Context.MODE_PRIVATE);

        if (prefs2.getString("emme", "") != null || prefs2.getString("sene", "") != null){
            mLoginEmailField.setText(prefs2.getString("emme", ""));
            mLoginPasswordField.setText(prefs2.getString("sene", ""));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LognOrkut.this);
        builder.setMessage("Quer mesmo fechar o sinezya?");
        builder.setCancelable(false);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog11 = builder.create();
        alertDialog11.setCanceledOnTouchOutside(false);
        alertDialog11.show();
    }

    public void esquecii(View view) {
        if (transicao) {
            Intent mainIntent = new Intent(LognOrkut.this, Recuper_Senha.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }else {
            Intent mainIntent = new Intent(LognOrkut.this, Recuper_Senha.class);
            startActivity(mainIntent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}