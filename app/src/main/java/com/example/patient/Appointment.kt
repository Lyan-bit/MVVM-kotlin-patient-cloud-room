package com.example.patient

import java.util.HashMap
import java.util.HashSet

class Appointment {

    init {
        AppointmentAllInstances.add(this)
    }

    companion object {
        var AppointmentAllInstances = ArrayList<Appointment>()
        fun createAppointment(): Appointment {
            return Appointment()
        }
        
        var AppointmentIndex: HashMap<String, Appointment> = HashMap<String, Appointment>()
        
        fun createByPKAppointment(idx: String): Appointment {
            var result: Appointment? = AppointmentIndex[idx]
            if (result != null) { return result }
                  result = Appointment()
                  AppointmentIndex.put(idx,result)
                  result.appointmentId = idx
                  return result
        }
        
		fun killAppointment(idx: String?) {
            val rem = AppointmentIndex[idx] ?: return
            val remd = ArrayList<Appointment>()
            remd.add(rem)
            AppointmentIndex.remove(idx)
            AppointmentAllInstances.removeAll(remd)
        }        
    }

    var appointmentId = ""  /* identity */
    var code = "" 
    var patients = ArrayList<Patient>()

}
