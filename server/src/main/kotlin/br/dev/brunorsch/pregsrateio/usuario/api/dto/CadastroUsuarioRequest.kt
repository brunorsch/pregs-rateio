package br.dev.brunorsch.pregsrateio.usuario.api.dto

import br.dev.brunorsch.pregsrateio.usuario.model.Usuario
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CadastroUsuarioRequest(
    val nome: String,
    val sobrenome: String,
    val email: String,
    val whatsApp: String,
    val fotoUrl: String? = null,
) {
    fun toUsuario(authSub: String) = Usuario(
        authSub = authSub,
        nome = nome,
        sobrenome = sobrenome,
        email = email,
        whatsApp = whatsApp,
    )
}