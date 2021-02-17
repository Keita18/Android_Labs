package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class SimpleThread : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var work = true
    var backgroundThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("FirstActivity::Create()")

    }

    override fun onResume() {
        super.onResume()
        println("FirstActivity::onResume()")

        backgroundThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                }
                if (!work) {
                    println("Thread interrupted")
                    Thread.currentThread().interrupt()
                }
            }
        }
        backgroundThread!!.start()
        work = true

    }

    override fun onPause() {
        super.onPause()
        println("FirstActivity::onPause()")

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
    override fun onStart() {
        super.onStart()
        println("FirstActivity::onStart()")
    }

    override fun onStop() {
        super.onStop()
        println("FirstActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("FirstActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("FirstActivity::onDestroy()")
    }
}