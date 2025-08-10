package br.app.pregsrateio.rateio.api.dto;

import static br.app.pregsrateio.rateio.data.Rateio.Status.EM_ANDAMENTO;
import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;

import br.app.pregsrateio.rateio.data.Rateio;
import br.app.pregsrateio.rateio.data.Rateio.Item;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CadastroRateioRequest {
    @NotBlank(message = "Nome é obrigatório")
    private final String nome;

    @Max(value = 100, message = "Tamanho máximo da descrição é de 100 caracteres")
    private final String descricao;

    @NotNull(message = "Tipo de recorrência é obrigatório")
    private final Rateio.TipoRecorrencia tipoRecorrencia;

    @DecimalMin(value = "0.01", message = "Valor total deve ser maior que R$0,00")
    private final BigDecimal valorTotal;

    @Min(value = 1, message = "Valor mínimo deve ser maior que 0")
    @Max(value = 31, message = "Dia de pagamento deve ser entre 1 e 31")
    private final Integer diaPagamento;

    @NotBlank(message = "Chave Pix é obrigatória")
    private final String chavePix;

    @Valid
    private final List<@Valid ItemRequest> itens;

    public Rateio toDomain(ObjectId usuarioId) {
        var rateio = Rateio.builder()
            .usuarioId(usuarioId)
            .nome(this.getNome())
            .descricao(this.getDescricao())
            .status(EM_ANDAMENTO)
            .tipoRecorrencia(this.getTipoRecorrencia())
            .valor(this.getValorTotal())
            .diaPagamento(this.getDiaPagamento())
            .chavePix(this.getChavePix())
            .itens(isNull(this.getItens()) ? null : ItemRequest.mapItens(this.getItens()))
            .build();

        rateio.calcularValorTotalItens();

        return rateio;
    }


    @Data
    @Builder
    public static class ItemRequest {
        @NotBlank(message = "Descrição do item é obrigatória")
        @Length(max = 40, message = "Tamanho máximo da descrição de itens é de 40 caracteres")
        private final String descricao;

        @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
        private final Integer quantidade;

        @DecimalMin(value = "0.01", message = "Valor do item deve ser maior que R$0,00")
        private final BigDecimal valor;

        public Item toRequest(Integer id) {
            return Item.builder()
                .id(id)
                .descricao(this.getDescricao())
                .quantidade(this.getQuantidade())
                .valor(this.getValor())
                .build();
        }

        private static List<Rateio.Item> mapItens(List<CadastroRateioRequest.ItemRequest> lista) {
            var result = new ArrayList<Item>();

            for (int i = 0; i < lista.size(); i++) {
                var itemRequest = lista.get(i);
                var item = itemRequest.toRequest(i + 1);
                result.add(item);
            }

            return result;
        }
    }
}
