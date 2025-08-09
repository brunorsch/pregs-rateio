package br.app.pregsrateio.rateio.data;

import org.springframework.data.mongodb.core.mapping.Document;

import br.app.pregsrateio.common.auditoria.Historico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rateios")
public class Rateio {
    @Builder.Default
    private Historico historico = new Historico();
}
