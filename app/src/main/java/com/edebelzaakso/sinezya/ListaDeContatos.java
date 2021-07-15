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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.edebelzaakso.sinezya.Models.Friend;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

public class ListaDeContatos extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private RecyclerView blog_list;
    private TextView AlertEm3, txt_set;
    private boolean yuio = false;
    private boolean yyubei = false;
    private FirebaseAuth mAuth;
    private String lisat;
    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_contatos);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ListaDeContatos.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        blog_list = findViewById(R.id.blog_list);
        AlertEm3 = findViewById(R.id.AlertEm3);
        txt_set = findViewById(R.id.txt_set);

        Bundle extras = getIntent().getExtras();
        lisat = extras.getString("ezaak");

        if (lisat.equals("1")){
            txt_set.setText("CONTATOS");
        }else if (lisat.equals("2")){
            txt_set.setText("POSTAGENS");
        }

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

        ConnectivityManager cm = (ConnectivityManager) ListaDeContatos.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()){
            AlertEm3.setEnabled(true);
            blog_list.setVisibility(View.VISIBLE);
            AlertEm3.setVisibility(View.GONE);
            chequeusuario();
        } else {
            AlertEm3.setEnabled(false);
            blog_list.setVisibility(View.GONE);
            AlertEm3.setText("Sem conexão com a internet.");
            AlertEm3.setVisibility(View.VISIBLE);
        }


        AlertEm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent intent = new Intent(ListaDeContatos.this, FazerAmizades.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Intent intent = new Intent(ListaDeContatos.this, FazerAmizades.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });
    }

    private void chequeusuario() {
        yuio = true;
        yyubei = true;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String user_id = user.getUid();
            mDatabase.child("Users").child(user_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (yuio) {
                        yuio = false;
                        if (dataSnapshot.child("nomepess").exists()) {
                            final String nomee = dataSnapshot.child("nomepess").getValue().toString();
                            if (dataSnapshot.child("OKNum").exists()) {
                                final String numeroo = dataSnapshot.child("OKNum").getValue().toString();
                                mDatabase.child("WCSKA").child(numeroo).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (yyubei) {
                                            yyubei = false;
                                            if (dataSnapshot.child("AMIGOS").exists()) {
                                                blog_list.setVisibility(View.VISIBLE);
                                                AlertEm3.setVisibility(View.GONE);
                                                testar(numeroo, nomee);
                                            } else {
                                                blog_list.setVisibility(View.GONE);
                                                AlertEm3.setText("Você não possui amigos, clique aqui para adicionar amigos.");
                                                AlertEm3.setVisibility(View.VISIBLE);
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

    private void testar(final String user_ide, final String nomee) {
        final FirebaseRecyclerAdapter<Friend, FriViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friend, FriViewHolder>(
                Friend.class,
                R.layout.lista_row,
                FriViewHolder.class,
                mDatabase.child("WCSKA").child(user_ide).child("AMIGOS")) {
            @Override
            protected void populateViewHolder(final FriViewHolder friViewHolder, Friend friend, int i) {
                final String post_key = getRef(i).getKey();

                friViewHolder.setImagem(getApplicationContext(), friend.getIMAGE());
                friViewHolder.setUid(friend.getUid());
                friViewHolder.setIdenticar(friend.getIdentificador());
                friViewHolder.setKeyyy(friend.getKeys());
                friViewHolder.setEmaiil(friend.getSmail());
                friViewHolder.setEuVocee(friend.getEu_voce());

                    friViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (lisat.equals("1")) {
                                if (transicao) {
                                    Intent intent = new Intent(ListaDeContatos.this, SinezyaAudio.class);
                                    intent.putExtra("Kpefil", friViewHolder.Kimagem);
                                    intent.putExtra("urlident", friViewHolder.identificadr);
                                    intent.putExtra("urluide", user_ide);
                                    intent.putExtra("urlkey", post_key);
                                    intent.putExtra("urlnome", friViewHolder.nomi);
                                    intent.putExtra("urlpost", "edebelzaakso");
                                    intent.putExtra("keyseu", friViewHolder.keyyy);
                                    intent.putExtra("etype", "1");
                                    intent.putExtra("eu_vocei", friViewHolder.euu_voce);
                                    intent.putExtra("eimail", friViewHolder.imail);
                                    intent.putExtra("meu_nome", nomee);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                }else {
                                    Intent intent = new Intent(ListaDeContatos.this, SinezyaAudio.class);
                                    intent.putExtra("Kpefil", friViewHolder.Kimagem);
                                    intent.putExtra("urlident", friViewHolder.identificadr);
                                    intent.putExtra("urluide", user_ide);
                                    intent.putExtra("urlkey", post_key);
                                    intent.putExtra("urlnome", friViewHolder.nomi);
                                    intent.putExtra("urlpost", "edebelzaakso");
                                    intent.putExtra("keyseu", friViewHolder.keyyy);
                                    intent.putExtra("etype", "1");
                                    intent.putExtra("eu_vocei", friViewHolder.euu_voce);
                                    intent.putExtra("eimail", friViewHolder.imail);
                                    intent.putExtra("meu_nome", nomee);
                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }
                            }else if (lisat.equals("2")){
                                if (transicao) {
                                Intent intent = new Intent(ListaDeContatos.this, SinezyaMeus.class);
                                intent.putExtra("Kpefil", friViewHolder.Kimagem);
                                intent.putExtra("urlident", friViewHolder.identificadr);
                                intent.putExtra("urluide", friViewHolder.uide);
                                intent.putExtra("urluser", user_ide);
                                intent.putExtra("urlkey", post_key);
                                intent.putExtra("eu_voceu", friViewHolder.euu_voce);
                                intent.putExtra("urlnome", friViewHolder.nomi);
                                intent.putExtra("keyseuu", friViewHolder.keyyy);
                                intent.putExtra("eimai", friViewHolder.imail);
                                intent.putExtra("meu_noome", nomee);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                }else {
                                    Intent intent = new Intent(ListaDeContatos.this, SinezyaMeus.class);
                                    intent.putExtra("Kpefil", friViewHolder.Kimagem);
                                    intent.putExtra("urlident", friViewHolder.identificadr);
                                    intent.putExtra("urluide", friViewHolder.uide);
                                    intent.putExtra("urluser", user_ide);
                                    intent.putExtra("urlkey", post_key);
                                    intent.putExtra("eu_voceu", friViewHolder.euu_voce);
                                    intent.putExtra("urlnome", friViewHolder.nomi);
                                    intent.putExtra("keyseuu", friViewHolder.keyyy);
                                    intent.putExtra("eimai", friViewHolder.imail);
                                    intent.putExtra("meu_noome", nomee);
                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }
                            }
                        }
                    });

                    friViewHolder.imgLista.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AlertDialog dialo;
                            dialo = new AlertDialog.Builder(ListaDeContatos.this).create();
                            View c = getLayoutInflater().inflate(R.layout.auver, null);
                            dialo.setCanceledOnTouchOutside(true);
                            dialo.setCancelable(true);
                            dialo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            final ImageView img_bu = c.findViewById(R.id.btimg);
                            dialo.setView(c);

                            RequestManager with = Glide.with(ListaDeContatos.this);
                            with.load(friViewHolder.Kimagem).thumbnail(Glide.with(ListaDeContatos.this).load(friViewHolder.Kimagem)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    Glide.with(ListaDeContatos.this).load(friViewHolder.Kimagem).thumbnail(Glide.with(ListaDeContatos.this).load(friViewHolder.Kimagem)).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_bu);
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
            }
        };
        blog_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FriViewHolder extends RecyclerView.ViewHolder {

        DatabaseReference mDatabaseUsu;
        String identificadr;
        ImageView imgLista;
        FirebaseAuth mAuth;
        TextView setnome;
        String Kimagem;
        String keyyy;
        String nomi;
        String uide;
        String imail;
        String euu_voce;
        View mView;

        public FriViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imgLista = mView.findViewById(R.id.imgLista);
            setnome = mView.findViewById(R.id.setnome);

            mAuth = FirebaseAuth.getInstance();
            mDatabaseUsu = FirebaseDatabase.getInstance().getReference().child("Users");
            mDatabaseUsu.keepSynced(true);
        }

        public void setImagem(final Context applicationContext, final String image) {
            Kimagem = image;
            RequestManager with = Glide.with(applicationContext);
            with.load(image).thumbnail(Glide.with(applicationContext).load(image)).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Glide.with(applicationContext).load(image).thumbnail(Glide.with(applicationContext).load(image)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgLista);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(imgLista);
        }

        public void setUid(String uid) {
            uide = uid;
            mDatabaseUsu.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("nomepess").exists()){
                        nomi = dataSnapshot.child("nomepess").getValue().toString();
                        setnome.setText(nomi);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setIdenticar(String identificador) {
            identificadr = identificador;

        }

        public void setKeyyy(String keys) {
            keyyy = keys;
        }

        public void setEmaiil(String smail) {
            imail = smail;
        }

        public void setEuVocee(String eu_voce) {
            euu_voce = eu_voce;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void ininii(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(ListaDeContatos.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(ListaDeContatos.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
