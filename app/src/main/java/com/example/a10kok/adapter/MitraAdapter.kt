package com.example.a10kok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a10kok.R
import com.example.a10kok.model.Mitra

class MitraAdapter(

    private val list: List<Mitra>,
    private val onStatus:(Mitra,String)->Unit

):RecyclerView.Adapter<MitraAdapter.ViewHolder>(){

    inner class ViewHolder(v:View):RecyclerView.ViewHolder(v){

        val nama=v.findViewById<TextView>(R.id.tvNama)
        val email=v.findViewById<TextView>(R.id.tvEmail)
        val status=v.findViewById<TextView>(R.id.tvStatus)

        val aktif=v.findViewById<Button>(R.id.btnAktif)
        val nonaktif=v.findViewById<Button>(R.id.btnNonaktif)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mitra,parent,false)

        return ViewHolder(v)

    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val m=list[position]

        holder.nama.text=m.nama
        holder.email.text=m.email
        holder.status.text="Status : ${m.status}"

        if (m.status == "Aktif") {
            holder.status.setTextColor(android.graphics.Color.parseColor("#155724"))
            holder.status.setBackgroundColor(android.graphics.Color.parseColor("#D4EDDA"))
        } else if (m.status == "Nonaktif") {
            holder.status.setTextColor(android.graphics.Color.parseColor("#721C24"))
            holder.status.setBackgroundColor(android.graphics.Color.parseColor("#F8D7DA"))
        } else {
            holder.status.setTextColor(android.graphics.Color.parseColor("#1E293B"))
            holder.status.setBackgroundColor(android.graphics.Color.parseColor("#EDF2F7"))
        }

        holder.aktif.setOnClickListener{
            onStatus(m,"Aktif")
        }

        holder.nonaktif.setOnClickListener{
            onStatus(m,"Nonaktif")
        }

    }

}