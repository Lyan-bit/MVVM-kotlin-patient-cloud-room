package com.example.patient

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

class RemovePatientattendsAppointmentFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var root: View
    private lateinit var myContext: Context
    private lateinit var model: ModelFacade

    private lateinit var appointmentBean: AppointmentBean

    private lateinit var patientIdSpinner: Spinner
    private lateinit var appointmentIdSpinner: Spinner

    private var allPatientpatientId: List<String> = ArrayList()
    private var allAppointmentappointmentId: List<String> = ArrayList()

    private lateinit var patientIdTextField: EditText
    private var patientIdData = ""
    private lateinit var appointmentIdTextField: EditText
    private var appointmentIdData = ""
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    companion object {
        fun newInstance(c: Context):  RemovePatientattendsAppointmentFragment {
            val fragment =  RemovePatientattendsAppointmentFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.myContext = c
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        root = inflater.inflate(R.layout.removepatientattendsappointment_layout, container, false)
        super.onViewCreated(root, savedInstanceState)
        return root
    }

    override fun onResume() {
        super.onResume()

        model = ModelFacade.getInstance(myContext)

        Log.i("model",model.listPatient().toString())
        Log.i("model",model.allPatientPatientIds().toString())

        allPatientpatientId = model.allPatientPatientIds()
        patientIdTextField = root.findViewById<View>(R.id.removePatientattendsAppointmentpatientIdField) as EditText
        patientIdSpinner = root.findViewById<View>(R.id.removePatientattendsAppointmentpatientIdSpinner) as Spinner
        val patientIdAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(myContext, android.R.layout.simple_spinner_item, allPatientpatientId)
        patientIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        patientIdSpinner.setAdapter(patientIdAdapter)
        patientIdSpinner.setOnItemSelectedListener(this)
        appointmentIdTextField =
            root.findViewById<View>(R.id.removePatientattendsAppointmentappointmentIdField) as EditText
        appointmentIdSpinner =
            root.findViewById<View>(R.id.removePatientattendsAppointmentappointmentIdSpinner) as Spinner

        model.allAppointmentAppointmentIds.observe(viewLifecycleOwner, androidx.lifecycle.Observer { allAppointmentappointmentIds ->
            allAppointmentappointmentIds.let {
                allAppointmentappointmentId = allAppointmentappointmentIds
                val appointmentIdAdapter =
                    ArrayAdapter(myContext, android.R.layout.simple_spinner_item, allAppointmentappointmentId)
                appointmentIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                appointmentIdSpinner.adapter = appointmentIdAdapter
                appointmentIdSpinner.onItemSelectedListener = this
            }
        })
        appointmentBean = AppointmentBean(myContext)

        okButton = root.findViewById(R.id.removePatientattendsAppointmentOK)
        okButton.setOnClickListener(this)
        cancelButton = root.findViewById(R.id.removePatientattendsAppointmentCancel)
        cancelButton.setOnClickListener(this)
    }

    override fun onItemSelected(_parent: AdapterView<*>, _v: View?, _position: Int, _id: Long) {
        if (_parent === patientIdSpinner) {
            patientIdTextField.setText(allPatientpatientId.get(_position))
        }
        if (_parent ==appointmentIdSpinner) {
            appointmentIdTextField.setText(allAppointmentappointmentId.get(_position))
        }
    }

    override fun onNothingSelected(_parent: AdapterView<*>?) {
    //onNothingSelected
    }

    override fun onClick(v: View) {
        val imm = myContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        when (v.id) {
            R.id.removePatientattendsAppointmentOK -> {
                removePatientattendsAppointmentOK()
            }
            R.id.removePatientattendsAppointmentCancel -> {
                removePatientattendsAppointmentCancel()
            }
        }
    }

    private fun removePatientattendsAppointmentOK() {
        patientIdData = patientIdTextField.getText().toString() + ""
        appointmentBean.setPatientId(patientIdData)
        appointmentIdData = appointmentIdTextField.getText().toString() + ""
        appointmentBean.setAppointmentId(appointmentIdData)
        if (appointmentBean.isRemovePatientattendsAppointmentError()) {
            Log.w(javaClass.name, appointmentBean.errors())
            Toast.makeText(myContext, "Errors: " + appointmentBean.errors(), Toast.LENGTH_LONG).show()
        } else {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                appointmentBean.removePatientattendsAppointment()
                Toast.makeText(myContext, "removed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun removePatientattendsAppointmentCancel() {
        appointmentBean.resetData()
        patientIdTextField.setText("")
        appointmentIdTextField.setText("")
    }
}
