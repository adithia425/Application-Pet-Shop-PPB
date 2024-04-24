package com.ppb.pawspal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.ppb.pawspal.databinding.ActivityLoginBinding
import com.ppb.pawspal.databinding.ActivityMainBinding
import db.UserHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//import com.loopj.android.http.AsyncHttpClient
//import com.loopj.android.http.AsyncHttpRequest
//import com.loopj.android.http.AsyncHttpResponseHandler
//import cz.msebera.android.httpclient.Header
//import com.loopj.android.http.RequestParams

class LoginActivity : AppCompatActivity() {


    private lateinit var loginBinding: ActivityLoginBinding

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button

    private lateinit var userHelper: UserHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        loginBinding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(loginBinding.root)

        edtUsername = loginBinding.textUsername
        edtPassword = loginBinding.textPassword

        var textUsername = edtUsername.text
        var textPassword = edtPassword.text


        loginBinding.btnLogin.setOnClickListener {

            var username = textUsername.toString();
            var password = textPassword.toString();

            login(username, password)
//            Intent(this@LoginActivity,
//                ShopActivity::class.java)
//            )

        }

        loginBinding.textSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String) {

        Log.e("Username", username)
        Log.e("Password", password)

        val sharedPreferences = getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE)

        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")


        Log.e("Login ", "Save Username : " + savedUsername + " AND Save Password : " + savedPassword)

        if(username == savedUsername && password == savedPassword){
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }else{
            loginBinding.textAlert.text = "Account Not Found"
        }


//            val client = AsyncHttpClient()
//            val url = "http://128.199.194.80/auth/login"
//
//            val parameter = RequestParams()
//            parameter.put("uniqueId", "000000000000000000")
//            parameter.put("password", "userUser123")

//            client.post(url, parameter, object : AsyncHttpResponseHandler() {
//                override fun onSuccess(
//                    statusCode: Int,
//                    headers: Array<out Header>?,
//                    responseBody: ByteArray?
//                ) {
//                    Log.e("LOGIN", "Login Berhasil")
//                    val intent = Intent(this@MainActivity, ActivityBaru::class.java)
//                    startActivity(intent)
//                }
//
//                override fun onFailure(
//                    statusCode: Int,
//                    headers: Array<out Header>?,
//                    responseBody: ByteArray?,
//                    error: Throwable?
//                ) {
//                    Log.e("Username", username)
//                    Log.e("Password", password)
//                    Log.e("LOGIN", "Login Gagal")
//                    Log.e("LOGIN", statusCode.toString())
//                }
//            })

    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            //progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {


            }
            //progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()

        }
    }

    private fun signUp(){

    }
}
