package com.ppb.pawspal

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ppb.pawspal.databinding.ActivityDetailItemBinding
import data.Item
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailItemActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private lateinit var savedID: String
    private lateinit var data: Item

    private lateinit var binding: ActivityDetailItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE)

        savedID = sharedPreferences.getString("id", "").toString()

        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dataInput = intent.getParcelableExtra<Item>("name")
        if (dataInput != null) {
            data = dataInput
        }

        binding.textName.text = data?.name
        binding.textPrice.text = "Rp. " + data?.price.toString()
        binding.textDescription.text = data?.description

        Glide.with(this)
            .load(data?.photo)
            .placeholder(R.drawable.logo_pawspal) // Gambar sementara yang ditampilkan sementara gambar dimuat
            .error(R.drawable.logo_pawspal) // Gambar yang ditampilkan jika gagal memuat gambar
            .into(binding.imageProduct)


        binding.buttonAddToCart.setOnClickListener {
            order()
        }
    }


    fun order(){

        val currentDate = Date()

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val dateStr = dateFormat.format(currentDate)
        val timeStr = timeFormat.format(currentDate)

        var timeOrder = "Date: $dateStr, Time: $timeStr"
        Log.d("Current Time", timeOrder)

        val chart = hashMapOf(
            "userID" to savedID,
            "productID" to data.id,
            "time" to timeOrder
        )

        db.collection("charts")
            .add(chart)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }


        binding.textOrderSuccess.text = "Add to Chart Success"
        Handler(Looper.getMainLooper()).postDelayed({
            // Set teks kembali menjadi kosong setelah 2 detik
            binding.textOrderSuccess.text = ""
        }, 2000)
    }
}