package br.dev.brunorsch.pregsrateio.usuario.api.dto

import br.dev.brunorsch.pregsrateio.usuario.model.Usuario

class UsuarioProprioResponse(
    val id: String,
    val authSub: String,
    val nome: String,
    val sobrenome: String,
    val email: String,
    val whatsApp: String,
    val fotoUrl: String? = null,
) {
    companion object {
        fun from(usuario: Usuario): UsuarioProprioResponse {
            return UsuarioProprioResponse(
                id = usuario.id?.toHexString() ?: "",
                authSub = usuario.authSub,
                nome = usuario.nome,
                sobrenome = usuario.sobrenome,
                email = usuario.email,
                whatsApp = usuario.whatsApp,
                fotoUrl = usuario.fotoUrl
            )
        }
    }
}