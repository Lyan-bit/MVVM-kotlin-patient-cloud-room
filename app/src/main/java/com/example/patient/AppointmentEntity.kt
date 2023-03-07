package com.example.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointment_table")
data class AppointmentEntity (
    @PrimaryKey
    val appointmentId: String, 
    val code: String
)
