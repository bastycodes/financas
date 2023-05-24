package com.antoni.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.antoni.financask.R
import com.antoni.financask.extension.formataMoedaBrasilera
import com.antoni.financask.model.Resumo
import com.antoni.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    context: Context,
    private val view: View,
    transacoes: List<Transacao>
) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    private fun adicionaReceita() {
        val totalReceita = resumo.receita()

        //chama apenas uma vez o objeto e dentro do bloco pode-se fazer a chamada das funções
        view?.let {
            with(it.resumo_card_receita) {
                setTextColor(corReceita)
                text = totalReceita.formataMoedaBrasilera()
            }
        }
    }

    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa()

        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = totalDespesa.formataMoedaBrasilera()
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total()

        val cor = corPor(total)


        with(view.resumo_card_total) {
            setTextColor(cor)
            text = total.formataMoedaBrasilera()
        }

    }

    private fun corPor(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }


    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

}