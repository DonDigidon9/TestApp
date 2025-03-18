package data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.database.model.OrderModel
import domain.entity.OrderEntity
import domain.repository.ClientRepository
import domain.repository.OrderRepository
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderRepositoryImpl(
    private val clientRepository: ClientRepository,
    private val gson: Gson,
) : OrderRepository {
    init {
        transaction {
            SchemaUtils.create(OrderModel)
        }
    }

    override fun getAllOrders(callback: (List<OrderEntity>) -> Unit) {
        try {
            transaction {
                val result = OrderModel.selectAll().map {
                    OrderEntity(
                        id = it[OrderModel.id].value,
                        client = clientRepository.getClientById(it[OrderModel.client].value)!!,
                        clientList = gson.fromJson<List<Long>>(
                            it[OrderModel.clientIdList],
                            object : TypeToken<List<Long>>() {}.type
                        ).map { id ->
                            clientRepository.getClientById(id)!!
                        }
                    )
                }
                callback(result)
            }
        } catch (e: Exception) {
            println(e.message)
            callback(emptyList())
        }
    }

    override fun createOrder(order: OrderEntity, callback: (List<OrderEntity>) -> Unit): Boolean {
        try {
            transaction {
                OrderModel.insert { insertStatement ->
                    insertStatement[client] = order.client.id
                    insertStatement[clientIdList] = gson.toJson(order.clientList.map { it.id })
                }
            }
            getAllOrders(callback)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}