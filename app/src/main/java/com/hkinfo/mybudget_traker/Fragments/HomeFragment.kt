package com.hkinfo.mybudget_traker.Fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hkinfo.mybudget_traker.Adapters.TransListAdapter
import com.hkinfo.mybudget_traker.Database.DbHelper
import com.hkinfo.mybudget_traker.Models.TransModel
import com.hkinfo.mybudget_traker.databinding.FragmentHomeBinding
import com.hkinfo.mybudget_traker.databinding.UpdateDialogeBinding
import java.text.SimpleDateFormat
import java.util.Date


class HomeFragment : Fragment() {

    var list = ArrayList<TransModel>()
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: TransListAdapter
    lateinit var dbHelper: DbHelper
    var isexpense = 0
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        dbHelper = DbHelper(context)
        list = dbHelper.getTransaction()
        updateTotal(list)
        if (list.size>2)
        list = list.reversed() as ArrayList<TransModel>

        adapter = TransListAdapter({
            updateDialog(it)
        }, {
            deleteTrans(it)
        })
        adapter.setTrans(list)

        list = dbHelper.getTransaction()

        binding.transList.layoutManager = LinearLayoutManager(context)
        binding.transList.adapter = adapter


        return binding.root
    }

    private fun deleteTrans(it: Int) {
        var dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage("Are you sure to delete ?")
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    dbHelper.deleteTrans(it)
                    updateTotal(dbHelper.getTransaction())
                    try {
                        adapter.updateData(
                            dbHelper.getTransaction().reversed() as ArrayList<TransModel>
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
        var totalIncome = 0
        var totalExpense = 0
        for (trans in transList) {
            if (trans.isexpense == 0) {
                totalIncome += trans.amt
            } else {
                totalExpense += trans.amt
            }
        }

        binding.txtIncome.text = totalIncome.toString()
        binding.txtExpense.text = totalExpense.toString()
        binding.allover.text = (totalIncome-totalExpense).toString()
    }

    private fun updateDialog(transModel: TransModel) {
        var dialog = Dialog(requireContext())
        var bind = UpdateDialogeBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.edtAmt.setText(transModel.amt.toString())
        bind.edtTitle.setText(transModel.title)
        bind.edtCategory.setText(transModel.category)
        bind.edtNotes.setText(transModel.notes)

        bind.txtDate.setOnClickListener {

            var date = Date()

            var format1 = SimpleDateFormat("dd-MM-YYYY")
            var currentDate = format1.format(date)

            var dates = currentDate.split("-")
            bind.txtDate.text = currentDate

            var dialog =
                DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

                        var selectedDate = "$p3-${(p2 + 1)}-$p1"
                        bind.txtDate.text = selectedDate
                    }

                }, dates[2].toInt(), dates[1].toInt() - 1, dates[0].toInt())
            dialog.show()
        }
        bind.txtTime.setOnClickListener {


            var date = Date()

            var format2 = SimpleDateFormat("hh:mm a")
            var currentTime = format2.format(date)

            bind.txtTime.text = currentTime

            var dialog2 = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

                }

            }, 12, 2, false)

            dialog2.show()
        }

        bind.cardincome.setOnClickListener {
            isexpense=0
            bind.cardexpense.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            bind.cardincome.setCardBackgroundColor(Color.parseColor("#536CC8"))
        }
        bind.cardexpense.setOnClickListener {
            isexpense=1
            bind.cardexpense.setCardBackgroundColor(Color.parseColor("#536CC8"))
            bind.cardincome.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        bind.cardUpdate.setOnClickListener {
            var amount = bind.edtAmt.text.toString().toInt()
            var title = bind.edtTitle.text.toString()
            var category = bind.edtCategory.text.toString()
            var note = bind.edtNotes.text.toString()
            var date = bind.txtDate.text.toString()
            var month = bind.txtDate.text.toString()
            var year = bind.txtDate.text.toString()
            var model = TransModel(
                transModel.id,
                amount,
                title,
                category,
                note,
                isexpense,
                date,
                month,
                year
            )
            dbHelper.updateTrans(model)
            dialog.dismiss()
            adapter.updateData(dbHelper.getTransaction())
            updateTotal(dbHelper.getTransaction())
            adapter.updateData(dbHelper.getTransaction().reversed() as ArrayList<TransModel>)
        }
        dialog.show()
    }
}