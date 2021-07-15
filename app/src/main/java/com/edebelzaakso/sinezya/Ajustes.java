package com.edebelzaakso.sinezya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;

import java.io.File;
import java.text.DecimalFormat;

public class Ajustes extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    private boolean transicao = true;
    private TextView id_cache;
    private Switch notifiva, batepapo, tempov, tempoamg, escuro, camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustess);

        id_cache = findViewById(R.id.id_cache);
        notifiva = findViewById(R.id.notifiva);
        batepapo = findViewById(R.id.batepapo);
        tempov = findViewById(R.id.tempov);
        tempoamg = findViewById(R.id.tempoamg);
        escuro = findViewById(R.id.escuro);
        camera = findViewById(R.id.camera);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Ajustes.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        inicializeCache();

        notifiva.setChecked(sharedPreferences.getBoolean("notifi", true));
        notifiva.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("notifi", b).apply();
            }
        });

        batepapo.setChecked(sharedPreferences.getBoolean("notifo", true));
        batepapo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("notifo", b).apply();
            }
        });

        tempov.setChecked(sharedPreferences.getBoolean("notife", true));
        tempov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("notife", b).apply();
            }
        });

        tempoamg.setChecked(sharedPreferences.getBoolean("notifu", true));
        tempoamg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("notifu", b).apply();
            }
        });

        escuro.setChecked(sharedPreferences.getBoolean("escuro", true));
        escuro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("escuro", b).apply();
                transicao = b;
            }
        });

        camera.setChecked(sharedPreferences.getBoolean("camera", true));
        camera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("camera", b).apply();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(Ajustes.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(Ajustes.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    public void excluir(View view) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final AlertDialog dialo = new AlertDialog.Builder(this).create();
        View c = getLayoutInflater().inflate(R.layout.delet, null);
        dialo.setCanceledOnTouchOutside(false);
        dialo.setCancelable(false);
        final EditText email = c.findViewById(R.id.edt_email);
        final EditText senha = c.findViewById(R.id.edt_senha);
        final TextView cancelar = c.findViewById(R.id.btncanci);
        final TextView deletar = c.findViewById(R.id.btn_dlt);
        final ProgressBar tpro = c.findViewById(R.id.podegi);
        dialo.setView(c);


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
                String e_email = email.getText().toString().trim();
                String e_senha = senha.getText().toString().trim();
                if (!e_email.isEmpty() & !e_senha.isEmpty()) {
                deletar.setEnabled(false);
                cancelar.setEnabled(false);
                tpro.setVisibility(View.VISIBLE);
                deletar.getBackground().setAlpha(128);
                cancelar.getBackground().setAlpha(128);

                        AuthCredential credential = EmailAuthProvider.getCredential(e_email, e_senha);
                        user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialo.dismiss();
                                        if (transicao) {
                                            Intent loginIntent = new Intent(Ajustes.this, LognOrkut.class);
                                            startActivity(loginIntent);
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            finish();
                                        }else {
                                            Intent loginIntent = new Intent(Ajustes.this, LognOrkut.class);
                                            startActivity(loginIntent);
                                            overridePendingTransition(0, 0);
                                            finish();
                                        }
                                    }
                                });
                            }
                        });
                }
            }
        });

        dialo.show();
    }

    public void compartilharr(View view) {
        if (transicao) {
            Intent intent3g = new Intent(Intent.ACTION_SEND);
            intent3g.setType("text/plain");
            intent3g.putExtra(Intent.EXTRA_TEXT, "A vida é mais divertida com movimento, sinezya é a rede social que está sempre em movimento. Baixe já  https://play.google.com/store/apps/details?id=com.edebelzaakso.sinezya");
            startActivity(intent3g);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent intent3g = new Intent(Intent.ACTION_SEND);
            intent3g.setType("text/plain");
            intent3g.putExtra(Intent.EXTRA_TEXT, "A vida é mais divertida com movimento, sinezya é a rede social que está sempre em movimento. Baixe já  https://play.google.com/store/apps/details?id=com.edebelzaakso.sinezya");
            startActivity(intent3g);
            overridePendingTransition(0, 0);
        }
    }

    public void mais_apps(View view) {
        if (transicao) {
            Intent intentcjhghfj = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5830168101610968765"));
            startActivity(intentcjhghfj);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent intentcjhghfj = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5830168101610968765"));
            startActivity(intentcjhghfj);
            overridePendingTransition(0, 0);
        }
    }

    public void facebookk(View view) {
        if (transicao) {
        Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/edebelzaakso/"));
        startActivity(bacer);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/edebelzaakso/"));
            startActivity(bacer);
            overridePendingTransition(0, 0);
        }
    }

    public void en_email(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "edebelzaakso@gmail.com"));
        startActivity(Intent.createChooser(intent,"Enviar e-mail"));
    }

    public void makemakee(View view) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "edebelzaakso@gmail.com");
        intent.putExtra(ContactsContract.Intents.Insert.NAME, "{E}DEBELZAAK S.O");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "+55 (33)998285010");
        startActivity(intent);
    }

    public void sobre_nos(View view) {
        final AlertDialog ler = new AlertDialog.Builder(this).create();
        View f = getLayoutInflater().inflate(R.layout.alerta, null);
        ler.setCanceledOnTouchOutside(false);
        ler.setCancelable(false);
        TextView titu_T = f.findViewById(R.id.titu);
        TextView a_T = f.findViewById(R.id.a);
        TextView submit_bud = f.findViewById(R.id.bt);
        ler.setView(f);

        titu_T.setText("{E}DEBELZAAK S.O");
        a_T.setGravity(Gravity.LEFT);
        a_T.setText("Acreditamos que é possível evoluir sem causar grandes danos a natureza, não por em risco a saúde dos seres vivos ao criar tecnologias e fazer ciência sem pensar no lucro.");
        submit_bud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ler.dismiss();
            }
        });
        ler.show();
    }

    public void instagram(View view) {
        if (transicao) {
        Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/edebelzaakso/"));
        startActivity(bacer);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/edebelzaakso/"));
            startActivity(bacer);
            overridePendingTransition(0, 0);
        }
    }

    public void youtube(View view) {
        if (transicao) {
        Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCkgzIk7ug_KWyzDIN4oISdA"));
        startActivity(bacer);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCkgzIk7ug_KWyzDIN4oISdA"));
            startActivity(bacer);
            overridePendingTransition(0, 0);
        }
    }

    public void retornar(View view) {
        onBackPressed();
    }

    public void privacidadee(View view) {
        if (transicao) {
        Intent intent = new Intent(Ajustes.this, P_POLITICA.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        }else {
            Intent intent = new Intent(Ajustes.this, P_POLITICA.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    public void lovely(View view) {
        if (transicao) {
        Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://edebelzaakso.com/social/index.php?a=page&name=edebelzaakso"));
        startActivity(bacer);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://edebelzaakso.com/social/index.php?a=page&name=edebelzaakso"));
            startActivity(bacer);
            overridePendingTransition(0, 0);
        }
    }

    public void twitter(View view) {
        if (transicao) {
        Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/edebelzaakso1"));
        startActivity(bacer);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent bacer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/edebelzaakso1"));
            startActivity(bacer);
            overridePendingTransition(0, 0);
        }
    }

    private void inicializeCache() {
        long size = 0;
        try {
            size += getDirSize(this.getCacheDir());
            size += getDirSize(this.getExternalCacheDir());
        }catch (Exception  e){

        }

        id_cache.setText(readableFileSize(size));

    }

    public long getDirSize(File dir){
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static String readableFileSize(long size) {
        if (size <= 0) return "0 Bytes";
        final String[] units = new String[]{"Bytes", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public void l_cache(View view) {
        deleteCache(getApplicationContext());
        inicializeCache();
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void maisjogos(View view) {
        if (transicao) {
        Intent intentcjhghfj = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5830168101610968765"));
        startActivity(intentcjhghfj);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent intentcjhghfj = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5830168101610968765"));
            startActivity(intentcjhghfj);
            overridePendingTransition(0, 0);
        }
    }

}
