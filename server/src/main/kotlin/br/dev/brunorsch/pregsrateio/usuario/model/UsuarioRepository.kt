package br.dev.brunorsch.pregsrateio.usuario.model

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

@MongoRepository
interface UsuarioRepository : CrudRepository<Usuario, ObjectId> {
    fun findByAuthSub(authSub: String): Usuario?
}