package com.berkedursunoglu.twitterclone.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.berkedursunoglu.twitterclone.Constants
import com.berkedursunoglu.twitterclone.viewmodels.LoginPageViewModel
import com.berkedursunoglu.twitterclone.R
import com.berkedursunoglu.twitterclone.databinding.ActivityLoginPageBinding

class LoginPageActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityLoginPageBinding
    private lateinit var viewModel: LoginPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_page)
        dataBinding.loginPage = this
        viewModel = ViewModelProvider(this).get(LoginPageViewModel::class.java)
        checkUser()
    }

    fun signInUser(){
        val email = dataBinding.emailEdittext.text.toString()
        val password = dataBinding.passwordEdittext.text.toString()
        if (email == "" || password == ""){
            Toast.makeText(this,"E-mail ya da şifre alanı boş bırakılamaz.",Toast.LENGTH_SHORT).show()
        }else{
            viewModel.signInUser(email,password)
        }
        viewModel.signInUser.observe(this, Observer {
            if (it){
                startActivity(Intent(this, MainPageActivity::class.java))
                finish()
            }
        })
    }

    fun signUpUser(){
        val email = dataBinding.emailEdittext.text.toString()
        val password = dataBinding.passwordEdittext.text.toString()
        if (email == "" || password == ""){
            Toast.makeText(this,"E-mail ya da şifre alanı boş bırakılamaz.",Toast.LENGTH_SHORT).show()
        }else{
            viewModel.signUpUser(email,password)
        }
        viewModel.singUpResultMessage.observe(this, Observer {
            var resultMessage:String = ""
            when(it){
                Constants.alreadyEmail -> resultMessage = "E-mail kullanımda."
                Constants.badEmailAdress -> resultMessage = "Geçerli bir email adresi giriniz."
                Constants.passwordLessSixChar -> resultMessage = "Şifreniz en az 6 karakter olmalıdır."
                Constants.succesSignUp -> resultMessage = Constants.succesSignUp
            }
            Toast.makeText(this,resultMessage,Toast.LENGTH_SHORT).show()
        })
    }

    private fun checkUser(){
        if (viewModel.userStillOnline() != null){
            startActivity(Intent(this, MainPageActivity::class.java))
            finish()
        }
    }



}