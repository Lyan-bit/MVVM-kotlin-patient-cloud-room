package com.example.patient

import android.content.Context
import java.util.regex.Pattern


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

		if (appointmentId != "") {}
		else {
			errors.add("appointmentId cannot be empty")
		}
		if (code != "") {}
		else {
			errors.add("code cannot be empty")
		}

		return errors.size > 0
	}

	suspend fun createAppointment() {
		model.createAppointment(AppointmentEntity(appointmentId, code))
		resetData()
	}

	fun isListAppointmentError(): Boolean {
		errors.clear()
		return errors.size > 0
	}


	fun isEditAppointmentError(allAppointmentappointmentIds: List<String>): Boolean {
		errors.clear()

		if (!allAppointmentappointmentIds.contains(appointmentId)) {
			errors.add("appointmentId" + checkParameter)
		}

		if (appointmentId != "") {}
		else {
			errors.add("appointmentId cannot be empty")
		}
		if (code != "") {}
		else {
			errors.add("code cannot be empty")
		}

		return errors.size > 0
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
		return errors.size > 0
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
		return errors.size > 0
	}

	fun errors(): String {
		return errors.toString()
	}

	fun isAddPatientattendsAppointmentError(): Boolean {
		errors.clear()
		return errors.size > 0
	}

	suspend fun addPatientattendsAppointment() {
		model.addPatientattendsAppointment(appointmentId, patientId)
		resetData()
	}

	fun isRemovePatientattendsAppointmentError(): Boolean {
		errors.clear()
		return errors.size > 0
	}

	suspend fun removePatientattendsAppointment() {
		model.removePatientattendsAppointment(appointmentId, patientId)
		resetData()
	}



}
