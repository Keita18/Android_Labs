package keita.`as`.lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import name.ank.lab4.BibDatabase
import name.ank.lab4.BibEntry
import name.ank.lab4.Keys
import java.io.InputStream
import java.io.InputStreamReader

class Adapter(inputStream: InputStream): RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var database: BibDatabase

    init {
        InputStreamReader(inputStream).use { reader ->
            database = BibDatabase(reader)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val journal: TextView = view.findViewById(R.id.JournalAndYear)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.biblib, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry: BibEntry = database.getEntry(position % database.size())
        holder.title.text = entry.getField(Keys.TITLE)
        holder.author.text = entry.getField(Keys.AUTHOR)
        holder.journal.text = entry.getField(Keys.JOURNAL)
    }
}