package br.app.pregsrateio.common.erros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErroResponse {

    private String codigo;
    private String mensagem;
    private List<Map<String, Object>> detalhes;

    // Constante de erro inesperado
    public static final ErroResponse INESPERADO = ErroResponse.builder()
            .codigo("ERRO_INESPERADO")
            .mensagem("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            .build();
}
