package keita.`as`.lab3.task4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.databinding.ActivityThirdBinding

class C: AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("Activity C::onCreate()")

        binding.activityThirdToFirstBtn.setOnClickListener {
            startActivity(Intent(this, D::class.java))
        }
        binding.activityThirdToSecondBtn.setOnClickListener { finish() }


    }

    override fun onStart() {
        super.onStart()
        println("Activity C::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("Activity C::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("Activity C::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("Activity C::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("Activity C::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity C::onDestroy()")
    }
}