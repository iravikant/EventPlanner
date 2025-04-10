package com.eventplanner.bxr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String,
    var description: String,
    var date: String,
    var time: String
)
