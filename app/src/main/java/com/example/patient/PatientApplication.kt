package com.example.patient

import android.app.Application
import androidx.room.Room

class PatientApplication : Application() {

    companion object {
        lateinit var database: PatientDatabase
            private set
    }
    override fun onCreate() {
        super.onCreate()
        database = Room
            .databaseBuilder(
                this,
                PatientDatabase::class.java,
                "patientDatabase"
            )
            .build() }
}
