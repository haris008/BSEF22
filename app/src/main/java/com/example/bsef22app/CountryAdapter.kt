package com.example.bsef22app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CountryAdapter(var countries:List<Country>): RecyclerView.Adapter<CountryItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return CountryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.countryNameTextView.setOnClickListener{
            
        }

    }

    override fun getItemCount(): Int {
        return countries.size
    }

}



class CountryItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    var countryNameTextView = itemView.findViewById<TextView>(R.id.countryName)
    var countryCapitalTextView = itemView.findViewById<TextView>(R.id.countryCapital)

    fun bind(country:Country){
        countryNameTextView.text = country.countryName
        countryCapitalTextView.text = country.countryCapital
    }
}