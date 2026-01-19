// com.example.bokoar.poi.RecommendedAdapter.kt
package com.example.bokoar.poi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bokoar.R
import com.google.android.material.imageview.ShapeableImageView
import android.widget.TextView

class RecommendedAdapter(
    private val items: List<PoiDetailContent>,
    private val onItemClick: (PoiDetailContent) -> Unit   // callback klik
) : RecyclerView.Adapter<RecommendedAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ShapeableImageView = view.findViewById(R.id.imgPoiRecommended)
        val tvTitle: TextView = view.findViewById(R.id.tvPoiTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvPoiSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poi_recommended, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.tvTitle.text = item.name
        holder.tvSubtitle.text = item.shortDescription

        val imageName = item.images.firstOrNull()
        if (imageName != null){
            val drawableName = imageName.substringBefore(".")
            val resId = holder.itemView.context.resources.getIdentifier(
                drawableName,
                "drawable",
                holder.itemView.context.packageName
            )

            if (resId != 0) {
                holder.img.setImageResource(resId)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}
