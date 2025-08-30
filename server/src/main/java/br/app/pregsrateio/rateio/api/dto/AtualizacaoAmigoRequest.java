package br.app.pregsrateio.rateio.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.app.pregsrateio.common.validation.MongoIdValido;
import br.app.pregsrateio.common.validation.Telefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record AtualizacaoAmigoRequest(
    @MongoIdValido
    String id,

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    String nome,

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 50, message = "Sobrenome deve ter no máximo 50 caracteres")
    String sobrenome,

    @NotBlank(message = "Número de celular é obrigatório")
    @Telefone
    String numeroCelular
) {}