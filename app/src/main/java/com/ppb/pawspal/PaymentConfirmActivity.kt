package com.ppb.pawspal

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ppb.pawspal.databinding.ActivityPaymentConfirmBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentConfirmActivity : AppCompatActivity() {


    val db = Firebase.firestore
    private lateinit var savedID: String
    private lateinit var listCart: ArrayList<Pair<String, Int>>

    private lateinit var binding: ActivityPaymentConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("main_shared_preferences", Context.MODE_PRIVATE)

        savedID = sharedPreferences.getString("id", "").toString()

        binding = ActivityPaymentConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedData = intent.getIntExtra("totalPrice", 0)

        val myArrayList = intent.getSerializableExtra("listCart") as ArrayList<Pair<String, Int>>

        listCart = myArrayList

        binding.hargaPayment1.text = "Rp. " + (receivedData + 4000)
        binding.hargaPayment2.text = "Rp. " + (receivedData + 5000)
        binding.hargaPayment3.text = "Rp. " + (receivedData + 6000)
        binding.hargaPayment4.text = "Rp. " + (receivedData + 7000)

        binding.buttonConfirm.setOnClickListener{
            payment()
        }
    }

    fun payment(){

        val currentDate = Date()

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val dateStr = dateFormat.format(currentDate)
        val timeStr = timeFormat.format(currentDate)

        var timePayment = "Date: $dateStr, Time: $timeStr"
        Log.d("Current Time", timePayment)

        val chart = hashMapOf(
            "userID" to savedID,
            "itemList" to listCart,
            "timePayment" to timePayment
        )

        db.collection("orders")
            .add(chart)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }



        val sharedPreferences = getSharedPreferences(savedID, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("arrayListCart", "")
        editor.apply()

        binding.textPaymentSuccess.text = "Payment Successfuly"
        Handler(Looper.getMainLooper()).postDelayed({
            binding.textPaymentSuccess.text = ""
        }, 2000)
    }
}