package com.example.api_bps.model_data.bpsdata

fun BpsBeData.toBpsData(): BpsData {
    val kelompokUmur = dimensions.find { it.key == "kelompok_umur" }?.value
    val jenisKelamin = dimensions.find { it.key == "jenis_kelamin" }?.value

    return BpsData(
        tahun = tahun,
        kelompokUmur = kelompokUmur,
        jenisKelamin = jenisKelamin,
        nilai = nilai,
        satuan = satuan
    )
}

