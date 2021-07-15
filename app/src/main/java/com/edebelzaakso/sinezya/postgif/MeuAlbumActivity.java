package com.edebelzaakso.sinezya.postgif;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.edebelzaakso.sinezya.OrkutActivity.MainOrkut;
import com.edebelzaakso.sinezya.R;
import com.edebelzaakso.sinezya.gravacao.AAAA;

import java.util.ArrayList;
import java.util.List;

public class MeuAlbumActivity extends AppCompatActivity {
    static final boolean a = true;
    private ViewPager c;
    private int[] d = {R.mipmap.icon_video, R.mipmap.icon_myalbum};
    SharedPreferences sharedPreferences = null;
    private ImageButton sicam;
    private boolean transicao = true;

    class a extends FragmentPagerAdapter {
        private final List<Fragment> b = new ArrayList();
        private final List<String> c = new ArrayList();

        public a(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return (Fragment) this.b.get(i);
        }

        public int getCount() {
            return this.b.size();
        }

        public void a(Fragment fragment, String str) {
            this.b.add(fragment);
            this.c.add(str);
        }

        public CharSequence getPageTitle(int i) {
            return (CharSequence) this.c.get(i);
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.malbumactivity);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MeuAlbumActivity.this);
        transicao = sharedPreferences.getBoolean("escuro", true);

        sicam = findViewById(R.id.sicam);
        sicam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transicao) {
                    Intent innyx = new Intent(MeuAlbumActivity.this, AAAA.class);
                    innyx.putExtra("moup", "2");
                    startActivity(innyx);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else {
                    Intent innyx = new Intent(MeuAlbumActivity.this, AAAA.class);
                    innyx.putExtra("moup", "2");
                    startActivity(innyx);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        @SuppressLint("WrongViewCast") Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Galeria");

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (a || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(a);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.c = (ViewPager) findViewById(R.id.viewpager);
            a(this.c);
            return;
        }
        throw new AssertionError();
    }
    private void a(ViewPager viewPager) {
        a aVar = new a(getSupportFragmentManager());
        aVar.a(new PostVideoFragment(), "MEUS VÍDEOS");
        viewPager.setAdapter(aVar);
    }

    @SuppressLint("WrongConstant")
    @Override public void onBackPressed() {
        if (transicao) {
            Intent intent = new Intent(MeuAlbumActivity.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(R.anim.volte, R.anim.volte_ii);
            finish();
        }else {
            Intent intent = new Intent(MeuAlbumActivity.this, MainOrkut.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return a;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return a;
        }
        if (itemId == R.id.shareapp) {
            StringBuilder sb = new StringBuilder();
            sb.append("A vida é mais divertida com movimento, sinezya é a rede social que está sempre em movimento. Baixe já  https://play.google.com/store/apps/details?id=com.edebelzaakso.sinezya");
            sb.append("https://play.google.com/store/apps/details?id=com.edebelzaakso.sinezya");
            String sb2 = sb.toString();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", sb2);
            startActivity(intent);
        } else if (itemId == R.id.moreapp) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/dev?id=5830168101610968765")));
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        } else if (itemId == R.id.rateus) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.edebelzaakso.sinezya")));
            } catch (ActivityNotFoundException unused2) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return a;
    }
}
