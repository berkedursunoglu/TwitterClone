package com.berkedursunoglu.twitterclone.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkedursunoglu.twitterclone.R
import com.berkedursunoglu.twitterclone.databinding.ActivityMainPageBinding
import com.berkedursunoglu.twitterclone.models.PostModel
import com.berkedursunoglu.twitterclone.recyclerview.MainActivityRecyclerView
import com.berkedursunoglu.twitterclone.viewmodels.MainPageViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainPageActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainPageBinding
    private lateinit var viewModel: MainPageViewModel
    private var recyclerView =  MainActivityRecyclerView(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_page)
        setSupportActionBar(dataBinding.mainPageToolbar)
        dataBinding.databinding = this
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        getPost()
    }

    fun getPost() {
        viewModel.getPost()
        viewModel.postArrayList.observe(this, Observer {
            recyclerView.arraylist = it
            dataBinding.recyclerView.adapter = recyclerView
        })
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

    fun postButton() {
        startActivity(Intent(this, PostActivity::class.java))
    }
}