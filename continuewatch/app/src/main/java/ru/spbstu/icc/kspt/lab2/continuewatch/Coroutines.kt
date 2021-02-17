package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class Coroutines : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var state: SharedPreferences
    private lateinit var timer: Job
    private val Scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        state = applicationContext.getSharedPreferences(
            "state",
            MODE_PRIVATE
        )
    }

    override fun onStart() {
        super.onStart()
        if (state.contains("seconds")) {
            secondsElapsed = state.getInt("seconds", 0)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
    }

    override fun onResume() {
        timer = Scope.launch {
            while (true) {
                delay(1000)
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
        super.onResume()
    }

    override fun onPause() {
        timer.cancel()
        super.onPause()
    }

}

