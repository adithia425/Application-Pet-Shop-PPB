package com.ppb.pawspal

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ppb.pawspal.databinding.ActivityLoginBinding
import com.ppb.pawspal.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private lateinit var signUpBinding: ActivitySignUpBinding

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(signUpBinding.root)


        edtUsername = signUpBinding.textUsername
        edtPassword = signUpBinding.textPassword

        var textUsername = edtUsername.text
        var textPassword = edtPassword.text

        signUpBinding.btnSignUp.setOnClickListener {


            Log.e("sign up", "Klik sign up")
            var username = textUsername.toString();
            var password = textPassword.toString();

            //addData()

            if(!username.isNullOrEmpty() and !password.isNullOrEmpty()){

                Log.e("sign up", username)

                val user = hashMapOf(
                    "username" to username,
                    "password" to password
                )

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }

        }
    }


    fun addData(){
        val d = hashMapOf(
            "name" to "TenderMeow",
            "description" to "Wet cat food with options of chicken, salmon, or shrimp flavors, specially designed for cats needing extra hydration.",
            "price" to 5000,
            "photo" to "https://img.lazcdn.com/g/p/2cda5609afa6a1027fcec95f7e604819.jpg_720x720q80.jpg"
        )
        db.collection("products")
            .add(d)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }
}