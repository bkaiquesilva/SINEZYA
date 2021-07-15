package com.edebelzaakso.sinezya;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
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
import com.edebelzaakso.sinezya.Models.Blog;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;
import com.edebelzaakso.sinezya.gravacao.err.Si;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SinezyaMeus extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private RecyclerView blog_list;
    private boolean mProcessLik = false;
    private ImageView Aperfil, btnexclu;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private TextView gh, Anamed;
    private Typeface sb;
    private boolean audii = true;
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
    private int nitica = 0;
    private FirebaseUser user;
    SharedPreferences sharedPreferences = null;
    private StorageReference mStorageRef;
    private boolean transicao = true;
    private boolean emailno = true;
    private boolean sledi = false;
    private boolean mAuti = false;
    private boolean mAuri4444 = false;
    private TextView merrer;
    private AlertDialog dialo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sinezya_meus);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SinezyaMeus.this);
        transicao = sharedPreferences.getBoolean("escuro", true);
        emailno = sharedPreferences.getBoolean("notifo", true);
        audii = sharedPreferences.getBoolean("notife", true);

        merrer = findViewById(R.id.AlertEm555);
        gh = findViewById(R.id.gh);
        blog_list = findViewById(R.id.blog_lisn);
        Aperfil = findViewById(R.id.Aperfil);
        Anamed = findViewById(R.id.Anamed);
        btnexclu = findViewById(R.id.btnexclu);
        mProgress = new ProgressDialog(this);
        sb = Typeface.createFromAsset(getAssets(), "fonts/frijole.ttf");
        gh.setTypeface(sb);

        Bundle extras = getIntent().getExtras();
        SMKfil = extras.getString("Kpefil");
        Sisin = extras.getString("urlident");
        Suide = extras.getString("urluide");
        Suser = extras.getString("urluser");
        Surlke = extras.getString("urlkey");
        Srome = extras.getString("urlnome");
        _seu_key = extras.getString("keyseuu");
        eimail = extras.getString("eimai");
        nnoome = extras.getString("meu_noome");
        EuuVoce = extras.getString("eu_voceu");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase.keepSynced(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        blog_list.setHasFixedSize(true);
        blog_list.setLayoutManager(layoutManager);

        Anamed.setText(Srome);
        RequestManager with = Glide.with(SinezyaMeus.this);
        with.load(SMKfil).thumbnail(Glide.with(SinezyaMeus.this).load(SMKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                RequestManager with = Glide.with(SinezyaMeus.this);
                with.load(SMKfil).thumbnail(Glide.with(SinezyaMeus.this).load(SMKfil)).diskCacheStrategy(DiskCacheStrategy.ALL).into(Aperfil);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(Aperfil);

        chequeusuario();


        Aperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent intent = new Intent(SinezyaMeus.this, Info_Sinezya.class);
                    intent.putExtra("Kpefil", SMKfil);
                    intent.putExtra("urlident", Sisin);
                    intent.putExtra("urluide", Suide);
                    intent.putExtra("urluser", Suser);
                    intent.putExtra("urlkey", Surlke);
                    intent.putExtra("urlnome", Srome);
                    intent.putExtra("keyseu", _seu_key);
                    intent.putExtra("eimail", eimail);
                    intent.putExtra("meu_nome", nnoome);
                    intent.putExtra("eu_vocei", EuuVoce);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Intent intent = new Intent(SinezyaMeus.this, Info_Sinezya.class);
                    intent.putExtra("Kpefil", SMKfil);
                    intent.putExtra("urlident", Sisin);
                    intent.putExtra("urluide", Suide);
                    intent.putExtra("urluser", Suser);
                    intent.putExtra("urlkey", Surlke);
                    intent.putExtra("urlnome", Srome);
                    intent.putExtra("keyseu", _seu_key);
                    intent.putExtra("eimail", eimail);
                    intent.putExtra("meu_nome", nnoome);
                    intent.putExtra("eu_vocei", EuuVoce);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        btnexclu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaMeus.this);
                builder.setMessage("Quer mesmo remover esse amigo?");
                builder.setCancelable(false);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mProgress.setMessage("Iniciando remosão.....");
                        mProgress.show();

                        mDatabase.child("WCSKA").child(Sisin).child("AMIGOS").child(_seu_key).removeValue();
                        mDatabase.child("WCSKA").child(Sisin).child("PENDENTE").child(_seu_key).removeValue();
                        mDatabase.child("WCSKA").child(Suser).child("AMIGOS").child(Surlke).removeValue();
                        mDatabase.child("WCSKA").child(Suser).child("PENDENTE").child(Surlke).removeValue();
                        mDatabase.child("TAmigos").child(EuuVoce).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mProgress.dismiss();
                                Toast.makeText(SinezyaMeus.this, "Amigo removido", Toast.LENGTH_SHORT).show();
                                if (transicao) {
                                    Intent intent = new Intent(SinezyaMeus.this, ListaDeContatos.class);
                                    intent.putExtra("ezaak", "2");
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.volte, R.anim.volte_ii);
                                    finish();
                                }else {
                                    Intent intent = new Intent(SinezyaMeus.this, ListaDeContatos.class);
                                    intent.putExtra("ezaak", "2");
                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                    finish();
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

    }

    private void chequeusuario() {
        ConnectivityManager cm = (ConnectivityManager) SinezyaMeus.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()){
            sledi = true;
            mDatabase.child("Postagens").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (sledi) {
                        sledi = false;
                        if (dataSnapshot.child(Suide).exists()) {
                            merrer.setVisibility(View.GONE);
                            blog_list.setVisibility(View.VISIBLE);
                            chequeusuario2();
                        }else {
                            merrer.setEnabled(true);
                            blog_list.setVisibility(View.GONE);
                            merrer.setText("Este usuário não possui postagens, aguarde mais um pouco.");
                            merrer.setVisibility(View.VISIBLE);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else {
            merrer.setEnabled(false);
            blog_list.setVisibility(View.GONE);
            merrer.setText("Sem conexão com a internet.");
            merrer.setVisibility(View.VISIBLE);
        }
    }

    private void chequeusuario2() {
        if (user != null) {
            mAuri4444 = true;
            mDatabase.child("Users").child(Suide).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (mAuri4444) {
                        mAuri4444 = false;
                        if (dataSnapshot.child("priva_info").exists()) {
                            final String priva_infoo = dataSnapshot.child("priva_info").getValue().toString();
                            if (priva_infoo.equals("1")) {
                                merrer.setEnabled(true);
                                blog_list.setVisibility(View.VISIBLE);
                                merrer.setVisibility(View.GONE);
                                testar(Suide, user.getUid());
                            } else if (priva_infoo.equals("0")) {
                                merrer.setEnabled(false);
                                blog_list.setVisibility(View.GONE);
                                merrer.setText("Esse usuário não permite que suas postagens sejam acessadas.");
                                merrer.setVisibility(View.VISIBLE);
                            }
                        } else {
                            testar(Suide, user.getUid());
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void testar(final String user_ide, final String uidee) {
        final FirebaseRecyclerAdapter<Blog, meuViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, meuViewHolder>(
                Blog.class,
                R.layout.blog_row,
                meuViewHolder.class,
                mDatabase.child("Postagens").child(user_ide)) {
            @Override
            protected void populateViewHolder(final meuViewHolder friViewHolder, Blog friend, int i) {
                final String post_key = getRef(i).getKey();

                friViewHolder.setImage(getApplicationContext(), friend.getIMAGE());
                friViewHolder.setUid(friend.getUid());
                friViewHolder.setLikke(friend.getLikke());
                friViewHolder.setIdentif(friend.getIdentificador());
                friViewHolder.setLikeBtn(post_key);

                friViewHolder.partilha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaMeus.this);
                        builder.setMessage("Quer mesmo compartilhar essa postagem em sua linha do tempo?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                final DatabaseReference newPost = mDatabase.child("Postagens").child(uidee).push();
                                newPost.child("IMAGE").setValue(friViewHolder.foto);
                                newPost.child("uid").setValue(uidee);
                                newPost.child("likke").setValue("0");
                                newPost.child("identificador").setValue(Suide).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SinezyaMeus.this, "Compartilhado", Toast.LENGTH_SHORT).show();
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
                });

                friViewHolder.post_comen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (transicao) {
                            Intent intent = new Intent(SinezyaMeus.this, SinezyaAudio.class);
                            intent.putExtra("Kpefil", SMKfil);
                            intent.putExtra("urlident", Sisin);
                            intent.putExtra("urluide", Suser);
                            intent.putExtra("urlkey", Surlke);
                            intent.putExtra("urlnome", Srome);
                            intent.putExtra("urlpost", friViewHolder.foto);
                            intent.putExtra("keyseu", _seu_key);
                            intent.putExtra("etype", "2");
                            intent.putExtra("eimail", eimail);
                            intent.putExtra("eu_vocei", EuuVoce);
                            intent.putExtra("meu_nome", nnoome);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }else {
                            Intent intent = new Intent(SinezyaMeus.this, SinezyaAudio.class);
                            intent.putExtra("Kpefil", SMKfil);
                            intent.putExtra("urlident", Sisin);
                            intent.putExtra("urluide", Suser);
                            intent.putExtra("urlkey", Surlke);
                            intent.putExtra("urlnome", Srome);
                            intent.putExtra("urlpost", friViewHolder.foto);
                            intent.putExtra("keyseu", _seu_key);
                            intent.putExtra("etype", "2");
                            intent.putExtra("eu_vocei", EuuVoce);
                            intent.putExtra("eimail", eimail);
                            intent.putExtra("meu_nome", nnoome);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }
                });

                friViewHolder.mLikebt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mProcessLik = true;
                        friViewHolder.mLikebt.setEnabled(false);
                        mDatabase.child("Likes").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (mProcessLik) {
                                    mProcessLik = false;
                                    if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                                        mDatabase.child("Likes").child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                        if (friViewHolder.myIntValu > 0) {
                                            int pts = friViewHolder.myIntValu - 1;
                                            String sol = String.valueOf(pts);
                                            friViewHolder.myIntValu = pts;
                                            mDatabase.child("Postagens").child(user_ide).child(post_key).child("likke").setValue(sol).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    friViewHolder.mLikebt.setEnabled(true);
                                                }
                                            });
                                        }

                                    } else {

                                        mDatabase.child("Likes").child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("RandomValue");
                                        if (friViewHolder.myIntValu > 0 || friViewHolder.myIntValu == 0) {
                                            int pts = friViewHolder.myIntValu + 1;
                                            String sol = String.valueOf(pts);
                                            friViewHolder.myIntValu = pts;
                                            mDatabase.child("Postagens").child(user_ide).child(post_key).child("likke").setValue(sol).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    friViewHolder.mLikebt.setEnabled(true);
                                                    friViewHolder.mLikebt.setClickable(true);
                                                }
                                            });
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });
            }
        };
        blog_list.setAdapter(firebaseRecyclerAdapter);

    }

    public static class meuViewHolder extends RecyclerView.ViewHolder {

        View mVie;
        TextView contslikes;
        ImageButton mLikebt;
        FirebaseAuth mAut;
        SharedPreferences sharedPreferences = null;
        DatabaseReference mDatabaseLik;
        private boolean reuu = false;
        int myIntValu;
        String foto;
        String liked;
        ImageView post_imag, post_comen, partilha;
        String uidio, identificad;
        LinearLayout layt;

        public meuViewHolder(View itemView) {
            super(itemView);
            mVie = itemView;

            mLikebt = mVie.findViewById(R.id.post_like);
            contslikes = mVie.findViewById(R.id.contslikes);
            post_comen = mVie.findViewById(R.id.post_coment);
            partilha = mVie.findViewById(R.id.post_share);
            post_imag = mVie.findViewById(R.id.post_image);
            layt = mVie.findViewById(R.id.layty);

            mDatabaseLik = FirebaseDatabase.getInstance().getReference().child("Likes");
            mDatabaseLik.keepSynced(true);
            mAut = FirebaseAuth.getInstance();

        }

        public void setLikeBtn(final String post_key) {
            mDatabaseLik.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(post_key).hasChild(mAut.getCurrentUser().getUid())) {
                        mLikebt.setImageResource(R.drawable.ic_yes_heart_colored);

                    } else {
                        mLikebt.setImageResource(R.drawable.ic_no_heart_gray);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setLikke(String likke) {
            int convertedVal = Integer.parseInt(likke);
            myIntValu = convertedVal;
            liked = likke;
            contslikes.setText(likke);
        }

        public void setImage(final Context ctx, final String IMAGE) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            reuu = sharedPreferences.getBoolean("notifu", true);
            foto = IMAGE;

            if (reuu){
                post_imag.setBackgroundResource(0);

                new CountDownTimer(Long.MAX_VALUE, 1) {
                    @Override
                    public void onTick(long l) {
                        if (post_imag.getDrawable() != null) {
                            layt.setVisibility(View.GONE);
                            cancel();
                        }else {
                            layt.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();

                RequestManager with = Glide.with(ctx);
                with.load(IMAGE).thumbnail(Glide.with(ctx).load(IMAGE)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        RequestManager with = Glide.with(ctx);
                        with.load(IMAGE).thumbnail(Glide.with(ctx).load(IMAGE)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                layt.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                layt.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(post_imag);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        layt.setVisibility(View.GONE);
                        return false;
                    }
                }).into(post_imag);

            }else {
                post_imag.setBackgroundResource(0);
                Picasso.with(ctx)
                        .load(IMAGE)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(post_imag, new Callback() {
                            @Override
                            public void onSuccess() {
                                layt.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                Picasso.with(ctx)
                                        .load(IMAGE)
                                        .into(post_imag, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                layt.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onError() {
                                            }
                                        });
                            }
                        });
            }
        }

        public void setUid(String uid) {
            uidio = uid;
        }

        public void setIdentif(String identificador) {
            identificad = identificador;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    public void audei(View view) {
        if (transicao) {
            Intent intent = new Intent(SinezyaMeus.this, SinezyaAudio.class);
            intent.putExtra("Kpefil", SMKfil);
            intent.putExtra("urlident", Sisin);
            intent.putExtra("urluide", Suser);
            intent.putExtra("urlkey", Surlke);
            intent.putExtra("urlnome", Srome);
            intent.putExtra("urlpost", "edebelzaakso");
            intent.putExtra("keyseu", _seu_key);
            intent.putExtra("etype", "2");
            intent.putExtra("eu_vocei", EuuVoce);
            intent.putExtra("eimail", eimail);
            intent.putExtra("meu_nome", nnoome);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }else {
            Intent intent = new Intent(SinezyaMeus.this, SinezyaAudio.class);
            intent.putExtra("Kpefil", SMKfil);
            intent.putExtra("urlident", Sisin);
            intent.putExtra("urluide", Suser);
            intent.putExtra("urlkey", Surlke);
            intent.putExtra("urlnome", Srome);
            intent.putExtra("eu_vocei", EuuVoce);
            intent.putExtra("urlpost", "edebelzaakso");
            intent.putExtra("keyseu", _seu_key);
            intent.putExtra("etype", "2");
            intent.putExtra("eimail", eimail);
            intent.putExtra("meu_nome", nnoome);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    public void futbol(View view) {
        Uri d = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.futebol);
        envioa(nnoome + " chamou você para jogar futebol.", d);
    }

    public void gamee(View view) {
        dialo = new AlertDialog.Builder(this).create();
        View c = getLayoutInflater().inflate(R.layout.delet, null);
        dialo.setCanceledOnTouchOutside(false);
        dialo.setCancelable(false);
        final EditText email = c.findViewById(R.id.edt_email);
        final EditText senha = c.findViewById(R.id.edt_senha);
        final TextView cancelar = c.findViewById(R.id.btncanci);
        final TextView deletar = c.findViewById(R.id.btn_dlt);
        final TextView etitle = c.findViewById(R.id.etitle);
        final TextView s_title = c.findViewById(R.id.s_title);
        final ProgressBar tpro = c.findViewById(R.id.podegi);
        dialo.setView(c);


        etitle.setText("Você está convidando " + Srome + " para jogar...");
        etitle.setTextColor(Color.BLACK);
        s_title.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        senha.setAllCaps(false);
        senha.setHint("Ninja American City");
        deletar.setText("ENVIAR");

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar.setEnabled(false);
                cancelar.setEnabled(false);
                tpro.setVisibility(View.VISIBLE);
                deletar.getBackground().setAlpha(128);
                cancelar.getBackground().setAlpha(128);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialo.dismiss();
                    }
                }, 1700);
            }
        });

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_senha = senha.getText().toString().trim();
                if (!e_senha.isEmpty()) {
                    deletar.setEnabled(false);
                    cancelar.setEnabled(false);
                    tpro.setVisibility(View.VISIBLE);
                    deletar.getBackground().setAlpha(128);
                    cancelar.getBackground().setAlpha(128);

                    Uri d = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.jogos);
                    envioa(nnoome + " chamou você para jogar " + e_senha + ".", d);
                }
            }
        });

        dialo.show();
    }

    public void alive(View view) {
        dialo = new AlertDialog.Builder(this).create();
        View c = getLayoutInflater().inflate(R.layout.delet, null);
        dialo.setCanceledOnTouchOutside(false);
        dialo.setCancelable(false);
        final EditText email = c.findViewById(R.id.edt_email);
        final EditText senha = c.findViewById(R.id.edt_senha);
        final TextView cancelar = c.findViewById(R.id.btncanci);
        final TextView deletar = c.findViewById(R.id.btn_dlt);
        final TextView etitle = c.findViewById(R.id.etitle);
        final TextView s_title = c.findViewById(R.id.s_title);
        final ProgressBar tpro = c.findViewById(R.id.podegi);
        dialo.setView(c);


        etitle.setText("Você está convidando " + Srome + " para assistir a live de...");
        etitle.setTextColor(Color.BLACK);
        s_title.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        senha.setAllCaps(false);
        senha.setHint("DevyJonas");
        deletar.setText("ENVIAR");

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar.setEnabled(false);
                cancelar.setEnabled(false);
                tpro.setVisibility(View.VISIBLE);
                deletar.getBackground().setAlpha(128);
                cancelar.getBackground().setAlpha(128);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialo.dismiss();
                    }
                }, 1700);
            }
        });

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_senha = senha.getText().toString().trim();
                if (!e_senha.isEmpty()) {
                    deletar.setEnabled(false);
                    cancelar.setEnabled(false);
                    tpro.setVisibility(View.VISIBLE);
                    deletar.getBackground().setAlpha(128);
                    cancelar.getBackground().setAlpha(128);

                    Uri d = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.livee);
                    envioa(nnoome + " chamou você para assistir a live de " + e_senha +".", d);
                }
            }
        });

        dialo.show();

    }

    public void menuu(View view) {
        dialo = new AlertDialog.Builder(this).create();
        View c = getLayoutInflater().inflate(R.layout.delet, null);
        dialo.setCanceledOnTouchOutside(false);
        dialo.setCancelable(false);
        final EditText email = c.findViewById(R.id.edt_email);
        final EditText senha = c.findViewById(R.id.edt_senha);
        final TextView cancelar = c.findViewById(R.id.btncanci);
        final TextView deletar = c.findViewById(R.id.btn_dlt);
        final TextView etitle = c.findViewById(R.id.etitle);
        final TextView s_title = c.findViewById(R.id.s_title);
        final ProgressBar tpro = c.findViewById(R.id.podegi);
        dialo.setView(c);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("+NN (NN)NNNNN-NNNN");
        MaskTextWatcher mtv = new MaskTextWatcher(senha, smf);
        senha.addTextChangedListener(mtv);

        etitle.setText("Você está enviando o seu número de telefone para " + Srome + ", exemplo...");
        etitle.setTextColor(Color.BLACK);
        s_title.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        senha.setInputType(InputType.TYPE_CLASS_NUMBER);
        senha.setAllCaps(false);
        senha.setHint("+55 (33)99828-5010");
        deletar.setText("ENVIAR");

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar.setEnabled(false);
                cancelar.setEnabled(false);
                tpro.setVisibility(View.VISIBLE);
                deletar.getBackground().setAlpha(128);
                cancelar.getBackground().setAlpha(128);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialo.dismiss();
                    }
                }, 1700);
            }
        });

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_senha = senha.getText().toString().trim();
                if (!e_senha.isEmpty()) {
                    deletar.setEnabled(false);
                    cancelar.setEnabled(false);
                    tpro.setVisibility(View.VISIBLE);
                    deletar.getBackground().setAlpha(128);
                    cancelar.getBackground().setAlpha(128);

                    Uri d = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.telefonee);
                    envioa(nnoome + " enviou o número de telefone dele(a) para você...\n" +
                            "\n" +
                            e_senha, d);
                }
            }
        });

        dialo.show();
    }

    public void filmee(View view) {
        dialo = new AlertDialog.Builder(this).create();
        View c = getLayoutInflater().inflate(R.layout.delet, null);
        dialo.setCanceledOnTouchOutside(false);
        dialo.setCancelable(false);
        final EditText email = c.findViewById(R.id.edt_email);
        final EditText senha = c.findViewById(R.id.edt_senha);
        final TextView cancelar = c.findViewById(R.id.btncanci);
        final TextView deletar = c.findViewById(R.id.btn_dlt);
        final TextView etitle = c.findViewById(R.id.etitle);
        final TextView s_title = c.findViewById(R.id.s_title);
        final ProgressBar tpro = c.findViewById(R.id.podegi);
        dialo.setView(c);


        etitle.setText("Você está convidando " + Srome + " para assistir o filme...");
        etitle.setTextColor(Color.BLACK);
        s_title.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        senha.setAllCaps(false);
        senha.setHint("Interestelar");
        deletar.setText("ENVIAR");

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar.setEnabled(false);
                cancelar.setEnabled(false);
                tpro.setVisibility(View.VISIBLE);
                deletar.getBackground().setAlpha(128);
                cancelar.getBackground().setAlpha(128);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialo.dismiss();
                    }
                }, 1700);
            }
        });

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_senha = senha.getText().toString().trim();
                if (!e_senha.isEmpty()) {
                    deletar.setEnabled(false);
                    cancelar.setEnabled(false);
                    tpro.setVisibility(View.VISIBLE);
                    deletar.getBackground().setAlpha(128);
                    cancelar.getBackground().setAlpha(128);

                    Uri d = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.filme);
                    envioa(nnoome + " chamou você para assistir o filme " + e_senha + ".", d);
                }
            }
        });

        dialo.show();
    }

    public void olar(View view) {
        Uri d = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.olaa);
        envioa(nnoome + " mandou um olá para você.", d);
    }

    public void lanche(View view) {
        Uri d = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.lanche);
        envioa(nnoome + " chamou você para fazer um lanche.", d);
    }


    private void envioa(final String textoo, final Uri drawable) {
        if (!textoo.isEmpty()) {
            mAuti = true;

            if (user != null) {
                StorageReference filepath = mStorageRef.child("Chat_Images").child(drawable.getLastPathSegment());
                filepath.putFile(drawable).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (mAuti) {
                            mAuti = false;
                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String datae = new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(new Date());

                            final DatabaseReference newPost = mDatabase.child("TAmigos").child(EuuVoce).push();
                            newPost.child("mident").setValue(Suser);
                            newPost.child("dataa").setValue(datae);
                            newPost.child("imagem").setValue(downloadUrl.toString());
                            newPost.child("typo").setValue("2");
                            newPost.child("menssa").setValue("1");

                            String datae2 = new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(new Date());
                            final DatabaseReference newPost2 = mDatabase.child("TAmigos").child(EuuVoce).push();
                            newPost2.child("mident").setValue(Suser);
                            newPost2.child("dataa").setValue(datae2);
                            newPost2.child("imagem").setValue("https://images.jpep");
                            newPost2.child("typo").setValue("1");
                            newPost2.child("menssa").setValue(textoo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (dialo != null){
                                        dialo.dismiss();
                                    }
                                    if (audii) {
                                        MediaPlayer mp = MediaPlayer.create(SinezyaMeus.this, R.raw.sound);
                                        try {
                                            mp.prepare();
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        mp.start();
                                    }

                                    if (emailno) {
                                        if (nitica == 0) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(SinezyaMeus.this);
                                            builder.setMessage("Sempre que iniciar um bate-papo com alguém que está off-line será feito a seguinte pergunta:\n" +
                                                    "\n" +
                                                    "Deseja enviar um alerta para " + Srome + " sobre sua necessidade de conversar com ele(a)?");
                                            builder.setCancelable(false);
                                            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(final DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    JavaMailAPI javaMailAPI = new JavaMailAPI(SinezyaMeus.this, eimail, "SINEZYA", nnoome + textoo);
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
                            });
                        }
                    }
                });
            } else {
                mAuti = false;
            }

        }else {

        }
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(SinezyaMeus.this, ListaDeContatos.class);
            intent.putExtra("ezaak", "2");
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(SinezyaMeus.this, ListaDeContatos.class);
            intent.putExtra("ezaak", "2");
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}