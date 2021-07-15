package com.edebelzaakso.sinezya.OrkutActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.sachinvarma.easypermission.EasyPermissionConstants;
import com.sachinvarma.easypermission.EasyPermissionList;
import com.edebelzaakso.sinezya.Ajustes;
import com.edebelzaakso.sinezya.FazerAmizades;
import com.edebelzaakso.sinezya.ListaDeContatos;
import com.edebelzaakso.sinezya.LognOrkut;
import com.edebelzaakso.sinezya.Models.Blog;
import com.edebelzaakso.sinezya.PerfilSinezya;
import com.edebelzaakso.sinezya.R;
import com.edebelzaakso.sinezya.SolicitacaoAmz;
import com.edebelzaakso.sinezya.gravacao.AAAA;
import com.edebelzaakso.sinezya.postgif.MeuAlbumActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MainOrkut extends AppCompatActivity {

    private DatabaseReference mDatabaseX;
    private RecyclerView mBlogList;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseLike;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView atualizeer, Kperfil;
    private SwipeRefreshLayout activity_main;
    private ImageView ejetarr, poste, vernotication, addamigo, btnpublico, btnaudio;
    private RelativeLayout atperfil, perfilo;
    private boolean mProcessLike = false;
    private boolean mAutorize2 = false;
    private boolean camera = false;
    private boolean yuba = false;
    private boolean yube = false;
    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;
    private boolean nitofi = true;
    private FirebaseAuth mAuth;
    private TextView AlertEm;
    private String user_id;
    String peffoto;
    String nonname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orkut_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainOrkut.this);
        camera = sharedPreferences.getBoolean("camera", true);
        nitofi = sharedPreferences.getBoolean("notifi", true);
        transicao = sharedPreferences.getBoolean("escuro", true);






        activity_main = findViewById(R.id.activity_main);
        btnpublico = findViewById(R.id.btnpublico);
        atualizeer = findViewById(R.id.atualizeer);
        mBlogList = findViewById(R.id.blog_list);
        atperfil = findViewById(R.id.atperfil);
        Kperfil = findViewById(R.id.Kperfil);
        AlertEm = findViewById(R.id.AlertEm);
        addamigo = findViewById(R.id.addamigo);
        btnaudio = findViewById(R.id.btnaudio);
        ejetarr = findViewById(R.id.ejetar);
        poste = findViewById(R.id.btnposte);
        vernotication = findViewById(R.id.vernotication);
        perfilo = findViewById(R.id.perfilo);





        firebaseInit();
        blogList();

        ConnectivityManager cm = (ConnectivityManager) MainOrkut.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()){
            AlertEm.setEnabled(true);
            mBlogList.setVisibility(View.VISIBLE);
            AlertEm.setVisibility(View.GONE);
            checkUserExist();
        }else {
            AlertEm.setEnabled(false);
            activity_main.setRefreshing(false);
            mBlogList.setVisibility(View.GONE);
            AlertEm.setText("Sem conexão com a internet.");
            AlertEm.setVisibility(View.VISIBLE);
        }

        btnaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent intent = new Intent(MainOrkut.this, ListaDeContatos.class);
                    intent.putExtra("ezaak", "1");
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Intent intent = new Intent(MainOrkut.this, ListaDeContatos.class);
                    intent.putExtra("ezaak", "1");
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        activity_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAuth.removeAuthStateListener(mAuthListener);
                        test();
                        mAuth.addAuthStateListener(mAuthListener);
                    }
                }, 700);
            }
        });

        addamigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!peffoto.equals("edebelzaakso")) {
                    if (peffoto != null) {
                        if (transicao) {
                            Intent intent = new Intent(MainOrkut.this, FazerAmizades.class);
                            intent.putExtra("pefoto", peffoto);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        } else {
                            Intent intent = new Intent(MainOrkut.this, FazerAmizades.class);
                            intent.putExtra("pefoto", peffoto);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainOrkut.this);
                    builder.setMessage("Para enviar solicitações de amizade é preciso ter um gif no gifil.");
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
                }
            }
        });

        btnpublico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                Intent intentyx = new Intent(MainOrkut.this, ListaDeContatos.class);
                intentyx.putExtra("ezaak", "2");
                startActivity(intentyx);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                }else {
                    Intent intentyx = new Intent(MainOrkut.this, ListaDeContatos.class);
                    intentyx.putExtra("ezaak", "2");
                    startActivity(intentyx);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        atualizeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_main.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAuth.removeAuthStateListener(mAuthListener);
                        test();
                        mAuth.addAuthStateListener(mAuthListener);
                    }
                }, 700);
            }
        });

        atperfil.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant"})
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent innyx = new Intent(MainOrkut.this, AAAA.class);
                    innyx.putExtra("moup", "1");
                    startActivity(innyx);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Intent innyx = new Intent(MainOrkut.this, AAAA.class);
                    innyx.putExtra("moup", "1");
                    startActivity(innyx);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        ejetarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        poste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera){
                    if (transicao) {
                    Intent innyx = new Intent(MainOrkut.this, AAAA.class);
                    innyx.putExtra("moup", "3");
                    startActivity(innyx);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    }else {
                        Intent innyx = new Intent(MainOrkut.this, AAAA.class);
                        innyx.putExtra("moup", "3");
                        startActivity(innyx);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }else {
                    if (transicao) {
                    Intent innyx = new Intent(MainOrkut.this, MeuAlbumActivity.class);
                    startActivity(innyx);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                    }else {
                        Intent innyx = new Intent(MainOrkut.this, MeuAlbumActivity.class);
                        startActivity(innyx);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }
            }
        });

        AlertEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                Intent innyx = new Intent(MainOrkut.this, MeuAlbumActivity.class);
                startActivity(innyx);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                }else {
                    Intent innyx = new Intent(MainOrkut.this, MeuAlbumActivity.class);
                    startActivity(innyx);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        vernotication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tcalert(false);
                if (transicao) {
                Intent intent = new Intent(MainOrkut.this, SolicitacaoAmz.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                }else {
                    Intent intent = new Intent(MainOrkut.this, SolicitacaoAmz.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        mBlogList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int topp = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                activity_main.setEnabled(topp >= 0);
            }
        });

        perfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent intyx = new Intent(MainOrkut.this, PerfilSinezya.class);
                    intyx.putExtra("lokiio", "no");
                    startActivity(intyx);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Intent intyx = new Intent(MainOrkut.this, PerfilSinezya.class);
                    intyx.putExtra("lokiio", "no");
                    startActivity(intyx);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseInit() {
        mAutorize2 = true;
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAutorize2) {
                    mAutorize2 = false;
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        if (transicao) {
                        Intent loginIntent = new Intent(MainOrkut.this, LognOrkut.class);
                        startActivity(loginIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        }else {
                            Intent loginIntent = new Intent(MainOrkut.this, LognOrkut.class);
                            startActivity(loginIntent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }
                }
            }
        };

        mDatabaseX = FirebaseDatabase.getInstance().getReference();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabaseLike.keepSynced(true);
        mDatabaseUsers.keepSynced(true);
        mDatabaseX.keepSynced(true);

    }

    private void blogList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(layoutManager);
    }

    private void checkUserExist() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                boolean emailcheck = user.isEmailVerified();
                if (emailcheck) {

                user_id = user.getUid();
                yuba = true;

                test();
                mDatabaseUsers.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        if (yuba) {
                            yuba = false;
                            if (dataSnapshot.child("name").exists()) {
                                nonname = dataSnapshot.child("name").getValue().toString();
                            }
                            if (dataSnapshot.child("image").exists()) {
                                peffoto = dataSnapshot.child("image").getValue().toString();
                                RequestManager with = Glide.with(getApplicationContext());
                                with.load(dataSnapshot.child("image").getValue().toString()).thumbnail(Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString())).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        RequestManager with = Glide.with(getApplicationContext());
                                        with.load(dataSnapshot.child("image").getValue().toString()).thumbnail(Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString())).diskCacheStrategy(DiskCacheStrategy.ALL).into(Kperfil);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(Kperfil);
                            }else {
                                peffoto = "edebelzaakso";
                            }
                            if (dataSnapshot.child("Noti").exists()) {
                                String notii = dataSnapshot.child("Noti").getValue().toString();
                                if (notii.equals("1")) {
                                    mDatabaseUsers.child(user_id).child("Noti").setValue("0");
                                    if (nitofi){
                                    int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                                    PendingIntent pendingIntent = PendingIntent.getActivity(MainOrkut.this, 0, new Intent(MainOrkut.this, SolicitacaoAmz.class),
                                            PendingIntent.FLAG_UPDATE_CURRENT);


                                    String channelId = "some_channel_id";
                                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    NotificationCompat.Builder notificationBuilder =
                                            new NotificationCompat.Builder(MainOrkut.this, channelId)
                                                    .setSmallIcon(R.drawable.ic_stat)
                                                    .setContentTitle(getString(R.string.app_name))
                                                    .setContentText("Há uma nova solicitação de amizade esperando por você.")
                                                    .setAutoCancel(true)
                                                    .setSound(defaultSoundUri)
                                                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                                                    .setContentIntent(pendingIntent);


                                    NotificationManager notificationManager =
                                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                            .setUsage(AudioAttributes.USAGE_ALARM)
                                            .build();

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(channelId,
                                                "Há uma nova solicitação de amizade esperando por você.",
                                                NotificationManager.IMPORTANCE_MAX);

                                        assert notificationManager != null;
                                        notificationManager.createNotificationChannel(channel);

                                        channel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, audioAttributes);
                                    } else {
                                        notificationBuilder.setPriority(Notification.PRIORITY_MAX);
                                    }

                                    assert notificationManager != null;
                                    notificationManager.notify(notificationId, notificationBuilder.build());
                                }

                                    vernotication.setImageDrawable(getDrawable(R.drawable.ic_notifications_red));
                                    tcalert(true);
                                } else {
                                    vernotication.setImageDrawable(getDrawable(R.drawable.ic_notifications_24));
                                    tcalert(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                }else {
                    if (transicao) {
                        Intent loginIntent = new Intent(MainOrkut.this, LognOrkut.class);
                        startActivity(loginIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }else {
                        Intent loginIntent = new Intent(MainOrkut.this, LognOrkut.class);
                        startActivity(loginIntent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }
            }
    }

    private void tcalert(boolean b) {
        if (b) {
            vernotication.setImageDrawable(getDrawable(R.drawable.ic_notifications_red));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    vernotication.setImageDrawable(getDrawable(R.drawable.ic_notifications_24));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tcalert(true);
                        }
                    }, 700);
                }
            }, 700);
        }else {
            vernotication.setImageDrawable(getDrawable(R.drawable.ic_notifications_24));
        }
    }

    private void test() {
        yube = true;
        mDatabaseX.child("Postagens").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (yube) {
                    yube = false;
                    if (dataSnapshot.child(user_id).exists()) {
                        mBlogList.setVisibility(View.VISIBLE);
                        AlertEm.setVisibility(View.GONE);
                        cnnt(user_id);
                    }else {
                        activity_main.setRefreshing(false);
                        mBlogList.setVisibility(View.GONE);
                        AlertEm.setText("Você não possui postagens, clique aqui para fazer seu primeiro post.");
                        AlertEm.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void cnnt(final String uide) {
        final FirebaseRecyclerAdapter<Blog, BloViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BloViewHolder>(
                            Blog.class,
                            R.layout.blog_row,
                            BloViewHolder.class,
                            mDatabaseX.child("Postagens").child(uide)) {
                        @Override
                        protected void populateViewHolder(final BloViewHolder viewHolder, final Blog model, final int position) {
                            final String post_key = getRef(position).getKey();

                            activity_main.setRefreshing(false);

                                viewHolder.setImage(getApplicationContext(), model.getIMAGE());
                                viewHolder.setUid(model.getUid());
                                viewHolder.setLikke(model.getLikke());
                                viewHolder.setIdentif(model.getIdentificador());

                                viewHolder.post_ex.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainOrkut.this);
                                        builder.setMessage("Quer mesmo deletar essa postagem?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialogInterface, int i) {
                                                mDatabaseX.child("Postagens").child(uide).child(post_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(MainOrkut.this, "Excluido", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                dialogInterface.dismiss();
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

                                viewHolder.setLikeBtn(post_key);

                                viewHolder.mLikebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mProcessLike = true;
                                        viewHolder.mLikebtn.setEnabled(false);
                                        viewHolder.mLikebtn.setClickable(false);
                                        mDatabaseLike.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (mProcessLike) {
                                                    mProcessLike = false;
                                                    if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                                                        mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                                        if (viewHolder.myIntValue > 0) {
                                                            int pts = viewHolder.myIntValue - 1;
                                                            String sol = String.valueOf(pts);
                                                            viewHolder.myIntValue = pts;
                                                            mDatabaseX.child("Postagens").child(uide).child(post_key).child("likke").setValue(sol).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    viewHolder.mLikebtn.setEnabled(true);
                                                                    viewHolder.mLikebtn.setClickable(true);
                                                                }
                                                            });
                                                        }

                                                    } else {

                                                        mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("RandomValue");
                                                        if (viewHolder.myIntValue > 0 || viewHolder.myIntValue == 0) {
                                                            int pts = viewHolder.myIntValue + 1;
                                                            String sol = String.valueOf(pts);
                                                            viewHolder.myIntValue = pts;
                                                            mDatabaseX.child("Postagens").child(uide).child(post_key).child("likke").setValue(sol).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    viewHolder.mLikebtn.setEnabled(true);
                                                                    viewHolder.mLikebtn.setClickable(true);
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

        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class BloViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView contslikess;
        SharedPreferences sharedPreferences = null;
        ImageButton mLikebtn, post_coment, partilhar, post_ex;
        FirebaseAuth mAuth;
        DatabaseReference mDatabaseLike;
        private boolean reuu = false;
        int myIntValue;
        String foto2;
        String likedg;
        ImageView post_image;
        String uidd, identificado;
        LinearLayout layty;

        public BloViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            post_ex = mView.findViewById(R.id.post_ex);
            mLikebtn = mView.findViewById(R.id.post_like);
            contslikess = mView.findViewById(R.id.contslikes);
            post_coment = mView.findViewById(R.id.post_coment);
            partilhar = mView.findViewById(R.id.post_share);
            post_image = mView.findViewById(R.id.post_image);
            layty = mView.findViewById(R.id.layty);
            partilhar.setVisibility(View.GONE);
            post_coment.setVisibility(View.GONE);
            post_ex.setVisibility(View.VISIBLE);

            mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
            mDatabaseLike.keepSynced(true);
            mAuth = FirebaseAuth.getInstance();

        }

        public void setLikeBtn(final String post_key) {
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                        mLikebtn.setImageResource(R.drawable.ic_yes_heart_colored);

                    } else {
                        mLikebtn.setImageResource(R.drawable.ic_no_heart_gray);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setLikke(String likke) {
            int convertedVal = Integer.parseInt(likke);
            myIntValue = convertedVal;
            likedg = likke;
            contslikess.setText(likke);
        }

        public void setImage(final Context ctx, final String IMAGE) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            reuu = sharedPreferences.getBoolean("notifu", true);
            foto2 = IMAGE;

            if (reuu){
                post_image.setBackgroundResource(0);

                new CountDownTimer(Long.MAX_VALUE, 1) {
                    @Override
                    public void onTick(long l) {
                        if (post_image.getDrawable() != null) {
                            layty.setVisibility(View.GONE);
                            cancel();
                        }else {
                            layty.setVisibility(View.VISIBLE);
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
                                layty.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                layty.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(post_image);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        layty.setVisibility(View.GONE);
                        return false;
                    }
                }).into(post_image);

            }else {
                post_image.setBackgroundResource(0);
                Picasso.with(ctx)
                        .load(IMAGE)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(post_image, new Callback() {
                            @Override
                            public void onSuccess() {
                                layty.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                Picasso.with(ctx)
                                        .load(IMAGE)
                                        .into(post_image, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                layty.setVisibility(View.GONE);
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
            uidd = uid;
        }

        public void setIdentif(String identificador) {
            identificado = identificador;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainOrkut.this);
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

    private void logout() {
        mAuth.signOut();
        if (transicao) {
            Intent logoutIntent = new Intent(MainOrkut.this, LognOrkut.class);
            startActivity(logoutIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }else {
            Intent logoutIntent = new Intent(MainOrkut.this, LognOrkut.class);
            startActivity(logoutIntent);
            overridePendingTransition(0, 0);
            finish();
        }
    }


    public void fecharr(View view) {
        Toast.makeText(this, "Finalizando...", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void configu(View view) {
        if (transicao) {
        Intent inteny = new Intent(MainOrkut.this, Ajustes.class);
        startActivity(inteny);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        }else {
            Intent inteny = new Intent(MainOrkut.this, Ajustes.class);
            startActivity(inteny);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}