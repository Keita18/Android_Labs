package keita.`as`.lab3.task3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.databinding.ActivityAboutBinding

class AboutActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("AboutActivityTask3::onCreate()")

    }




    override fun onStart() {
        super.onStart()
        println("AboutActivityTask3::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("AboutActivityTask3::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("AboutActivityTask3::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("AboutActivityTask3::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("AboutActivityTask3::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("AboutActivityTask3::onDestroy()")
    }
}