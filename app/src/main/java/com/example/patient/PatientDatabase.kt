package com.example.patient

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(AppointmentEntity::class)], version = 1, exportSchema = false)
abstract class PatientDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentEntityDAO
}
