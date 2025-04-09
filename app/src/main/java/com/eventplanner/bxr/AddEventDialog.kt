package com.eventplanner.bxr

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.eventplanner.bxr.databinding.DialogAddEventBinding

class AddEventDialog(context: Context, private val date: String, private val onAdd: (Event) -> Unit) : Dialog(context) {

    private val binding = DialogAddEventBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val time = binding.timeInput.text.toString()

            if (title.isNotEmpty() && time.isNotEmpty()) {
                val event = Event(title = title, description = description, date = date, time = time)
                onAdd(event)
                dismiss()
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}
