package br.app.pregsrateio.rateio.data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import br.app.pregsrateio.common.auditoria.Historico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "amigos")
public class Amigo {
    @MongoId
    private ObjectId id;

    @Indexed
    private ObjectId organizadorId;

    @Indexed
    private String numeroCelular;

    private String nome;

    private String sobrenome;

    @Builder.Default
    private Historico historico = new Historico();
}
