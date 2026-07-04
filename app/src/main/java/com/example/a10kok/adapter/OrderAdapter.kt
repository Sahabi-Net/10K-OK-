package com.example.a10kok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a10kok.R
import com.example.a10kok.model.Order

class OrderAdapter(
    private val list: List<Order>,
    private val onUpdateStatus: (Order, String) -> Unit
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val tvInvoice: TextView = v.findViewById(R.id.tvInvoice)
        val tvNama: TextView = v.findViewById(R.id.tvNama)
        val tvProduk: TextView = v.findViewById(R.id.tvProduk)
        val tvTotal: TextView = v.findViewById(R.id.tvTotal)
        val tvStatus: TextView = v.findViewById(R.id.tvStatus)
        val tvTanggal: TextView = v.findViewById(R.id.tvTanggal)
        val btnStatus: Button = v.findViewById(R.id.btnStatus)
        val btnCancel: Button = v.findViewById(R.id.btnCancel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val order = list[position]

        holder.tvInvoice.text = "Invoice : ${order.kode_invoice}"
        holder.tvNama.text = "Customer : ${order.nama}"
        holder.tvProduk.text = "Produk : ${order.nama_produk ?: "-"}"
        holder.tvTotal.text = "Total : Rp ${order.total}"
        holder.tvStatus.text = "Status : ${order.status}"
        holder.tvTanggal.text = order.created_at ?: "-"

        when (order.status) {

            "Menunggu" -> {

                holder.btnStatus.visibility = View.VISIBLE
                holder.btnStatus.text = "Proses"

                holder.btnStatus.setOnClickListener {
                    onUpdateStatus(order, "Diproses")
                }

            }

            "Diproses" -> {

                holder.btnStatus.visibility = View.VISIBLE

                if (order.metode_pengambilan == "Delivery") {

                    holder.btnStatus.text = "Kirim"

                    holder.btnStatus.setOnClickListener {
                        onUpdateStatus(order, "Dikirim")
                    }

                } else {

                    holder.btnStatus.text = "Siap Diambil"

                    holder.btnStatus.setOnClickListener {
                        onUpdateStatus(order, "Siap Diambil")
                    }

                }

            }

            "Dikirim",
            "Siap Diambil" -> {

                holder.btnStatus.visibility = View.VISIBLE
                holder.btnStatus.text = "Selesai"

                holder.btnStatus.setOnClickListener {
                    onUpdateStatus(order, "Selesai")
                }

            }

            else -> {

                holder.btnStatus.visibility = View.GONE

            }
        }

        if (order.status == "Selesai" || order.status == "Cancel" || order.status == "Dibatalkan") {
            holder.btnCancel.visibility = View.GONE
        } else {
            holder.btnCancel.visibility = View.VISIBLE
            holder.btnCancel.setOnClickListener {
                onUpdateStatus(order, "Cancel")
            }
        }

    }

}