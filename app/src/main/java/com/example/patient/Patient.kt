package com.example.patient

import java.util.HashMap

class Patient {

    init {
        PatientAllInstances.add(this)
    }

    companion object {
        var PatientAllInstances = ArrayList<Patient>()
        fun createPatient(): Patient {
            return Patient()
        }
        
        var PatientIndex: HashMap<String, Patient> = HashMap<String, Patient>()
        
        fun createByPKPatient(idx: String): Patient {
            var result: Patient? = PatientIndex[idx]
            if (result != null) { return result }
                  result = Patient()
                  PatientIndex.put(idx,result)
                  result.patientId = idx
                  return result
        }
        
		fun killPatient(idx: String?) {
            val rem = PatientIndex[idx] ?: return
            val remd = ArrayList<Patient>()
            remd.add(rem)
            PatientIndex.remove(idx)
            PatientAllInstances.removeAll(remd)
        }        
    }

    var patientId = ""  /* identity */
    var name = "" 
    var appointmentId = "" 
    var attends : Appointment? = null

}
