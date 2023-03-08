package com.example.patient

import org.json.JSONObject
import java.lang.Exception
import org.json.JSONArray
import kotlin.collections.ArrayList

class PatientDAO {

    companion object {

        fun getURL(command: String?, pars: ArrayList<String>, values: ArrayList<String>): String {
            var res = "base url for the data source"
            if (command != null) {
                res += command
            }
            if (pars.size == 0) {
                return res
            }
            res = "$res?"
            for (item in pars.indices) {
                val par = pars[item]
                val vals = values[item]
                res = "$res$par=$vals"
                if (item < pars.size - 1) {
                    res = "$res&"
                }
            }
            return res
        }

        fun isCached(id: String?): Boolean {
            val x: Patient = Patient.PatientIndex.get(id) ?: return false
            return true
        }

        fun getCachedInstance(id: String): Patient? {
            return Patient.PatientIndex.get(id)
        }

      fun parseCSV(line: String?): Patient? {
          if (line == null) {
              return null
          }
          val line1vals: ArrayList<String> = Ocl.tokeniseCSV(line)
          var patientx: Patient? = Patient.PatientIndex.get(line1vals[0])
          if (patientx == null) {
              patientx = Patient.createByPKPatient(line1vals[0])
          }
          patientx.patientId = line1vals[0].toString()
          patientx.name = line1vals[1].toString()
          patientx.appointmentId = line1vals[2].toString()
          return patientx
      }


        fun parseJSON(obj: JSONObject?): Patient? {
            return if (obj == null) {
                null
            } else try {
                val id = obj.getString("patientId")
                var patientx: Patient? = Patient.PatientIndex.get(id)
                if (patientx == null) {
                    patientx = Patient.createByPKPatient(id)
                }
                patientx.patientId = obj.getString("patientId")
                patientx.name = obj.getString("name")
                patientx.appointmentId = obj.getString("appointmentId")
                patientx
            } catch (e: Exception) {
                null
            }
        }

      fun makeFromCSV(lines: String?): ArrayList<Patient> {
          val result: ArrayList<Patient> = ArrayList<Patient>()
          if (lines == null) {
              return result
          }
          val rows: ArrayList<String> = Ocl.parseCSVtable(lines)
          for (item in rows.indices) {
              val row = rows[item]
              if (row == null || row.trim { it <= ' ' }.length == 0) {
                  //trim
              } else {
                  val x: Patient? = parseCSV(row)
                  if (x != null) {
                      result.add(x)
                  }
              }
          }
          return result
      }


        fun parseJSONArray(jarray: JSONArray?): ArrayList<Patient>? {
            if (jarray == null) {
                return null
            }
            val res: ArrayList<Patient> = ArrayList<Patient>()
            val len = jarray.length()
            for (i in 0 until len) {
                try {
                    val x = jarray.getJSONObject(i)
                    if (x != null) {
                        val y: Patient? = parseJSON(x)
                        if (y != null) {
                            res.add(y)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return res
        }


        fun writeJSON(x: Patient): JSONObject? {
            val result = JSONObject()
            try {
                result.put("patientId", x.patientId)
                result.put("name", x.name)
                result.put("appointmentId", x.appointmentId)
            } catch (e: Exception) {
                return null
            }
            return result
        }


        fun parseRaw(obj: Any?): Patient? {
             if (obj == null) {
                 return null
            }
            try {
                val map = obj as HashMap<String, Object>
                val id: String = map["patientId"].toString()
                var patientx: Patient? = Patient.PatientIndex.get(id)
                if (patientx == null) {
                    patientx = Patient.createByPKPatient(id)
                }
                patientx.patientId = map["patientId"].toString()
                patientx.name = map["name"].toString()
                patientx.appointmentId = map["appointmentId"].toString()
                return patientx
            } catch (e: Exception) {
                return null
            }
        }

        fun writeJSONArray(es: ArrayList<Patient>): JSONArray {
            val result = JSONArray()
            for (i in 0 until es.size) {
                val ex: Patient = es[i]
                val jx = writeJSON(ex)
                if (jx == null) {
                    //jx
                } else {
                    try {
                        result.put(jx)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            return result
        }
    }
}
