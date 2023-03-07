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
            val _x: Patient = Patient.Patient_index.get(id) ?: return false
            return true
        }

        fun getCachedInstance(id: String): Patient? {
            return Patient.Patient_index.get(id)
        }

      fun parseCSV(_line: String?): Patient? {
          if (_line == null) {
              return null
          }
          val _line1vals: ArrayList<String> = Ocl.tokeniseCSV(_line)
          var patientx: Patient? = Patient.Patient_index.get(_line1vals[0])
          if (patientx == null) {
              patientx = Patient.createByPKPatient(_line1vals[0])
          }
          patientx.patientId = _line1vals[0].toString()
          patientx.name = _line1vals[1].toString()
          patientx.appointmentId = _line1vals[2].toString()
          return patientx
      }


        fun parseJSON(obj: JSONObject?): Patient? {
            return if (obj == null) {
                null
            } else try {
                val id = obj.getString("patientId")
                var _patientx: Patient? = Patient.Patient_index.get(id)
                if (_patientx == null) {
                    _patientx = Patient.createByPKPatient(id)
                }
                _patientx.patientId = obj.getString("patientId")
                _patientx.name = obj.getString("name")
                _patientx.appointmentId = obj.getString("appointmentId")
                _patientx
            } catch (_e: Exception) {
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
              } else {
                  val _x: Patient? = parseCSV(row)
                  if (_x != null) {
                      result.add(_x)
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
                    val _x = jarray.getJSONObject(i)
                    if (_x != null) {
                        val _y: Patient? = parseJSON(_x)
                        if (_y != null) {
                            res.add(_y)
                        }
                    }
                } catch (_e: Exception) {
                }
            }
            return res
        }


        fun writeJSON(_x: Patient): JSONObject? {
            val result = JSONObject()
            try {
                result.put("patientId", _x.patientId)
                result.put("name", _x.name)
                result.put("appointmentId", _x.appointmentId)
            } catch (_e: Exception) {
                return null
            }
            return result
        }


        fun parseRaw(obj: Any?): Patient? {
             if (obj == null) {
                 return null
            }
            try {
                val _map = obj as HashMap<String, Object>
                val id: String = _map["patientId"].toString()
                var _patientx: Patient? = Patient.Patient_index.get(id)
                if (_patientx == null) {
                    _patientx = Patient.createByPKPatient(id)
                }
                _patientx.patientId = _map["patientId"].toString()
                _patientx.name = _map["name"].toString()
                _patientx.appointmentId = _map["appointmentId"].toString()
                return _patientx
            } catch (_e: Exception) {
                return null
            }
        }

        fun writeJSONArray(es: ArrayList<Patient>): JSONArray {
            val result = JSONArray()
            for (_i in 0 until es.size) {
                val _ex: Patient = es[_i]
                val _jx = writeJSON(_ex)
                if (_jx == null) {
                } else {
                    try {
                        result.put(_jx)
                    } catch (_ee: Exception) {
                    }
                }
            }
            return result
        }
    }
}
