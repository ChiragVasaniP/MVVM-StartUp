package com.dr.rajkul.view.adapter.rvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dr.rajkul.R
import com.dr.rajkul.databinding.RowItemBubbleBinding
import com.dr.rajkul.model.ModelDynasty
import com.dr.rajkul.utils.AppHelper.getPercentage
import kotlin.math.roundToInt

class AdapterDynasty(private val items: List<ModelDynasty>, private val rvHeight: Int, private val onSelect: (item: ModelDynasty) -> Unit) : Adapter<AdapterDynasty.Holder>() {

    inner class Holder(itemView: View, val binding: RowItemBubbleBinding) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
         val binding = RowItemBubbleBinding.inflate(
             LayoutInflater.from(parent.context),parent, false
         )
        return Holder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val _binding = holder.binding
        val item = items[position]

        val isViewBig = position % 2 == 0

        val size = getPercentage(rvHeight, if (isViewBig) 34f else 26f)

        holder.itemView.layoutParams.width = size.roundToInt()
        holder.itemView.layoutParams.height = size.roundToInt()
        _binding.text.layoutParams.width = size.roundToInt() - 20

        _binding.icon.layoutParams.height = (size / 4).roundToInt()
        _binding.icon.layoutParams.width = (size / 4).roundToInt()

        _binding.text.text = item.title

        holder.itemView.setBackgroundResource(if (item.selected) R.drawable.shape_round_200_white_stroke_4_selected else R.drawable.shape_round_200_white_stroke_1)

        holder.itemView.setOnClickListener {
            items.onEach {
                it.selected = it == item
            }
            notifyDataSetChanged()
            onSelect(item)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun getItemCount() = items.size



}