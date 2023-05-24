package com.antoni.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.antoni.financask.R
import com.antoni.financask.extension.converteParaCalendar
import com.antoni.financask.extension.formataParaBrasileiro
import com.antoni.financask.model.Tipo
import com.antoni.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(private val context: Context,
                                     private val viewGroup: ViewGroup) {
    private val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data

    // property abstrata
    abstract protected val tituloBotaoPositivo: String

    fun chama(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {

        configuraCampoData()

        configuraCampoCategoria(tipo)

        configuraFormulario(tipo, delegate)
    }

    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(tituloBotaoPositivo, { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()

                val valor = converteCampoValor(valorEmTexto)

                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriadada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                delegate(transacaoCriadada)

            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    abstract protected fun tituloPor(tipo: Tipo): Int

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        // try expression
        return try {
            BigDecimal(valorEmTexto)
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_SHORT).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriaPor(tipo)

        val adapter = ArrayAdapter
            .createFromResource(context, categorias, android.R.layout.simple_spinner_dropdown_item)
        campoCategoria.adapter = adapter
    }

    protected fun categoriaPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        val dataHoje = Calendar.getInstance()
        val ano = dataHoje.get(Calendar.YEAR)
        val mes = dataHoje.get(Calendar.MONTH)
        val dia = dataHoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(dataHoje.formataParaBrasileiro())
        campoData.setOnClickListener {
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(year, month, dayOfMonth)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                },
                ano, mes, dia
            ).show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, viewGroup, false)
    }
}