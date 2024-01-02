package com.dr.rajkul.dialog.choosecountry

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dr.rajkul.R
import com.dr.rajkul.databinding.RowItemCountriesBinding
import com.dr.rajkul.model.CountryData

class AdapterCountries(private val countries: List<CountryData>, val selectedCountry: (country: CountryData) -> Unit): Adapter<AdapterCountries.Holder>() {

    class Holder(itemView: View, val binding: RowItemCountriesBinding) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RowItemCountriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding.root, binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val _binding = holder.binding
        val item = countries[position]

        _binding.countryName.text = item.flag + " " + item.name
        _binding.txtCountryCode.text = "+${item.dial_code}"

        _binding.check.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, if (item.selected) R.color.colorDark else R.color.colorLight))

        holder.itemView.setOnClickListener {
            countries.onEach {
                it.selected = it.name == item.name
            }
            selectedCountry(item)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = countries.size

}