package com.example.patient

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentEntityDAO {
    //LiveData
    //Read (list entity)
    @Query("SELECT * FROM appointment_table")
    fun listAppointments(): Flow<List<AppointmentEntity>>

    //Read (list appointmentId)
	@Query("SELECT appointmentId FROM appointment_table")
	fun listAppointmentappointmentIds (): Flow<List<String>>
    //Read (list code)
	@Query("SELECT code FROM appointment_table")
	fun listAppointmentcodes (): Flow<List<String>>

	//Suspend
    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createAppointment (appointment: AppointmentEntity)

    //Read (list entity with suspend)
    @Query("SELECT * FROM appointment_table")
    suspend fun listAppointment(): List<AppointmentEntity>

    //Update
    @Update
    suspend fun updateAppointment (appointment: AppointmentEntity)

    //Delete all records
    @Query("DELETE FROM appointment_table")
    suspend fun deleteAppointments ()

    //Delete a single record by PK
    @Query("DELETE FROM appointment_table WHERE appointmentId = :id")
    suspend fun deleteAppointment (id: String)
    
    //Search with live data
	@Query("SELECT * FROM  appointment_table WHERE appointmentId LIKE :searchQuery ")
	fun searchByAppointmentappointmentId(searchQuery: String): Flow<List<AppointmentEntity>>
	@Query("SELECT * FROM  appointment_table WHERE code LIKE :searchQuery ")
	fun searchByAppointmentcode(searchQuery: String): Flow<List<AppointmentEntity>>

    //Search with suspend
    @Query("SELECT * FROM  appointment_table WHERE appointmentId LIKE :searchQuery")
	suspend fun searchByAppointmentappointmentId2(searchQuery: String): List<AppointmentEntity>
    @Query("SELECT * FROM  appointment_table WHERE code LIKE :searchQuery")
	suspend fun searchByAppointmentcode2(searchQuery: String): List<AppointmentEntity>

}
