package com.hkinfo.mybudget_traker.Models

class TransModel {

    var id = 0
    var amt = 0
    lateinit var title :String
    lateinit var category:String
    lateinit var notes:String
    var isexpense = 0
    var time = 0
    var date = 0
    var month = 0
    var year = 0

    constructor(
        id: Int,
        amt: Int,
        title: String,
        category: String,
        notes: String,
        isexpense: Int
    ) {
        this.id = id
        this.amt = amt
        this.title = title
        this.category = category
        this.notes = notes
        this.isexpense = isexpense
        this.time = time
        this.date = date
        this.month = month
        this.year = year
    }


}