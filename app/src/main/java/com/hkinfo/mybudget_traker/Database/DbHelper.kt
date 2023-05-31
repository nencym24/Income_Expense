package com.hkinfo.mybudget_traker.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hkinfo.mybudget_traker.Models.TransModel

class DbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "Budget.db", null, 1) {

    var TABLE_NAME = "trans"

    override fun onCreate(p0: SQLiteDatabase?) {

        var sql =
            "CREATE TABLE $TABLE_NAME(id INTEGER PRIMARY KEY AUTOINCREMENT,amt INTEGER,title TEXT,category TEXT,notes TEXT,isexpense INTEGER,time INTEGER,date INTEGER,month INTEGER,year INTEGER)"
        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addAmount(transModel: TransModel) {
        var db = writableDatabase

        var values = ContentValues().apply {
            transModel.apply {
                put("amt", amt)
                put("title", title)
                put("category", category)
                put("notes", notes)
                put("isexpense", isexpense)
                put("time", time)
                put("date", date)
                put("month", month)
                put("year", year)
            }
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getTransaction(month: Int, year: Int): ArrayList<TransModel> {
        var TransList = ArrayList<TransModel>()
        var db = readableDatabase
        var sql = "SELECT * FROM $TABLE_NAME WHERE month=$month AND year=$year"
        var cursor: Cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()

        for (i in 0..cursor.count - 1) {

            var id = cursor.getInt(0)
            var amt = cursor.getInt(1)
            var title = cursor.getString(2)
            var category = cursor.getString(3)
            var notes = cursor.getString(4)
            var isexpense = cursor.getInt(5)
            var time = cursor.getString(6)
            var date = cursor.getInt(7)
            var month = cursor.getInt(8)
            var year = cursor.getInt(9)

            var model =
                TransModel(id, amt, title, category, notes, isexpense)
            TransList.add(model)
            cursor.moveToNext()
        }
        return TransList
    }
    fun getTrans(): ArrayList<TransModel> {
        var TransList = ArrayList<TransModel>()
        var db = readableDatabase
        var sql = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()

        for (i in 0..cursor.count - 1) {

            var id = cursor.getInt(0)
            var amt = cursor.getInt(1)
            var title = cursor.getString(2)
            var category = cursor.getString(3)
            var notes = cursor.getString(4)
            var isexpense = cursor.getInt(5)

            var model =
                TransModel(id, amt, title, category, notes,0)
            TransList.add(model)
            cursor.moveToNext()
        }
        return TransList
    }

    fun updateTrans(transModel: TransModel) {
        var db = writableDatabase
        var values = ContentValues().apply {
            transModel.apply {
                put("amt", amt)
                put("title", title)
                put("category", category)
                put("notes", notes)
                put("isexpense", isexpense)
            }
        }
        db.update(TABLE_NAME, values, "id = ${transModel.id}", null)
    }
    fun deleteTrans(id : Int){
        var db = readableDatabase
        db.delete(TABLE_NAME,"id = $id",null)
    }

}