package com.ppb.pawspal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ppb.pawspal.databinding.ActivityDetailItemBinding
import data.Item

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getParcelableExtra<Item>("name")

        binding.tvName.text = name.toString()
    }
}