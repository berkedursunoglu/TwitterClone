package com.berkedursunoglu.twitterclone.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.berkedursunoglu.twitterclone.R
import com.berkedursunoglu.twitterclone.databinding.MainactivityRecyclerviewRowBinding
import com.berkedursunoglu.twitterclone.models.PostModel
import com.squareup.picasso.Picasso


class MainActivityRecyclerView(var arraylist:ArrayList<PostModel>) : RecyclerView.Adapter<MainActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =  DataBindingUtil.inflate<MainactivityRecyclerviewRowBinding>(inflater,R.layout.mainactivity_recyclerview_row,parent,false)
        return MainActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.dataBinding.post = arraylist[position]
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }
}

class MainActivityViewHolder(val dataBinding: MainactivityRecyclerviewRowBinding) : RecyclerView.ViewHolder(dataBinding.root) {
}
