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
import com.example.api_bps.adapter_data.BpsAdapter
import com.example.api_bps.databinding.FragmentTabelDataBinding
import com.example.api_bps.model_data.bpsdata.BpsData

class FragmentTabelData : Fragment() {

    private var _binding: FragmentTabelDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var bpsAdapter: BpsAdapter

    // Data dummy jadi satu tempat
    private val dummyData = listOf(
        BpsData(2020, "0-4 Tahun", "Laki-laki", 10520, "Jiwa"),
        BpsData(2020, "5-9 Tahun", "Perempuan", 11200, "Jiwa"),
        BpsData(2021, "0-4 Tahun", "Laki-laki", 10600, "Jiwa"),
        BpsData(2021, "5-9 Tahun", "Perempuan", 11350, "Jiwa"),
        BpsData(2022, "0-4 Tahun", "Laki-laki", 10700, "Jiwa"),
        BpsData(2022, "5-9 Tahun", "Perempuan", 11400, "Jiwa"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabelDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Setup RecyclerView ---
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bpsAdapter = BpsAdapter(emptyList())
        binding.recyclerView.adapter = bpsAdapter

        // --- Setup Spinner Tahun ---
        val tahunList = dummyData.map { it.tahun }.distinct().sorted()
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tahunList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTahun.adapter = spinnerAdapter

        // Listener Spinner Tahun
        binding.spinnerTahun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedTahun = tahunList[position]
                val filteredData = dummyData.filter { it.tahun == selectedTahun }
                bpsAdapter.updateData(filteredData)
                Log.d("FragmentTabelData", "Filter tahun: $selectedTahun, Jumlah item: ${filteredData.size}")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // --- tampilkan default tahun pertama ---
        if (tahunList.isNotEmpty()) {
            val firstYear = tahunList.first()
            binding.spinnerTahun.setSelection(0)
            bpsAdapter.updateData(dummyData.filter { it.tahun == firstYear })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
