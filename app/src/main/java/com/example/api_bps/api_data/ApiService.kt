package com.example.api_bps.api_data

import com.example.api_bps.model_data.bpsdata.BpsBeData
import com.example.api_bps.model_data.bpsdata.BpsDatasetResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
interface ApiService {
    @GET("api/data/{dataset_code}")
    suspend fun getDataset(
        @Path("dataset_code") datasetCode: String
    ): Response<BpsDatasetResponse> // Response harus sesuai dengan model
}