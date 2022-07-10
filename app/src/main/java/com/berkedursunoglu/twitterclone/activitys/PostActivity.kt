package com.berkedursunoglu.twitterclone.activitys

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.berkedursunoglu.twitterclone.R
import com.berkedursunoglu.twitterclone.databinding.ActivityPostBinding
import com.berkedursunoglu.twitterclone.viewmodels.PostActivityViewModels
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityPostBinding
    private var imageUrl:Uri? = null
    private lateinit var viewModels: PostActivityViewModels
    private val firebaseauth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_post)
        dataBinding.postActivity = this
        viewModels = ViewModelProvider(this)[PostActivityViewModels::class.java]
        dataBinding.postUpload.setOnClickListener {
            val comment = dataBinding.postCommentEdittext.text.toString()
            if (imageUrl != null && comment != null){
                viewModels.uploadPost(imageUrl!!,firebaseauth.currentUser?.email.toString(),comment,
                    Timestamp.now())
                finish()
            }
        }

    }




    private fun picktoGaleria(){
        startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),100)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun galeriaPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                showRationaleDialog(getString(R.string.rationale_title),getString(R.string.rationale_message),Manifest.permission.READ_EXTERNAL_STORAGE,1)
            }else{
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }
        }else{
            picktoGaleria()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissions.isNotEmpty()){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                picktoGaleria()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == RESULT_OK && data != null){
            imageUrl = data.data
            dataBinding.postImageview.setImageURI(imageUrl)

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showRationaleDialog(title: String, message: String, permission: String, requestCode: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, which ->
                requestPermissions(arrayOf(permission), requestCode)
            }
        builder.create().show()
    }
}