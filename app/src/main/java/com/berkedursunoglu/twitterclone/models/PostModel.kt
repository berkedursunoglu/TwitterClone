package com.berkedursunoglu.twitterclone.models

import com.google.firebase.Timestamp

class PostModel(
    val date:Timestamp,
    val imageurl:String,
    val postcomment:String,
    val username:String
) {
}