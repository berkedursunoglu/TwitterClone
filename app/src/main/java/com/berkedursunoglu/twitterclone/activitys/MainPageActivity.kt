package com.berkedursunoglu.twitterclone.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkedursunoglu.twitterclone.R
import com.berkedursunoglu.twitterclone.databinding.ActivityMainPageBinding
import com.berkedursunoglu.twitterclone.models.PostModel
import com.berkedursunoglu.twitterclone.recyclerview.MainActivityRecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainPageActivity : AppCompatActivity() {

    private lateinit var dataBinding:ActivityMainPageBinding
    private lateinit var postModel:PostModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main_page)
        setSupportActionBar(dataBinding.mainPageToolbar)
        dataBinding.postButton.setOnClickListener {
            startActivity(Intent(this,PostActivity::class.java))
        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        getPosts()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_page_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_action -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginPageActivity::class.java))
                finish()
                return true
            }
        }
        return false
    }

    private fun getPosts(){
        val firebaseCloud = FirebaseFirestore.getInstance()
        firebaseCloud.collection("post").addSnapshotListener { value, error ->
            println("sea ${error?.localizedMessage}")
            if (value != null) {
                    val documents = value.documents
                    val arraylist = ArrayList<PostModel>()
                    for (document in documents){
                        val username = document.get("username") as String
                        val comment = document.get("postcomment") as String
                        val imageurl = document.get("imageurl") as String
                        val date = document.get("date") as Timestamp
                        postModel = PostModel(date,imageurl,comment,username)
                        arraylist.add(postModel)
                    }
                    val rv = MainActivityRecyclerView(arraylist)
                    dataBinding.recyclerView.adapter = rv
                }
            }
        }
    }