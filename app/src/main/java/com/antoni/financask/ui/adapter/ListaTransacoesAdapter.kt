package com.antoni.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.antoni.financask.R
import com.antoni.financask.extension.formataMoedaBrasilera
import com.antoni.financask.model.Transacao
import com.antoni.financask.extension.formataParaBrasileiro
import com.antoni.financask.extension.limitaAte
import com.antoni.financask.model.Tipo
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListaTransacoesAdapter(transacoes: List<Transacao>,
    context: Context) : BaseAdapter() {

    private val transacoes = transacoes
    private val context = context
    private val limiteCategoria = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes.get(position)

        adicionaValor(view, transacao)
        adicionarIcone(view, transacao)
        adicionaCategoria(view, transacao)
        adicionaData(view, transacao)

        return view
    }

    private fun adicionaData(view: View, transacao: Transacao) {
        view.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(view: View, transacao: Transacao) {
        view.transacao_categoria.text = transacao.categoria.limitaAte(limiteCategoria)
    }

    private fun adicionarIcone(view: View, transacao: Transacao) {
        val icone = getIconeByTipo(transacao.tipo)
        view.transacao_icone.setBackgroundResource(icone)
    }

    private fun adicionaValor(view: View, transacao: Transacao) {
        val cor = getCorByTipo(transacao.tipo)
        view.transacao_valor.setTextColor(ContextCompat.getColor(context, cor))
        view.transacao_valor.text = transacao.valor.formataMoedaBrasilera()
    }

    private fun getCorByTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.color.receita
        } else {
            return R.color.despesa
        }
    }

    private fun getIconeByTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.drawable.icone_transacao_item_receita
        } else {
            return R.drawable.icone_transacao_item_despesa
        }
    }

    override fun getItem(position: Int): Transacao {
        return transacoes.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }

}