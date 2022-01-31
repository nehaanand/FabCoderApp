package com.neha.fabcoderapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neha.fabcoderapp.R
import com.neha.fabcoderapp.data.model.Drink
import com.neha.fabcoderapp.views.view.DrinksList
import kotlinx.android.synthetic.main.list_item_layout.view.*


class DrinksListAdapter(
    private val DrinksLists: ArrayList<Drink>,
    val itemClickListener: OnItemClickListener
    ) : RecyclerView.Adapter<DrinksListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(DrinksList: Drink,clickListener: OnItemClickListener) {
            itemView.textViewUserName.text = DrinksList.strDrink
            Glide.with(itemView.imageViewAvatar.context)
                .load(DrinksList.strDrinkThumb)
                .thumbnail(0.5f)
                .into(itemView.imageViewAvatar)

            itemView.setOnClickListener {
                clickListener.onItemClicked(DrinksList)
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClicked(drink: Drink)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = DrinksLists.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(DrinksLists[position],itemClickListener)

    fun addData(list: List<Drink>) {
        DrinksLists.addAll(list)
    }

}