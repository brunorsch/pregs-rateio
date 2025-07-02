package br.dev.brunorsch.pregsrateio.common.erros

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErroResponse(
    val codigo: String,
    val mensagem: String,
    val detalhes: List<Map<String, Any>> = emptyList(),
)