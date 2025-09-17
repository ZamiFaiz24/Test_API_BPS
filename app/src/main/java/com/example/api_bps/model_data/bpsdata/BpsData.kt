package com.example.api_bps.model_data.bpsdata

data class BpsData(
    val tahun: Int,
    val kelompokUmur: String?,
    val jenisKelamin: String?,
    val nilai: Int,
    val satuan: String
)
