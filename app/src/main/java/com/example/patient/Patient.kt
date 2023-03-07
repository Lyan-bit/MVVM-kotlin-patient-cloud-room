package com.example.patient

import java.util.HashMap
import java.util.HashSet

class Patient {

    init {
        Patient_allInstances.add(this)
    }

    companion object {
        var Patient_allInstances = ArrayList<Patient>()
        fun createPatient(): Patient {
            return Patient()
        }
        
        var Patient_index: HashMap<String, Patient> = HashMap<String, Patient>()
        
        fun createByPKPatient(idx: String): Patient {
            var result: Patient? = Patient_index[idx]
            if (result != null) { return result }
                  result = Patient()
                  Patient_index.put(idx,result)
                  result.patientId = idx
                  return result
        }
        
		fun killPatient(idx: String?) {
            val rem = Patient_index[idx] ?: return
            val remd = ArrayList<Patient>()
            remd.add(rem)
            Patient_index.remove(idx)
            Patient_allInstances.removeAll(remd)
        }        
    }

    var patientId = ""  /* identity */
    var name = "" 
    var appointmentId = "" 
    var attends : Appointment? = null

}
