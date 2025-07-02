package br.dev.brunorsch.pregsrateio.recorrencia.api.dto

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDate

@Serdeable
data class CriacaoRecorrenciaRequest(
    val nome: String,
    val valorTotal: BigDecimal,
    val diaPagamento: Int,
    val primeiroPagamento: LocalDate,
    val organizadorIsento: Boolean,
    val idsAmigos: List<String>,
)