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

class CreateAppointmentFragment : Fragment(), View.OnClickListener {
	private lateinit var root: View
	private lateinit var myContext: Context
	private lateinit var model: ModelFacade
	
	private lateinit var appointmentBean: AppointmentBean
	
	private lateinit var appointmentIdTextField: EditText
	private var appointmentIdData = ""
	private lateinit var codeTextField: EditText
	private var codeData = ""

    private lateinit var createAppointmentButton: Button
	private lateinit var cancelAppointmentButton: Button

		  		
    companion object {
        fun newInstance(c: Context): CreateAppointmentFragment {
            val fragment = CreateAppointmentFragment()
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
		root = inflater.inflate(R.layout.createappointment_layout, container, false)
        model = ModelFacade.getInstance(myContext)

				appointmentIdTextField = root.findViewById(R.id.createAppointmentappointmentIdField)
		codeTextField = root.findViewById(R.id.createAppointmentcodeField)
		
		appointmentBean = AppointmentBean(myContext)

		createAppointmentButton = root.findViewById(R.id.createAppointmentOK)
		createAppointmentButton.setOnClickListener(this)

		cancelAppointmentButton = root.findViewById(R.id.createAppointmentCancel)
		cancelAppointmentButton.setOnClickListener(this)
		
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
			R.id.createAppointmentOK-> {
				createAppointmentOK()
			}
			R.id.createAppointmentCancel-> {
				createAppointmentCancel()
			}
		}
	}

	private fun createAppointmentOK () {
		appointmentIdData = appointmentIdTextField.text.toString()
		appointmentBean.setAppointmentId(appointmentIdData)
		codeData = codeTextField.text.toString()
		appointmentBean.setCode(codeData)

			if (appointmentBean.isCreateAppointmentError()) {
				Log.w(javaClass.name, appointmentBean.errors())
				Toast.makeText(myContext, "Errors: " + appointmentBean.errors(), Toast.LENGTH_LONG).show()
			} else {
				viewLifecycleOwner.lifecycleScope.launchWhenStarted  {	
					appointmentBean.createAppointment()
					Toast.makeText(myContext, "Appointment created!", Toast.LENGTH_LONG).show()
					
				}
			}
	}

	private fun createAppointmentCancel () {
		appointmentBean.resetData()
		appointmentIdTextField.setText("")
		codeTextField.setText("")
	}
	

		
}
