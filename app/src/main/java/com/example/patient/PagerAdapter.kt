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
            return when (position) {
                0 -> { 
                    CreatePatientFragment.newInstance(mContext) 
                }            1 -> { 
                    ListPatientFragment.newInstance(mContext) 
                }            2 -> { 
                    EditPatientFragment.newInstance(mContext) 
                }            3 -> { 
                    DeletePatientFragment.newInstance(mContext) 
                }            4 -> { 
                    CreateAppointmentFragment.newInstance(mContext) 
                }            5 -> { 
                    ListAppointmentFragment.newInstance(mContext) 
                }            6 -> { 
                    EditAppointmentFragment.newInstance(mContext) 
                }            7 -> { 
                    DeleteAppointmentFragment.newInstance(mContext) 
                }            8 -> { 
                    AddPatientattendsAppointmentFragment.newInstance(mContext) 
                }            9 -> { 
                    RemovePatientattendsAppointmentFragment.newInstance(mContext) 
                }
                else -> CreatePatientFragment.newInstance(mContext) 
             }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 10
    }
}
