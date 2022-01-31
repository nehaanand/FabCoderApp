package com.neha.fabcoderapp.views.view

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.neha.fabcoderapp.R
import com.neha.fabcoderapp.adapter.DrinksListAdapter
import com.neha.fabcoderapp.data.api.ApiHelper
import com.neha.fabcoderapp.data.api.RetrofitBuilder
import com.neha.fabcoderapp.data.model.Drink
import com.neha.fabcoderapp.databinding.ActivityCocktailsListBinding
import com.neha.fabcoderapp.utilities.Status
import com.neha.fabcoderapp.views.base.ViewModelFactory
import com.neha.fabcoderapp.views.viewmodel.ViewModelCocktails


class DrinksList : AppCompatActivity(), DrinksListAdapter.OnItemClickListener {
    private lateinit var drinksListViewModel: ViewModelCocktails
    private lateinit var adapter: DrinksListAdapter
    private lateinit var binding: ActivityCocktailsListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cocktails_list)
        setupUI()
        setupViewModel()
        setupObserver()

    }

    override fun onItemClicked(drink: Drink) {
        val intent = Intent(this, DrinkDetail::class.java)
        intent.putExtra("drinkDetail", drink)
        startActivity(intent)

    }

    private fun setupUI() {
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.logocolor)));
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.logocolor)
        binding.ivScanner.setOnClickListener(View.OnClickListener {
            IntentIntegrator(this).initiateScan(); // `this` is the current Activity

        })
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = DrinksListAdapter(arrayListOf(), this)
        binding.recyclerView.adapter = adapter

        binding.searchCocktail.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                drinksListViewModel.searchDrinkByName(p0)
                setupUI()
                return false
            }
            override fun onQueryTextChange(p0: String): Boolean {
                if (p0 == " ") {
                    drinksListViewModel.getDrinksByFirstChar(getResources().getString(R.string.firstChar))
                } else {
                    drinksListViewModel.searchDrinkByName(p0)
                }
                setupUI()
                return false
            }
        })

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                drinksListViewModel.searchDrinkByName(result.contents)
                setupUI()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setupObserver() {
        drinksListViewModel.drinksList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.searchCocktail.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.searchCocktail.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        drinksListViewModel.errorMessage.observe(this,Observer{
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }


    private fun renderList(users: List<Drink>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        drinksListViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ViewModelCocktails::class.java)
        drinksListViewModel.getDrinksByFirstChar(getResources().getString(R.string.firstChar));
    }
}