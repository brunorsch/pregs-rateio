package br.app.pregsrateio.rateio.api.dto;

import br.app.pregsrateio.common.auditoria.HistoricoResponse;
import br.app.pregsrateio.rateio.data.Amigo;

public record AmigoProprioResponse(
    String id,
    String nome,
    String sobrenome,
    String numeroCelular,
    HistoricoResponse historico
) {
    public static AmigoProprioResponse fromDomain(Amigo amigo) {
        return new AmigoProprioResponse(
            amigo.getId().toHexString(),
            amigo.getNome(),
            amigo.getSobrenome(),
            amigo.getNumeroCelular(),
            HistoricoResponse.fromDomain(amigo.getHistorico())
        );
    }
}
