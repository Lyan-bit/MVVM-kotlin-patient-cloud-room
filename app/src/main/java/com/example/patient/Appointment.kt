package com.example.patient

import java.util.HashMap
import java.util.HashSet

class Appointment {

    init {
        Appointment_allInstances.add(this)
    }

    companion object {
        var Appointment_allInstances = ArrayList<Appointment>()
        fun createAppointment(): Appointment {
            return Appointment()
        }
        
        var Appointment_index: HashMap<String, Appointment> = HashMap<String, Appointment>()
        
        fun createByPKAppointment(idx: String): Appointment {
            var result: Appointment? = Appointment_index[idx]
            if (result != null) { return result }
                  result = Appointment()
                  Appointment_index.put(idx,result)
                  result.appointmentId = idx
                  return result
        }
        
		fun killAppointment(idx: String?) {
            val rem = Appointment_index[idx] ?: return
            val remd = ArrayList<Appointment>()
            remd.add(rem)
            Appointment_index.remove(idx)
            Appointment_allInstances.removeAll(remd)
        }        
    }

    var appointmentId = ""  /* identity */
    var code = "" 
    var patients = ArrayList<Patient>()

}
