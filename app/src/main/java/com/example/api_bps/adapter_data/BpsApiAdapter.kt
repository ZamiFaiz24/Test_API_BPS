package com.example.api_bps.adapter_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.api_bps.R
import com.example.api_bps.model_data.bpsdata.BpsData

class BpsApiAdapter(private var list: List<BpsData>) :
    RecyclerView.Adapter<BpsApiAdapter.BpsViewHolder>() {

    inner class BpsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTahun: TextView = view.findViewById(R.id.tvTahun)
        val tvKelompokUmur: TextView = view.findViewById(R.id.tvKelompokUmur)
        val tvJenisKelamin: TextView = view.findViewById(R.id.tvJenisKelamin)
        val tvNilai: TextView = view.findViewById(R.id.tvNilai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BpsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_api_tabel, parent, false)
        return BpsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BpsViewHolder, position: Int) {
        val item = list[position]
        holder.tvTahun.text = "Tahun: ${item.tahun}"
        holder.tvKelompokUmur.text = "Kelompok Umur: ${item.kelompokUmur}"
        holder.tvJenisKelamin.text = "Jenis Kelamin: ${item.jenisKelamin}"
        holder.tvNilai.text = "Nilai: ${item.nilai}" // atau item.total jika pakai total
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<BpsData>) {
        list = newList
        notifyDataSetChanged()
    }
}
