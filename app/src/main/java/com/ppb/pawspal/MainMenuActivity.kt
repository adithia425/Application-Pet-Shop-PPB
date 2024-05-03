
package com.ppb.pawspal

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ppb.pawspal.databinding.ActivityMainMenuBinding
import data.Item

class MainMenuActivity : AppCompatActivity() {

    private lateinit var savedID: String

    val list = ArrayList<Item>()
    private lateinit var mainMenuBinding: ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("main_shared_preferences", Context.MODE_PRIVATE)

        savedID = sharedPreferences.getString("id", "").toString()

        mainMenuBinding = ActivityMainMenuBinding.inflate(layoutInflater)

        setContentView(mainMenuBinding.root)

        val myArrayList = intent.getSerializableExtra("myArrayList") as ArrayList<Item>?
        if (myArrayList != null) {
            Log.w("MainMenu Created", "Name : " + myArrayList.count())

            list.addAll(myArrayList)
        }

        val fragment = HomeFragment.newInstance("test1", "test2")

        val bundle = Bundle()
        bundle.putSerializable("myArrayList", myArrayList)
        fragment.arguments = bundle



        mainMenuBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItemSelected)
        addFragment(fragment)
    }

    private val menuItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.item_home -> {
                val fragment = HomeFragment.newInstance("test1", "test2")

                val bundle = Bundle()
                bundle.putSerializable("myArrayList", list)
                fragment.arguments = bundle

                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_cart -> {
                val fragment = CartFragment.newInstance("test1", "test2")

                val cartList = getCartList()

                val bundle = Bundle()
                bundle.putSerializable("myArrayList", list)
                if (cartList != null) {
                    bundle.putSerializable("cartList", cartList)
                } else {

                    bundle.putSerializable("cartList", ArrayList<Pair<String, Int>>())
                }
                fragment.arguments = bundle

                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_payment -> {
                val fragment = PaymentFragment.newInstance(savedID, "test2")

                val cartList = getCartList()

                val bundle = Bundle()
                bundle.putSerializable("myArrayList", list)
                bundle.putSerializable("cartList", cartList)
                fragment.arguments = bundle

                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_profile -> {
                val fragment = ProfileFragment.newInstance("test1", "test2")
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(com.google.android.material.R.anim.design_bottom_sheet_slide_in, com.google.android.material.R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    fun getCartList(): ArrayList<Pair<String, Int>>{

        val sharedPreferences = getSharedPreferences(savedID, Context.MODE_PRIVATE)
        val getArrayListCart = sharedPreferences.getString("arrayListCart", null)
        Log.w("getCatList", getArrayListCart.toString())

        val arrayListCart = if (getArrayListCart != null) {
            val type = object : TypeToken<ArrayList<Pair<String, Int>>>() {}.type
            Gson().fromJson<ArrayList<Pair<String, Int>>>(getArrayListCart, type)
        } else {
            ArrayList()
        }

        return arrayListCart
    }
}