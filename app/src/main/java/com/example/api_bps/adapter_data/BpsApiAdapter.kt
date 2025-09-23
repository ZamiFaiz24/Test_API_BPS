package com.example.api_bps.adapter_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.api_bps.R
import com.example.api_bps.model_data.bpsdata.DataTableItem
import java.text.NumberFormat
import java.util.Locale

class BpsApiAdapter(private var list: List<DataTableItem>) :
    RecyclerView.Adapter<BpsApiAdapter.DataViewHolder>() {

    inner class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvKelompokUmur: TextView = view.findViewById(R.id.tvKelompokUmur)
        val tvLakiLaki: TextView = view.findViewById(R.id.tvLakiLaki)
        val tvPerempuan: TextView = view.findViewById(R.id.tvPerempuan)
        val tvTotal: TextView = view.findViewById(R.id.tvTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_api_tabel, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = list[position]
        val numberFormat = NumberFormat.getNumberInstance(Locale.Builder().setLanguage("id").setRegion("ID").build())

        holder.tvKelompokUmur.text = item.kelompokUmur
        holder.tvLakiLaki.text = item.lakiLaki?.let { numberFormat.format(it) } ?: "N/A"
        holder.tvPerempuan.text = item.perempuan?.let { numberFormat.format(it) } ?: "N/A"
        holder.tvTotal.text = item.total?.let { numberFormat.format(it) } ?: "N/A"
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<DataTableItem>) {
        list = newList
        notifyDataSetChanged()
    }
}