package br.dev.brunorsch.pregsrateio.recorrencia.api.dto

import java.math.BigDecimal
import java.time.LocalDate

data class CriarRecorrenciaRequest(
    val nome: String,
    val valorTotal: BigDecimal,
    val diaPagamento: Int,
    val primeiroPagamento: LocalDate,
    val organizadorIsento: Boolean,
    val idsPessoas: List<String>,
)