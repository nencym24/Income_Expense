package com.hkinfo.mybudget_traker.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import com.hkinfo.mybudget_traker.Database.DbHelper
import com.hkinfo.mybudget_traker.Models.TransModel
import com.hkinfo.mybudget_traker.databinding.FragmentAddDataBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class AddDatatFragment : Fragment() {


    lateinit var binding: FragmentAddDataBinding

    lateinit var DbHelper : DbHelper
    var isexpense=0
    lateinit var transModel: TransModel
    var year = 0
    var month = 0
    var date = 0
    var time = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDataBinding.inflate(layoutInflater)

        DbHelper = DbHelper(context)

        initview()

        return binding.root
    }

    private fun initview() {

        binding.cardincome.setOnClickListener {
            isexpense=0
            binding.cardincome.setCardBackgroundColor(Color.parseColor("#536CC8"))
            binding.cardexpense.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        binding.cardincome.setOnClickListener {
            isexpense=0
            binding.cardexpense.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.cardincome.setCardBackgroundColor(Color.parseColor("#536CC8"))
            binding.txtAddIncome.text = "Add Income"
        }
        binding.cardexpense.setOnClickListener {
            isexpense=1
            binding.cardexpense.setCardBackgroundColor(Color.parseColor("#536CC8"))
            binding.cardincome.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.txtAddIncome.text = "Add Expense"
        }

        DbHelper = DbHelper(context)
        var list = DbHelper.getTransaction()

        var income = 0
        var expence = 0
        for (trans in list) {
            if (trans.isexpense == 0) {
                income += trans.amt
            } else {
                expence += trans.amt
            }
        }

        SetDateandTime()
    }

    private fun SetDateandTime() {

        var date1 = Date()

        var format1 = SimpleDateFormat("hh:mm:ss a")
        var currentTime = format1.format(date1)

        binding.txtTime.text = currentTime
        time = currentTime

        var format = SimpleDateFormat("dd/MM/YYYY")
        var currentDate = format.format(date1)

        var dates = currentDate.split("/")
        binding.txtDate.text = currentDate
        val c = Calendar.getInstance()
        val year1 = c.get(Calendar.YEAR)
        val month1 = c.get(Calendar.MONTH)
        val day1 = c.get(Calendar.DAY_OF_MONTH)

        date = day1
        month = month1
        year = year1

        binding.txtDate.setOnClickListener {


            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year2, monthOfYear, dayOfMonth ->

                date = dayOfMonth
                month = monthOfYear+1
                year = year2

                // Display Selected date in textbox
                binding.txtDate.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year2)

            }, year1, month1, day1)

            dpd.show()
        }

        binding.txtTime.setOnClickListener {

            var format2 = SimpleDateFormat("hh:mm a")
            var currentTime = format2.format(date)

            binding.txtTime.text = currentTime
            var seleTime = currentTime

            var dialog = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

                    var selectedTime = "$p1:$p2"
                    binding.txtTime.text = selectedTime
                }

            }, 10, 0, true)
            dialog.show()
        }

        binding.AddIncome.setOnClickListener {

            var amt = binding.edtAmt.text.toString().toInt()
            var title = binding.edtTitle.text.toString()
            var category = binding.edtCategory.text.toString()
            var notes = binding.edtNotes.text.toString()

            if (title.isEmpty() || category.isEmpty() || notes.isEmpty() || amt.toString().isEmpty()) {
                Toast.makeText(context, "Please enter data", Toast.LENGTH_SHORT).show()
            }else {
                var model = TransModel(
                    0,
                    amt,
                    title,
                    category,
                    notes,
                    isexpense,
                    date.toString(),
                    month.toString(),
                    year.toString()
                )
                DbHelper.addAmount(model)
                binding.edtAmt.setText("")
                binding.edtTitle.setText("")
                binding.edtCategory.setText("")
                binding.edtNotes.setText("")
            }
        }
    }
}