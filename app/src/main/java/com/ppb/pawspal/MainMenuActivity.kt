
package com.ppb.pawspal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ppb.pawspal.databinding.ActivityLoginBinding
import com.ppb.pawspal.databinding.ActivityMainMenuBinding
import data.Item

class MainMenuActivity : AppCompatActivity() {


    val list = ArrayList<Item>()
    private lateinit var mainMenuBinding: ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                val bundle = Bundle()
                bundle.putSerializable("myArrayList", list)
                fragment.arguments = bundle

                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_payment -> {
                val fragment = PaymentFragment.newInstance("test1", "test2")
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
}