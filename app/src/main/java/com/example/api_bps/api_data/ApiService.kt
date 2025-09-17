package com.example.api_bps.api_data

import com.example.api_bps.model_data.bpsdata.BpsBeData
import com.example.api_bps.model_data.bpsdata.BpsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("bps/jumlah-penduduk")
    fun getJumlahPenduduk(
        @Query("tahun") tahun: Int? = null
    ): Call<BpsResponse>
}