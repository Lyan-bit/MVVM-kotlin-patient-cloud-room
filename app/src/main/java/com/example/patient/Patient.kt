package com.example.patient

import java.util.HashMap

class Patient {

    init {
        patientAllInstances.add(this)
    }

    companion object {
        var patientAllInstances = ArrayList<Patient>()
        fun createPatient(): Patient {
            return Patient()
        }

        var patientIndex: HashMap<String, Patient> = HashMap<String, Patient>()

        fun createByPKPatient(idx: String): Patient {
            var result: Patient? = patientIndex[idx]
            if (result != null) { return result }
            result = Patient()
            patientIndex.put(idx,result)
            result.patientId = idx
            return result
        }

        fun killPatient(idx: String?) {
            val rem = patientIndex[idx] ?: return
            val remd = ArrayList<Patient>()
            remd.add(rem)
            patientIndex.remove(idx)
            patientAllInstances.removeAll(remd)
        }
    }

    var patientId = ""  /* identity */
    var name = ""
    var appointmentId = ""
    var attends : Appointment? = null

}
