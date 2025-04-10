package com.eventplanner.bxr

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eventplanner.bxr.databinding.FragmentDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DialogFragment(
    private val selectedDate: String,
    private val onEventSaved: (Event) -> Unit,
    private val eventToEdit: Event? = null
) : BottomSheetDialogFragment() {

    private var b: FragmentDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentDialogBinding.inflate(inflater, container, false)

        eventToEdit?.let { event ->
            b!!.titleInput.setText(event.title)
            b!!.descriptionInput.setText(event.description)
            b!!.timeInput.setText(event.time)
        }

        b!!.timeInput.setOnClickListener {
            showDateTimePicker()
        }

        b!!.saveButton.setOnClickListener {
            val title = b!!.titleInput.text.toString()
            val description = b!!.descriptionInput.text.toString()
            val time = b!!.timeInput.text.toString()

            if (title.isNotBlank() && time.isNotBlank()) {
                val event = eventToEdit?.copy(title = title, description = description, time = time)
                    ?: Event(title = title, description = description, date = selectedDate, time = time)
                onEventSaved(event)
                dismiss()
            }
        }

        b!!.cancelButton.setOnClickListener {
            dismiss()
        }

        return b!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)

                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)

                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val formattedDateTime = dateFormat.format(calendar.time)

                        b!!.timeInput.setText(formattedDateTime)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}
