package br.app.pregsrateio.rateio.data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RateioRepository extends MongoRepository<Rateio, ObjectId> {
}
