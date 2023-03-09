package com.example.patient

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import java.lang.Exception
import java.util.ArrayList

class DeletePatientFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
	private lateinit var root: View
	private lateinit var myContext: Context
	private lateinit var model: ModelFacade
	
	private lateinit var patientBean: PatientBean
	
	private lateinit var deletePatientSpinner: Spinner
	private var allPatientpatientIds: List<String> = ArrayList()
	private lateinit var patientIdTextField: EditText
	private var patientIdData = ""
	private lateinit var deletePatientButton : Button
	private lateinit var cancelPatientButton : Button	
	
    companion object {
        fun newInstance(c: Context): DeletePatientFragment {
            val fragment = DeletePatientFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.myContext = c
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		root = inflater.inflate(R.layout.deletepatient_layout, container, false)
	    return root
	}
	
	override fun onResume() {
		super.onResume()
		model = ModelFacade.getInstance(myContext)
		patientBean = PatientBean(myContext)
		patientIdTextField = root.findViewById(R.id.crudPatientpatientIdField)	    
		deletePatientSpinner = root.findViewById(R.id.crudPatientSpinner)

		Log.i("model",model.listPatient().toString())
		Log.i("model",model.allPatientPatientIds().toString())

		allPatientpatientIds = model.allPatientPatientIds()
		val deletePatientAdapter =
		ArrayAdapter(myContext, android.R.layout.simple_spinner_item, allPatientpatientIds)
		deletePatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		deletePatientSpinner.adapter = deletePatientAdapter
		deletePatientSpinner.onItemSelectedListener = this

		deletePatientButton = root.findViewById(R.id.crudPatientOK)
		deletePatientButton.setOnClickListener(this)
		cancelPatientButton = root.findViewById(R.id.crudPatientCancel)
		cancelPatientButton.setOnClickListener(this)
	}
	
	override fun onItemSelected(_parent: AdapterView<*>, _v: View?, _position: Int, _id: Long) {
		if (_parent === deletePatientSpinner) {
			patientIdTextField.setText(allPatientpatientIds[_position])
		}
	}

	override fun onNothingSelected(_parent: AdapterView<*>?) {
		//onNothingSelected
	}

	override fun onClick(v: View) {
	val imm = myContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	try {
		imm.hideSoftInputFromWindow(v.windowToken, 0)
		} catch (_e: Exception) {
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

	private fun crudPatientOK() {
		patientIdData = patientIdTextField.text.toString()
		patientBean.setPatientId(patientIdData)
		if (patientBean.isDeletePatientError(allPatientpatientIds)) {
			Log.w(javaClass.name, patientBean.errors())
			Toast.makeText(myContext, "Errors: " + patientBean.errors(), Toast.LENGTH_LONG).show()
		} else {
			viewLifecycleOwner.lifecycleScope.launchWhenStarted  {	
				patientBean.deletePatient()
				Toast.makeText(myContext, "Patient deleted!", Toast.LENGTH_LONG).show()
				
			}
		}
	}

	private fun crudPatientCancel() {
		patientBean.resetData()
		patientIdTextField.setText("")
	}
		 
}
