package com.berkedursunoglu.twitterclone.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkedursunoglu.twitterclone.R
import com.berkedursunoglu.twitterclone.models.PostModel
import com.squareup.picasso.Picasso


class MainActivityRecyclerView(var arraylist:ArrayList<PostModel>) :
    RecyclerView.Adapter<MainActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
       val view =  LayoutInflater.from(parent.context).inflate(R.layout.mainactivity_recyclerview_row,parent,false)
        return MainActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        Picasso.get().load(arraylist.get(position).imageurl).into(holder.imageview)
        holder.commentTextView.text = arraylist[position].postcomment
        holder.usernameTextView.text = arraylist[position].username

    }

    override fun getItemCount(): Int {
        return arraylist.size
    }
}

class MainActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageview:ImageView = itemView.findViewById(R.id.post_imageview)
    var commentTextView:TextView = itemView.findViewById(R.id.comment_edittext)
    var usernameTextView:TextView = itemView.findViewById(R.id.username_textview)
}
