package br.app.pregsrateio.rateio.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import br.app.pregsrateio.common.auditoria.EventoAuditoria;
import br.app.pregsrateio.common.auditoria.Historico;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "rateios")
public class Rateio {
    @MongoId
    private ObjectId id;

    @Indexed
    @Nullable
    private ObjectId usuarioId;

    private String nome;

    @Nullable
    private String descricao;

    private Status status;

    private TipoRecorrencia tipoRecorrencia;

    private List<Item> itens;

    private BigDecimal valor;

    @Nullable
    private Integer diaPagamento;

    private String chavePix;

    private String emvQrCode;

    @Builder.Default
    private Historico historico = new Historico();

    public void calcularValorTotalItens() {
        if(itens == null || itens.isEmpty()) {
            this.valor = (valor == null) ? BigDecimal.ZERO : valor;
            return;
        }

        this.valor = itens.stream()
            .map(item -> item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void registrarAtualizacao(ObjectId usuarioId) {
        this.historico.pushEvento(new EventoAuditoria("ATUALIZACAO", usuarioId.toHexString()));
    }

    public enum Status {
        EM_ANDAMENTO,
        FINALIZADO,
        CANCELADO
    }

    public enum TipoRecorrencia {
        UNICO,
        MENSAL
    }

    @Data
    @Builder
    public static class Item {
        private Integer id;
        private String descricao;
        private Integer quantidade;
        private BigDecimal valor;
    }
}
