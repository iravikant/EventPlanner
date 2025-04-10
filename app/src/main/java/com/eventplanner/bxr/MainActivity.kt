package com.eventplanner.bxr

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventplanner.bxr.ViewModel.EventViewModel
import com.eventplanner.bxr.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var viewModel: EventViewModel
    private val calendarView by lazy { b.calendarView }

    private val adapter: EventAdapter by lazy {
        EventAdapter(viewModel)
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var selectedDate = dateFormat.format(Date())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[EventViewModel::class.java]

        b.recyclerView.layoutManager = LinearLayoutManager(this)
        b.recyclerView.adapter = adapter

        viewModel.getEventsByDate(selectedDate).observe(this, Observer { events ->
            adapter.submitList(events)
        })

        b.calendarView.setSelectedDate(CalendarDay.today())


        calendarView.setOnDateChangedListener { widget, date, selected ->
            val selectedDate = String.format(
                "%04d-%02d-%02d",
                date.year,
                date.month + 1,
                date.day
            )

            viewModel.getEventsByDate(selectedDate).observe(this) { events ->
                adapter.submitList(events)
            }
        }

        b.fab.setOnClickListener {
            val dialog = DialogFragment(
                selectedDate = selectedDate,
                onEventSaved = { event ->
                    viewModel.insert(event)
                },
                eventToEdit = null
            )
            dialog.show(supportFragmentManager, "EventDialog")
        }
    }


}
