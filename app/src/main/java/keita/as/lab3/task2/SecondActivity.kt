package keita.`as`.lab3.task2

import android.app.Activity
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
    val THIRD_ACTIVITY_REQUEST_CODE = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("SecondActivity::onCreate()")


        binding.activitySecondToFirstBtn.setOnClickListener { finish() }
        binding.activitySecondToThirdBtn.setOnClickListener {

            val thirdActivityIntent = Intent(this, ThirdActivity::class.java)

            startActivityForResult(thirdActivityIntent, THIRD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (THIRD_ACTIVITY_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {

            val backToFirstActivity = data!!.getBooleanExtra(ThirdActivity().EXTRA_BACK_TO_FIRST, false)
            if (backToFirstActivity) finish()
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
        println("SecondActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("SecondActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("SecondActivity::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("SecondActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("SecondActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("SecondActivity::onDestroy()")
    }
}
