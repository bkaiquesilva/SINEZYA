package com.edebelzaakso.sinezya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PerfilSinezya extends AppCompatActivity {

    private TextView pesquia, blokeio, bloqueadoo, senia;
    private ImageView Sperfil, imagem_1, imagem_2, imagem_3, imagem_4, imagem_11, imagem_22, imagem_33, imagem_44;
    private ImageView barrar, securyy, ejetar, atualizeer, vernotication, perfilo, addamigo;
    private ImageView deleta1, deleta2, deleta3, deleta4;
    private static final int GALLERY_REQUEST1 = 599;
    private static final int GALLERY_REQUEST2 = 899;
    private static final int GALLERY_REQUEST3 = 799;
    private static final int GALLERY_REQUEST4 = 699;
    private Switch setupName;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabseUsers;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String uimage;
    private String nome;
    private String numero;
    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;
    private boolean mAutori1 = false;
    private boolean mAutori2 = false;
    private boolean mAutori3 = false;
    private boolean mAutori4 = false;
    private boolean mAutori44 = false;
    private String user_id;
    private String lokiiooo;
    private boolean bloqueadooo = true;
    private EditText editya, linklov, data_nas, num_tele;
    private CheckBox amigosss, eusss, eus_info, amigos_info;
    private CardView progre1, progre2, progre3, progre4;
    private LinearLayout linea_Nome, linea_Config, linea_Lovely, linea_Fotos, linea_Phone, linea_Nasce, linea_Bloquei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orkut_perfil);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PerfilSinezya.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        Bundle extras = getIntent().getExtras();
        lokiiooo = extras.getString("lokiio");


        deleta1 = findViewById(R.id.deleta1);
        deleta2 = findViewById(R.id.deleta2);
        deleta3 = findViewById(R.id.deleta3);
        deleta4 = findViewById(R.id.deleta4);

        senia = findViewById(R.id.senia);
        barrar = findViewById(R.id.barrar);
        securyy = findViewById(R.id.securyy);
        ejetar = findViewById(R.id.ejetar);
        atualizeer = findViewById(R.id.atualizeer);
        vernotication = findViewById(R.id.vernotication);
        perfilo = findViewById(R.id.perfilo);
        addamigo = findViewById(R.id.addamigo);
        bloqueadoo = findViewById(R.id.bloqueadooo);
        linea_Nome = findViewById(R.id.linea_Nome);
        linea_Config = findViewById(R.id.linea_Config);
        linea_Lovely = findViewById(R.id.linea_Lovely);
        linea_Fotos = findViewById(R.id.linea_Fotos);
        linea_Phone = findViewById(R.id.linea_Phone);
        linea_Nasce = findViewById(R.id.linea_Nasce);
        linea_Bloquei = findViewById(R.id.linea_Bloquei);
        blokeio = findViewById(R.id.blokeio);

        progre1 = findViewById(R.id.progre1);
        progre2 = findViewById(R.id.progre2);
        progre3 = findViewById(R.id.progre3);
        progre4 = findViewById(R.id.progre4);
        imagem_1 = findViewById(R.id.imagem_1);
        imagem_2 = findViewById(R.id.imagem_2);
        imagem_3 = findViewById(R.id.imagem_3);
        imagem_4 = findViewById(R.id.imagem_4);
        imagem_11 = findViewById(R.id.imagem_11);
        imagem_22 = findViewById(R.id.imagem_22);
        imagem_33 = findViewById(R.id.imagem_33);
        imagem_44 = findViewById(R.id.imagem_44);
        num_tele = findViewById(R.id.num_tele);
        data_nas = findViewById(R.id.data_nas);
        linklov = findViewById(R.id.linklov);
        editya = findViewById(R.id.editya);
        pesquia = findViewById(R.id.pesquia);
        Sperfil = findViewById(R.id.Sperfil);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        amigosss = findViewById(R.id.amigosss);
        eusss = findViewById(R.id.eusss);
        amigos_info = findViewById(R.id.amigos_info);
        eus_info = findViewById(R.id.eus_info);
        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        setupName = findViewById(R.id.setupName);

        if (currentUser != null) {
            mAutori44 = true;
            user_id = mAuth.getCurrentUser().getUid();
            mDatabseUsers.child(user_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("nomepess").exists()) {
                        String nme = dataSnapshot.child("nomepess").getValue().toString();
                        nome = nme;
                        editya.setHint(nme);
                        editya.setEnabled(true);
                    }

                    if (dataSnapshot.child("name").exists()) {
                        final String nameroo = dataSnapshot.child("name").getValue().toString();
                        pesquia.setText(nameroo);
                    }

                    if (dataSnapshot.child("OKNum").exists()) {
                        final String numeroo = dataSnapshot.child("OKNum").getValue().toString();
                        numero = numeroo;
                    }

                    if (dataSnapshot.hasChild("image")) {
                        uimage = String.valueOf(dataSnapshot.child("image").getValue());
                        RequestManager with = Glide.with(getApplicationContext());
                        with.load(uimage).thumbnail(Glide.with(getApplicationContext()).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Glide.with(getApplicationContext()).load(uimage).thumbnail(Glide.with(getApplicationContext()).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).into(Sperfil);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(Sperfil);
                    }

                    if (mAutori44) {
                        mAutori44 = false;
                        if (dataSnapshot.child("cadeado").exists()) {
                            blokeio.setText("Bloqueada");
                            bloqueadoo.setText("DESBLOQUEAR");
                            bloqueadoo.setEnabled(true);
                            bloqueadooo = true;
                            String refre = dataSnapshot.child("cadeado").getValue().toString();
                            senia.setText(refre);
                        } else {
                            blokeio.setText("Desbloqueada");
                            bloqueadoo.setText("BLOQUEAR");
                            bloqueadoo.setEnabled(true);
                            bloqueadooo = false;
                            senia.setText("----");
                        }

                        if (dataSnapshot.child("checki").exists()) {
                            if (dataSnapshot.child("checki").getValue().toString() != null) {
                                final boolean nurt = Boolean.parseBoolean(dataSnapshot.child("checki").getValue().toString());
                                setupName.setChecked(nurt);
                            }
                        }

                        if (dataSnapshot.child("nu_telef").exists()) {
                            if (dataSnapshot.child("nu_telef").getValue().toString() != null) {
                                final String love = dataSnapshot.child("nu_telef").getValue().toString();
                                num_tele.setText(love);
                            } else {
                                num_tele.setHint("+55 (33)99828-5010");
                            }
                        }

                        if (dataSnapshot.child("data_nasci").exists()) {
                            if (dataSnapshot.child("data_nasci").getValue().toString() != null) {
                                final String love = dataSnapshot.child("data_nasci").getValue().toString();
                                data_nas.setText(love);
                            } else {
                                data_nas.setHint("01/06/2000");
                            }
                        }

                        if (dataSnapshot.child("lovely").exists()) {
                            if (dataSnapshot.child("lovely").getValue().toString() != null) {
                                final String love = dataSnapshot.child("lovely").getValue().toString();
                                linklov.setText(love);
                            } else {
                                linklov.setHint("https://");
                            }
                        }

                        if (dataSnapshot.child("priva_post").exists()) {
                            if (dataSnapshot.child("priva_post").getValue().toString() != null) {
                                final String priva_postt = dataSnapshot.child("priva_post").getValue().toString();
                                if (priva_postt.equals("1")) {
                                    amigosss.setChecked(true);
                                    eusss.setChecked(false);
                                } else if (priva_postt.equals("0")) {
                                    eusss.setChecked(true);
                                    amigosss.setChecked(false);
                                }
                            }
                        } else {
                            amigosss.setChecked(true);
                            eusss.setChecked(false);
                        }


                        if (dataSnapshot.child("priva_info").exists()) {
                            if (dataSnapshot.child("priva_info").getValue().toString() != null) {
                                final String priva_infoo = dataSnapshot.child("priva_info").getValue().toString();
                                if (priva_infoo.equals("1")) {
                                    amigos_info.setChecked(true);
                                    eus_info.setChecked(false);
                                } else if (priva_infoo.equals("0")) {
                                    eus_info.setChecked(true);
                                    amigos_info.setChecked(false);
                                }
                            }
                        } else {
                            amigos_info.setChecked(true);
                            eus_info.setChecked(false);
                        }


                        if (dataSnapshot.child("image1").exists()) {
                            if (dataSnapshot.child("image1").getValue().toString() != null) {
                                final String love = dataSnapshot.child("image1").getValue().toString();
                                RequestManager with = Glide.with(getApplicationContext());
                                with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_1);
                                deleta1.setVisibility(View.VISIBLE);
                            }
                        }

                        if (dataSnapshot.child("image2").exists()) {
                            if (dataSnapshot.child("image2").getValue().toString() != null) {
                                final String love = dataSnapshot.child("image2").getValue().toString();
                                RequestManager with = Glide.with(getApplicationContext());
                                with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_2);
                                deleta2.setVisibility(View.VISIBLE);
                            }
                        }


                        if (dataSnapshot.child("image3").exists()) {
                            if (dataSnapshot.child("image3").getValue().toString() != null) {
                                final String love = dataSnapshot.child("image3").getValue().toString();
                                RequestManager with = Glide.with(getApplicationContext());
                                with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_3);
                                deleta3.setVisibility(View.VISIBLE);
                            }
                        }


                        if (dataSnapshot.child("image4").exists()) {
                            if (dataSnapshot.child("image4").getValue().toString() != null) {
                                final String love = dataSnapshot.child("image4").getValue().toString();
                                RequestManager with = Glide.with(getApplicationContext());
                                with.load(love).thumbnail(Glide.with(getApplicationContext()).load(love)).into(imagem_4);
                                deleta4.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (!lokiiooo.isEmpty()){
            if (lokiiooo.equals("no")){
                linea_Bloquei.setVisibility(View.GONE);
                linea_Nome.setVisibility(View.VISIBLE);
                linea_Config.setVisibility(View.GONE);
                linea_Lovely.setVisibility(View.GONE);
                linea_Fotos.setVisibility(View.GONE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.GONE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security_255));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_24));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }else if (lokiiooo.equals("si")){
                linea_Bloquei.setVisibility(View.VISIBLE);
                linea_Nome.setVisibility(View.GONE);
                linea_Config.setVisibility(View.GONE);
                linea_Lovely.setVisibility(View.GONE);
                linea_Fotos.setVisibility(View.GONE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.GONE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_244));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }
        }

        amigosss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    eusss.setChecked(false);
                    priva_poste("1");
                }
            }
        });

        bloqueadoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bloqueadoo.setEnabled(false);
                if (bloqueadooo){
                    blokeio.setText(".........");
                    mDatabseUsers.child(user_id).child("cadeado").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    senia.setText("----");
                                    blokeio.setText("Desbloqueada");
                                    bloqueadoo.setText("BLOQUEAR");
                                    bloqueadoo.setEnabled(true);
                                    bloqueadooo = false;
                                }
                            }, 700);
                        }
                    });

                }else {
                    bloqueadoo.setEnabled(true);
                    if (transicao) {
                        Intent intent = new Intent(PerfilSinezya.this, Digite_Senha.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }else {
                        Intent intent = new Intent(PerfilSinezya.this, Digite_Senha.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                    }
                }
            }
        });

        barrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linea_Bloquei.setVisibility(View.GONE);
                linea_Nome.setVisibility(View.GONE);
                linea_Config.setVisibility(View.VISIBLE);
                linea_Lovely.setVisibility(View.GONE);
                linea_Fotos.setVisibility(View.GONE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.GONE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_244));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_24));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }
        });

        securyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linea_Bloquei.setVisibility(View.GONE);
                linea_Nome.setVisibility(View.VISIBLE);
                linea_Config.setVisibility(View.GONE);
                linea_Lovely.setVisibility(View.GONE);
                linea_Fotos.setVisibility(View.GONE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.GONE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security_255));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_24));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }
        });

        ejetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linea_Bloquei.setVisibility(View.GONE);
                linea_Nome.setVisibility(View.GONE);
                linea_Config.setVisibility(View.GONE);
                linea_Lovely.setVisibility(View.VISIBLE);
                linea_Fotos.setVisibility(View.GONE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.GONE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_244));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_24));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }
        });

        atualizeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linea_Bloquei.setVisibility(View.GONE);
                linea_Nome.setVisibility(View.GONE);
                linea_Config.setVisibility(View.GONE);
                linea_Lovely.setVisibility(View.GONE);
                linea_Fotos.setVisibility(View.VISIBLE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.GONE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_244));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_24));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }
        });

        vernotication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
                MaskTextWatcher mtv = new MaskTextWatcher(data_nas, smf);
                data_nas.addTextChangedListener(mtv);
                linea_Bloquei.setVisibility(View.GONE);
                linea_Nome.setVisibility(View.GONE);
                linea_Config.setVisibility(View.GONE);
                linea_Lovely.setVisibility(View.GONE);
                linea_Fotos.setVisibility(View.GONE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.VISIBLE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_244));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_24));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }
        });

        addamigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linea_Bloquei.setVisibility(View.VISIBLE);
                linea_Nome.setVisibility(View.GONE);
                linea_Config.setVisibility(View.GONE);
                linea_Lovely.setVisibility(View.GONE);
                linea_Fotos.setVisibility(View.GONE);
                linea_Phone.setVisibility(View.GONE);
                linea_Nasce.setVisibility(View.GONE);
                barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
                securyy.setImageDrawable(getDrawable(R.drawable.ic_security));
                ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
                atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
                vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
                addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_244));
                perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_24));
            }
        });

        eusss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    amigosss.setChecked(false);
                    priva_poste("0");
                }
            }
        });

        amigos_info.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    eus_info.setChecked(false);
                    priva_info("1");
                }
            }
        });

        eus_info.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    amigos_info.setChecked(false);
                    priva_info("0");
                }
            }
        });

        Sperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!uimage.isEmpty()){
                    final AlertDialog dialo;
                    dialo = new AlertDialog.Builder(PerfilSinezya.this).create();
                    View c = getLayoutInflater().inflate(R.layout.auver, null);
                    dialo.setCanceledOnTouchOutside(true);
                    dialo.setCancelable(true);
                    dialo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final ImageView img_bu = c.findViewById(R.id.btimg);
                    dialo.setView(c);

                    RequestManager with = Glide.with(PerfilSinezya.this);
                    with.load(uimage).thumbnail(Glide.with(PerfilSinezya.this).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(PerfilSinezya.this).load(uimage).thumbnail(Glide.with(PerfilSinezya.this).load(uimage)).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_bu);
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

        num_tele.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND){
                    String etiia = num_tele.getText().toString().trim();
                    telefom(etiia);
                    handled = true;
                }
                return handled;
            }
        });

        editya.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND){
                    String etia = editya.getText().toString().trim();
                    atnome(etia);
                    handled = true;
                }
                return handled;
            }
        });

        linklov.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND){
                    String eta = linklov.getText().toString().trim();
                    link_lovely(eta);
                    handled = true;
                }
                return handled;
            }
        });

        data_nas.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND){
                    String etta = data_nas.getText().toString().trim();
                    link_nasci(etta);
                    handled = true;
                }
                return handled;
            }
        });

        setupName.setChecked(sharedPreferences.getBoolean("notifa", false));
        setupName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("notifa", b).apply();
                if (b){
                    if (nome != null) {
                        nomeounumero(nome, "true");
                    }
                }else {
                    if (numero != null) {
                        nomeounumero(numero, "false");
                    }
                }
            }
        });
    }

    private void telefom(String etiia) {
        mDatabseUsers.child(user_id).child("nu_telef").setValue(etiia);
    }

    private void link_nasci(String etta) {
        mDatabseUsers.child(user_id).child("data_nasci").setValue(etta);
    }

    private void link_lovely(String eta) {
        if (URLUtil.isValidUrl(eta)) {
            mDatabseUsers.child(user_id).child("lovely").setValue(eta);
        }
    }

    private void priva_info(String s) {
        mDatabseUsers.child(user_id).child("priva_info").setValue(s);
    }

    private void priva_poste(String s) {
        mDatabseUsers.child(user_id).child("priva_post").setValue(s);
    }

    private void atnome(String etia) {
        mDatabseUsers.child(user_id).child("nomepess").setValue(etia);
    }

    private void nomeounumero(String identificador, String sss) {
        mDatabseUsers.child(user_id).child("name").setValue(identificador);
        mDatabseUsers.child(user_id).child("checki").setValue(sss);

    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(PerfilSinezya.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(PerfilSinezya.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }
    }

    public void image1(View view) {
        if (transicao) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST1);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST1);
            overridePendingTransition(0, 0);
        }
    }

    public void image2(View view) {
        if (transicao) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST2);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST2);
            overridePendingTransition(0, 0);
        }
    }

    public void image3(View view) {
        if (transicao) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST3);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST3);
            overridePendingTransition(0, 0);
        }
    }

    public void image4(View view) {
        if (transicao) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST4);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST4);
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST1 && resultCode == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Uri mImageUri = data.getData();
                imagem_1.setImageURI(mImageUri);
                imagem_11.setEnabled(false);
                imagem_11.setClickable(false);
                mAutori1 = true;
                progre1.setVisibility(View.VISIBLE);

                StorageReference filepath = mStorageRef.child("Album").child(mImageUri.getLastPathSegment());
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (mAutori1) {
                            mAutori1 = false;
                            progre1.setVisibility(View.GONE);
                            imagem_11.setEnabled(true);
                            imagem_11.setClickable(true);
                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            mDatabseUsers.child(user_id).child("image1").setValue(downloadUrl.toString());

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        }

        if (requestCode == GALLERY_REQUEST2 && resultCode == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Uri mImageUri = data.getData();
                imagem_2.setImageURI(mImageUri);
                imagem_22.setEnabled(false);
                imagem_22.setClickable(false);
                progre2.setVisibility(View.VISIBLE);
                mAutori2 = true;

                StorageReference filepath = mStorageRef.child("Album").child(mImageUri.getLastPathSegment());
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (mAutori2) {
                            mAutori2 = false;
                            progre2.setVisibility(View.GONE);
                            imagem_22.setEnabled(true);
                            imagem_22.setClickable(true);
                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            mDatabseUsers.child(user_id).child("image2").setValue(downloadUrl.toString());

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        }

        if (requestCode == GALLERY_REQUEST3 && resultCode == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Uri mImageUri = data.getData();
                imagem_3.setImageURI(mImageUri);
                imagem_33.setEnabled(false);
                imagem_33.setClickable(false);
                progre3.setVisibility(View.VISIBLE);
                mAutori3 = true;

                StorageReference filepath = mStorageRef.child("Album").child(mImageUri.getLastPathSegment());
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (mAutori3) {
                            mAutori3 = false;
                            progre3.setVisibility(View.GONE);
                            imagem_33.setEnabled(true);
                            imagem_33.setClickable(true);
                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            mDatabseUsers.child(user_id).child("image3").setValue(downloadUrl.toString());

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        }

        if (requestCode == GALLERY_REQUEST4 && resultCode == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Uri mImageUri = data.getData();
                imagem_4.setImageURI(mImageUri);
                imagem_44.setEnabled(false);
                imagem_44.setClickable(false);
                progre4.setVisibility(View.VISIBLE);
                mAutori4 = true;

                StorageReference filepath = mStorageRef.child("Album").child(mImageUri.getLastPathSegment());
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (mAutori4) {
                            mAutori4 = false;
                            progre4.setVisibility(View.GONE);
                            imagem_44.setEnabled(true);
                            imagem_44.setClickable(true);
                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            mDatabseUsers.child(user_id).child("image4").setValue(downloadUrl.toString());

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        }

    }

    public void ponee(View view) {
        SimpleMaskFormatter smf = new SimpleMaskFormatter("+NN (NN)NNNNN-NNNN");
        MaskTextWatcher mtv = new MaskTextWatcher(num_tele, smf);
        num_tele.addTextChangedListener(mtv);
        linea_Bloquei.setVisibility(View.GONE);
        linea_Nome.setVisibility(View.GONE);
        linea_Config.setVisibility(View.GONE);
        linea_Lovely.setVisibility(View.GONE);
        linea_Fotos.setVisibility(View.GONE);
        linea_Phone.setVisibility(View.VISIBLE);
        linea_Nasce.setVisibility(View.GONE);
        barrar.setImageDrawable(getDrawable(R.drawable.ic_settings_24));
        securyy.setImageDrawable(getDrawable(R.drawable.ic_security));
        ejetar.setImageDrawable(getDrawable(R.drawable.ic_basellov_24));
        atualizeer.setImageDrawable(getDrawable(R.drawable.ic_b_email_24));
        vernotication.setImageDrawable(getDrawable(R.drawable.ic_baseline_24));
        addamigo.setImageDrawable(getDrawable(R.drawable.ic_lock_landscape_24));
        perfilo.setImageDrawable(getDrawable(R.drawable.ic_segs_phone_244));
    }

    public void deletia4(View view) {
        mDatabseUsers.child(user_id).child("image4").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                imagem_4.setBackgroundResource(0);
                deleta4.setVisibility(View.GONE);
                Toast.makeText(PerfilSinezya.this, "Sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletia3(View view) {
        mDatabseUsers.child(user_id).child("image3").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                imagem_3.setBackgroundResource(0);
                deleta3.setVisibility(View.GONE);
                Toast.makeText(PerfilSinezya.this, "Sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletia2(View view) {
        mDatabseUsers.child(user_id).child("image2").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                imagem_2.setBackgroundResource(0);
                deleta2.setVisibility(View.GONE);
                Toast.makeText(PerfilSinezya.this, "Sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletia1(View view) {
        mDatabseUsers.child(user_id).child("image1").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                imagem_1.setBackgroundResource(0);
                deleta1.setVisibility(View.GONE);
                Toast.makeText(PerfilSinezya.this, "Sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
