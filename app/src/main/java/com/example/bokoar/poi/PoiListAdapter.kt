package com.example.bokoar.poi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bokoar.R
import android.widget.TextView

class PoiListAdapter(
    private val items: List<Poi>,
    private val onItemClick: (Poi) -> Unit
) : RecyclerView.Adapter<PoiListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvPoiTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvPoiSubtitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poi_list, parent, false)
        return ViewHolder(view)

    }
    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvSubtitle.text = item.subtitle

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}


