package com.example.patient

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.ArrayList

class ModelFacade private constructor(context: Context) {

    private var cdb: FirebaseDB = FirebaseDB.getInstance()
    private val fileSystem: FileAccessor by lazy { FileAccessor(context) }


    private var currentPatient: PatientVO? = null
    private var currentPatients: ArrayList<PatientVO> = ArrayList()

    init {
        //init
    }

    companion object {
        private val repository by lazy { Repository() }
        private var instance: ModelFacade? = null
        fun getInstance(context: Context): ModelFacade {
            return instance ?: ModelFacade(context)
        }
    }

    val allAppointments: LiveData<List<AppointmentEntity>> = repository.allAppointments.asLiveData()

    val allAppointmentAppointmentIds: LiveData<List<String>> = repository.allAppointmentappointmentIds.asLiveData()
    val allAppointmentCodes: LiveData<List<String>> = repository.allAppointmentcodes.asLiveData()
    private var currentAppointment: AppointmentEntity? = null
    private var currentAppointments: List<AppointmentEntity> = ArrayList()

    fun searchByAppointmentappointmentId(searchQuery: String): LiveData<List<AppointmentEntity>>  {
        return repository.searchByAppointmentappointmentId(searchQuery).asLiveData()
    }

    fun searchByAppointmentcode(searchQuery: String): LiveData<List<AppointmentEntity>>  {
        return repository.searchByAppointmentcode(searchQuery).asLiveData()
    }


    fun getAppointmentByPK(value: String): Flow<Appointment> {
        val res: Flow<List<AppointmentEntity>> = repository.searchByAppointmentappointmentId(value)
        return res.map { appointment ->
            val itemx = Appointment.createByPKAppointment(value)
            if (appointment.isNotEmpty()) {
                itemx.appointmentId = appointment[0].appointmentId
                itemx.code = appointment[0].code
            }
            itemx
        }
    }

    fun createPatient(x: PatientVO) {
        editPatient(x)
    }

    fun editPatient(x: PatientVO) {
        var obj = getPatientByPK(x.patientId)
        if (obj == null) {
            obj = Patient.createByPKPatient(x.patientId)
        }

        obj.patientId = x.patientId
        obj.name = x.name
        obj.appointmentId = x.appointmentId
        cdb.persistPatient(obj)
        currentPatient = x
    }

    fun searchPatientById(search: String) : PatientVO {
        var res = PatientVO()
        for (x in currentPatients.indices) {
            if ( currentPatients[x].patientId.toString() == search)
                res = currentPatients[x]
        }
        return res
    }

    fun deletePatient(id: String) {
        val obj = getPatientByPK(id)
        if (obj != null) {
            cdb.deletePatient(obj)
            Patient.killPatient(id)
        }
        currentPatient = null
    }

    fun setSelectedPatient(x: PatientVO) {
        currentPatient = x
    }

    suspend fun createAppointment(x: AppointmentEntity) {
        repository.createAppointment(x)
        currentAppointment = x
    }

    suspend fun editAppointment(x: AppointmentEntity) {
        repository.updateAppointment(x)
        currentAppointment = x
    }

    fun setSelectedAppointment(x: AppointmentEntity) {
        currentAppointment = x
    }

    suspend fun deleteAppointment(id: String) {
        repository.deleteAppointment(id)
        currentAppointment = null
    }

    fun addPatientattendsAppointment(appointmentId: String, patientId: String) {
        var obj = getPatientByPK(patientId)
        if (obj == null) {
            obj = Patient.createByPKPatient(patientId)
        }
        obj.appointmentId = appointmentId
        cdb.persistPatient(obj)
        currentPatient = PatientVO(obj)

    }

    fun removePatientattendsAppointment(appointmentId: String, patientId: String) {
        var obj = getPatientByPK(patientId)
        if (obj == null) {
            obj = Patient.createByPKPatient(patientId)
        }
        obj.appointmentId = "Null"
        cdb.persistPatient(obj)
        currentPatient = PatientVO(obj)

    }

    suspend fun listAppointment(): List<AppointmentEntity> {
        currentAppointments = repository.listAppointment()
        return currentAppointments
    }

    suspend fun listAllAppointment(): ArrayList<Appointment> {
        currentAppointments = repository.listAppointment()
        var res = ArrayList<Appointment>()
        for (x in currentAppointments.indices) {
            val vo: AppointmentEntity = currentAppointments[x]
            val itemx = Appointment.createByPKAppointment(vo.appointmentId)
            itemx.appointmentId = vo.appointmentId
            itemx.code = vo.code
            res.add(itemx)
        }
        return res
    }

    suspend fun stringListAppointment(): List<String> {
        currentAppointments = repository.listAppointment()
        val res: ArrayList<String> = ArrayList()
        for (x in currentAppointments.indices) {
            res.add(currentAppointments[x].toString())
        }
        return res
    }

    suspend fun getAppointmentByPK2(value: String): Appointment? {
        val res: List<AppointmentEntity> = repository.searchByAppointmentappointmentId2(value)
        return if (res.isEmpty()) {
            null
        } else {
            val vo: AppointmentEntity = res[0]
            val itemx = Appointment.createByPKAppointment(value)
            itemx.appointmentId = vo.appointmentId
            itemx.code = vo.code
            itemx
        }
    }

    suspend fun retrieveAppointment(value: String): Appointment? {
        return getAppointmentByPK2(value)
    }

    suspend fun allAppointmentAppointmentIds(): ArrayList<String> {
        currentAppointments = repository.listAppointment()
        val res: ArrayList<String> = ArrayList()
        for (appointment in currentAppointments.indices) {
            res.add(currentAppointments[appointment].appointmentId)
        }
        return res
    }

    fun setSelectedAppointment(i: Int) {
        if (i < currentAppointments.size) {
            currentAppointment = currentAppointments[i]
        }
    }

    fun getSelectedAppointment(): AppointmentEntity? {
        return currentAppointment
    }

    suspend fun persistAppointment(x: Appointment) {
        val vo = AppointmentEntity(x.appointmentId, x.code)
        repository.updateAppointment(vo)
        currentAppointment = vo
    }


    fun listPatient(): List<PatientVO> {
        val patients: ArrayList<Patient> = Patient.patientAllInstances
        currentPatients.clear()
        for (i in patients.indices) {
            currentPatients.add(PatientVO(patients[i]))
        }

        return currentPatients
    }

    fun listAllPatient(): ArrayList<Patient> {
        val patients: ArrayList<Patient> = Patient.patientAllInstances
        return patients
    }

    fun stringListPatient(): List<String> {
        val res: ArrayList<String> = ArrayList()
        for (x in currentPatients.indices) {
            res.add(currentPatients[x].toString())
        }
        return res
    }

    fun getPatientByPK(value: String): Patient? {
        return Patient.patientIndex[value]
    }

    fun retrievePatient(value: String): Patient? {
        return getPatientByPK(value)
    }

    fun allPatientPatientIds(): ArrayList<String> {
        val res: ArrayList<String> = ArrayList()
        for (x in currentPatients.indices) {
            res.add(currentPatients[x].patientId)
        }
        return res
    }

    fun setSelectedPatient(i: Int) {
        if (i < currentPatients.size) {
            currentPatient = currentPatients[i]
        }
    }

    fun getSelectedPatient(): PatientVO? {
        return currentPatient
    }

    fun persistPatient(x: Patient) {
        val vo = PatientVO(x)
        cdb.persistPatient(x)
        currentPatient = vo
    }

}
