package com.example.booksport

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class CategoryAdapter(
    private val categories: List<String>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val cardView: CardView = itemView as CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category

        // Apply different styling for selected category
        if (position == selectedPosition) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#2196F3")) // Blue color
            holder.categoryName.setTextColor(Color.WHITE)
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F5F5F5")) // Light gray
            holder.categoryName.setTextColor(Color.BLACK)
        }

        holder.itemView.setOnClickListener {
            val previousSelected = selectedPosition
            selectedPosition = holder.adapterPosition

            // Update both the previously selected and newly selected items
            notifyItemChanged(previousSelected)
            notifyItemChanged(selectedPosition)

            onCategorySelected(category)
        }
    }

    override fun getItemCount(): Int = categories.size

    // New method to get the currently selected category
    fun getSelectedCategory(): String {
        return if (selectedPosition >= 0 && selectedPosition < categories.size) {
            categories[selectedPosition]
        } else {
            "All"
        }
    }
}