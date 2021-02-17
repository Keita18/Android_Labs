package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class AsyncTask : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var state: SharedPreferences
    private lateinit var timerAsyncTask: TimerAsyncTask


     inner class TimerAsyncTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled) {
                TimeUnit.SECONDS.sleep(1)
                publishProgress()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
        }
    }

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
        timerAsyncTask = TimerAsyncTask()
        timerAsyncTask.execute()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        state.edit().putInt("seconds", secondsElapsed).apply()
    }

}