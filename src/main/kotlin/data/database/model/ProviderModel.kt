package data.database.model

import org.jetbrains.exposed.dao.id.LongIdTable

object ProviderModel: LongIdTable() {
    val name = varchar("name", 255)
    val date = varchar("date", 255)
}
