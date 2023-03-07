package com.example.patient

import kotlinx.coroutines.flow.Flow

class Repository : AppointmentRepository {

    private val appointmentDAO: AppointmentEntityDAO by lazy { PatientApplication.database.appointmentDao() }

    val allAppointments: Flow<List<AppointmentEntity>> = appointmentDAO.listAppointments()

    val allAppointmentappointmentIds: Flow<List<String>> = appointmentDAO.listAppointmentappointmentIds()
    val allAppointmentcodes: Flow<List<String>> = appointmentDAO.listAppointmentcodes()

    //Create
    override suspend fun createAppointment(appointment: AppointmentEntity) {
        appointmentDAO.createAppointment(appointment)
    }

    //Read
    override suspend fun listAppointment(): List<AppointmentEntity> {
        return appointmentDAO.listAppointment()
    }

    //Update
    override suspend fun updateAppointment(appointment: AppointmentEntity) {
        appointmentDAO.updateAppointment(appointment)
    }

    //Delete all Appointments
    override suspend fun deleteAppointments() {
       appointmentDAO.deleteAppointments()
    }

    //Delete a Appointment
	override suspend fun deleteAppointment(appointmentId: String) {
	   appointmentDAO.deleteAppointment(appointmentId)
    }
    
     //Search with live data
     override fun searchByAppointmentappointmentId (searchQuery: String): Flow<List<AppointmentEntity>>  {
         return appointmentDAO.searchByAppointmentappointmentId(searchQuery)
     }
     
     //Search with live data
     override fun searchByAppointmentcode (searchQuery: String): Flow<List<AppointmentEntity>>  {
         return appointmentDAO.searchByAppointmentcode(searchQuery)
     }
     

    //Search with suspend
     override suspend fun searchByAppointmentappointmentId2 (appointmentId: String): List<AppointmentEntity> {
          return appointmentDAO.searchByAppointmentappointmentId2(appointmentId)
     }
	     
    //Search with suspend
     override suspend fun searchByAppointmentcode2 (code: String): List<AppointmentEntity> {
          return appointmentDAO.searchByAppointmentcode2(code)
     }
	     


}
