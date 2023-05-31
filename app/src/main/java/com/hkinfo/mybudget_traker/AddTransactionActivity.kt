package com.hkinfo.mybudget_traker

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hkinfo.mybudget_traker.Database.DbHelper
import com.hkinfo.mybudget_traker.Models.TransModel
import com.hkinfo.mybudget_traker.databinding.ActivityAddTransactionBinding

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class AddTransactionActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTransactionBinding

    lateinit var DbHelper : DbHelper
    var isexpense=0
    var year = 0
    var month = 0
    var date = 0
    var time = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DbHelper = DbHelper(this)

        initview()
    }

    private fun initview() {

        binding.cardincome.setOnClickListener {
            isexpense=0
            binding.cardincome.setCardBackgroundColor(Color.parseColor("#8C9EFF"))
            binding.cardexpense.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        binding.cardincome.setOnClickListener {
            isexpense=0
            binding.cardexpense.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.cardincome.setCardBackgroundColor(Color.parseColor("#8C9EFF"))
            binding.txtAddIncome.text = "Add Income"
        }
        binding.AddIncome.setOnClickListener {

            var amt = binding.edtAmt.text.toString().toInt()
            var title = binding.edtTitle.text.toString()
            var category = binding.edtCategory.text.toString()
            var notes = binding.edtNotes.text.toString()

            var model = TransModel(0, amt, title, category, notes, isexpense)
            DbHelper.addAmount(model)
            binding.edtAmt.setText("")
            binding.edtTitle.setText("")
            binding.edtCategory.setText("")
            binding.edtNotes.setText("")
        }

        binding.cardexpense.setOnClickListener {
            isexpense=1
            binding.cardexpense.setCardBackgroundColor(Color.parseColor("#8C9EFF"))
            binding.cardincome.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.txtAddIncome.text = "Add Expense"
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


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year2, monthOfYear, dayOfMonth ->

                date = dayOfMonth
                month = monthOfYear+1
                year = year2

                // Display Selected date in textbox
                binding.txtDate.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year2)

            }, year1, month1, day1)

            dpd.show()
        }
    }
}