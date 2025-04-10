package com.eventplanner.bxr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eventplanner.bxr.Room.EventDao
import com.eventplanner.bxr.ViewModel.EventViewModel
import com.eventplanner.bxr.databinding.ItemEventBinding

class EventAdapter(private val viewModel: EventViewModel) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var events: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    fun submitList(eventList: List<Event>) {
        events = eventList
        notifyDataSetChanged()
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.titleText.text = event.title
            binding.descriptionText.text = event.description
            binding.timeText.text = event.time

            binding.editButton.setOnClickListener {
                DialogFragment(
                    selectedDate = event.date,
                    onEventSaved = { updatedEvent ->
                        viewModel.update(updatedEvent)
                    },
                    eventToEdit = event
                ).show((itemView.context as AppCompatActivity).supportFragmentManager, "EventDialog")
            }

            binding.deleteBtn.setOnClickListener {
                viewModel.delete(event) // Call ViewModel to delete the event
            }
        }
    }
}
