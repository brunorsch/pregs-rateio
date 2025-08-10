package br.app.pregsrateio.rateio.data;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RateioRepository extends MongoRepository<Rateio, ObjectId> {
    Optional<Rateio> findByIdAndUsuarioId(ObjectId id, ObjectId usuarioId);
    Page<Rateio> findAllByUsuarioId(ObjectId usuarioId, Pageable pageable);
}
