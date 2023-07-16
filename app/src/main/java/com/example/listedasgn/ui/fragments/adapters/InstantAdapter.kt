package com.example.listedasgn.ui.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listedasgn.R

class InstantAdapter(val mContext: Context,val mList:List<InstantViewModel>):RecyclerView.Adapter<InstantAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val iconIV: ImageView = itemView.findViewById(R.id.instant_icon_iv)
        val dataTV: TextView = itemView.findViewById(R.id.instant_data_tv)
        val titleTV: TextView = itemView.findViewById(R.id.instant_title_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.instant_rv,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val instantListViewModel = mList[position]
        holder.iconIV.setImageResource(instantListViewModel.icon)
        holder.dataTV.text = instantListViewModel.data
        holder.titleTV.text = instantListViewModel.title
    }
}