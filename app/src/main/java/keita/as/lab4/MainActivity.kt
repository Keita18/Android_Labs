package keita.`as`.lab4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var adapter: Adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val articles = resources.openRawResource(R.raw.articles)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val manager = LinearLayoutManager(this)

        recyclerView.layoutManager = manager

        val itemDecoration: ItemDecoration = DividerItemDecoration(recyclerView.context, manager.orientation)
        recyclerView.addItemDecoration(itemDecoration)



        try {
            adapter = Adapter(articles)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        recyclerView.adapter = adapter
    }
}
