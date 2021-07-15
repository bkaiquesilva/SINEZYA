package com.edebelzaakso.sinezya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.edebelzaakso.sinezya.Models.Pendentes;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

public class SolicitacaoAmz extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private RecyclerView blog_list;
    private TextView AlertEm4;
    private boolean yuio = false;
    private boolean yybe = false;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solicitacaoamz);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SolicitacaoAmz.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        blog_list = findViewById(R.id.blog_listi);
        AlertEm4 = findViewById(R.id.AlertEm4);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        blog_list.setHasFixedSize(true);
        blog_list.setLayoutManager(layoutManager);

        ConnectivityManager cm = (ConnectivityManager) SolicitacaoAmz.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()){
            AlertEm4.setEnabled(true);
            blog_list.setVisibility(View.VISIBLE);
            AlertEm4.setVisibility(View.GONE);
            chequeusuario();
        } else {
            AlertEm4.setEnabled(false);
            blog_list.setVisibility(View.GONE);
            AlertEm4.setText("Sem conexão com a internet.");
            AlertEm4.setVisibility(View.VISIBLE);
        }

    }

    private void chequeusuario() {
        yuio = true;
        yybe = true;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String emailll = user.getEmail();
            final String user_id = user.getUid();
            mDatabase.child("Users").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (yuio) {
                        yuio = false;
                        if (dataSnapshot.child("OKNum").exists()) {
                            if (dataSnapshot.child("image").exists()) {
                                final String imaeage = dataSnapshot.child("image").getValue().toString();
                                final String numeroo = dataSnapshot.child("OKNum").getValue().toString();
                                mDatabase.child("WCSKA").child(numeroo).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (yybe) {
                                            yybe = false;
                                            if (dataSnapshot.child("PENDENTE").exists()) {
                                                blog_list.setVisibility(View.VISIBLE);
                                                AlertEm4.setVisibility(View.GONE);
                                                testar(numeroo, user_id, emailll, imaeage);
                                            } else {
                                                blog_list.setVisibility(View.GONE);
                                                AlertEm4.setText("Ainda não há novidades.");
                                                AlertEm4.setVisibility(View.VISIBLE);
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

    private void testar(final String user_ide, final String id_use, final String emailll, final String imager) {
        final FirebaseRecyclerAdapter<Pendentes, FViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pendentes, FViewHolder>(
                Pendentes.class,
                R.layout.pende_row,
                FViewHolder.class,
                mDatabase.child("WCSKA").child(user_ide).child("PENDENTE")) {
            @Override
            protected void populateViewHolder(final FViewHolder friViewHolder, Pendentes friend, int i) {
                final String post_key = getRef(i).getKey();

                friViewHolder.setImagem(getApplicationContext(), friend.getIMAGE());
                friViewHolder.setUid(friend.getUid());
                friViewHolder.setIdenticar(friend.getIdentificador());
                friViewHolder.setEnvio(friend.getEnvio());
                friViewHolder.setKeyy(friend.getKeys());
                friViewHolder.setEmails(friend.getSmail());
                friViewHolder.setEuVoce(friend.getEu_voce());


                if (friViewHolder.ennvio == 0){
                    friViewHolder.setacet.setVisibility(View.VISIBLE);
                    friViewHolder.setacet.setEnabled(true);
                    friViewHolder.setacet.setText("ACEITAR");



                    friViewHolder.imgPende.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AlertDialog dialo;
                            dialo = new AlertDialog.Builder(SolicitacaoAmz.this).create();
                            View c = getLayoutInflater().inflate(R.layout.auver, null);
                            dialo.setCanceledOnTouchOutside(true);
                            dialo.setCancelable(true);
                            dialo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            final ImageView img_bu = c.findViewById(R.id.btimg);
                            dialo.setView(c);

                            RequestManager with = Glide.with(SolicitacaoAmz.this);
                            with.load(friViewHolder.imagemepnde).thumbnail(Glide.with(SolicitacaoAmz.this).load(friViewHolder.imagemepnde)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    RequestManager with = Glide.with(SolicitacaoAmz.this);
                                    with.load(friViewHolder.imagemepnde).thumbnail(Glide.with(SolicitacaoAmz.this).load(friViewHolder.imagemepnde)).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_bu);
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


                friViewHolder.setacet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            friViewHolder.setacet.setEnabled(false);
                            friViewHolder.setacet.setText("Aguarde...");
                            final DatabaseReference newPost = friViewHolder.mDatabaseUsu.child("WCSKA").child(friViewHolder.identificadr).child("AMIGOS").child(friViewHolder.keyy);
                            final DatabaseReference newPost1 = friViewHolder.mDatabaseUsu.child("WCSKA").child(user_ide).child("AMIGOS").child(post_key);

                            friViewHolder.mDatabaseUsu.child("TAmigos").child(friViewHolder.eu_voceee).setValue("o");
                            newPost1.child("IMAGE").setValue(friViewHolder.imagemepnde);
                            newPost1.child("uid").setValue(friViewHolder.uide);
                            newPost1.child("keys").setValue(friViewHolder.keyy);
                            newPost1.child("Smail").setValue(friViewHolder.emaill);
                            newPost1.child("identificador").setValue(friViewHolder.identificadr);
                            newPost1.child("eu_voce").setValue(friViewHolder.eu_voceee);

                            newPost.child("IMAGE").setValue(imager);
                            newPost.child("uid").setValue(id_use);
                            newPost.child("keys").setValue(post_key);
                            newPost.child("Smail").setValue(emailll);
                            newPost.child("eu_voce").setValue(friViewHolder.eu_voceee);
                            newPost.child("identificador").setValue(user_ide);
                            friViewHolder.mDatabaseUsu.child("WCSKA").child(friViewHolder.identificadr).child("PENDENTE").child(friViewHolder.keyy).child("envio").setValue("4");
                            friViewHolder.mDatabaseUsu.child("WCSKA").child(user_ide).child("PENDENTE").child(post_key).child("envio").setValue("3");
                            friViewHolder.mDatabaseUsu.child("TPendentes").child(friViewHolder.eu_voceee).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    friViewHolder.setacet.setEnabled(false);
                                    friViewHolder.setacet.setText("ACEITEI");
                                }
                            });
                        }
                    }
                });


                }else if(friViewHolder.ennvio == 3) {
                    friViewHolder.setacet.setVisibility(View.VISIBLE);
                    friViewHolder.setacet.setEnabled(false);
                    friViewHolder.setacet.setText("ACEITEI");

                }else if(friViewHolder.ennvio == 4) {
                    friViewHolder.setacet.setVisibility(View.VISIBLE);
                    friViewHolder.setacet.setEnabled(false);
                    friViewHolder.setacet.setText("ACEITOU");
                }else {
                    friViewHolder.setacet.setVisibility(View.VISIBLE);
                    friViewHolder.setacet.setEnabled(false);
                    friViewHolder.setacet.setText("ENVIADO");
                }
            }
        };
        blog_list.setAdapter(firebaseRecyclerAdapter);
    }

    public void ionoi(View view) {
        if (transicao) {
            Intent intent = new Intent(SolicitacaoAmz.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }else {
            Intent intent = new Intent(SolicitacaoAmz.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    public static class FViewHolder extends RecyclerView.ViewHolder {

        DatabaseReference mDatabaseUsu;
        FirebaseAuth mAuth;
        String identificadr;
        String imagemepnde;
        ImageView imgPende;
        TextView setacet;
        int ennvio;
        String uide;
        String keyy;
        String emaill;
        String eu_voceee;
        View mView;

        public FViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imgPende = mView.findViewById(R.id.imgPende);
            setacet = mView.findViewById(R.id.setacet);

            mAuth = FirebaseAuth.getInstance();
            mDatabaseUsu = FirebaseDatabase.getInstance().getReference();
            mDatabaseUsu.keepSynced(true);
        }

        public void setImagem(final Context applicationContext, final String image) {
            imagemepnde = image;
            RequestManager with = Glide.with(applicationContext);
            with.load(image).thumbnail(Glide.with(applicationContext).load(image)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Glide.with(applicationContext).load(image).thumbnail(Glide.with(applicationContext).load(image)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgPende);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(imgPende);
        }

        public void setUid(String uid) {
            uide = uid;
        }

        public void setIdenticar(String identificador) {
            identificadr = identificador;

        }

        public void setEnvio(String envio) {
            if (envio != null) {
                ennvio = Integer.parseInt(envio);
            }
        }

        public void setKeyy(String keys) {
            keyy = keys;
        }

        public void setEmails(String smail) {
            emaill = smail;
        }

        public void setEuVoce(String eu_voce) {
            eu_voceee = eu_voce;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(SolicitacaoAmz.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }else {
            Intent intent = new Intent(SolicitacaoAmz.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
