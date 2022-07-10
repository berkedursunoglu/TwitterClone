package com.berkedursunoglu.twitterclone.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import java.util.*


class PostActivityViewModels : ViewModel() {

    var storage = Firebase.storage
    private val db = FirebaseFirestore.getInstance()

    fun uploadPost(uri: Uri,username:String,postComment:String,date:Timestamp) {
        GlobalScope.launch(Dispatchers.IO) {
            val uuid: UUID = UUID.randomUUID()
            val ref = storage.reference.child("images").child("$uuid")
            val uploadTask = ref.putFile(uri)
            withContext(Dispatchers.Main) {
                uploadTask.addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        val data = hashMapOf<String, Any>()
                        data.put("username",username)
                        data.put("imageurl",it.toString())
                        data.put("postcomment",postComment)
                        data.put("date",date)
                        db.collection("post").add(data)
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                    }
                }
            }
        }
    }

}