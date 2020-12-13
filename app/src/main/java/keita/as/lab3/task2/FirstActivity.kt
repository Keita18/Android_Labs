package keita.`as`.lab3.task2
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import keita.`as`.lab3.R
import keita.`as`.lab3.databinding.ActivityFirstBinding


class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    open var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("FirstActivity::OnCreate()")

        binding.activityFirstToSecondBtn.setOnClickListener {
            val secondActivityIntent = Intent(this, SecondActivity::class.java)
            startActivity(secondActivityIntent)
        }
        check = this.isTaskRoot
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
        println("FirstActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("FirstActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("FirstActivity::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("FirstActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("FirstActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("FirstActivity::onDestroy()")
    }
    open fun check(): Boolean {
        return check
    }
}