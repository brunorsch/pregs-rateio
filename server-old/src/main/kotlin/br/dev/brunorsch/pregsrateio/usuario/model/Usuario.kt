package br.dev.brunorsch.pregsrateio.usuario.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId

// TODO: Campos de auditoria (criado em, atualizado em, etc.)
@MappedEntity
data class Usuario(
    @field:Id
    @GeneratedValue
    val id: ObjectId? = null,
    val authSub: String,
    val nome: String,
    val sobrenome: String,
    val email: String,
    val whatsApp: String,
    val fotoUrl: String? = null,
    val isCadastroCompleto: Boolean = true,
)