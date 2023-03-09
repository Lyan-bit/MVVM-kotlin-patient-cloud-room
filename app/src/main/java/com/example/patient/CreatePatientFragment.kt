package com.example.patient
	
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.*

class CreatePatientFragment : Fragment(), View.OnClickListener {
	private lateinit var root: View
	private lateinit var myContext: Context
	private lateinit var model: ModelFacade
	
	private lateinit var patientBean: PatientBean
	
	private lateinit var patientIdTextField: EditText
	private var patientIdData = ""
	private lateinit var nameTextField: EditText
	private var nameData = ""
	private lateinit var appointmentIdTextField: EditText
	private var appointmentIdData = ""

    private lateinit var createPatientButton: Button
	private lateinit var cancelPatientButton: Button

		  		
    companion object {
        fun newInstance(c: Context): CreatePatientFragment {
            val fragment = CreatePatientFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.myContext = c
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		root = inflater.inflate(R.layout.createpatient_layout, container, false)
        model = ModelFacade.getInstance(myContext)

				patientIdTextField = root.findViewById(R.id.crudPatientpatientIdField)
		nameTextField = root.findViewById(R.id.crudPatientnameField)
		appointmentIdTextField = root.findViewById(R.id.crudPatientappointmentIdField)
		
		patientBean = PatientBean(myContext)

		createPatientButton = root.findViewById(R.id.crudPatientOK)
		createPatientButton.setOnClickListener(this)

		cancelPatientButton = root.findViewById(R.id.crudPatientCancel)
		cancelPatientButton.setOnClickListener(this)
		
	    return root
	}

	override fun onClick(v: View) {
		val imm = myContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		try {
			imm.hideSoftInputFromWindow(v.windowToken, 0)
		} catch (_e: Exception) {
			_e.printStackTrace()
		}
		when (v.id) {
			R.id.crudPatientOK-> {
				crudPatientOK()
			}
			R.id.crudPatientCancel-> {
				crudPatientCancel()
			}
		}
	}

	private fun crudPatientOK () {
		patientIdData = patientIdTextField.text.toString()
		patientBean.setPatientId(patientIdData)
		nameData = nameTextField.text.toString()
		patientBean.setName(nameData)
		appointmentIdData = appointmentIdTextField.text.toString()
		patientBean.setAppointmentId(appointmentIdData)

			if (patientBean.isCreatePatientError()) {
				Log.w(javaClass.name, patientBean.errors())
				Toast.makeText(myContext, "Errors: " + patientBean.errors(), Toast.LENGTH_LONG).show()
			} else {
				viewLifecycleOwner.lifecycleScope.launchWhenStarted  {	
					patientBean.createPatient()
					Toast.makeText(myContext, "Patient created!", Toast.LENGTH_LONG).show()
					
				}
			}
	}

	private fun crudPatientCancel () {
		patientBean.resetData()
		patientIdTextField.setText("")
		nameTextField.setText("")
		appointmentIdTextField.setText("")
	}
	

		
}
