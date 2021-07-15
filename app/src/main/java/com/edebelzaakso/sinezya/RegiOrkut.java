package com.edebelzaakso.sinezya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class RegiOrkut extends AppCompatActivity {

    private EditText mNameField, mEmailField, mNumero, mPasswordField;
    private ImageButton mRegBtn;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mOrkutNum;
    private LinearLayout ier;
    String name, numee;
    private Typeface sb;
    private TextView toolbar, opcao1, opcao2, opcao3, opcao4, alerta1, alerta2, seri;
    private CheckBox chech;
    private int cique = 0;
    private int rche = 8;
    private boolean existe = false;
    private SwipeRefreshLayout refresh_rv_video;
    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orkut_registro);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegiOrkut.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        mProgress = new ProgressDialog(this);
        mRegBtn = findViewById(R.id.signup);
        toolbar = findViewById(R.id.toolbar2);
        sb = Typeface.createFromAsset(getAssets(), "fonts/frijole.ttf");
        toolbar.setTypeface(sb);

        chech = findViewById(R.id.checkbo);

        opcao1 = findViewById(R.id.opcao1);
        opcao2 = findViewById(R.id.opcao2);
        opcao3 = findViewById(R.id.opcao3);
        opcao4 = findViewById(R.id.opcao4);
        ier = findViewById(R.id.ier);
        mAuth = FirebaseAuth.getInstance();
        mOrkutNum = FirebaseDatabase.getInstance().getReference().child("OKNum");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mNameField = findViewById(R.id.name);
        mNumero = findViewById(R.id.numero);
        alerta1 = findViewById(R.id.alerta1);
        alerta2 = findViewById(R.id.alerta2);
        seri = findViewById(R.id.seri);
        refresh_rv_video = findViewById(R.id.refresh_rv_video);

        option1();

        opcao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opcao11 = opcao1.getText().toString().trim();
                mNumero.getText().clear();
                mNumero.setText(opcao11);
                alerta1.setVisibility(View.GONE);
            }
        });

        opcao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opcao11 = opcao2.getText().toString().trim();
                mNumero.getText().clear();
                mNumero.setText(opcao11);
                alerta1.setVisibility(View.GONE);
            }
        });

        opcao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opcao11 = opcao3.getText().toString().trim();
                mNumero.getText().clear();
                mNumero.setText(opcao11);
                alerta1.setVisibility(View.GONE);
            }
        });

        opcao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opcao11 = opcao4.getText().toString().trim();
                mNumero.getText().clear();
                mNumero.setText(opcao11);
                alerta1.setVisibility(View.GONE);
            }
        });

        refresh_rv_video.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (rche == 8) {
                    option1();
                }else {
                    cique = 0;
                    rche = 8;
                    mEmailField.setVisibility(View.GONE);
                    mPasswordField.setVisibility(View.GONE);
                    mNameField.setVisibility(View.VISIBLE);
                    opcao1.setVisibility(View.VISIBLE);
                    opcao2.setVisibility(View.VISIBLE);
                    opcao3.setVisibility(View.VISIBLE);
                    opcao4.setVisibility(View.VISIBLE);
                    mNumero.setVisibility(View.VISIBLE);
                    chech.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            }
        });

        mOrkutNum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    if (cique == 0) {
                        String opcao11 = opcao1.getText().toString().trim();
                        String opcao22 = opcao2.getText().toString().trim();
                        String opcao33 = opcao3.getText().toString().trim();
                        String opcao44 = opcao4.getText().toString().trim();

                        if (dataSnapshot.child(opcao11).exists()) {
                            option1();
                        } else if (dataSnapshot.child(opcao22).exists()) {
                            option1();
                        } else if (dataSnapshot.child(opcao33).exists()) {
                            option1();
                        } else if (dataSnapshot.child(opcao44).exists()) {
                            option1();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cique == 0) {
                    alerta1.setVisibility(View.GONE);
                    String nomee = mNameField.getText().toString().trim();
                    String numee = mNumero.getText().toString().trim();

                        if (!nomee.isEmpty()) {
                            if (!numee.isEmpty()) {
                                mEmailField.setVisibility(View.VISIBLE);
                                mPasswordField.setVisibility(View.VISIBLE);
                                mNameField.setVisibility(View.GONE);
                                opcao1.setVisibility(View.GONE);
                                opcao2.setVisibility(View.GONE);
                                opcao3.setVisibility(View.GONE);
                                opcao4.setVisibility(View.GONE);
                                mNumero.setVisibility(View.GONE);
                                chech.setVisibility(View.VISIBLE);
                                alerta1.setVisibility(View.GONE);
                                rche = 0;
                                cique = 1;
                            }else {
                                alerta1.setVisibility(View.VISIBLE);
                                alerta1.setText("Todos os campos devem ser preenchidos.");
                            }
                        } else {
                            alerta1.setVisibility(View.VISIBLE);
                            alerta1.setText("Todos os campos devem ser preenchidos.");
                        }
                }else {
                    if (cique == 1) {
                        numee = mNumero.getText().toString().trim();
                        name = mNameField.getText().toString().trim();
                        String email = mEmailField.getText().toString().trim();
                        String password = mPasswordField.getText().toString().trim();

                        mProgress.setMessage("Salvando identificador......");
                        mProgress.show();

                        if (!TextUtils.isEmpty(email)) {
                            if (!TextUtils.isEmpty(password)) {
                                    existe = true;
                                        mOrkutNum.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (existe) {
                                                    existe = false;
                                                    if (dataSnapshot.child(numee).exists()) {
                                                        mProgress.dismiss();
                                                        mEmailField.setVisibility(View.GONE);
                                                        mPasswordField.setVisibility(View.GONE);
                                                        chech.setVisibility(View.GONE);
                                                        mNumero.setVisibility(View.VISIBLE);
                                                        mNameField.setVisibility(View.GONE);
                                                        opcao1.setVisibility(View.VISIBLE);
                                                        opcao2.setVisibility(View.VISIBLE);
                                                        opcao3.setVisibility(View.VISIBLE);
                                                        opcao4.setVisibility(View.VISIBLE);
                                                        cique = 0;
                                                        rche = 8;
                                                        alerta1.setVisibility(View.VISIBLE);
                                                        alerta1.setText("Esse número já existe, tente outro.");
                                                    } else {
                                                        ier.setVisibility(View.VISIBLE);
                                                        rche = 0;
                                                        //mProgress.dismiss();
                                                        mEmailField.setVisibility(View.VISIBLE);
                                                        mPasswordField.setVisibility(View.VISIBLE);
                                                        mNameField.setVisibility(View.GONE);
                                                        opcao1.setVisibility(View.GONE);
                                                        opcao2.setVisibility(View.GONE);
                                                        opcao3.setVisibility(View.GONE);
                                                        opcao4.setVisibility(View.GONE);
                                                        mNumero.setVisibility(View.GONE);
                                                        chech.setVisibility(View.VISIBLE);
                                                        alerta1.setVisibility(View.GONE);
                                                        startRegister();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                mProgress.dismiss();
                                                Toast.makeText(RegiOrkut.this, "Erro, tente novamente", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }else {
                                mProgress.dismiss();
                                mPasswordField.setError("Campo vazio");
                            }
                        }else {
                            mProgress.dismiss();
                            mEmailField.setError("Campo vazio");
                        }
                    }
                }
            }
        });

    }

    private void option4() {
        final String ge2 = Gerador2(1);
        int cv2;

        if (ge2.equals("A")){
            cv2 = 1;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });

        }else if (ge2.equals("B")){
            cv2 = 2;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("C")){
            cv2 = 3;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("D")){
            cv2 = 4;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("E")){
            cv2 = 5;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("F")){
            cv2 = 6;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("G")){
            cv2 = 7;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("H")){
            cv2 = 8;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("I")){
            cv2 = 9;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("J")){
            cv2 = 10;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("K")){
            cv2 = 11;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("L")){
            cv2 = 12;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }else if (ge2.equals("M")){
            cv2 = 13;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao4.setVisibility(View.VISIBLE);
                        opcao4.setText(op1);
                        refresh_rv_video.setRefreshing(false);
                    }else {
                        option4();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao4.setVisibility(View.GONE);
                    refresh_rv_video.setRefreshing(false);
                }
            });
        }
    }

    private void option3() {
        final String ge2 = Gerador2(1);
        int cv2;

        if (ge2.equals("A")){
            cv2 = 1;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });

        }else if (ge2.equals("B")){
            cv2 = 2;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("C")){
            cv2 = 3;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("D")){
            cv2 = 4;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("E")){
            cv2 = 5;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("F")){
            cv2 = 6;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("G")){
            cv2 = 7;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("H")){
            cv2 = 8;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("I")){
            cv2 = 9;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("J")){
            cv2 = 10;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("K")){
            cv2 = 11;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("L")){
            cv2 = 12;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("M")){
            cv2 = 13;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao3.setVisibility(View.VISIBLE);
                        opcao3.setText(op1);
                        option4();
                    }else {
                        option3();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao3.setVisibility(View.GONE);
                }
            });
        }
    }

    private void option2() {
        final String ge2 = Gerador2(1);
        int cv2;

        if (ge2.equals("A")){
            cv2 = 1;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });

        }else if (ge2.equals("B")){
            cv2 = 2;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("C")){
            cv2 = 3;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("D")){
            cv2 = 4;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("E")){
            cv2 = 5;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("F")){
            cv2 = 6;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("G")){
            cv2 = 7;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("H")){
            cv2 = 8;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("I")){
            cv2 = 9;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("J")){
            cv2 = 10;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("K")){
            cv2 = 11;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("L")){
            cv2 = 12;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("M")){
            cv2 = 13;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao2.setVisibility(View.VISIBLE);
                        opcao2.setText(op1);
                        option3();
                    }else {
                        option2();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao2.setVisibility(View.GONE);
                }
            });
        }
    }

    private void option1() {
        final String ge2 = Gerador2(1);
        int cv2;

        if (ge2.equals("A")){
            cv2 = 1;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });

        }else if (ge2.equals("B")){
            cv2 = 2;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("C")){
            cv2 = 3;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("D")){
            cv2 = 4;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("E")){
            cv2 = 5;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("F")){
            cv2 = 6;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("G")){
            cv2 = 7;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("H")){
            cv2 = 8;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("I")){
            cv2 = 9;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("J")){
            cv2 = 10;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("K")){
            cv2 = 11;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("L")){
            cv2 = 12;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }else if (ge2.equals("M")){
            cv2 = 13;

            final String op1 = Gerador(cv2);
            mOrkutNum.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(op1).exists()){
                        opcao1.setVisibility(View.VISIBLE);
                        opcao1.setText(op1);
                        option2();
                    }else {
                        option1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    opcao1.setVisibility(View.GONE);
                }
            });
        }
    }

    private void startRegister() {
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

            mProgress.setMessage("Criando conta......");
            //mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //mProgress.dismiss();
                        enviarverificar();
                    } else {
                        mProgress.dismiss();
                        Toast.makeText(RegiOrkut.this, "Erro no servidor", Toast.LENGTH_LONG).show();
                    }

                }
            });
    }

    private void enviarverificar(){
        mProgress.setMessage("Enviando e-mail......");
        //mProgress.show();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                            //mProgress.dismiss();
                            mProgress.setMessage("Salvando identificador......");
                            //mProgress.show();
                            final String user_id = mAuth.getCurrentUser().getUid();
                            final DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("name").setValue(numee).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    current_user_db.child("OKNum").setValue(numee).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            current_user_db.child("nomepess").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mOrkutNum.child(numee).setValue(user_id).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            ier.setVisibility(View.GONE);
                                                            mProgress.dismiss();
                                                            mEmailField.setVisibility(View.GONE);
                                                            mPasswordField.setVisibility(View.GONE);
                                                            mNameField.setVisibility(View.GONE);
                                                            opcao1.setVisibility(View.GONE);
                                                            opcao2.setVisibility(View.GONE);
                                                            opcao3.setVisibility(View.GONE);
                                                            opcao4.setVisibility(View.GONE);
                                                            mNumero.setVisibility(View.GONE);
                                                            chech.setVisibility(View.GONE);
                                                            seri.setVisibility(View.GONE);
                                                            mRegBtn.setVisibility(View.GONE);
                                                            alerta2.setVisibility(View.VISIBLE);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                    }else {
                        Toast.makeText(RegiOrkut.this, "Erro no servidor", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }

    public String Gerador(int lenght){
        char[] chars = "0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < lenght; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public String Gerador2(int lenght){
        char[] chars = "ABCDEFGHIJKLM".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < lenght; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent setupIntent = new Intent(RegiOrkut.this, LognOrkut.class);
            startActivity(setupIntent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent setupIntent = new Intent(RegiOrkut.this, LognOrkut.class);
            startActivity(setupIntent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
