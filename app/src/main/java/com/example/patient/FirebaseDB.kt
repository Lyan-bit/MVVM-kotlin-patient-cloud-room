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
        val patient_listener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get instances from the cloud database
                val _patients = dataSnapshot.value as HashMap<String, Object>?
                if (_patients != null) {
                    val _keys = _patients.keys
                    for (_key in _keys) {
                        val _x = _patients[_key]
                        PatientDAO.parseRaw(_x)
                    }
                    // Delete local objects which are not in the cloud:
                    val _locals = ArrayList<Patient>()
                    _locals.addAll(Patient.Patient_allInstances)
                    for (_x in _locals) {
                        if (_keys.contains(_x.patientId)) {
                        } else {
                            Patient.killPatient(_x.patientId)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        database!!.child("patients").addValueEventListener(patient_listener)
    }

    fun persistPatient(ex: Patient) {
        val _evo = PatientVO(ex)
        val _key = _evo.getPatientId()
        if (database == null) {
            return
        }
        database!!.child("patients").child(_key).setValue(_evo)
    }

    fun deletePatient(ex: Patient) {
        val _key: String = ex.patientId
        if (database == null) {
            return
        }
        database!!.child("patients").child(_key).removeValue()
    }
}
