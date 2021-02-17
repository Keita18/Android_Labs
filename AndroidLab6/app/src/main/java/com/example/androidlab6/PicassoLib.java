package com.example.androidlab6;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlab6.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class PicassoLib extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picasso.with(PicassoLib.this).load("https://nakleykiavto.ru/upload/iblock/62d/62d7552304d1115bdf0f6d2f5c47457b.png").into(binding.ImageView);
                        binding.button.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
