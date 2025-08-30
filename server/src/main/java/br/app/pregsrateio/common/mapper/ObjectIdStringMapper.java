package br.app.pregsrateio.common.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

import jakarta.annotation.Nullable;

@Mapper
public interface ObjectIdStringMapper {
    default String toString(ObjectId objectId) {
        return objectId.toHexString();
    }

    @Nullable
    default ObjectId toObjectId(String string) {
        return ObjectId.isValid(string) ? new ObjectId(string) : null;
    }
}
