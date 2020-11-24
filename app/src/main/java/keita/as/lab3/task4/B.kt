package keita.`as`.lab3.task4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.databinding.ActivitySecondBinding

class B: AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity B::onCreate()")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activitySecondToFirstBtn.setOnClickListener { finish() }

        binding.activitySecondToThirdBtn.setOnClickListener {
            startActivity(Intent(this, C::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("SecondActivity onNewIntent() created")
    }
    override fun onStart() {
        super.onStart()
        println("Activity B::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("Activity B::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("Activity B::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("Activity B::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("Activity B::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity B::onDestroy()")
    }
}