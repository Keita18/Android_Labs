package com.example.androidlab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.JobIntentService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidlab7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity", "1");
         br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message= intent.getStringExtra("Message");
                binding.textView.setText(message);
            }
        };
        registerReceiver(br, new IntentFilter("broadcast"));
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("thread", Thread.currentThread().getName());
                        Intent intent = new Intent(MainActivity.this, ImageDownloadService.class).putExtra("url", "https://wallbox.ru/wallpapers/preview/201432/8b74eb4d1393499.jpg");
                        ImageDownloadService.enqueueWork(MainActivity.this,intent);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}