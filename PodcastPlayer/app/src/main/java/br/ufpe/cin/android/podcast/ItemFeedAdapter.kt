package br.ufpe.cin.android.podcast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemlista.view.*

class ItemFeedAdapter (private val items: List<ItemFeed>, private val c: Context): RecyclerView.Adapter<ItemFeedAdapter.ViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = items[position]
        holder.titulo?.text = i.title
        holder.dataPublicacao?.text = i.pubDate
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item), View.OnClickListener {
        val titulo = item.item_title
        val dataPublicacao = item.item_dataPub

        init {
            item.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Toast.makeText(v.context, "Clicked on item", Toast.LENGTH_SHORT).show()
        }
    }
}