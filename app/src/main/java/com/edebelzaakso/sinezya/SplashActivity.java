package com.edebelzaakso.sinezya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eyalbira.loadingdots.LoadingDots;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sachinvarma.easypermission.EasyPermissionConstants;
import com.sachinvarma.easypermission.EasyPermissionInit;
import com.sachinvarma.easypermission.EasyPermissionList;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    SharedPreferences sharedPreferences2 = null;
    private boolean transicao = true;
    List<String> deniedPermissions = new ArrayList<>();
    private LoadingDots loadingDots;
    private TextView permissan, edebell;
    private DatabaseReference mDatabseUsers;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private String user_id;
    private boolean edewe = false;
    private boolean edewe2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        sharedPreferences2 = getSharedPreferences("firsRun", MODE_PRIVATE);
        transicao = sharedPreferences.getBoolean("escuro", true);

        loadingDots = findViewById(R.id.lodeee);
        permissan = findViewById(R.id.permissan);
        edebell = findViewById(R.id.edebel);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("Users");









        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences2.getBoolean("firsRun", true)){
                    sharedPreferences2.edit().putBoolean("firsRun", false).apply();
                    sharedPreferences.edit().putBoolean("notifi", true).apply();
                    sharedPreferences.edit().putBoolean("notifo", true).apply();
                    sharedPreferences.edit().putBoolean("notife", true).apply();
                    sharedPreferences.edit().putBoolean("notifu", true).apply();
                    sharedPreferences.edit().putBoolean("escuro", true).apply();
                    sharedPreferences.edit().putBoolean("camera", false).apply();

                    List<String> permission = new ArrayList<>();
                    permission.add(EasyPermissionList.CAMERA);
                    permission.add(EasyPermissionList.RECORD_AUDIO);
                    permission.add(EasyPermissionList.WRITE_EXTERNAL_STORAGE);

                    if (permission.size() > 0) {
                        new EasyPermissionInit(SplashActivity.this, permission);
                    }
                }else {
                    List<String> permission = new ArrayList<>();
                    permission.add(EasyPermissionList.CAMERA);
                    permission.add(EasyPermissionList.RECORD_AUDIO);
                    permission.add(EasyPermissionList.WRITE_EXTERNAL_STORAGE);

                    if (permission.size() > 0) {
                        new EasyPermissionInit(SplashActivity.this, permission);
                    }
                }
            }
        }, 4000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EasyPermissionConstants.INTENT_CODE:
                if (data != null) {
                    boolean isGotAllPermissions = data.getBooleanExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION, false);
                    if (data.hasExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION)) {
                        if (isGotAllPermissions) {
                            loadingDots.setVisibility(View.VISIBLE);
                            edebell.setVisibility(View.VISIBLE);
                            permissan.setVisibility(View.GONE);
                            if (transicao) {
                                if (currentUser != null) {
                                    edewe = true;
                                    user_id = mAuth.getCurrentUser().getUid();
                                    mDatabseUsers.child(user_id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (edewe) {
                                                edewe = false;
                                                if (dataSnapshot.child("cadeado").exists()) {
                                                    String nme = dataSnapshot.child("cadeado").getValue().toString();
                                                    Intent intent = new Intent(SplashActivity.this, Bloqueio.class);
                                                    intent.putExtra("cedia", nme);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                    finish();
                                                }else {
                                                    Intent intent = new Intent(SplashActivity.this, MainOrkut.class);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                    finish();
                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }else {
                                    Intent intent = new Intent(SplashActivity.this, MainOrkut.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                }













                            }else {
                                if (currentUser != null) {
                                    edewe2 = true;
                                    user_id = mAuth.getCurrentUser().getUid();
                                    mDatabseUsers.child(user_id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (edewe2) {
                                                edewe2 = false;
                                                if (dataSnapshot.child("cadeado").exists()) {
                                                    String nme = dataSnapshot.child("cadeado").getValue().toString();
                                                    Intent intent = new Intent(SplashActivity.this, Bloqueio.class);
                                                    intent.putExtra("cedia", nme);
                                                    startActivity(intent);
                                                    overridePendingTransition(0, 0);
                                                    finish();
                                                }else {
                                                    Intent intent = new Intent(SplashActivity.this, MainOrkut.class);
                                                    startActivity(intent);
                                                    overridePendingTransition(0, 0);
                                                    finish();
                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }else {
                                    Intent intent = new Intent(SplashActivity.this, MainOrkut.class);
                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }
                            }
                        } else {
                            loadingDots.setVisibility(View.GONE);
                            edebell.setVisibility(View.GONE);
                            permissan.setVisibility(View.VISIBLE);
                        }
                    }
                    if (data.hasExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST)) {
                        if (data.getSerializableExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST) != null) {
                            deniedPermissions = new ArrayList<>();
                            deniedPermissions.addAll((Collection<? extends String>) data.getSerializableExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST));
                            if (deniedPermissions.size() > 0) {
                                for (int i = 0; i < deniedPermissions.size(); i++) {
                                    switch (deniedPermissions.get(i)) {
                                        case EasyPermissionList.CAMERA:
                                            loadingDots.setVisibility(View.GONE);
                                            edebell.setVisibility(View.GONE);
                                            permissan.setVisibility(View.VISIBLE);
                                            break;
                                        case EasyPermissionList.RECORD_AUDIO:
                                            edebell.setVisibility(View.GONE);
                                            loadingDots.setVisibility(View.GONE);
                                            permissan.setVisibility(View.VISIBLE);
                                            break;
                                        case EasyPermissionList.WRITE_EXTERNAL_STORAGE:
                                            loadingDots.setVisibility(View.GONE);
                                            permissan.setVisibility(View.VISIBLE);
                                            edebell.setVisibility(View.GONE);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}
