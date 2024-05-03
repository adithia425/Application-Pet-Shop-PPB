package com.ppb.pawspal

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        val sharedPreferences = getSharedPreferences("main_shared_preferences", Context.MODE_PRIVATE)

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
            addCart()
        }
    }

    fun addCart(){

        val idToAdd: String = data.id
        val quantityToAdd = 1

//        val arrayListCartTemp = arrayListOf(
//            Pair("7x7g69GGVqAHgTLRhTEr", 2)
//        )

        val gson = Gson()
        val sharedPreferences = getSharedPreferences(savedID, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


//        editor.putString("arrayListCart", "arrayListCartTemp")
//        editor.apply()

        val getArrayListCart = sharedPreferences.getString("arrayListCart", null)
        Log.w("addCart", getArrayListCart.toString())

        var arrayListCart: ArrayList<Pair<String, Int>>

        if(getArrayListCart.isNullOrEmpty()){
            //Buat List Kosong
            val arrayListCartTemp = arrayListOf<Pair<String, Int>>()
            val json = gson.toJson(arrayListCartTemp)
            editor.putString("arrayListCart", json)
            editor.apply()
            arrayListCart = arrayListCartTemp
        }else{
            arrayListCart = if (getArrayListCart != null) {
                val type = object : TypeToken<ArrayList<Pair<String, Int>>>() {}.type
                Gson().fromJson<ArrayList<Pair<String, Int>>>(getArrayListCart, type)
            } else {
                arrayListOf<Pair<String, Int>>()
            }
        }
        //Add to list
        val existingPair = arrayListCart.find { it.first == idToAdd }

        if (existingPair != null) {
            val index = arrayListCart.indexOf(existingPair)
            val newQuantity = existingPair.second + quantityToAdd
            arrayListCart[index] = Pair(idToAdd, newQuantity)
        } else {
            arrayListCart.add(Pair(idToAdd, quantityToAdd))
        }

        val arrayListBJsonNew = Gson().toJson(arrayListCart)

        //val editor = sharedPreferences.edit()
        editor.putString("arrayListCart", arrayListBJsonNew)
        editor.apply()

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