package com.honestboook.focusbook.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.honestboook.focusbook.R
import com.honestboook.focusbook.databinding.CardLayoutBinding
import com.honestboook.focusbook.model.Site

class RecyclerAdapter() :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var siteList: List<Site> = emptyList<Site>()

    class ViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(site: Site) {
            binding.itemImage.setImageResource(site.image)
            binding.itemTitle.text = site.name
            binding.itemDetail.text = site.url
            binding.cardView.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(site)
                binding.cardView.findNavController().navigate(action)
            }
        }
    }

//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var itemImage: ImageView
//        var itemTitle: TextView
//        var itemDetail: TextView
//        var cardView: CardView
//
//        init {
//            itemImage = itemView.findViewById(R.id.itemImage)
//            itemTitle = itemView.findViewById(R.id.itemTitle)
//            itemDetail = itemView.findViewById(R.id.itemDetail)
//            cardView = itemView.findViewById(R.id.cardView)
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val curItem = siteList[position]
//
//        holder.itemImage.setImageResource(curItem.image)
//        holder.itemTitle.text = curItem.name
//        holder.itemDetail.text = curItem.url
//
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curSite = siteList[position]
        holder.bind(curSite)
    }

    override fun getItemCount(): Int {
        return siteList.size
    }

    fun setData(sites: List<Site>) {
        this.siteList = sites
        notifyDataSetChanged()
    }


}