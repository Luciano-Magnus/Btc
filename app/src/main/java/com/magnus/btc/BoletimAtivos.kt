package com.magnus.btc

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BoletimAtivos (
    var high: String?,
    var low: String,
    var vol: String,
    var last: String,
    var buy: String,
    var sell: String,
    var opem: String,
    var data: String

) {
    override fun toString(): String {
        return data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(data: String): String {
        val diaString = data
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var date = LocalDate.parse(diaString)
        var formattedDate = date.format(formatter)
        return formattedDate
    }

}