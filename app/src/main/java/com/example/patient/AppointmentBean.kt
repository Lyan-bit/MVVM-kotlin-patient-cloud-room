package com.example.patient

import android.content.Context

class AppointmentBean(_c: Context) {

	private var model: ModelFacade = ModelFacade.getInstance(_c)

	private var appointmentId = ""
	private var code = ""
	private var patientId = ""
	private var errors = ArrayList<String>()
	private var checkParameter = "is not exist"

	fun setAppointmentId(appointmentIdx: String) {
		appointmentId = appointmentIdx
	}
	fun setCode(codex: String) {
		code = codex
	}
	fun setPatientId(patientIdx : String) {
		patientId = patientIdx
	}
	fun resetData() {
		appointmentId = ""
		code = ""
	}
	fun isCreateAppointmentError(): Boolean {

		errors.clear()

		if (appointmentId != "") {
		//ok
		}
		else {
			errors.add("appointmentId cannot be empty")
		}
		if (code != "") {
		//ok
		}
		else {
			errors.add("code cannot be empty")
		}

		return errors.isNotEmpty()
	}

	suspend fun createAppointment() {
		model.createAppointment(AppointmentEntity(appointmentId, code))
		resetData()
	}

	fun isListAppointmentError(): Boolean {
		errors.clear()
		//isListAppointmentError
		return errors.isNotEmpty()
	}


	fun isEditAppointmentError(allAppointmentappointmentIds: List<String>): Boolean {
		errors.clear()

		if (!allAppointmentappointmentIds.contains(appointmentId)) {
			errors.add("appointmentId" + checkParameter)
		}

		if (appointmentId != "") {
		//ok
		}
		else {
			errors.add("appointmentId cannot be empty")
		}
		if (code != "") {
		//ok
		}
		else {
			errors.add("code cannot be empty")
		}

		return errors.isNotEmpty()
	}

	suspend fun editAppointment() {
		model.editAppointment(AppointmentEntity(appointmentId, code))
		resetData()
	}

	fun isDeleteAppointmentError(allAppointmentappointmentIds: List<String>): Boolean {
		errors.clear()
		if (!allAppointmentappointmentIds.contains(appointmentId)) {
			errors.add("appointmentId" + checkParameter)
		}
		return errors.isNotEmpty()
	}

	suspend fun deleteAppointment() {
		model.deleteAppointment(appointmentId)
		resetData()
	}


	fun isSearchAppointmentIdError(allAppointmentIds: List<String>): Boolean {
		errors.clear()
		if (!allAppointmentIds.contains(appointmentId)) {
			errors.add("appointmentId" + checkParameter)
		}
		return errors.isNotEmpty()
	}

	fun errors(): String {
		return errors.toString()
	}

	fun isAddPatientattendsAppointmentError(): Boolean {
		errors.clear()
		if (appointmentId != "") 
		else 
		errors.add("appointmentId" + checkParameter)
		return errors.isNotEmpty()
	}

	fun addPatientattendsAppointment() {
		model.addPatientattendsAppointment(appointmentId, patientId)
		resetData()
	}

	fun isRemovePatientattendsAppointmentError(): Boolean {
		errors.clear()
		if (patientId != "") 
		else 
		errors.add(patientId + checkParameter)
		return errors.isNotEmpty()
	}

	fun removePatientattendsAppointment() {
		model.removePatientattendsAppointment(appointmentId, patientId)
		resetData()
	}



}
