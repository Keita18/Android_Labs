package keita.`as`.lab3.task3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.R
import keita.`as`.lab3.databinding.ActivityFirstBinding

class FirstActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("FirstActivityTask3::Create()")

        binding.activityFirstToSecondBtn.setOnClickListener {
            val secondActivityIntent = Intent(this, SecondActivity::class.java)
            startActivity(secondActivityIntent)
        }
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
        println("FirstActivityTask3::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("FirstActivityTask3::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("FirstActivityTask3::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("FirstActivityTask3::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("FirstActivityTask3::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("FirstActivityTask3::onDestroy()")
    }
}