package keita.`as`.lab3.task3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.R
import keita.`as`.lab3.databinding.ActivitySecondBinding

class SecondActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("SecondActivityTask3::onCreate()")

        binding.activitySecondToFirstBtn.setOnClickListener { finish() }
        binding.activitySecondToThirdBtn.setOnClickListener { startActivity(Intent(this, ThirdActivity::class.java)) }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_button -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        println("SecondActivityTask3::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("SecondActivityTask3::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("SecondActivityTask3::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("SecondActivityTask3::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("SecondActivityTask3::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("SecondActivityTask3::onDestroy()")
    }
}