package com.edebelzaakso.sinezya;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.eyalbira.loadingdots.LoadingDots;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import com.edebelzaakso.sinezya.Models.NMsn;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SinezyaAudio extends AppCompatActivity {

    private TextView iumsn, ionli;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private RecyclerView blog_list;
    private LinearLayout laytia;
    private RelativeLayout rvimg;
    private static final int GALLERY_REQUEST = 999;
    private FirebaseAuth mAuth;
    private EditText new_message;
    private ImageView sendy, fimamiz, mkper, audy, posi_image, deletia;
    SharedPreferences sharedPreferenc = null;
    private boolean emailno = true;
    LinearLayoutManager layoutManager;
    private ProgressBar prossi;
    private boolean mAutori = false;
    private boolean audii = true;
    private boolean reuuu = false;
    private String MKfil;
    private String urlke;
    private String isin;
    private String uide;
    private String rome;
    private String ifoto;
    private String key_seu;
    private String etype;
    private String eimail;
    private String noome;
    private String euVoce;
    private int nitica = 0;
    private int onli = 3;
    private boolean transicao = true;
    private LoadingDots looeee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sinezyaaudio);

        sharedPreferenc = PreferenceManager.getDefaultSharedPreferences(SinezyaAudio.this);
        emailno = sharedPreferenc.getBoolean("notifo", true);
        audii = sharedPreferenc.getBoolean("notife", true);
        reuuu = sharedPreferenc.getBoolean("notifu", true);
        transicao = sharedPreferenc.getBoolean("escuro", true);

        looeee = findViewById(R.id.looeee);
        blog_list = findViewById(R.id.recycler_view);
        new_message = findViewById(R.id.new_message);
        mProgress = new ProgressDialog(this);
        posi_image = findViewById(R.id.posi_image);
        deletia = findViewById(R.id.deletia);
        laytia = findViewById(R.id.laytia);
        mkper = findViewById(R.id.MKpfil);
        fimamiz = findViewById(R.id.fimamiz);
        ionli = findViewById(R.id.ionli);
        iumsn = findViewById(R.id.iumsn);
        sendy = findViewById(R.id.sendy);
        audy = findViewById(R.id.audy);
        rvimg = findViewById(R.id.rvimg);
        prossi = findViewById(R.id.prossi);


        Bundle extras = getIntent().getExtras();
        MKfil = extras.getString("Kpefil");
        isin = extras.getString("urlident");
        uide = extras.getString("urluide");
        urlke = extras.getString("urlkey");
        rome = extras.getString("urlnome");
        ifoto = extras.getString("urlpost");
        key_seu = extras.getString("keyseu");
        etype = extras.getString("etype");
        eimail = extras.getString("eimail");
        noome = extras.getString("meu_nome");
        euVoce = extras.getString("eu_vocei");


        iumsn.setText(rome + " está");

        audy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, GALLERY_REQUEST);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, GALLERY_REQUEST);
                    overridePendingTransition(0, 0);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase.keepSynced(true);

        if (ifoto.equals("edebelzaakso")){
            rvimg.setVisibility(View.GONE);
        }else {
            rvimg.setVisibility(View.VISIBLE);
            if (reuuu){
                posi_image.setBackgroundResource(0);
            RequestManager with = Glide.with(SinezyaAudio.this);
            with.load(ifoto).thumbnail(Glide.with(SinezyaAudio.this).load(ifoto)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    RequestManager with = Glide.with(SinezyaAudio.this);
                    with.load(ifoto).thumbnail(Glide.with(SinezyaAudio.this).load(ifoto)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            laytia.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            laytia.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(posi_image);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    laytia.setVisibility(View.GONE);
                    return false;
                }
            }).into(posi_image);
            }else {
                posi_image.setBackgroundResource(0);
                Picasso.with(SinezyaAudio.this)
                        .load(ifoto)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(posi_image, new Callback() {
                            @Override
                            public void onSuccess() {
                                laytia.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                Picasso.with(SinezyaAudio.this)
                                        .load(ifoto)
                                        .into(posi_image, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                laytia.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onError() {
                                            }
                                        });
                            }
                        });
            }
        }

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(false);
        blog_list.setHasFixedSize(true);
        blog_list.setLayoutManager(layoutManager);

        RequestManager with = Glide.with(SinezyaAudio.this);
        with.load(MKfil).thumbnail(Glide.with(SinezyaAudio.this).load(MKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Glide.with(SinezyaAudio.this).load(MKfil).thumbnail(Glide.with(SinezyaAudio.this).load(MKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).into(mkper);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(mkper);

        mkper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialo;
                dialo = new AlertDialog.Builder(SinezyaAudio.this).create();
                View c = getLayoutInflater().inflate(R.layout.auver, null);
                dialo.setCanceledOnTouchOutside(true);
                dialo.setCancelable(true);
                dialo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final ImageView img_bu = c.findViewById(R.id.btimg);
                dialo.setView(c);

                if (reuuu){
                img_bu.setBackgroundResource(0);
                RequestManager with = Glide.with(SinezyaAudio.this);
                with.load(MKfil).thumbnail(Glide.with(SinezyaAudio.this).load(MKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        RequestManager with = Glide.with(SinezyaAudio.this);
                        with.load(MKfil).thumbnail(Glide.with(SinezyaAudio.this).load(MKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_bu);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(img_bu);
                }else {
                    img_bu.setBackgroundResource(0);
                    Picasso.with(SinezyaAudio.this)
                            .load(MKfil)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(img_bu, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError() {
                                    Picasso.with(SinezyaAudio.this)
                                            .load(MKfil)
                                            .into(img_bu, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                }

                                                @Override
                                                public void onError() {
                                                }
                                            });
                                }
                            });
                }

                img_bu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialo.dismiss();
                    }
                });

                dialo.show();
            }
        });

        deletia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifoto = "edebelzaakso";
                rvimg.setVisibility(View.GONE);
            }
        });

        fimamiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaAudio.this);
                builder.setMessage("Quer mesmo remover esse amigo?");
                builder.setCancelable(false);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mProgress.setMessage("Iniciando remosão.....");
                        mProgress.show();
                        mDatabase.child("WCSKA").child(isin).child("AMIGOS").child(key_seu).removeValue();
                        mDatabase.child("WCSKA").child(isin).child("PENDENTE").child(key_seu).removeValue();
                        mDatabase.child("WCSKA").child(uide).child("AMIGOS").child(urlke).removeValue();
                        mDatabase.child("WCSKA").child(uide).child("PENDENTE").child(urlke).removeValue();
                        mDatabase.child("TAmigos").child(euVoce).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mProgress.dismiss();
                                Toast.makeText(SinezyaAudio.this, "Amigo removido", Toast.LENGTH_SHORT).show();
                                if (etype.equals("1")) {
                                    if (transicao) {
                                        Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
                                        intent.putExtra("ezaak", "1");
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.volte, R.anim.volte_ii);
                                        finish();
                                    }else {
                                        Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
                                        intent.putExtra("ezaak", "1");
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }

                                }else if (etype.equals("2")){
                                    if (transicao) {
                                        Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
                                        intent.putExtra("ezaak", "2");
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.volte, R.anim.volte_ii);
                                        finish();
                                    }else {
                                        Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
                                        intent.putExtra("ezaak", "2");
                                        startActivity(intent);
                                        overridePendingTransition(0,0);
                                        finish();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mProgress.dismiss();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog11 = builder.create();
                alertDialog11.setCanceledOnTouchOutside(false);
                alertDialog11.show();
            }
        });

        new_message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND){
                    envioa();
                    handled = true;
                }
                return handled;
            }
        });

        sendy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envioa();
            }
        });

        chequeusuario();


    }

    private void envioa() {
        final String msn = new_message.getText().toString().trim();
        if (!msn.isEmpty()) {

            hideKeyboard();

            if (audii) {
                MediaPlayer mp = MediaPlayer.create(SinezyaAudio.this, R.raw.sound);
                try {
                    mp.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.start();
            }

            new_message.getText().clear();
            sendy.setEnabled(false);


            if (ifoto.equals("edebelzaakso")){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String datae = new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(new Date());

                    final DatabaseReference newPost = mDatabase.child("TAmigos").child(euVoce).push();
                    newPost.child("mident").setValue(uide);
                    newPost.child("dataa").setValue(datae);
                    newPost.child("imagem").setValue("https://images.jpep");
                    newPost.child("typo").setValue("1");
                    newPost.child("menssa").setValue(msn).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ifoto = "edebelzaakso";
                            rvimg.setVisibility(View.GONE);
                            sendy.setEnabled(true);

                            if (emailno) {
                                if (nitica == 0) {
                                    if (onli == 1) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaAudio.this);
                                        builder.setMessage("Sempre que iniciar um bate-papo com alguém que está off-line será feito a seguinte pergunta:\n" +
                                                "\n" +
                                                "Deseja enviar um alerta para " + noome + " sobre sua necessidade de conversar com ele(a)?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                JavaMailAPI javaMailAPI = new JavaMailAPI(SinezyaAudio.this, eimail, "SINEZYA", noome + " enviou uma nova mensagem para você pelo bate-papo do aplicativo sinezya.");
                                                javaMailAPI.execute();
                                                nitica = 8;
                                            }
                                        });
                                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                nitica = 8;
                                            }
                                        });
                                        AlertDialog alertDialog11 = builder.create();
                                        alertDialog11.setCanceledOnTouchOutside(false);
                                        alertDialog11.show();
                                    }
                                }
                            }
                        }
                    });
                } else {
                }


            }else {
                prossi.setVisibility(View.VISIBLE);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String datae = new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(new Date());

                    final DatabaseReference newPost = mDatabase.child("TAmigos").child(euVoce).push();
                    newPost.child("mident").setValue(uide);
                    newPost.child("dataa").setValue(datae);
                    newPost.child("imagem").setValue(ifoto);
                    newPost.child("typo").setValue("2");
                    newPost.child("menssa").setValue("1");

                    String datae2 = new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(new Date());
                    final DatabaseReference newPost2 = mDatabase.child("TAmigos").child(euVoce).push();
                    newPost2.child("mident").setValue(uide);
                    newPost2.child("dataa").setValue(datae2);
                    newPost2.child("imagem").setValue("https://images.jpep");
                    newPost2.child("typo").setValue("1");
                    newPost2.child("menssa").setValue(msn).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ifoto = "edebelzaakso";
                            rvimg.setVisibility(View.GONE);
                            sendy.setEnabled(true);

                            if (emailno) {
                                if (nitica == 0) {
                                    if (onli == 1) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaAudio.this);
                                        builder.setMessage("Sempre que iniciar um bate-papo com alguém que está off-line será feito a seguinte pergunta:\n" +
                                                "\n" +
                                                "Deseja enviar um alerta para " + noome + " sobre sua necessidade de conversar com ele(a)?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                JavaMailAPI javaMailAPI = new JavaMailAPI(SinezyaAudio.this, eimail, "SINEZYA", noome + " comentou um gif seu através de uma mensagem enviada pelo bate-papo do aplicativo sinezya.");
                                                javaMailAPI.execute();
                                                nitica = 8;
                                            }
                                        });
                                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                nitica = 8;
                                            }
                                        });
                                        AlertDialog alertDialog11 = builder.create();
                                        alertDialog11.setCanceledOnTouchOutside(false);
                                        alertDialog11.show();
                                    }
                                }
                            }
                        }
                    });

                } else {
                }
            }

        }else {

        }
    }


    private void chequeusuario() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            testar(euVoce);

            mDatabase.child("Users").child(isin).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("eOnline").exists()) {
                            if (dataSnapshot.child("eOnline").getValue().toString() != null){
                                onli = Integer.parseInt(dataSnapshot.child("eOnline").getValue().toString());
                                if (onli == 1){
                                    ionli.setText("off-line");
                                }else if (onli == 2){
                                    ionli.setText("on-line");
                                }
                            }
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void testar(final String user_ide) {
        final FirebaseRecyclerAdapter<NMsn, SinViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NMsn, SinViewHolder>(
                NMsn.class,
                R.layout.msn_row,
                SinViewHolder.class,
                mDatabase.child("TAmigos").child(user_ide)) {
            @Override
            protected void populateViewHolder(final SinViewHolder friViewHolder, NMsn friend, final int i) {
                final String post_key = getRef(i).getKey();

                friViewHolder.setTextoo(friend.getMenssa());
                friViewHolder.setUide(friend.getMident());
                friViewHolder.setDatai(friend.getDataa());
                friViewHolder.setTyypo(friend.getTypo());
                friViewHolder.setImaagem(getApplicationContext(),friend.getImagem());


                friViewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (i != 0) {
                            if (friViewHolder.uidei.equals(uide)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaAudio.this);
                                builder.setMessage("Quer mesmo excluir essa mensagem?");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialogInterface, int i) {
                                        mDatabase.child("TAmigos").child(user_ide).child(post_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialogInterface.dismiss();
                                            }
                                        });
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
                        }
                        return false;
                    }
                });

                friViewHolder.idcard.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (i != 0) {
                            if (friViewHolder.uidei.equals(uide)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaAudio.this);
                                builder.setMessage("Quer mesmo excluir essa mensagem?");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialogInterface, int i) {
                                        mDatabase.child("TAmigos").child(user_ide).child(post_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialogInterface.dismiss();
                                            }
                                        });
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
                        }
                        return false;
                    }
                });

                friViewHolder.idcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog dialo;
                        dialo = new AlertDialog.Builder(SinezyaAudio.this).create();
                        View c = getLayoutInflater().inflate(R.layout.auver, null);
                        dialo.setCanceledOnTouchOutside(true);
                        dialo.setCancelable(true);
                        dialo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        final ImageView img_bu = c.findViewById(R.id.btimg);
                        dialo.setView(c);

                        RequestManager with = Glide.with(SinezyaAudio.this);
                        with.load(friViewHolder.fotos).thumbnail(Glide.with(SinezyaAudio.this).load(friViewHolder.fotos)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                RequestManager with = Glide.with(SinezyaAudio.this);
                                with.load(friViewHolder.fotos).thumbnail(Glide.with(SinezyaAudio.this).load(friViewHolder.fotos)).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_bu);
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
                });

                if (uide.equals(friViewHolder.uidei)){

                    if (friViewHolder.typoo == 1){
                        friViewHolder.idcard.setVisibility(View.GONE);
                        friViewHolder.setms.setVisibility(View.VISIBLE);
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            friViewHolder.idcard.setCardBackgroundColor(0);
                            friViewHolder.setms.setTextColor(Color.parseColor("#ffffff"));
                            friViewHolder.elayout.setBackgroundDrawable(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_imessage));
                        } else {
                            friViewHolder.idcard.setCardBackgroundColor(0);
                            friViewHolder.setms.setTextColor(Color.parseColor("#ffffff"));
                            friViewHolder.elayout.setBackground(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_imessage));
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        params.setMargins(80, 0, 0, 0);
                        friViewHolder.elayouti.setLayoutParams(params);


                    }else if (friViewHolder.typoo == 2){
                        friViewHolder.setms.setVisibility(View.GONE);
                        friViewHolder.idcard.setVisibility(View.VISIBLE);
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            friViewHolder.idcard.setCardBackgroundColor(Color.parseColor("#ff2871"));
                            friViewHolder.elayout.setBackgroundDrawable(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_imessage));
                        } else {
                            friViewHolder.idcard.setCardBackgroundColor(Color.parseColor("#ff2871"));
                            friViewHolder.elayout.setBackground(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_imessage));
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        params.setMargins(60, 0, 0, 0);
                        friViewHolder.elayouti.setLayoutParams(params);

                    }

                }else {
                    if (friViewHolder.typoo == 1){
                        friViewHolder.idcard.setVisibility(View.GONE);
                        friViewHolder.setms.setVisibility(View.VISIBLE);
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            friViewHolder.idcard.setCardBackgroundColor(0);
                            friViewHolder.setms.setTextColor(Color.parseColor("#ff2871"));
                            friViewHolder.elayout.setBackgroundDrawable(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_omessage));
                        } else {
                            friViewHolder.idcard.setCardBackgroundColor(0);
                            friViewHolder.setms.setTextColor(Color.parseColor("#ff2871"));
                            friViewHolder.elayout.setBackground(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_omessage));
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        params.setMargins(0, 0, 80, 0);
                        friViewHolder.elayouti.setLayoutParams(params);


                    }else if (friViewHolder.typoo == 2){
                        friViewHolder.setms.setVisibility(View.GONE);
                        friViewHolder.idcard.setVisibility(View.VISIBLE);
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            friViewHolder.idcard.setCardBackgroundColor(Color.parseColor("#F2F2F2"));
                            friViewHolder.elayout.setBackgroundDrawable(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_omessage));
                        } else {
                            friViewHolder.idcard.setCardBackgroundColor(Color.parseColor("#F2F2F2"));
                            friViewHolder.elayout.setBackground(ContextCompat.getDrawable(SinezyaAudio.this, R.drawable.shape_omessage));
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        params.setMargins(0, 0, 60, 0);
                        friViewHolder.elayouti.setLayoutParams(params);

                    }
                }

            }
        };

        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                layoutManager.smoothScrollToPosition(blog_list, null, firebaseRecyclerAdapter.getItemCount());
            }
        });

        blog_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SinViewHolder extends RecyclerView.ViewHolder {

        SharedPreferences sharedPreferences = null;
        private boolean reuu = false;
        RelativeLayout elayout, elayouti;
        ImageView imgchat, sewrf;
        TextView setms, tempto;
        String uidei;
        String fotos;
        CardView idcard;
        int typoo = 0;
        View mView;

        public SinViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            setms = mView.findViewById(R.id.setmsn);
            tempto = mView.findViewById(R.id.tempto);
            idcard = mView.findViewById(R.id.idcard);
            elayout = mView.findViewById(R.id.elayout);
            imgchat = mView.findViewById(R.id.imgchat);
            elayouti = mView.findViewById(R.id.elayouti);
            sewrf = mView.findViewById(R.id.sewrf);

        }

        public void setTextoo(String msntext) {
            setms.setText(msntext);
        }

        public void setUide(String mident) {
            uidei = mident;
        }

        public void setDatai(String dataa) {
            tempto.setText(dataa);
        }

        public void setTyypo(String typo) {
            if (typo != null) {
                typoo = Integer.parseInt(typo);
            }
        }

        public void setImaagem(final Context applicationContext, final String imagem) {
            fotos = imagem;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            reuu = sharedPreferences.getBoolean("notifu", true);
            if (reuu){
                imgchat.setBackgroundResource(0);

                new CountDownTimer(Long.MAX_VALUE, 1) {
                    @Override
                    public void onTick(long l) {
                        if (imgchat.getDrawable() != null) {
                            sewrf.setVisibility(View.GONE);
                            cancel();
                        }else {
                            sewrf.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();

                RequestManager with = Glide.with(applicationContext);
                with.load(imagem).thumbnail(Glide.with(applicationContext).load(imagem)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        sewrf.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imgchat);
            }else {
                imgchat.setBackgroundResource(0);
                Picasso.with(applicationContext)
                        .load(imagem)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imgchat, new Callback() {
                            @Override
                            public void onSuccess() {
                                sewrf.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                Picasso.with(applicationContext)
                                        .load(imagem)
                                        .into(imgchat, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                sewrf.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onError() {
                                            }
                                        });
                            }
                        });
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mDatabase.child("Users").child(uide).child("eOnline").setValue("1");
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.child("Users").child(uide).child("eOnline").setValue("2");
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (etype.equals("1")) {
            if (transicao) {
                Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
                intent.putExtra("ezaak", "1");
                startActivity(intent);
                overridePendingTransition(R.anim.volte, R.anim.volte_ii);
                finish();
            }else {
                Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
                intent.putExtra("ezaak", "1");
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        }else if (etype.equals("2")){
            if (transicao) {
            Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
            intent.putExtra("ezaak", "2");
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
            }else {
                Intent intent = new Intent(SinezyaAudio.this, ListaDeContatos.class);
                intent.putExtra("ezaak", "2");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
            Uri mImageUri = data.getData();
            mAutori = true;
            looeee.setVisibility(View.VISIBLE);

            StorageReference filepath = mStorageRef.child("Chat_Images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    looeee.setVisibility(View.GONE);
                    if (audii) {
                        MediaPlayer mp = MediaPlayer.create(SinezyaAudio.this, R.raw.sound);
                        try {
                            mp.prepare();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mp.start();
                    }

                    if (mAutori) {
                        mAutori = false;
                        final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String datae = new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(new Date());

                        final DatabaseReference newPost = mDatabase.child("TAmigos").child(euVoce).push();
                        newPost.child("mident").setValue(uide);
                        newPost.child("dataa").setValue(datae);
                        newPost.child("typo").setValue("2");
                        newPost.child("imagem").setValue(downloadUrl.toString());
                        newPost.child("menssa").setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (emailno) {
                                    if (nitica == 0) {
                                        if (onli == 1) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaAudio.this);
                                            builder.setMessage("Sempre que iniciar um bate-papo com alguém que está off-line será feito a seguinte pergunta:\n" +
                                                    "\n" +
                                                    "Deseja enviar um alerta para " + noome + " sobre sua necessidade de conversar com ele(a)?");
                                            builder.setCancelable(false);
                                            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(final DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    JavaMailAPI javaMailAPI = new JavaMailAPI(SinezyaAudio.this, eimail, "SINEZYA", noome + " enviou um arquivo de imagem para você pelo bate-papo do aplicativo sinezya.");
                                                    javaMailAPI.execute();
                                                    nitica = 8;
                                                }
                                            });
                                            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    nitica = 8;
                                                }
                                            });
                                            AlertDialog alertDialog11 = builder.create();
                                            alertDialog11.setCanceledOnTouchOutside(false);
                                            alertDialog11.show();
                                        }
                                    }
                                }
                            }
                        });
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
}
