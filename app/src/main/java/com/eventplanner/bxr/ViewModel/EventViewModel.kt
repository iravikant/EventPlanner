package com.eventplanner.bxr.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.eventplanner.bxr.Event
import com.eventplanner.bxr.Repo.EventRepository
import com.eventplanner.bxr.Room.EventDatabase
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository

    init {
        val dao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(dao)
    }

    fun getEventsByDate(date: String): LiveData<List<Event>> = repository.getEventsByDate(date)
    fun insert(event: Event) = viewModelScope.launch { repository.insert(event) }
    fun update(event: Event) = viewModelScope.launch { repository.update(event) }
    fun delete(event: Event) = viewModelScope.launch { repository.delete(event) }
}
