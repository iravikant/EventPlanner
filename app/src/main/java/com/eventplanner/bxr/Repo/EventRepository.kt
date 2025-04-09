package com.eventplanner.bxr.Repo

import com.eventplanner.bxr.Event
import com.eventplanner.bxr.Room.EventDao

class EventRepository(private val dao: EventDao) {
    fun getEventsByDate(date: String) = dao.getEventsByDate(date)
    suspend fun insert(event: Event) = dao.insert(event)
    suspend fun update(event: Event) = dao.update(event)
    suspend fun delete(event: Event) = dao.delete(event)
}
