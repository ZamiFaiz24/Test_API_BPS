package com.example.api_bps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api_bps.adapter_data.BpsApiAdapter
import com.example.api_bps.api_data.RetrofitClient
import com.example.api_bps.databinding.FrgmentApiTabelBinding
import com.example.api_bps.model_data.bpsdata.BpsBeData
import com.example.api_bps.model_data.bpsdata.BpsData
import com.example.api_bps.model_data.bpsdata.BpsResponse
import com.example.api_bps.model_data.bpsdata.toBpsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentApiTabel : Fragment() {

    private var _binding: FrgmentApiTabelBinding? = null
    private val binding get() = _binding!!

    private lateinit var bpsAdapter: BpsApiAdapter
    private val allData = mutableListOf<BpsData>()
    private var tahunList = listOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrgmentApiTabelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Setup RecyclerView ---
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bpsAdapter = BpsApiAdapter(emptyList())
        binding.recyclerView.adapter = bpsAdapter

        // --- Panggil API Laravel ---
        RetrofitClient.apiService.getJumlahPenduduk().enqueue(object : Callback<BpsResponse> {
            override fun onResponse(call: Call<BpsResponse>, response: Response<BpsResponse>) {
                Log.d("FragmentApiTabel", "Response code: ${response.code()}")
                Log.d("FragmentApiTabel", "Body: ${response.body()}")
                if (response.isSuccessful) {
                    val wrapper = response.body()
                    val apiData = wrapper?.data
                    if (!apiData.isNullOrEmpty()) {
                        allData.clear()
                        allData.addAll(apiData.map { it.toBpsData() })

                        // Update Spinner Tahun
                        tahunList = allData.map { it.tahun }.distinct().sorted()
                        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tahunList)
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerTahun.adapter = spinnerAdapter

                        // Default tahun pertama
                        binding.spinnerTahun.setSelection(0)
                        bpsAdapter.updateData(allData.filter { it.tahun == tahunList.first() })
                    }
                } else {
                    Log.e("FragmentApiTabel", "Response gagal: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BpsResponse>, t: Throwable) {
                Log.e("FragmentApiTabel", "Gagal panggil API", t)
            }
        })


        // --- Listener Spinner Tahun ---
        binding.spinnerTahun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (tahunList.isNotEmpty()) {
                    val selectedTahun = tahunList[position]
                    val filteredData = allData.filter { it.tahun == selectedTahun }
                    bpsAdapter.updateData(filteredData)
                    Log.d("FragmentApiTabel", "Filter tahun: $selectedTahun, Jumlah item: ${filteredData.size}")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
