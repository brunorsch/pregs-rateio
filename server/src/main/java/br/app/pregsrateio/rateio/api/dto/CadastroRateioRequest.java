package br.app.pregsrateio.rateio.api.dto;

import java.math.BigDecimal;
import java.util.List;

import br.app.pregsrateio.rateio.data.Rateio.TipoRecorrecia;
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
    private final TipoRecorrecia tipoRecorrecia;

    private final BigDecimal valorTotal;

    @Min(value = 1, message = "Valor mínimo deve ser maior que 0")
    @Max(value = 31, message = "Dia de pagamento deve ser entre 1 e 31")
    private final Integer diaPagamento;

    @NotBlank(message = "Chave Pix é obrigatória")
    private final String chavePix;

    @Valid
    private final List<@Valid ItemRequest> itens;

    @Data
    @Builder
    public static class ItemRequest {
        @NotBlank(message = "Descrição do item é obrigatória")
        private final String descricao;

        @Min(value = 1, message = "Quantidade deve pelo menos 1")
        private final Integer quantidade;

        @DecimalMin(value = "0.01", message = "Valor do item deve ser maior que R$0,00")
        private final BigDecimal valor;
    }
}
