package com.edebelzaakso.sinezya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

public class FazerAmizades extends AppCompatActivity {

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabase;
    private DatabaseReference mOrkutNum;
    private RelativeLayout progressi;
    private LinearLayout slamizade;
    private TextView btnconvite;
    private FirebaseAuth mAuth;
    private ImageView sharchi;
    private ImageView imgSoli;
    private EditText textsech;
    private String numeero;
    private String imaage;
    private String iuodo;
    private String nuero;
    private String pefoto;
    private boolean auto3 = false;
    private boolean auto4 = false;
    private boolean auto5 = false;
    private boolean auto6 = false;
    private boolean auto7 = false;
    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fazeramizades);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FazerAmizades.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        Bundle extras = getIntent().getExtras();
        pefoto = extras.getString("pefoto");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mOrkutNum = FirebaseDatabase.getInstance().getReference().child("OKNum");
        mDatabaseUsers.keepSynced(true);

        progressi = findViewById(R.id.progressi);
        slamizade = findViewById(R.id.slamizade);
        sharchi = findViewById(R.id.sarchi);
        imgSoli = findViewById(R.id.imgSoli);
        textsech = findViewById(R.id.textsech);
        btnconvite = findViewById(R.id.btnconvite);

        imgSoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!imaage.isEmpty()) {
                    final AlertDialog dialo;
                    dialo = new AlertDialog.Builder(FazerAmizades.this).create();
                    View c = getLayoutInflater().inflate(R.layout.auver, null);
                    dialo.setCanceledOnTouchOutside(true);
                    dialo.setCancelable(true);
                    dialo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final ImageView img_bu = c.findViewById(R.id.btimg);
                    dialo.setView(c);

                    RequestManager with = Glide.with(FazerAmizades.this);
                    with.load(imaage).thumbnail(Glide.with(FazerAmizades.this).load(imaage)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(FazerAmizades.this).load(imaage).thumbnail(Glide.with(FazerAmizades.this).load(imaage)).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_bu);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(img_bu);

                    img_bu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialo.dismiss();
                        }
                    });

                    dialo.show();
                }
            }
        });

        btnconvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    if (!numeero.isEmpty()) {
                        final String pushi = mDatabase.push().getKey();
                        final String pushi2 = mDatabase.push().getKey();
                        final DatabaseReference newPost = mDatabase.child("WCSKA").child(nuero).child("PENDENTE").child(pushi);
                        final DatabaseReference newPost1 = mDatabase.child("WCSKA").child(numeero).child("PENDENTE").child(pushi2);

                        final String user_id = mAuth.getCurrentUser().getUid();
                        final String eemail = user.getEmail();
                        btnconvite.setText(".....");

                        final String eu_vocee = numeero + "+" + nuero;
                        mDatabase.child("TPendentes").child(eu_vocee).setValue("o").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                newPost.child("IMAGE").setValue(imaage);
                                newPost.child("uid").setValue(iuodo);
                                newPost.child("envio").setValue("1");
                                newPost.child("keys").setValue(pushi2);
                                newPost.child("Smail").setValue("teste");
                                newPost.child("eu_voce").setValue(eu_vocee);
                                newPost.child("identificador").setValue(numeero);

                                newPost1.child("IMAGE").setValue(pefoto);
                                newPost1.child("uid").setValue(user_id);
                                newPost1.child("envio").setValue("0");
                                newPost1.child("keys").setValue(pushi);
                                newPost1.child("Smail").setValue(eemail);
                                newPost1.child("eu_voce").setValue(eu_vocee);
                                newPost1.child("identificador").setValue(nuero).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mDatabaseUsers.child(iuodo).child("Noti").setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                btnconvite.setEnabled(false);
                                                btnconvite.setText("PENDENTE");
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });

        sharchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                progressi.setVisibility(View.VISIBLE);
                slamizade.setVisibility(View.GONE);
                final String textsec = textsech.getText().toString().trim();
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    auto3 = true;
                    auto4 = true;
                    auto5 = true;
                    auto6 = true;
                    auto7 = true;
                    btnconvite.setEnabled(false);
                    final String user_id = mAuth.getCurrentUser().getUid();


                    mOrkutNum.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (auto4) {
                                auto4 = false;
                                if (dataSnapshot.getValue().toString() != null) {
                                    if (!textsec.isEmpty()) {
                                        if (dataSnapshot.child(textsec).exists()) {
                                            mOrkutNum.child(textsec).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (auto5) {
                                                     auto5 = false;
                                                    if (dataSnapshot.getValue().toString() != null) {
                                                        iuodo = dataSnapshot.getValue().toString();
                                                        mDatabaseUsers.child(iuodo).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                                                if (auto3) {
                                                                    auto3 = false;
                                                                    if (dataSnapshot.child("image").exists()) {
                                                                        if (dataSnapshot.child("OKNum").exists()) {
                                                                            numeero = dataSnapshot.child("OKNum").getValue().toString();

                                                                            imaage = dataSnapshot.child("image").getValue().toString();
                                                                            RequestManager with = Glide.with(FazerAmizades.this);
                                                                            with.load(imaage).thumbnail(Glide.with(FazerAmizades.this).load(imaage)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgSoli);

                                                                            mDatabaseUsers.child(user_id).addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                    if (dataSnapshot.child("OKNum").exists()) {
                                                                                        nuero = dataSnapshot.child("OKNum").getValue().toString();
                                                                                        if (nuero.equals(textsec)) {
                                                                                            btnconvite.setText("VOCÊ");
                                                                                            btnconvite.setEnabled(false);
                                                                                            progressi.setVisibility(View.GONE);
                                                                                            slamizade.setVisibility(View.VISIBLE);
                                                                                        }else {
                                                                                        mDatabase.child("TPendentes").addValueEventListener(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                                                                                if (auto6) {
                                                                                                    auto6 = false;
                                                                                                    if (dataSnapshot.child(numeero + "+" + nuero).exists()) {
                                                                                                        btnconvite.setText("PENDENTE");
                                                                                                        btnconvite.setEnabled(false);
                                                                                                        progressi.setVisibility(View.GONE);
                                                                                                        slamizade.setVisibility(View.VISIBLE);

                                                                                                    } else if (dataSnapshot.child(nuero + "+" + numeero).exists()) {
                                                                                                        btnconvite.setText("PENDENTE");
                                                                                                        btnconvite.setEnabled(false);
                                                                                                        progressi.setVisibility(View.GONE);
                                                                                                        slamizade.setVisibility(View.VISIBLE);

                                                                                                    } else {

                                                                                                        mDatabase.child("TAmigos").addValueEventListener(new ValueEventListener() {
                                                                                                            @Override
                                                                                                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                                                                                                if (auto7) {
                                                                                                                    auto7 = false;
                                                                                                                    if (dataSnapshot.child(numeero + "+" + nuero).exists()) {
                                                                                                                        btnconvite.setText("AMIGOS");
                                                                                                                        btnconvite.setEnabled(false);
                                                                                                                        progressi.setVisibility(View.GONE);
                                                                                                                        slamizade.setVisibility(View.VISIBLE);

                                                                                                                    } else if (dataSnapshot.child(nuero + "+" + numeero).exists()) {
                                                                                                                        btnconvite.setText("AMIGOS");
                                                                                                                        btnconvite.setEnabled(false);
                                                                                                                        progressi.setVisibility(View.GONE);
                                                                                                                        slamizade.setVisibility(View.VISIBLE);

                                                                                                                    } else {
                                                                                                                        btnconvite.setText("CONVIDAR");
                                                                                                                        btnconvite.setEnabled(true);
                                                                                                                        progressi.setVisibility(View.GONE);
                                                                                                                        slamizade.setVisibility(View.VISIBLE);
                                                                                                                    }
                                                                                                                }
                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                                            }
                                                                                                        });


                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onCancelled(DatabaseError databaseError) {

                                                                                }
                                                                            });
                                                                        } else {
                                                                            new Handler().postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    progressi.setVisibility(View.GONE);
                                                                                    Toast.makeText(FazerAmizades.this, "Usuário sem identificação", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }, 700);
                                                                        }
                                                                    } else {
                                                                        new Handler().postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                progressi.setVisibility(View.GONE);
                                                                                Toast.makeText(FazerAmizades.this, "Usuário irreconhecível", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }, 700);
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                                progressi.setVisibility(View.GONE);
                                                                slamizade.setVisibility(View.GONE);
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        } else {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressi.setVisibility(View.GONE);
                                                    Toast.makeText(FazerAmizades.this, "Usuário inexistente", Toast.LENGTH_SHORT).show();
                                                }
                                            }, 700);
                                        }
                                    } else {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressi.setVisibility(View.GONE);
                                                Toast.makeText(FazerAmizades.this, "Digite o identificador", Toast.LENGTH_SHORT).show();
                                            }
                                        }, 700);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

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
            Intent intent = new Intent(FazerAmizades.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(FazerAmizades.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
