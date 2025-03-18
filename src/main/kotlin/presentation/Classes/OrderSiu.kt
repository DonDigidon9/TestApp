package presentation.Classes

import domain.entity.ClientEntity
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections

class OrderSiu(id: Long, client: ClientEntity, clientList: List<ClientEntity>) {
    val idProperty = SimpleLongProperty(id)
    val clientProperty = SimpleObjectProperty(client)
    val clientListProperty = SimpleListProperty(FXCollections.observableArrayList(clientList))

    fun getId() = idProperty.get()
    fun getClient() = clientProperty.get()
    fun getClientList() = clientListProperty.get()
}