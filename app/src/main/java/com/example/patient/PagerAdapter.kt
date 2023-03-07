package com.example.patient

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAB_TITLES = arrayOf("+Patient", "ListPatient", "EditPatient", "DeletePatient", "+Appointment", "ListAppointment", "EditAppointment", "DeleteAppointment", "AddPatientattendsAppointment", "RemovePatientattendsAppointment")
    }

    override fun getItem(position: Int): Fragment {
        // instantiate a fragment for the page.
        if (position == 0) {
            return CreatePatientFragment.newInstance(mContext)    } 
        else if (position == 1) {
            return ListPatientFragment.newInstance(mContext)    } 
        else if (position == 2) {
            return EditPatientFragment.newInstance(mContext)    } 
        else if (position == 3) {
            return DeletePatientFragment.newInstance(mContext)    } 
        else if (position == 4) {
            return CreateAppointmentFragment.newInstance(mContext)    } 
        else if (position == 5) {
            return ListAppointmentFragment.newInstance(mContext)    } 
        else if (position == 6) {
            return EditAppointmentFragment.newInstance(mContext)    } 
        else if (position == 7) {
            return DeleteAppointmentFragment.newInstance(mContext)    } 
        else if (position == 8) {
            return AddPatientattendsAppointmentFragment.newInstance(mContext)    } 
        else if (position == 9) {
            return RemovePatientattendsAppointmentFragment.newInstance(mContext)    } 
        return CreatePatientFragment.newInstance(mContext) 
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 10
    }
}
