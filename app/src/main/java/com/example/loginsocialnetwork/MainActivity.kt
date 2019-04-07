package com.example.loginsocialnetwork

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.activity_main.*
import com.facebook.FacebookException
import com.facebook.AccessToken
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.content.Intent










class MainActivity : AppCompatActivity() {

    var callbackManager: CallbackManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setReadPermissions("email", "public_profile")

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // Lấy access token sử dụng LoginResult
                val accessToken = loginResult.accessToken

            }

            override fun onCancel() {
            }

            override fun onError(exception: FacebookException) {
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

         val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)




    }

}
