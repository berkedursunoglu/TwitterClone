package com.berkedursunoglu.twitterclone.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkedursunoglu.twitterclone.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginPageViewModel : ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    var singUpResultMessage = MutableLiveData<String>()
    var signInUser = MutableLiveData<Boolean>()
    var signInResultMessage = MutableLiveData<String>()

    fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnFailureListener {
            singUpResultMessage.value = it.localizedMessage
        }.addOnSuccessListener {
            singUpResultMessage.value = Constants.succesSignUp
        }
    }

    fun userStillOnline(): FirebaseUser? {
        return auth.currentUser
    }

    fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            signInUser.value = false
        }.addOnSuccessListener {
            signInUser.value = true
            println(it.user)
        }
    }
}