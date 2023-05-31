package com.hkinfo.mybudget_traker.Fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hkinfo.mybudget_traker.Adapters.TransListAdapter
import com.hkinfo.mybudget_traker.AddTransactionActivity
import com.hkinfo.mybudget_traker.Database.DbHelper
import com.hkinfo.mybudget_traker.Models.TransModel
import com.hkinfo.mybudget_traker.databinding.FragmentHomeBinding
import com.hkinfo.mybudget_traker.databinding.UpdateDialogeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class HomeFragment : Fragment() {

    var list = ArrayList<TransModel>()
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter : TransListAdapter
    lateinit var database : DbHelper
    var isexpense = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)

        database = DbHelper(context)
        list = database.getTrans()
        updateTotal(list)
        list = list.reversed() as ArrayList<TransModel>

        binding.Transaction.setOnClickListener {
            val intent = Intent(context,AddTransactionActivity::class.java)
            startActivity(intent)
        }

        adapter = TransListAdapter({
            updateDialog(it)
        }, {
            deleteTrans(it)
        })
        adapter.setTrans(list)

        var date1 = Date()
        var format = SimpleDateFormat("MMMM YYYY")
        var currentDate = format.format(date1)
        binding.txtDate.text = currentDate
        val c = Calendar.getInstance()
        val year1 = c.get(Calendar.YEAR)
        val month1 = c.get(Calendar.MONTH)
        val day1 = c.get(Calendar.DAY_OF_MONTH)

        list = database.getTransaction(month1,year1)
        adapter.setTrans(list)

        binding.transList.layoutManager = LinearLayoutManager(context)
        binding.transList.adapter = adapter

        binding.txtDate.setOnClickListener {

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year2, monthOfYear, dayOfMonth ->

                adapter.update(database.getTransaction((monthOfYear+1),year2))

                // Display Selected date in textbox
                binding.txtDate.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year2)

            }, year1, month1, day1)

            dpd.show()
        }

        return binding.root
    }

    private fun deleteTrans(it : Int) {
        var dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage("Are you want sure?")
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    database.deleteTrans(it)
                    updateTotal(database.getTrans())
                    try {
                        adapter.updateData(
                            database.getTrans().reversed() as ArrayList<TransModel>
                        )

                    } catch (e: Exception) {

                    }
                }
            }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            }).create()
        dialog.show()
    }


    fun updateTotal(transList: ArrayList<TransModel>) {
        var totalIncome = 1
        var totalExpense = 0
        for(trans in transList){
            if (trans.isexpense == 0) {
                totalIncome += trans.amt
            } else {
                totalExpense += trans.amt
            }
        }
    }

    private fun updateDialog(transModel: TransModel) {
        var dialog = Dialog(requireContext())
        var bind = UpdateDialogeBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.edtAmt.setText(transModel.amt.toString())
        bind.edtTitle.setText(transModel.title.toString())
        bind.edtCategory.setText(transModel.category.toString())
        bind.edtNotes.setText(transModel.notes.toString())

        bind.cardUpdate.setOnClickListener {
            var amount = bind.edtAmt.text.toString().toInt()
            var title = bind.edtTitle.text.toString()
            var category = bind.edtCategory.text.toString()
            var note = bind.edtNotes.text.toString()
            var model = TransModel(
                transModel.id,
                amount,
                title,
                category,
                note,
                transModel.isexpense
            )
            database.updateTrans(model)
            dialog.dismiss()
            adapter.updateData(database.getTrans().reversed() as ArrayList<TransModel>)
        }

        dialog.show()
    }
}