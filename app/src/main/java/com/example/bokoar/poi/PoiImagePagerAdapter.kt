package com.example.bokoar.poi

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bokoar.R

class PoiImagePagerAdapter(
    private val images: List<String> //drawable
) : RecyclerView.Adapter<PoiImagePagerAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgSlide: ImageView = itemView.findViewById(R.id.imgSlide)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poi_image, parent, false)
        return VH(v)
    }
    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val imageName = images[position]

        val context = holder.itemView.context

        val drawableName = imageName.substringBefore(".")
        val resId = context.resources.getIdentifier(
            drawableName,
            "drawable",
            context.packageName
        )

        if (resId != 0) {
            holder.imgSlide.setImageResource(resId)
        } else {
            holder.imgSlide.setImageResource(R.drawable.placeholder_image)
        }

    }
}