package com.hkinfo.mybudget_traker.Models

import java.time.Month

class TransModel {

    var id = 0
    var amt = 0
    lateinit var category: String
    lateinit var title: String
    lateinit var notes: String
    var isexpense = 0
    lateinit var date: String
    lateinit var month: String
    lateinit var year: String

    constructor(
        id: Int,
        amt: Int,
        category: String,
        title: String,
        notes: String,
        isexpense: Int,
        date: String,
        month: String,
        year: String
    ) {
        this.id = id
        this.amt = amt
        this.category = category
        this.title = title
        this.notes = notes
        this.isexpense = isexpense
        this.date = date
        this.month = month
        this.year = year
    }
}