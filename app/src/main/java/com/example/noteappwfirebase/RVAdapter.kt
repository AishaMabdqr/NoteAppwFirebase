package com.example.noteappwfirebase

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (val activity: MainActivity) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    private var itemList = emptyList<Notes>()

    class ItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var items = itemList[position]

        holder.itemView.apply {
            tvItems.text = items.note
            Log.d("RVAdapter", "Items = $items")

            bEdit.setOnClickListener{
                activity.dialog(items.pk)
            }

            bDel.setOnClickListener {
                activity.myViewModel.deleteNote(items.pk)
            }
        }
    }

    override fun getItemCount() = itemList.size


    fun update(itemList : List<Notes>){
        this.itemList = itemList
        notifyDataSetChanged()
    }

}