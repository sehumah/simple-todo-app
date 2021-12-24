package com.example.simple_todo_app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * this class is the bridge that tells the recycler view exactly how to lay out the data we give it
 */
class TaskItemAdapter(private val listOfItems: List<String>, val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClicked (position: Int)
    }

    // inflate a layout from XML and return the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)  // inflate the custom layout
        return ViewHolder(contactView)  // return a new holder instance
    }

    // populate data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get the data model based on position
        val item = listOfItems[position]

        // set the textView to be whatever the text for the specific task is
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        // store references to elements in our layout view
        val textView: TextView
        init {
            textView = itemView.findViewById(android.R.id.text1)

            // implement functionality for deleting tasks
            itemView.setOnLongClickListener {
                // Log.i("Stephen", "User long-clicked on item: $adapterPosition")
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}
