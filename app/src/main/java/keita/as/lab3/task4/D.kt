package keita.`as`.lab3.task4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.databinding.ActivityFourthBinding

class D: AppCompatActivity() {
    private lateinit var binding: ActivityFourthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity B::onCreate()")
        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityDToABtn.setOnClickListener {
            startActivity(Intent(this, A::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        binding.activityDToBBtn.setOnClickListener{
            startActivity(Intent(this, B::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }

        binding.activityDToCBtn.setOnClickListener {
            startActivity(Intent(this, D::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        }
    }

    override fun onStart() {
        super.onStart()
        println("Activity D::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("Activity D::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("Activity D::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("Activity D::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("Activity D::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity D::onDestroy()")
    }
}