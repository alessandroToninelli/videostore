package data.db

import org.postgresql.util.PGobject

class UserTypeObject <T: Enum<T>>(enumTypeName: String, enumValue: T? ): PGobject(){
    init {
        value = enumValue?.name
        type = enumTypeName
    }
}