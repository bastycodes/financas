package com.antoni.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transacao (valor: BigDecimal,
                 categoria: String = "Indefinida",
                 data: Calendar = Calendar.getInstance(),
                 tipo: Tipo){

    val valor: BigDecimal = valor
    val categoria: String = categoria
    val data: Calendar = data
    val tipo: Tipo = tipo
}