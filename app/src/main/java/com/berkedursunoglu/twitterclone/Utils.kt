package com.berkedursunoglu.twitterclone

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("android:downloadImage")
fun downloadImage(imageview:ImageView, url:String){
    Picasso.get().load(url).into(imageview)
}