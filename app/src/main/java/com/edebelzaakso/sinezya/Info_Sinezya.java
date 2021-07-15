package com.edebelzaakso.sinezya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

public class Info_Sinezya extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;
    private ImageView imagem_Perfil, imagem_111, imagem_222, imagem_333, imagem_444;
    private ImageView separador1, separador2;
    private TextView texto_contato, texto_nome, texto_nasci, texto_lovely, texto_adicional;
    private TextView texto_priva;
    private DatabaseReference mDatabseUsers;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private boolean mAuri44 = false;
    private int nasci = 0;
    private int lovely = 0;
    private int contato = 0;
    private int img1 = 0;
    private int img2 = 0;
    private int img3 = 0;
    private int img4 = 0;
    private String ilove;
    private String SMKfil;
    private String Surlke;
    private String Sisin;
    private String Suser;
    private String Suide;
    private String Srome;
    private String _seu_key;
    private String eimail;
    private String nnoome;
    private String EuuVoce;
    private TextView fotii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacoes_sine);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Info_Sinezya.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        Bundle extras = getIntent().getExtras();
        SMKfil = extras.getString("Kpefil");
        Sisin = extras.getString("urlident");
        Suide = extras.getString("urluide");
        Suser = extras.getString("urluser");
        Surlke = extras.getString("urlkey");
        Srome = extras.getString("urlnome");
        _seu_key = extras.getString("keyseu");
        eimail = extras.getString("eimail");
        nnoome = extras.getString("meu_nome");
        EuuVoce = extras.getString("eu_vocei");

        fotii = findViewById(R.id.fotii);
        texto_adicional = findViewById(R.id.texto_adicional);
        separador1 = findViewById(R.id.separador1);
        separador2 = findViewById(R.id.separador2);
        imagem_444 = findViewById(R.id.imagem_444);
        imagem_333 = findViewById(R.id.imagem_333);
        imagem_222 = findViewById(R.id.imagem_222);
        imagem_111 = findViewById(R.id.imagem_111);
        imagem_Perfil = findViewById(R.id.imSoliaa);
        texto_contato = findViewById(R.id.cuntatooo);
        texto_lovely = findViewById(R.id.ilovelyy);
        texto_priva = findViewById(R.id.texto_priva);
        texto_nome =findViewById(R.id.numeee);
        texto_nasci = findViewById(R.id.nascii);

        new CountDownTimer(Long.MAX_VALUE, 1) {
            @Override
            public void onTick(long l) {
                if (nasci == 7 & lovely == 7 & contato == 7 & img1 == 7 & img2 == 7 & img3 == 7 & img4 == 7) {
                    texto_adicional.setVisibility(View.VISIBLE);
                }else {
                    texto_adicional.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();


        if (currentUser != null) {
            mAuri44 = true;
            mDatabseUsers.child(Suide).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (mAuri44) {
                        mAuri44 = false;
                        if (dataSnapshot.child("priva_info").exists()) {
                            final String priva_infoo = dataSnapshot.child("priva_info").getValue().toString();
                            if (priva_infoo.equals("1")) {
                                texto_priva.setGravity(View.GONE);
                                if (dataSnapshot.child("nomepess").exists()) {
                                    String nme = dataSnapshot.child("nomepess").getValue().toString();
                                    texto_nome.setText("Olá, meu nome é " + nme + "!");
                                }

                                if (dataSnapshot.child("nu_telef").exists()) {
                                    final String love = dataSnapshot.child("nu_telef").getValue().toString();
                                    texto_contato.setText("Telefone: " + love);
                                    texto_contato.setVisibility(View.VISIBLE);
                                    contato = 0;
                                }else {
                                    contato = 7;
                                    texto_nome.setVisibility(View.GONE);
                                }
                                if (dataSnapshot.child("data_nasci").exists()) {
                                    final String love = dataSnapshot.child("data_nasci").getValue().toString();
                                    texto_nasci.setText("Nasci em: " + love);
                                    texto_nasci.setVisibility(View.VISIBLE);
                                    nasci = 0;
                                }else {
                                    nasci = 7;
                                    texto_nasci.setVisibility(View.GONE);
                                }
                                if (dataSnapshot.child("lovely").exists()) {
                                    final String love = dataSnapshot.child("lovely").getValue().toString();
                                    ilove = love;
                                    texto_lovely.setVisibility(View.VISIBLE);
                                    lovely = 0;
                                }else {
                                    lovely = 7;
                                    texto_lovely.setVisibility(View.GONE);
                                }
                                if (dataSnapshot.child("image1").exists()) {
                                    final String love = dataSnapshot.child("image1").getValue().toString();
                                    RequestManager with = Glide.with(getApplicationContext());
                                    with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_111);
                                    imagem_111.setVisibility(View.VISIBLE);
                                    fotii.setVisibility(View.VISIBLE);
                                    separador1.setVisibility(View.VISIBLE);
                                    img1 = 0;
                                }else {
                                    img1 = 7;
                                    imagem_111.setVisibility(View.GONE);
                                    separador1.setVisibility(View.GONE);
                                }
                                if (dataSnapshot.child("image2").exists()) {
                                    final String love = dataSnapshot.child("image2").getValue().toString();
                                    RequestManager with = Glide.with(getApplicationContext());
                                    with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_222);
                                    imagem_222.setVisibility(View.VISIBLE);
                                    fotii.setVisibility(View.VISIBLE);
                                    img2 = 0;
                                }else {
                                    img2 = 7;
                                    imagem_222.setVisibility(View.GONE);
                                    separador1.setVisibility(View.GONE);
                                }
                                if (dataSnapshot.child("image3").exists()) {
                                    final String love = dataSnapshot.child("image3").getValue().toString();
                                    RequestManager with = Glide.with(getApplicationContext());
                                    with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_333);
                                    imagem_333.setVisibility(View.VISIBLE);
                                    fotii.setVisibility(View.VISIBLE);
                                    separador2.setVisibility(View.VISIBLE);
                                    img3 = 0;
                                }else {
                                    img3 = 7;
                                    imagem_333.setVisibility(View.GONE);
                                    separador2.setVisibility(View.GONE);
                                }
                                if (dataSnapshot.child("image4").exists()) {
                                    final String love = dataSnapshot.child("image4").getValue().toString();
                                    RequestManager with = Glide.with(getApplicationContext());
                                    with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_444);
                                    imagem_444.setVisibility(View.VISIBLE);
                                    fotii.setVisibility(View.VISIBLE);
                                    img4 = 0;
                                }else {
                                    img4 = 7;
                                    imagem_444.setVisibility(View.GONE);
                                    separador2.setVisibility(View.GONE);
                                }

                            } else if (priva_infoo.equals("0")) {
                                if (dataSnapshot.child("name").exists()) {
                                    final String nameroo = dataSnapshot.child("name").getValue().toString();
                                    texto_nome.setText("Olá, meu nome é " + nameroo + "!");
                                }
                                if (dataSnapshot.hasChild("image")) {
                                    final String uimage = String.valueOf(dataSnapshot.child("image").getValue());
                                    RequestManager with = Glide.with(getApplicationContext());
                                    with.load(uimage).thumbnail(Glide.with(getApplicationContext()).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            Glide.with(getApplicationContext()).load(uimage).thumbnail(Glide.with(getApplicationContext()).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imagem_Perfil);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    }).into(imagem_Perfil);
                                }
                                texto_priva.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (dataSnapshot.child("name").exists()) {
                                final String nameroo = dataSnapshot.child("name").getValue().toString();
                                texto_nome.setText("Olá, meu nome é " + nameroo + "!");
                            }
                            if (dataSnapshot.hasChild("image")) {
                                final String uimage = String.valueOf(dataSnapshot.child("image").getValue());
                                RequestManager with = Glide.with(getApplicationContext());
                                with.load(uimage).thumbnail(Glide.with(getApplicationContext()).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        Glide.with(getApplicationContext()).load(uimage).thumbnail(Glide.with(getApplicationContext()).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imagem_Perfil);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(imagem_Perfil);
                            }
                            texto_priva.setVisibility(View.VISIBLE);
                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        RequestManager with = Glide.with(getApplicationContext());
        with.load(SMKfil).thumbnail(Glide.with(getApplicationContext()).load(SMKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Glide.with(getApplicationContext()).load(SMKfil).thumbnail(Glide.with(getApplicationContext()).load(SMKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imagem_Perfil);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imagem_Perfil);

        texto_lovely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse(ilove));
                    startActivity(bacer);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else {
                    Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse(ilove));
                    startActivity(bacer);
                    overridePendingTransition(0, 0);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(Info_Sinezya.this, SinezyaMeus.class);
            intent.putExtra("Kpefil", SMKfil);
            intent.putExtra("urlident", Sisin);
            intent.putExtra("urluide", Suide);
            intent.putExtra("urluser", Suser);
            intent.putExtra("urlkey", Surlke);
            intent.putExtra("eu_voceu", EuuVoce);
            intent.putExtra("urlnome", Srome);
            intent.putExtra("keyseuu", _seu_key);
            intent.putExtra("eimai", eimail);
            intent.putExtra("meu_noome", nnoome);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(Info_Sinezya.this, SinezyaMeus.class);
            intent.putExtra("Kpefil", SMKfil);
            intent.putExtra("urlident", Sisin);
            intent.putExtra("urluide", Suide);
            intent.putExtra("urluser", Suser);
            intent.putExtra("urlkey", Surlke);
            intent.putExtra("eu_voceu", EuuVoce);
            intent.putExtra("urlnome", Srome);
            intent.putExtra("keyseuu", _seu_key);
            intent.putExtra("eimai", eimail);
            intent.putExtra("meu_noome", nnoome);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    public void retornar(View view) {
        onBackPressed();
    }
}
