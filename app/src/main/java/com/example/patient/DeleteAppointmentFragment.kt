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

class DeleteAppointmentFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
	private lateinit var root: View
	private lateinit var myContext: Context
	private lateinit var model: ModelFacade

	private lateinit var appointmentBean: AppointmentBean

	private lateinit var deleteAppointmentSpinner: Spinner
	private var allAppointmentappointmentIds: List<String> = ArrayList()
	private lateinit var appointmentIdTextField: EditText
	private var appointmentIdData = ""
	private lateinit var deleteAppointmentButton : Button
	private lateinit var cancelAppointmentButton : Button

	companion object {
		fun newInstance(c: Context): DeleteAppointmentFragment {
			val fragment = DeleteAppointmentFragment()
			val args = Bundle()
			fragment.arguments = args
			fragment.myContext = c
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		root = inflater.inflate(R.layout.deleteappointment_layout, container, false)
		return root
	}

	override fun onResume() {
		super.onResume()
		model = ModelFacade.getInstance(myContext)
		appointmentBean = AppointmentBean(myContext)
		appointmentIdTextField = root.findViewById(R.id.deleteAppointmentappointmentIdField)
		deleteAppointmentSpinner = root.findViewById(R.id.deleteAppointmentSpinner)

		model.allAppointmentAppointmentIds.observe( viewLifecycleOwner, androidx.lifecycle.Observer { Appointmentid ->
			Appointmentid.let {
				allAppointmentappointmentIds = Appointmentid
				val deleteAppointmentAdapter =
					ArrayAdapter(myContext, android.R.layout.simple_spinner_item, allAppointmentappointmentIds)
				deleteAppointmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
				deleteAppointmentSpinner.adapter = deleteAppointmentAdapter
				deleteAppointmentSpinner.onItemSelectedListener = this
			}
		})


		deleteAppointmentButton = root.findViewById(R.id.deleteAppointmentOK)
		deleteAppointmentButton.setOnClickListener(this)
		cancelAppointmentButton = root.findViewById(R.id.deleteAppointmentCancel)
		cancelAppointmentButton.setOnClickListener(this)
	}

	override fun onItemSelected(_parent: AdapterView<*>, _v: View?, _position: Int, _id: Long) {
		if (_parent === deleteAppointmentSpinner) {
			appointmentIdTextField.setText(allAppointmentappointmentIds[_position])
		}
	}

	override fun onNothingSelected(_parent: AdapterView<*>?) {}

	override fun onClick(v: View) {
		val imm = myContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		try {
			imm.hideSoftInputFromWindow(v.windowToken, 0)
		} catch (_e: Exception) {
		}

		when (v.id) {
			R.id.deleteAppointmentOK-> {
				deleteAppointmentOK(v)
			}
			R.id.deleteAppointmentCancel-> {
				deleteAppointmentCancel(v)
			}
		}
	}

	private fun deleteAppointmentOK(_v: View?) {
		appointmentIdData = appointmentIdTextField.text.toString()
		appointmentBean.setAppointmentId(appointmentIdData)
		if (appointmentBean.isDeleteAppointmentError(allAppointmentappointmentIds)) {
			Log.w(javaClass.name, appointmentBean.errors())
			Toast.makeText(myContext, "Errors: " + appointmentBean.errors(), Toast.LENGTH_LONG).show()
		} else {
			viewLifecycleOwner.lifecycleScope.launchWhenStarted  {
				appointmentBean.deleteAppointment()
				Toast.makeText(myContext, "Appointment deleted!", Toast.LENGTH_LONG).show()

			}
		}
	}

	private fun deleteAppointmentCancel(_v: View?) {
		appointmentBean.resetData()
		appointmentIdTextField.setText("")
	}

}
