package com.example.patient

import com.google.firebase.database.*
import kotlin.collections.ArrayList

class FirebaseDB() {

    var database: DatabaseReference? = null

    companion object {
        private var instance: FirebaseDB? = null
        fun getInstance(): FirebaseDB {
            return instance ?: FirebaseDB()
        }
    }

    init {
        connectByURL("https://patient-161e1-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    fun connectByURL(url: String) {
        database = FirebaseDatabase.getInstance(url).reference
        if (database == null) {
            return
        }
        val patientListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get instances from the cloud database
                val patients = dataSnapshot.value as HashMap<String, Object>?
                if (patients != null) {
                    val keys = patients.keys
                    for (key in keys) {
                        val x = patients[key]
                        PatientDAO.parseRaw(x)
                    }
                    // Delete local objects which are not in the cloud:
                    val _locals = ArrayList<Patient>()
                    locals.addAll(Patient.PatientAllInstances)
                    for (x in locals) {
                        if (keys.contains(x.patientId)) {
                        } else {
                            Patient.killPatient(x.patientId)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            //cancel
            }
        }
        database!!.child("patients").addValueEventListener(patientListener)
    }

    fun persistPatient(ex: Patient) {
        val evo = PatientVO(ex)
        val key = evo.getPatientId()
        if (database == null) {
            return
        }
        database!!.child("patients").child(key).setValue(evo)
    }

    fun deletePatient(ex: Patient) {
        val key: String = ex.patientId
        if (database == null) {
            return
        }
        database!!.child("patients").child(key).removeValue()
    }
}
