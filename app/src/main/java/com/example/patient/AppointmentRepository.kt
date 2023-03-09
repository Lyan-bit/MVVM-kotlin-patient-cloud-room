package com.example.patient

import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    //Read
    suspend fun listAppointment(): List<AppointmentEntity>

    //Create
    suspend fun createAppointment(appointment: AppointmentEntity)

    //Update
    suspend fun updateAppointment(appointment: AppointmentEntity)

    //Delete All Appointments
    suspend fun deleteAppointments()


    //Delete a Appointment by PK
	suspend fun deleteAppointment(id: String)
	    
    //Search with live data
    fun searchByAppointmentappointmentId(searchQuery: String): Flow<List<AppointmentEntity>>
    //Search with live data
    fun searchByAppointmentcode(searchQuery: String): Flow<List<AppointmentEntity>>

    //Search with suspend
    suspend fun searchByAppointmentappointmentId2(searchQuery: String): List<AppointmentEntity>
    suspend fun searchByAppointmentcode2(searchQuery: String): List<AppointmentEntity>

}
