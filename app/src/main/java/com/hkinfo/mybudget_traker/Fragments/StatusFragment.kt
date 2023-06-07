package com.hkinfo.mybudget_traker.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.hkinfo.mybudget_traker.databinding.FragmentStatusBinding


class StatusFragment : Fragment() {



    lateinit var binding : FragmentStatusBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStatusBinding.inflate(layoutInflater)

        val list = ArrayList<PieEntry>()
        list.add(PieEntry(30f,"Expence"))
        list.add(PieEntry(80f,"Income"))

        val pieDataSet = PieDataSet(list,"")
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        pieDataSet.valueTextSize=15f
        pieDataSet.valueTextColor= Color.BLACK

        var pieData = PieData(pieDataSet)
        binding.pieChart.data = pieData
        binding.pieChart.description.text = "Pie Chart Of Transaction"
        binding.pieChart.centerText = "Transaction List"
        binding.pieChart.animateY(1300)

        return binding.root
    }
}