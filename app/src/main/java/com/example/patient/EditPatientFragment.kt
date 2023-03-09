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

	class EditPatientFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
		private lateinit var root: View
		private lateinit var myContext: Context
		private lateinit var model: ModelFacade
		
		private lateinit var patientBean: PatientBean
		
		private lateinit var editPatientSpinner: Spinner
		private var allPatientpatientIds: List<String> = ArrayList()
		private lateinit var searchPatientButton : Button
		private lateinit var editPatientButton : Button
		private lateinit var cancelPatientButton : Button	
		
		  private lateinit var patientIdTextField: EditText
		private var patientIdData = ""
	  private lateinit var nameTextField: EditText
		private var nameData = ""
	  private lateinit var appointmentIdTextField: EditText
		private var appointmentIdData = ""
	
		
	    companion object {
	        fun newInstance(c: Context): EditPatientFragment {
	            val fragment = EditPatientFragment()
	            val args = Bundle()
	            fragment.arguments = args
	            fragment.myContext = c
	            return fragment
	        }
	    }
	
	    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
			root = inflater.inflate(R.layout.editpatient_layout, container, false)
			model = ModelFacade.getInstance(myContext)
				patientBean = PatientBean(myContext)
			editPatientSpinner = root.findViewById(R.id.crudPatientSpinner)
	
			Log.i("model",model.listPatient().toString())
			Log.i("model",model.allPatientPatientIds().toString())
	
			allPatientpatientIds = model.allPatientPatientIds()
			val editPatientAdapter =
			ArrayAdapter(myContext, android.R.layout.simple_spinner_item, allPatientpatientIds)
			editPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
			editPatientSpinner.adapter = editPatientAdapter
			editPatientSpinner.onItemSelectedListener = this
				
					searchPatientButton = root.findViewById(R.id.crudPatientSearch)
			searchPatientButton.setOnClickListener(this)
			editPatientButton = root.findViewById(R.id.crudPatientOK)
			editPatientButton.setOnClickListener(this)
			cancelPatientButton = root.findViewById(R.id.crudPatientCancel)
			cancelPatientButton.setOnClickListener(this)
			
			patientIdTextField = root.findViewById(R.id.crudPatientpatientIdField)
		nameTextField = root.findViewById(R.id.crudPatientnameField)
		appointmentIdTextField = root.findViewById(R.id.crudPatientappointmentIdField)

				return root
		}
		
		override fun onItemSelected(_parent: AdapterView<*>, _v: View?, _position: Int, _id: Long) {
			if (_parent === editPatientSpinner) {
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
			R.id.crudPatientSearch-> {
				crudPatientSearch()
			}
			R.id.crudPatientOK-> {
				crudPatientOK()
			}
			R.id.crudPatientCancel-> {
				crudPatientCancel()
			}
			  }
	    }
	    
		private fun crudPatientSearch() {
			patientIdData = patientIdTextField.text.toString()
			patientBean.setPatientId(patientIdData)
			
			if (patientBean.isSearchPatientIdError(allPatientpatientIds)) {
				Log.w(javaClass.name, patientBean.errors())
				Toast.makeText(myContext, "Errors: " + patientBean.errors(), Toast.LENGTH_LONG).show()
			} else {
				viewLifecycleOwner.lifecycleScope.launchWhenStarted  {	
						val selectedItem = model.searchPatientById(patientIdData)
						patientIdTextField.setText(selectedItem.getPatientId().toString())
				nameTextField.setText(selectedItem.getName().toString())
				appointmentIdTextField.setText(selectedItem.getAppointmentId().toString())
		
					Toast.makeText(myContext, "search Patient done!", Toast.LENGTH_LONG).show()
					
				}
			}
		}

		private fun crudPatientOK() {
			patientIdData = patientIdTextField.text.toString()
				patientBean.setPatientId(patientIdData)
		nameData = nameTextField.text.toString()
				patientBean.setName(nameData)
		appointmentIdData = appointmentIdTextField.text.toString()
				patientBean.setAppointmentId(appointmentIdData)
		
			if (patientBean.isEditPatientError(allPatientpatientIds)) {
				Log.w(javaClass.name, patientBean.errors())
				Toast.makeText(myContext, "Errors: " + patientBean.errors(), Toast.LENGTH_LONG).show()
			} else {
				viewLifecycleOwner.lifecycleScope.launchWhenStarted  {	
					patientBean.editPatient()
					Toast.makeText(myContext, "Patient editd!", Toast.LENGTH_LONG).show()
					
				}
			}
			}

		private fun crudPatientCancel() {
			patientBean.resetData()
				patientIdTextField.setText("")
		nameTextField.setText("")
		appointmentIdTextField.setText("")
		}
		
				 
	}	
