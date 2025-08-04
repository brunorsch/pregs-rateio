package br.dev.brunorsch.pregsrateio.recorrencia.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId
import java.math.BigDecimal
import java.time.LocalDate

// TODO: Campos de auditoria (criado em, atualizado em, etc.)
@MappedEntity
class Recorrencia(
    @field:Id
    @GeneratedValue
    val id: ObjectId? = null,

    val idDono: ObjectId,

    val nome: String,

    val valorTotal: BigDecimal,

    val diaPagamento: Int,

    val primeiroPagamento: LocalDate,

    val organizadorIsento: Boolean,

    val idsAmigos: List<ObjectId>,
) {
    fun isUsuarioAutorizado(usuarioId: ObjectId): Boolean {
        return idsAmigos.contains(usuarioId) || idDono == usuarioId
    }
}