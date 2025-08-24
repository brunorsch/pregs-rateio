package br.app.pregsrateio.rateio.api.dto;

import br.app.pregsrateio.common.auditoria.HistoricoResponse;

public record AmigoProprioResponse(
    String id,
    String nome,
    String sobrenome,
    String numeroCelular,
    HistoricoResponse historico
) {}
