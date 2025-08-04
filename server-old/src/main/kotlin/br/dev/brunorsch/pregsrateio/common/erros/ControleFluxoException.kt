package br.dev.brunorsch.pregsrateio.common.erros

import io.micronaut.http.HttpStatus


class ControleFluxoException(
    val codigo: String,
    val mensagem: String,
    val detalhes: List<Map<String, Any>> = emptyList(),
    val status: HttpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
    override val message: String = "Erro de controle de fluxo: $codigo",
) : RuntimeException(
    message, null, true, false
)