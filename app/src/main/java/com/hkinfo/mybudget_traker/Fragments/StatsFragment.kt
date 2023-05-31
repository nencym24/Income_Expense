package com.hkinfo.mybudget_traker.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hkinfo.mybudget_traker.R
import com.hkinfo.mybudget_traker.databinding.FragmentStatsBinding


class StatsFragment : Fragment() {



    lateinit var binding : FragmentStatsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStatsBinding.inflate(layoutInflater)

        binding.pieChart.setDataPoints(floatArrayOf(450F,1230F))
        binding.pieChart.setCenterColor(R.color.black)
        binding.pieChart.setSliceColor(intArrayOf(
            com.nex3z.togglebuttongroup.R.color.abc_background_cache_hint_selector_material_dark,
            androidx.appcompat.R.color.abc_background_cache_hint_selector_material_dark))
        return binding.root

    }
}