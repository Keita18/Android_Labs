package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class SyncTask : AppCompatActivity() {
    var clicked = false
    var button: Button? = null
    var secondsElapsed: Int = 0
    var work = true
    private lateinit var state: SharedPreferences
    var backgroundThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_sync)
        button = findViewById(R.id.button2)

}

    override fun onResume() {
        super.onResume()

        backgroundThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                }
                if (!work) {Thread.currentThread().interrupt()}
            }
        }
        if (clicked) button!!.isVisible = false
        
        button!!.setOnClickListener {
            clicked = true
            backgroundThread!!.start()
        }
        if (clicked) backgroundThread!!.start()
        work = true
    }

    override fun onPause() {
        super.onPause()
        work = false
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", secondsElapsed)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt("seconds")
        textSecondsElapsed.post {
            textSecondsElapsed.text = "Seconds elapsed: $secondsElapsed"
        }
    }
}