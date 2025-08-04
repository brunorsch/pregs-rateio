package br.dev.brunorsch.pregsrateio.recorrencia.api.dto

import br.dev.brunorsch.pregsrateio.recorrencia.model.Recorrencia
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.bson.types.ObjectId
import java.math.BigDecimal
import java.time.LocalDate

@Serdeable
data class CriacaoRecorrenciaRequest(
    @field:NotBlank(message = "Nome não foi informado")
    val nome: String,

    @field:NotNull(message = "Valor total não foi informado")
    @field:Positive(message = "Valor total deve ser maior que R$0,00")
    val valorTotal: BigDecimal,

    @field:NotNull(message = "Dia de pagamento não foi informado")
    @field:Min(message = "Dia de pagamento deve estar entre 1 e 28", value = 1)
    @field:Max(message = "Dia de pagamento deve estar entre 1 e 28", value = 28)
    val diaPagamento: Int,

    @field:FutureOrPresent(message = "Primeiro pagamento deve ser uma data futura ou hoje")
    val primeiroPagamento: LocalDate,

    @field:NotNull(message = "Flag organizador isento não foi informada")
    val organizadorIsento: Boolean,

    @field:NotNull(message = "Lista de amigos não foi informada")
    @field:NotEmpty(message = "Lista de amigos não pode estar vazia")
    val idsAmigos: List<String>,
)

fun CriacaoRecorrenciaRequest.toRecorrencia(
    donoId: ObjectId,
): Recorrencia {
    return Recorrencia(
        id = null,
        idDono = donoId,
        nome = this.nome,
        valorTotal = this.valorTotal,
        diaPagamento = this.diaPagamento,
        primeiroPagamento = this.primeiroPagamento,
        organizadorIsento = this.organizadorIsento,
        idsAmigos = this.idsAmigos.map { ObjectId(it) }
    )
}