package com.example.patient

import java.util.ArrayList

class PatientVO  {

     var patientId: String = ""
     var name: String = ""
     var appointmentId: String = ""

    constructor() {
    	//constructor
    }

    constructor(patientIdx: String, 
        namex: String, 
        appointmentIdx: String
        ) {
        this.patientId = patientIdx
        this.name = namex
        this.appointmentId = appointmentIdx
    }

    constructor (x: Patient) {
        patientId = x.patientId
        name = x.name
        appointmentId = x.appointmentId
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
    
}
