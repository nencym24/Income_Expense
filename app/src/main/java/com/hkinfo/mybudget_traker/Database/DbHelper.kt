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
    var Id = "id"
    var AMOUNT = "amt"
    var TITLE = "title"
    var CATEGORY = "category"
    var NOTE = "notes"
    var IS_EXPENCE = "isexpence"
    var DATE = "date"
    var MONTH = "month"
    var YEAR = "year"

    override fun onCreate(p0: SQLiteDatabase?) {

        var sql =
            "CREATE TABLE $TABLE_NAME($Id INTEGER PRIMARY KEY AUTOINCREMENT ,$AMOUNT INTEGER , $TITLE TEXT, $CATEGORY TEXT, $NOTE TEXT, $IS_EXPENCE INTEGER, $DATE TEXT, $MONTH TEXT, $YEAR TEXT)"
        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addAmount(transModel: TransModel) {
        var db = writableDatabase

        var values = ContentValues().apply {
            transModel.apply {
                put(AMOUNT,amt)
                put(CATEGORY,category)
                put(TITLE,title)
                put(NOTE,notes)
                put(IS_EXPENCE,isexpense)
                put(DATE,date)
                put(MONTH,month)
                put(YEAR,year)
            }
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getTransaction(): ArrayList<TransModel> {
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
            var date = cursor.getString(6)
            var month = cursor.getString(7)
            var year = cursor.getString(8)

            var model =
                TransModel(id, amt,title,category,notes,isexpense, date, month , year)
            TransList.add(model)
            cursor.moveToNext()
        }
        return TransList
    }


    fun updateTrans(transModel: TransModel) {
        var db = writableDatabase
        var values = ContentValues().apply {
            transModel.apply {
                put(AMOUNT,amt)
                put(CATEGORY,category)
                put(TITLE,title)
                put(NOTE,notes)
                put(IS_EXPENCE,isexpense)
                put(DATE,date)
                put(MONTH,month)
                put(YEAR,year)
            }
        }
        db.update(TABLE_NAME, values, "id = ${transModel.id}", null)
    }
    fun deleteTrans(id : Int){
        var db = readableDatabase
        db.delete(TABLE_NAME,"id = $id",null)
    }
}