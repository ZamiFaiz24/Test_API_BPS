package com.example.api_bps.model_data.bpsdata

// Model data baru untuk merepresentasikan satu baris di tabel UI
data class DataTableItem(
    val kelompokUmur: String,
    val lakiLaki: Double?,
    val perempuan: Double?,
    val total: Double?
)