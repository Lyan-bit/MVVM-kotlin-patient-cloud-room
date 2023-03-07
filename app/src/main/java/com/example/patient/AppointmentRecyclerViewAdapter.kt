package com.example.patient

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class AppointmentRecyclerViewAdapter (items: List<AppointmentEntity>, listener: ListAppointmentFragment.OnListAppointmentFragmentInteractionListener)
    : RecyclerView.Adapter<AppointmentRecyclerViewAdapter.AppointmentViewHolder>() {

    private var mValues: List<AppointmentEntity> = items
    private var mListener: ListAppointmentFragment.OnListAppointmentFragmentInteractionListener = listener
	
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewlistappointment_item, parent, false)
        return AppointmentViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.listAppointmentAppointmentIdView.text = " " + mValues[position].appointmentId + " "
        holder.listAppointmentCodeView.text = " " + mValues[position].code + " "

        holder.mView.setOnClickListener { mListener.onListAppointmentFragmentInteraction(holder.mItem) }
    }
    
    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mView: View
        var listAppointmentAppointmentIdView: TextView
        var listAppointmentCodeView: TextView
        lateinit var mItem: AppointmentEntity

        init {
            mView = view
            listAppointmentAppointmentIdView = view.findViewById(R.id.listAppointmentAppointmentIdView)
            listAppointmentCodeView = view.findViewById(R.id.listAppointmentCodeView)
        }

        override fun toString(): String {
            return super.toString() + " " + mItem
        }

    }
}
