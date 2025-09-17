package com.example.api_bps.model_data.bpsdata
data class Dimension(
    val key: String,
    val value: String
)

data class BpsBeData(
    val id: Int,
    val dataset_id: Int,
    val kode_data: String?,
    val tahun: Int,
    val bulan: String?,
    val nilai: Int,
    val satuan: String,
    val dimensions: List<DimensionItem>
)
