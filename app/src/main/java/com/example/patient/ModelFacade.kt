package com.example.patient

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.ArrayList

class ModelFacade private constructor(context: Context) {

    private var cdb: FirebaseDB = FirebaseDB.getInstance()
    private var fileSystem: FileAccessor


    private var currentPatient: PatientVO? = null
    private var currentPatients: ArrayList<PatientVO> = ArrayList()

    init {
        fileSystem = FileAccessor(context)
    }

    companion object {
    	private val repository by lazy { Repository() }
        private var instance: ModelFacade? = null
        fun getInstance(context: Context): ModelFacade {
            return instance ?: ModelFacade(context)
        }
    }
    
	    /* This metatype code requires OclType.java, OclAttribute.java, OclOperation.java */
	    fun initialiseOclTypes() {	    val Patient_OclType: OclType = OclType.createByPKOclType("Patient")
	        Patient_OclType.setMetatype(Patient::class.java)
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
    

	fun getAppointmentByPK(_val: String): Flow<Appointment> {
        val res: Flow<List<AppointmentEntity>> = repository.searchByAppointmentappointmentId(_val)
        return res.map { appointment ->
            val itemx = Appointment.createByPKAppointment(_val)
            if (appointment.isNotEmpty()) {
            itemx.appointmentId = appointment[0].appointmentId
            }
            if (appointment.isNotEmpty()) {
            itemx.code = appointment[0].code
            }
            itemx
        }
    }
    
    fun createPatient(_x: PatientVO) { 
		editPatient(_x)
	}
				    
    fun editPatient(_x: PatientVO) {
		var obj = getPatientByPK(_x.getPatientId())
		if (obj == null) {
			obj = Patient.createByPKPatient(_x.getPatientId())
	    }
			
		  obj.patientId = _x.getPatientId()
		  obj.name = _x.getName()
		  obj.appointmentId = _x.getAppointmentId()
		cdb.persistPatient(obj)
		currentPatient = _x
	}
		
	fun searchPatientById(search: String) : PatientVO {
		var res = PatientVO()
		for (x in currentPatients.indices) {
			if ( currentPatients[x].getPatientId().toString() == search)
			res = currentPatients[x]
		}
		return res
	}
	
  	fun deletePatient(_id: String) {
		  val obj = getPatientByPK(_id)
		  if (obj != null) {
			  cdb.deletePatient(obj)
			  Patient.killPatient(_id)
		   }
		   currentPatient = null	
	}
		
	fun setSelectedPatient(x: PatientVO) {
		currentPatient = x
	}
			    
    suspend fun createAppointment(_x: AppointmentEntity) {
        repository.createAppointment(_x)
        currentAppointment = _x
    }
    
   fun setSelectedAppointment(x: AppointmentEntity) {
	     currentAppointment = x
	}
	    
   suspend fun editAppointment(_x: AppointmentEntity) {
        repository.updateAppointment(_x)
        currentAppointment = _x
    }
   suspend fun deleteAppointment(_id: String) {
        repository.deleteAppointment(_id)
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

    suspend fun getAppointmentByPK2(_val: String): Appointment? {
        val res: List<AppointmentEntity> = repository.searchByAppointmentappointmentId2(_val)
	        return if (res.isEmpty()) {
	            null
	        } else {
	            val vo: AppointmentEntity = res[0]
	            val itemx = Appointment.createByPKAppointment(_val)
	            itemx.appointmentId = vo.appointmentId
            itemx.code = vo.code
	            itemx
	        }
    }
    
    suspend fun retrieveAppointment(_val: String): Appointment? {
            return getAppointmentByPK2(_val)
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

    suspend fun persistAppointment(_x: Appointment) {
        val vo = AppointmentEntity(_x.appointmentId, _x.code)
        repository.updateAppointment(vo)
        currentAppointment = vo
    }
	
    suspend fun searchByAppointmentappointmentId2(appointmentIdx: String): List<AppointmentEntity> {
        currentAppointments = repository.searchByAppointmentappointmentId2(appointmentIdx)
	    return currentAppointments
	}
    suspend fun searchByAppointmentcode2(codex: String): List<AppointmentEntity> {
        currentAppointments = repository.searchByAppointmentcode2(codex)
	    return currentAppointments
	}

	fun listPatient(): List<PatientVO> {
        val patients: ArrayList<Patient> = Patient.Patient_allInstances
		   currentPatients.clear()
		   for (i in patients.indices) {
			   currentPatients.add(PatientVO(patients[i]))
		   }
			        
			return currentPatients
	}
	
	fun listAllPatient(): ArrayList<Patient> {
		  val patients: ArrayList<Patient> = Patient.Patient_allInstances    
		  return patients
	}
			    
    fun stringListPatient(): List<String> {
        val res: ArrayList<String> = ArrayList()
        for (x in currentPatients.indices) {
            res.add(currentPatients[x].toString())
        }
        return res
    }

    fun getPatientByPK(_val: String): Patient? {
        return Patient.Patient_index[_val]
    }
    
    fun retrievePatient(_val: String): Patient? {
            return getPatientByPK(_val)
        }

    fun allPatientPatientIds(): ArrayList<String> {
        val res: ArrayList<String> = ArrayList()
            for (x in currentPatients.indices) {
                res.add(currentPatients[x].getPatientId())
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

    fun persistPatient(_x: Patient) {
        val vo = PatientVO(_x)
        cdb.persistPatient(_x)
        currentPatient = vo
    }
	
}
