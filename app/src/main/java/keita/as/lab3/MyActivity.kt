package keita.`as`.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val button: Button = findViewById(R.id.activity_main_btn)
        button.setOnClickListener{
            button.text = getString(R.string.won)
        }
    }

    override fun isTaskRoot(): Boolean {
        return super.isTaskRoot()
    }
    open fun check(): Boolean {
        return isTaskRoot
    }
}