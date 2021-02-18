package com.example.androidlab7;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class ImageDownloadService extends JobIntentService {
    static final int JOB_ID = 1000;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String url = intent.getStringExtra("url");
        Log.d("thread", Thread.currentThread().getName());
        if (url == null) {
            sendBroadcast("Error");
        } else {
            try {
                String path = download(url);
                sendBroadcast(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ImageDownloadService.class, JOB_ID, work);
    }

    public String download(String url) throws IOException {
        Bitmap mIcon11 = null;
        String path = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            path = save(mIcon11, "test"+ (int) (Math.random() * 1000));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return path;
    }

    public String save(Bitmap bitmap, String name) {
        FileOutputStream Stream;
        try {
            Stream = this.getApplicationContext().openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, Stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return getApplicationContext().getFileStreamPath(name).getAbsolutePath();
    }

    public void sendBroadcast(String message) {
        sendBroadcast(new Intent("broadcast").putExtra("Message", message));
    }
}
