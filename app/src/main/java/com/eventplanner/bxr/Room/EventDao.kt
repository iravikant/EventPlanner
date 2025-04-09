package com.eventplanner.bxr.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eventplanner.bxr.Event


@Dao
interface EventDao {
    @Query("SELECT * FROM event_table WHERE date = :date")
    fun getEventsByDate(date: String): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)
}
