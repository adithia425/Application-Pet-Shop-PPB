package com.ppb.pawspal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ppb.pawspal.databinding.ActivityLoginBinding
import com.ppb.pawspal.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(signUpBinding.root)

        edtUsername = signUpBinding.textUsername
        edtPassword = signUpBinding.textPassword

        var textUsername = edtUsername.text
        var textPassword = edtPassword.text

        signUpBinding.btnSignUp.setOnClickListener {

            var username = textUsername.toString();
            var password = textPassword.toString();

            val sharedPreferences = getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}