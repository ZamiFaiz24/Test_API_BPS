package com.example.api_bps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api_bps.adapter_data.BpsApiAdapter
import com.example.api_bps.api_data.RetrofitClient
import com.example.api_bps.databinding.FrgmentApiTabelBinding
import com.example.api_bps.model_data.bpsdata.BpsDatavalue
import com.example.api_bps.model_data.bpsdata.DataTableItem
import kotlinx.coroutines.launch

class FragmentApiTabel : Fragment() {

    private var _binding: FrgmentApiTabelBinding? = null
    private val binding get() = _binding!!

    private lateinit var bpsAdapter: BpsApiAdapter
    private val allData = mutableListOf<BpsDatavalue>()
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

        setupRecyclerView()
        fetchApiData()

        binding.btnRefresh.setOnClickListener {
            fetchApiData()
        }
    }

    private fun setupRecyclerView() {
        bpsAdapter = BpsApiAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = bpsAdapter
    }

    private fun fetchApiData() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

        lifecycleScope.launch {
            try {
                // Ganti "1873" dengan kode variabel BPS yang Anda inginkan
                val response = RetrofitClient.apiService.getDataset("1873")
                if (response.isSuccessful) {
                    val dataset = response.body()
                    val dataValues = dataset?.values
                    if (!dataValues.isNullOrEmpty()) {
                        allData.clear()
                        allData.addAll(dataValues)
                        setupTahunSpinner()
                    }
                } else {
                    Log.e("FragmentApiTabel", "Response gagal: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("FragmentApiTabel", "Gagal panggil API", e)
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun setupTahunSpinner() {
        tahunList = allData.map { it.year }.distinct().sortedDescending()
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tahunList)
        (binding.spinnerTahun as? AutoCompleteTextView)?.setAdapter(spinnerAdapter)

        if (tahunList.isNotEmpty()) {
            val latestYear = tahunList.first()
            (binding.spinnerTahun as? AutoCompleteTextView)?.setText(latestYear.toString(), false)
            filterAndTransformData(latestYear)
        }

        (binding.spinnerTahun as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
            val selectedYear = tahunList[position]
            filterAndTransformData(selectedYear)
        }
    }

    private fun filterAndTransformData(year: Int) {
        val filteredByYear = allData.filter { it.year == year }

        // Logikanya menjadi JAUH LEBIH SEDERHANA
        val tableRows = filteredByYear
            .groupBy { it.vervarLabel ?: "Lainnya" }
            .map { (kelompokUmur, values) ->
                val lakiLaki = values.find { it.turvarLabel?.trim().equals("Laki-laki", ignoreCase = true) }?.value
                val perempuan = values.find { it.turvarLabel?.trim().equals("Perempuan", ignoreCase = true) }?.value
                val total = values.find { it.turvarLabel?.trim().equals("Jumlah", ignoreCase = true) }?.value

                DataTableItem(kelompokUmur, lakiLaki, perempuan, total)
            }
            .filterNot { it.kelompokUmur.equals("Total", ignoreCase = true) }
            .sortedBy { it.kelompokUmur }

        bpsAdapter.updateData(tableRows)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}