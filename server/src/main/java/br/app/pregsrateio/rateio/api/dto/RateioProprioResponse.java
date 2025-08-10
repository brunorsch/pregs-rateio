package br.app.pregsrateio.rateio.api.dto;

import java.math.BigDecimal;
import java.util.List;

import br.app.pregsrateio.common.auditoria.HistoricoResponse;
import br.app.pregsrateio.rateio.data.Rateio;
import br.app.pregsrateio.rateio.data.Rateio.TipoRecorrecia;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateioProprioResponse {
    private final String nome;
    private final String descricao;
    private final TipoRecorrecia tipoRecorrecia;
    private final BigDecimal valorTotal;
    private final Integer diaPagamento;
    private final String chavePix;
    private final List<ItemResponse> itens;
    private final HistoricoResponse historico;

    public static RateioProprioResponse fromDomain(Rateio rateio) {
        return RateioProprioResponse.builder()
            .nome(rateio.getNome())
            .descricao(rateio.getDescricao())
            .tipoRecorrecia(rateio.getTipoRecorrecia())
            .valorTotal(rateio.getValor())
            .diaPagamento(rateio.getDiaPagamento())
            .chavePix(rateio.getChavePix())
            .itens(rateio.getItens().stream()
                .map(ItemResponse::fromDomain)
                .toList())
            .historico(HistoricoResponse.fromDomain(rateio.getHistorico()))
            .build();
    }

    @Data
    @Builder
    public static class ItemResponse {
        private final Integer id;
        private final String descricao;
        private final Integer quantidade;
        private final BigDecimal valor;

        public static ItemResponse fromDomain(Rateio.Item item) {
            return ItemResponse.builder()
                .descricao(item.getDescricao())
                .quantidade(item.getQuantidade())
                .valor(item.getValor())
                .build();
        }
    }
}
