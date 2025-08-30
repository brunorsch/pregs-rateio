package br.app.pregsrateio.rateio.data;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AmigoRepository extends MongoRepository<Amigo, ObjectId> {
    Optional<Amigo> findByIdAndOrganizadorId(ObjectId id, ObjectId organizadorId);
    Page<Amigo> findAllByOrganizadorId(ObjectId organizadorId, Pageable pageable);
    boolean existsByIdAndOrganizadorId(ObjectId id, ObjectId organizadorId);
}
