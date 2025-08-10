package br.app.pregsrateio.rateio.data;

import static br.app.pregsrateio.rateio.data.Rateio.Status.EM_ANDAMENTO;
import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import br.app.pregsrateio.common.auditoria.Historico;
import br.app.pregsrateio.rateio.api.dto.CadastroRateioRequest;
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
    private ObjectId usuarioId;

    private String nome;

    @Nullable
    private String descricao;

    private Status status;

    private TipoRecorrecia tipoRecorrecia;

    private List<Item> itens;

    private BigDecimal valor;

    @Nullable
    private Integer diaPagamento;

    private String chavePix;

    private String emvQrCode;

    @Builder.Default
    private List<ObjectId> amigosIds = new ArrayList<>();

    @Builder.Default
    private Historico historico = new Historico();

    public enum Status {
        EM_ANDAMENTO,
        FINALIZADO,
        CANCELADO
    }

    public enum TipoRecorrecia {
        UNICO,
        MENSAL
    }

    @Data
    @Builder
    public static class Item {
        private String descricao;
        private Integer quantidade;
        private BigDecimal valor;

        public static Item fromRequest(CadastroRateioRequest.ItemRequest itemRequest) {
            return Item.builder()
                .descricao(itemRequest.getDescricao())
                .quantidade(itemRequest.getQuantidade())
                .valor(itemRequest.getValor())
                .build();
        }
    }

    public static Rateio criar(CadastroRateioRequest request, ObjectId usuarioId) {
        var rateio = Rateio.builder()
            .usuarioId(usuarioId)
            .nome(request.getNome())
            .descricao(request.getDescricao())
            .status(EM_ANDAMENTO)
            .tipoRecorrecia(request.getTipoRecorrecia())
            .valor(request.getValorTotal())
            .diaPagamento(request.getDiaPagamento())
            .chavePix(request.getChavePix())
            .itens(
                isNull(request.getItens())
                    ? null
                    : request.getItens().stream().map(Item::fromRequest).toList())
            .build();

        rateio.calcularValorTotalItens();

        return rateio;
    }

    public void calcularValorTotalItens() {
        if(itens == null || itens.isEmpty()) {
            this.valor = (valor == null) ? BigDecimal.ZERO : valor;
            return;
        }

        this.valor = itens.stream()
            .map(item -> item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
