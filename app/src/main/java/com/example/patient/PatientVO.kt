package com.example.patient

import java.util.ArrayList

class PatientVO  {

    private var patientId: String = ""
    private var name: String = ""
    private var appointmentId: String = ""

    constructor() {}

    constructor(patientIdx: String, 
        namex: String, 
        appointmentIdx: String
        ) {
        this.patientId = patientIdx
        this.name = namex
        this.appointmentId = appointmentIdx
    }

    constructor (_x: Patient) {
        patientId = _x.patientId
        name = _x.name
        appointmentId = _x.appointmentId
    }

    override fun toString(): String {
        return "patientId = $patientId,name = $name,appointmentId = $appointmentId"
    }

    fun toStringList(list: List<PatientVO>): List<String> {
        val res: MutableList<String> = ArrayList()
        for (i in list.indices) {
            res.add(list[i].toString())
        }
        return res
    }
    
    fun getPatientId(): String {
        return patientId
    }
    
    fun getName(): String {
        return name
    }
    
    fun getAppointmentId(): String {
        return appointmentId
    }
    

    fun setPatientId(_x: String) {
    	patientId = _x
    }
    
    fun setName(_x: String) {
    	name = _x
    }
    
    fun setAppointmentId(_x: String) {
    	appointmentId = _x
    }
    
}
