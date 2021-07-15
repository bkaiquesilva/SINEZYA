package com.edebelzaakso.sinezya.postgif;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Video.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.edebelzaakso.sinezya.R;
import com.edebelzaakso.sinezya.listvideoandmyvideo.ContentUtill;
import com.edebelzaakso.sinezya.listvideoandmyvideo.VideoData;

import java.util.ArrayList;

public class PostVideoFragment extends Fragment {
    PostVideoAdapter a;
    ImageLoader b;
    ArrayList<VideoData> c = new ArrayList<>();
    ListView d;
    private PowerManager e;
    private WakeLock f;

    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog a;

        private a() {
            this.a = null;
        }


        public void onPreExecute() {
            this.a = new ProgressDialog(PostVideoFragment.this.getActivity());
            this.a.setMessage("Carregando...");
            this.a.setCancelable(false);
            this.a.show();
        }



        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(PostVideoFragment.this.b());
        }



        public void onPostExecute(Boolean bool) {
            this.a.dismiss();
            if (bool.booleanValue()) {
                PostVideoFragment.this.a = new PostVideoAdapter(PostVideoFragment.this.getActivity(), PostVideoFragment.this.c, PostVideoFragment.this.b);
                PostVideoFragment.this.d.setAdapter(PostVideoFragment.this.a);
            }
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("InvalidWakeLockTag")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.videoafragment, viewGroup, false);
        this.e = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        this.f = this.e.newWakeLock(6, "My Tag");
        a();
        this.d = (ListView) inflate.findViewById(R.id.VideogridView);
        new a().execute(new Void[0]);

        if (isOnline()) {

        } else {

        }
        return inflate;
    }

    private void a() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.b = ImageLoader.getInstance();
        this.b.init(build);
    }


    @SuppressLint({"NewApi"})
    public boolean b() {
        Cursor managedQuery = getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "_display_name", "duration"}, null, null, " _id DESC");
        int count = managedQuery.getCount();
        if (count <= 0) {
            return false;
        }
        managedQuery.moveToFirst();
        for (int i = 0; i < count; i++) {
            Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, ContentUtill.getLong(managedQuery));
            this.c.add(new VideoData(managedQuery.getString(managedQuery.getColumnIndexOrThrow("_display_name")), withAppendedPath, managedQuery.getString(managedQuery.getColumnIndex("_data")), ContentUtill.getTime(managedQuery, "duration")));
            managedQuery.moveToNext();
        }
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
