package domain.repository

import domain.entity.ClientEntity

interface ClientRepository {
    fun getAllClients(): List<ClientEntity>

    fun createClient(client: ClientEntity): Boolean

    fun updateClient(client: ClientEntity): Boolean

    fun deleteClient(client: ClientEntity): Boolean
}
