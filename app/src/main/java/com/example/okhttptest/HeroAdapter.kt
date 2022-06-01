package com.example.okhttptest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load


interface HeroActionListener {
    fun onHeroDetail(heroInfo: List<HeroInfo>, position: Int)
}

class HeroAdapter(
    private val actionListener: HeroActionListener,
    private var names: List<HeroInfo>
) : RecyclerView.Adapter<HeroAdapter.ViewHolder>() {

    fun setListData(names: List<HeroInfo>) {
        this.names = names
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val baseURl = "https://api.opendota.com${names[position].icon}"
            val itemTitle: TextView = itemView.findViewById(R.id.textViewNameHero)
            val itemIcon: ImageView = itemView.findViewById(R.id.imageViewIconHero)
            itemTitle.text = names[position].localized_name
            itemIcon.load(baseURl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.hero_list, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener {
            actionListener.onHeroDetail(names, position)
        }
    }
}