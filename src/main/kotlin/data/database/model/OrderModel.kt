package data.database.model

import org.jetbrains.exposed.dao.id.LongIdTable

object OrderModel: LongIdTable() {
    val client = reference("client", ClientModel)
    val clientIdList = varchar("client_id_list", 255)
}
