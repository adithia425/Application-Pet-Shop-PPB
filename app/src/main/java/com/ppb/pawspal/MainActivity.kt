package com.ppb.pawspal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ppb.pawspal.databinding.ActivityLoginBinding
import com.ppb.pawspal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var mainBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        Handler(mainLooper).postDelayed({
            // Navigasi ke LoginActivity setelah 3 detik
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // finish() // Hapus pemanggilan finish()
        }, 1500) // 3000 milliseconds = 3 detik

//        mainBinding.btnStart.setOnClickListener {
//            startActivity(
//                Intent(this@MainActivity,LoginActivity::class.java)
//            )
//        }
    }
}