package keita.`as`.lab3.task3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import keita.`as`.lab3.R
import keita.`as`.lab3.databinding.ActivityThirdBinding

class ThirdActivity: AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("ThirdActivityTask3::onCreate()")

        binding.activityThirdToFirstBtn.setOnClickListener {

            val firstActivityIntent = Intent(this, FirstActivity::class.java)
            startActivity(firstActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
        binding.activityThirdToSecondBtn.setOnClickListener { finish() }
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
        println("ThirdActivityTask3::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("ThirdActivityTask3::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("ThirdActivityTask3::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("ThirdActivityTask3::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("ThirdActivityTask3::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("ThirdActivityTask3::onDestroy()")
    }
}