package com.example.a10kok.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a10kok.R
import com.example.a10kok.model.Product

class ProductAdapter(
    private val context: Context,
    private val list: List<Product>,
    private val layoutRes: Int = R.layout.item_product_mitra,
    private val onEdit: ((Product) -> Unit)? = null,
    private val onDelete: ((Product) -> Unit)? = null,
    private val onClick: ((Product) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)

        val btnEdit: Button? = itemView.findViewById(R.id.btnEdit)
        val btnHapus: Button? = itemView.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]

        holder.tvNama.text = product.namaProduk
        holder.tvHarga.text = "Rp ${product.harga}"
        holder.tvKategori.text = "${product.namaCategory} • ⭐ ${product.rating}"

        Glide.with(context)
            .load("http://10.0.2.2/10kok_api/uploads/products/${product.gambar}")
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.imgProduct)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }

        if (onEdit != null && holder.btnEdit != null) {
            holder.btnEdit.visibility = View.VISIBLE
            holder.btnEdit.setOnClickListener { onEdit.invoke(product) }
        } else {
            holder.btnEdit?.visibility = View.GONE
        }

        if (onDelete != null && holder.btnHapus != null) {
            holder.btnHapus.visibility = View.VISIBLE
            holder.btnHapus.setOnClickListener { onDelete.invoke(product) }
        } else {
            holder.btnHapus?.visibility = View.GONE
        }
    }
}
