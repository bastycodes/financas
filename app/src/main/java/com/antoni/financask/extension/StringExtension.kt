package com.antoni.financask.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar

fun String.limitaAte(limite: Int): String{
    if(this.length > limite){
        return "${this.substring(0, limite)}..."
    }
    return this
}

fun String.converteParaCalendar():Calendar{
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida: Date = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}
