package br.dev.brunorsch.pregsrateio.recorrencia.model

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

@MongoRepository
interface RecorrenciaRepository : CrudRepository<Recorrencia, ObjectId> {
}