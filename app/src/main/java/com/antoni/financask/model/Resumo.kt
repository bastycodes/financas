package com.antoni.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    fun receita(): BigDecimal {
        return somaPor(Tipo.RECEITA)
    }

    fun despesa(): BigDecimal {
        return somaPor(Tipo.DESPESA)
    }

    private fun somaPor(tipo: Tipo): BigDecimal{
        val somaTransacoes = transacoes
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble()}
        return BigDecimal(somaTransacoes)
    }

    //single expression function
    fun total() =  receita().subtract(despesa())

}