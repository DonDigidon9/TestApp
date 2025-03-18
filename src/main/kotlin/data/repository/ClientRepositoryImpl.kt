package data.repository

import data.database.model.ClientModel
import domain.entity.ClientEntity
import domain.repository.ClientRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class ClientRepositoryImpl : ClientRepository {
    init {
        transaction {
            SchemaUtils.create(ClientModel)
        }
    }

    override fun getAllClients(callback: (List<ClientEntity>) -> Unit) {
        try {
            transaction {
                val result = ClientModel.selectAll().map {
                    ClientEntity(
                        id = it[ClientModel.id].value,
                        name = it[ClientModel.name],
                        age = it[ClientModel.age],
                        date = LocalDate.parse(it[ClientModel.date]),
                    )
                }
                callback(result)
            }
        } catch (e: Exception) {
            callback(emptyList())
        }
    }

    override fun getClientById(id: Long): ClientEntity? = transaction {
        val client = ClientModel.select { ClientModel.id eq id }.firstOrNull()?.let {
            ClientEntity(
                id = it[ClientModel.id].value,
                name = it[ClientModel.name],
                age = it[ClientModel.age],
                date = LocalDate.parse(it[ClientModel.date])
            )
        }
        return@transaction client
    }

    override fun createClient(client: ClientEntity, callback: (List<ClientEntity>) -> Unit): Boolean {
        try {
            transaction {
                ClientModel.insert {
                    it[name] = client.name
                    it[age] = client.age
                    it[date] = client.date.toString()
                }
            }
            getAllClients(callback)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun updateClient(client: ClientEntity, callback: (List<ClientEntity>) -> Unit): Boolean {
        try {
            ClientModel.update({ ClientModel.id eq client.id }) {
                it[name] = client.name
                it[age] = client.age
                it[date] = client.date.toString()
            }
            getAllClients(callback)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun deleteClient(client: ClientEntity, callback: (List<ClientEntity>) -> Unit): Boolean {
        try {
            ClientModel.deleteWhere { id eq client.id }
            getAllClients(callback)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
