package com.example.api_bps.adapter_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.api_bps.R
import com.example.api_bps.model_data.bpsdata.BpsData

class BpsAdapter(private var dataList: List<BpsData>) :
    RecyclerView.Adapter<BpsAdapter.BpsViewHolder>() {

    class BpsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemTahun: TextView = itemView.findViewById(R.id.tvItemTahun)
        val tvItemKelompokUmur: TextView = itemView.findViewById(R.id.tvItemKelompokUmur)
        val tvItemJenisKelamin: TextView = itemView.findViewById(R.id.tvItemJenisKelamin)
        val tvItemNilai: TextView = itemView.findViewById(R.id.tvItemNilai)
        val tvItemSatuan: TextView = itemView.findViewById(R.id.tvItemSatuan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BpsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tabel_data, parent, false)
        return BpsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BpsViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.tvItemTahun.text = currentItem.tahun.toString()
        holder.tvItemKelompokUmur.text = currentItem.kelompokUmur ?: "-"
        holder.tvItemJenisKelamin.text = currentItem.jenisKelamin ?: "-"
        holder.tvItemNilai.text = currentItem.nilai.toString()
        holder.tvItemSatuan.text = currentItem.satuan
    }

    override fun getItemCount() = dataList.size

    fun updateData(newDataList: List<BpsData>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}
