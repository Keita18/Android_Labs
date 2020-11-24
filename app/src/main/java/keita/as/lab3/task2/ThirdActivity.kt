package keita.`as`.lab3.task2

import android.app.Activity
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
    var BACK_TO_FIRST = false
    val EXTRA_BACK_TO_FIRST = "EXTRA_BACK_TO_FIRST"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("ThirdActivity::onCreate()")

        binding.activityThirdToFirstBtn.setOnClickListener {

            BACK_TO_FIRST = true
            // End the activity
            val intent = Intent()
            intent.putExtra(EXTRA_BACK_TO_FIRST, BACK_TO_FIRST)
            setResult(Activity.RESULT_OK, intent)
            finish()
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
        println("ThirdActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("ThirdActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("ThirdActivity::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("ThirdActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("ThirdActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("ThirdActivity::onDestroy()")
    }
}