package com.antoni.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.antoni.financask.R
import com.antoni.financask.model.Tipo


class AdicionaTransacaoDialog(viewGroup: ViewGroup,
                              context: Context) : FormularioTransacaoDialog(context, viewGroup) {


    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }


}