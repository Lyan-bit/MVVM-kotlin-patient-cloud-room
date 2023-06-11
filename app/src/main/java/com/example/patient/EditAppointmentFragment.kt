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

class EditAppointmentFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
	private lateinit var root: View
	private lateinit var myContext: Context
	private lateinit var model: ModelFacade

	private lateinit var appointmentBean: AppointmentBean

	private lateinit var editAppointmentSpinner: Spinner
	private var allAppointmentappointmentIds: List<String> = ArrayList()
	private lateinit var searchAppointmentButton : Button
	private lateinit var editAppointmentButton : Button
	private lateinit var cancelAppointmentButton : Button

	private lateinit var appointmentIdTextField: EditText
	private var appointmentIdData = ""
	private lateinit var codeTextField: EditText
	private var codeData = ""


	companion object {
		fun newInstance(c: Context): EditAppointmentFragment {
			val fragment = EditAppointmentFragment()
			val args = Bundle()
			fragment.arguments = args
			fragment.myContext = c
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		root = inflater.inflate(R.layout.editappointment_layout, container, false)
		model = ModelFacade.getInstance(myContext)
		appointmentBean = AppointmentBean(myContext)
		editAppointmentSpinner = root.findViewById(R.id.editAppointmentSpinner)

		model.allAppointmentAppointmentIds.observe( viewLifecycleOwner, androidx.lifecycle.Observer { appointmentId ->
			appointmentId.let {
				allAppointmentappointmentIds = appointmentId
				val editAppointmentAdapter =
					ArrayAdapter(myContext, android.R.layout.simple_spinner_item, allAppointmentappointmentIds)
				editAppointmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
				editAppointmentSpinner.adapter = editAppointmentAdapter
				editAppointmentSpinner.onItemSelectedListener = this
			}
		})

		searchAppointmentButton = root.findViewById(R.id.editAppointmentSearch)
		searchAppointmentButton.setOnClickListener(this)
		editAppointmentButton = root.findViewById(R.id.editAppointmentOK)
		editAppointmentButton.setOnClickListener(this)
		cancelAppointmentButton = root.findViewById(R.id.editAppointmentCancel)
		cancelAppointmentButton.setOnClickListener(this)

		appointmentIdTextField = root.findViewById(R.id.editAppointmentappointmentIdField)
		codeTextField = root.findViewById(R.id.editAppointmentcodeField)

		return root
	}

	override fun onItemSelected(parent: AdapterView<*>, v: View?, position: Int, id: Long) {
		if (parent === editAppointmentSpinner) {
			appointmentIdTextField.setText(allAppointmentappointmentIds[position])
		}
	}

	override fun onNothingSelected(parent: AdapterView<*>?) {
		//onNothingSelected
	}

	override fun onClick(v: View?) {
		val imm = myContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		try {
			imm.hideSoftInputFromWindow(v?.windowToken, 0)
		} catch (e: Exception) {
			e.message
		}

		when (v?.id) {
			R.id.editAppointmentSearch-> {
				editSearch()
			}
			R.id.editAppointmentOK-> {
				editOK()
			}
			R.id.editAppointmentCancel-> {
				editCancel()
			}
		}
	}

	private fun editSearch() {
		appointmentIdData = appointmentIdTextField.text.toString()
		appointmentBean.setAppointmentId(appointmentIdData)

		if (appointmentBean.isSearchAppointmentIdError(allAppointmentappointmentIds)) {
			Log.w(javaClass.name, appointmentBean.errors())
			Toast.makeText(myContext, "Errors: " + appointmentBean.errors(), Toast.LENGTH_LONG).show()
		} else {
			viewLifecycleOwner.lifecycleScope.launchWhenStarted  {
				val selectedItem = model.getAppointmentByPK2(appointmentIdData)
				appointmentIdTextField.setText(selectedItem?.appointmentId.toString())
				codeTextField.setText(selectedItem?.code.toString())

				Toast.makeText(myContext, "search Appointment done!", Toast.LENGTH_LONG).show()

			}
		}
	}

	private fun editOK() {
		appointmentIdData = appointmentIdTextField.text.toString()
		appointmentBean.setAppointmentId(appointmentIdData)
		codeData = codeTextField.text.toString()
		appointmentBean.setCode(codeData)

		if (appointmentBean.isEditAppointmentError(allAppointmentappointmentIds)) {
			Log.w(javaClass.name, appointmentBean.errors())
			Toast.makeText(myContext, "Errors: " + appointmentBean.errors(), Toast.LENGTH_LONG).show()
		} else {
			viewLifecycleOwner.lifecycleScope.launchWhenStarted  {
				appointmentBean.editAppointment()
				Toast.makeText(myContext, "Appointment editd!", Toast.LENGTH_LONG).show()

			}
		}
	}

	private fun editCancel() {
		appointmentBean.resetData()
		appointmentIdTextField.setText("")
		codeTextField.setText("")
	}


}
