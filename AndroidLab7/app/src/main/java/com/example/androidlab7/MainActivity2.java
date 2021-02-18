package com.example.androidlab7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlab7.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity2", "2");
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("thread", Thread.currentThread().getName());
                        Intent intent = new Intent(MainActivity2.this, ImageDownloadService.class).putExtra("url", "https://wallbox.ru/wallpapers/preview/201432/8b74eb4d1393499.jpg");
                        ImageDownloadService.enqueueWork(MainActivity2.this,intent);
                    }
                });
    }

}
