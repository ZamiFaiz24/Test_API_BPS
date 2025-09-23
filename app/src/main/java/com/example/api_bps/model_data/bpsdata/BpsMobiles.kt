// File: BpsModels.kt (gabungkan dalam satu file agar rapi)
package com.example.api_bps.model_data.bpsdata

import com.google.gson.annotations.SerializedName

// Model untuk objek paling luar
data class BpsDatasetResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("dataset_code") val datasetCode: String,
    @SerializedName("dataset_name") val datasetName: String,
    @SerializedName("subject") val subject: String?,
    @SerializedName("source") val source: String,
    @SerializedName("values") val values: List<BpsDatavalue> // Daftar nilai data
)

// Model untuk setiap item di dalam "values"
data class BpsDatavalue(
    @SerializedName("id") val id: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("value") val value: Double,
    @SerializedName("unit") val unit: String?,
    @SerializedName("vervar_label") val vervarLabel: String?,
    @SerializedName("turvar_label") val turvarLabel: String?
)

// Model untuk objek dimensi yang bersarang
data class BpsDatadimension(
    @SerializedName("id") val id: Int,
    @SerializedName("dimension_value") val dimensionValue: String
)