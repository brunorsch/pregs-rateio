package br.app.pregsrateio.usuario.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import lombok.*;
import java.time.Instant;

// TODO: Adicionar campos de auditoria
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private ObjectId id;

    private String authSub;

    private String nome;

    private String sobrenome;

    private String email;

    private String numeroCelular;

    private String fotoUrl;

    @Builder.Default
    private boolean cadastroCompleto = true;
}
