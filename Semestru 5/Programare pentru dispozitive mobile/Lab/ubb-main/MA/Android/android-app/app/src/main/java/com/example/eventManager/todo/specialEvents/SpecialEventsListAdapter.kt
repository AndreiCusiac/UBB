package com.example.eventManager.todo.specialEvents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eventManager.R
import com.example.eventManager.core.TAG
import com.example.eventManager.todo.data.Item
import com.example.eventManager.todo.specialEvent.SpecialEventEditFragment
import java.util.*

class SpecialEventsListAdapter(
    private val fragment: Fragment,
) : RecyclerView.Adapter<SpecialEventsListAdapter.ViewHolder>() {

    var items = emptyList<Item>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    private var onItemClick: View.OnClickListener = View.OnClickListener { view ->
        val specialEvent = view.tag as Item

        fragment.findNavController().navigate(R.id.SpecialEventEditFragment, Bundle().apply {
            putString(SpecialEventEditFragment.ITEM_ID, specialEvent._id)
        })
    };

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_special_event, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")

        val specialEvent = items[position]
        holder.titleView.text = specialEvent.title
        val isbnT = "ISBN: " + specialEvent.isbn.toString()
        holder.isbnView.text = isbnT

        val calendar = Calendar.getInstance()
        calendar.time = specialEvent.date
        val dateString = "Borrowed on: " + calendar.get(Calendar.DAY_OF_MONTH).toString() + " " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " +
                calendar.get(Calendar.YEAR).toString()

        holder.date.text = dateString

        holder.isReturnedView.text = if (specialEvent.isReturned) "Returned" else "Not returned yet"

        holder.itemView.tag = specialEvent
        holder.itemView.setOnClickListener(onItemClick)

        val newPos = position + 1
        val bookNum = "BookRow #$newPos"
        holder.bookRowNo.text = bookNum
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView
        val isbnView: TextView
        val isReturnedView: TextView
        val date: TextView
        val bookRowNo: TextView

        init {
            titleView = view.findViewById(R.id.product)
            isbnView = view.findViewById(R.id.numberOfPeople)
            isReturnedView = view.findViewById(R.id.isApproved)
            date = view.findViewById(R.id.date)
            bookRowNo = view.findViewById(R.id.bookRowNumber)
        }
    }
}