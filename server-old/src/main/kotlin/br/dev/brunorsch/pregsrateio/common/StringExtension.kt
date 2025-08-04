package br.dev.brunorsch.pregsrateio.common

import org.bson.types.ObjectId

fun String.toObjectId(): ObjectId {
    return ObjectId(this)
}