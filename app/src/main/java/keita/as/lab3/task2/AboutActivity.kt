package keita.`as`.lab3.task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.databinding.ActivityAboutBinding

class AboutActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("AboutActivity::onCreate()")

    }

    override fun onStart() {
        super.onStart()
        println("AboutActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("AboutActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("AboutActivity::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("AboutActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("AboutActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("AboutActivity::onDestroy()")
    }
}