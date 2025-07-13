package br.dev.brunorsch.pregsrateio.recorrencia.api.dto

import br.dev.brunorsch.pregsrateio.recorrencia.model.Recorrencia
import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDate

@Serdeable
data class RecorrenciaResponse(
    val id: String,
    val idDono: String,
    val nome: String,
    val valorTotal: BigDecimal,
    val diaPagamento: Int,
    val primeiroPagamento: LocalDate,
    val organizadorIsento: Boolean,
    val idsAmigos: List<String>,
) {
    companion object {
        fun from(recorrencia: Recorrencia): RecorrenciaResponse {
            return RecorrenciaResponse(
                id = recorrencia.id.toString(),
                idDono = recorrencia.idDono.toString(),
                nome = recorrencia.nome,
                valorTotal = recorrencia.valorTotal,
                diaPagamento = recorrencia.diaPagamento,
                primeiroPagamento = recorrencia.primeiroPagamento,
                organizadorIsento = recorrencia.organizadorIsento,
                idsAmigos = recorrencia.idsAmigos.map { it.toString() }
            )
        }
    }
}