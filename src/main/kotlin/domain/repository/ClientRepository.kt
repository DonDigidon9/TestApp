package domain.repository

import domain.entity.ClientEntity

interface ClientRepository {
    fun getAllClients(callback: (List<ClientEntity>) -> Unit)

    fun createClient(client: ClientEntity, callback: (List<ClientEntity>) -> Unit): Boolean

    fun updateClient(client: ClientEntity, callback: (List<ClientEntity>) -> Unit): Boolean

    fun deleteClient(client: ClientEntity, callback: (List<ClientEntity>) -> Unit): Boolean
}
