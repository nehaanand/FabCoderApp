package com.neha.fabcoderapp.views.view

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neha.fabcoderapp.R
import com.neha.fabcoderapp.data.model.Drink
import kotlinx.android.synthetic.main.activity_cocktail_detail.*
import kotlinx.android.synthetic.main.list_item_layout.view.*

class DrinkDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_detail)

        setUpUI()
    }

    private fun setUpUI() {
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.logocolor)));

        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.logocolor)
        val drink: Drink = intent.getSerializableExtra("drinkDetail") as Drink
        renderUI(drink)
    }

    private fun renderUI(users: Drink) {
        tvInstructions.text = users.strInstructions
        tvGlass.text = "Serve: " + users.strGlass
        Glide.with(ivDrink.context)
            .load(users.strDrinkThumb)
            .thumbnail(0.5f)
            .into(ivDrink)
    }
}