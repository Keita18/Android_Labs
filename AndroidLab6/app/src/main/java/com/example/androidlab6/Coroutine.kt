package com.example.androidlab6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab6.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class Coroutine : AppCompatActivity() {
    private val Scope = CoroutineScope(Dispatchers.Main)
    private fun DownloadImageTask(url: String): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            val inputStream = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("Error", e.message.orEmpty())
            e.printStackTrace()
        }

        return bitmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.button.visibility = View.INVISIBLE

            Scope.launch(Dispatchers.IO) {
                val image = DownloadImageTask("https://prodigits.co.uk/thumbs/wallpapers/p2ls/fun/34/b2b2870312587092.jpg")
                launch(Dispatchers.Main) {
                    binding.ImageView.setImageBitmap(image)
                }
            }
        }
    }
}