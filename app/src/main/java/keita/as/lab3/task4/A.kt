package keita.`as`.lab3.task4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.databinding.ActivityFirstBinding

class A: AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity A::onCreate()")
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityFirstToSecondBtn.setOnClickListener {
            startActivity(Intent(this, B::class.java))
        }
    }
    override fun onStart() {
        super.onStart()
        println("Activity A::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("Activity A::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("Activity A::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("Activity A::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("Activity A::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity A::onDestroy()")
    }
}