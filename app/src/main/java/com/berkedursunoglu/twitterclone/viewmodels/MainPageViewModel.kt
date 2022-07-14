package com.berkedursunoglu.twitterclone.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkedursunoglu.twitterclone.models.PostModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainPageViewModel : ViewModel() {

    var postArrayList = MutableLiveData<ArrayList<PostModel>>()
    private lateinit var postModel: PostModel

    fun getPost(){
        val firebaseCloud = FirebaseFirestore.getInstance()
        firebaseCloud.collection("post").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (value != null) {
                val documents = value.documents
                val arraylist = ArrayList<PostModel>()
                for (document in documents) {
                    val username = document.get("username") as String
                    val comment = document.get("postcomment") as String
                    val imageurl = document.get("imageurl") as String
                    val date = document.get("date") as Timestamp
                    postModel = PostModel(date, imageurl, comment, username)
                    arraylist.add(postModel)
                }
                postArrayList.value = arraylist
            }
        }
    }

}