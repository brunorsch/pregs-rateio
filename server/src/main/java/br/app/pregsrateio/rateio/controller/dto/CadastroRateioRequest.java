package br.app.pregsrateio.rateio.controller.dto;

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

@Builder
public record CadastroRateioRequest(
    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @Length(max = 100, message = "Tamanho máximo da descrição é de 100 caracteres")
    String descricao,

    @NotNull(message = "Tipo de recorrência é obrigatório")
    Rateio.TipoRecorrencia tipoRecorrencia,

    @DecimalMin(value = "0.01", message = "Valor total deve ser maior que R$0,00")
    BigDecimal valorTotal,

    @Min(value = 1, message = "Valor mínimo deve ser maior que 0")
    @Max(value = 31, message = "Dia de pagamento deve ser entre 1 e 31")
    Integer diaPagamento,

    @NotBlank(message = "Chave Pix é obrigatória")
    String chavePix,

    @Valid
    List<@Valid ItemRequest> itens
) {
    public Rateio toDomain(ObjectId usuarioId) {
        var rateio = Rateio.builder()
            .usuarioId(usuarioId)
            .nome(this.nome())
            .descricao(this.descricao())
            .status(EM_ANDAMENTO)
            .tipoRecorrencia(this.tipoRecorrencia())
            .valor(this.valorTotal())
            .diaPagamento(this.diaPagamento())
            .chavePix(this.chavePix())
            .itens(isNull(this.itens()) ? null : ItemRequest.mapItens(this.itens()))
            .build();

        rateio.calcularValorTotalItens();

        return rateio;
    }

    public Rateio toDomain() {
        return toDomain(null);
    }

    @Builder
    public record ItemRequest(
        @NotBlank(message = "Descrição do item é obrigatória")
        @Length(max = 40, message = "Tamanho máximo da descrição de itens é de 40 caracteres")
        String descricao,

        @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
        Integer quantidade,

        @DecimalMin(value = "0.01", message = "Valor do item deve ser maior que R$0,00")
        BigDecimal valor
    ) {
        public Item toRequest(Integer id) {
            return Item.builder()
                .id(id)
                .descricao(this.descricao())
                .quantidade(this.quantidade())
                .valor(this.valor())
                .build();
        }

        private static List<Item> mapItens(List<ItemRequest> lista) {
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
