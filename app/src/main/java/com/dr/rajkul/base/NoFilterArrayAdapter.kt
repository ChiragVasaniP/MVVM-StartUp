package com.dr.rajkul.base

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class NoFilterArrayAdapter<T>(
    context: Context,
    resource: Int,
    objects: List<T>
) : ArrayAdapter<T>(context, resource, objects) {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                // Disable filtering by returning the original list
                val results = FilterResults()
                results.values = this@NoFilterArrayAdapter
                results.count = count
                return results
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults?
            ) {
                // No need to publish any results as filtering is disabled
            }
        }
    }
}
