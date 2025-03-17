package data.database.model

import org.jetbrains.exposed.dao.id.LongIdTable

object ClientModel: LongIdTable() {
    val name = varchar("name", 255)
    val age = integer("age")
    val date = varchar("date", 255)
}
