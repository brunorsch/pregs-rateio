package br.app.pregsrateio.rateio.api.dto;

import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.List;

import org.bson.types.ObjectId;

import br.app.pregsrateio.common.auditoria.HistoricoResponse;
import br.app.pregsrateio.rateio.data.Rateio;
import br.app.pregsrateio.rateio.data.Rateio.TipoRecorrencia;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateioProprioResponse {
    private final String id;
    private final String nome;
    private final String descricao;
    private final TipoRecorrencia tipoRecorrencia;
    private final BigDecimal valorTotal;
    private final Integer diaPagamento;
    private final String chavePix;
    private final List<ItemResponse> itens;
    private final HistoricoResponse historico;

    public static RateioProprioResponse fromDomain(Rateio rateio) {
        return RateioProprioResponse.builder()
            .id(rateio.getId().toHexString())
            .nome(rateio.getNome())
            .descricao(rateio.getDescricao())
            .tipoRecorrencia(rateio.getTipoRecorrencia())
            .valorTotal(rateio.getValor())
            .diaPagamento(rateio.getDiaPagamento())
            .chavePix(rateio.getChavePix())
            .itens(ofNullable(rateio.getItens())
                .map(itens -> itens.stream()
                    .map(ItemResponse::fromDomain)
                    .toList())
                .orElse(null))
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
                .id(item.getId())
                .descricao(item.getDescricao())
                .quantidade(item.getQuantidade())
                .valor(item.getValor())
                .build();
        }
    }
}
