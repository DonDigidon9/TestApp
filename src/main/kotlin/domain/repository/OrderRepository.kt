package domain.repository

import domain.entity.OrderEntity

interface OrderRepository {
    fun getAllOrders(): List<OrderEntity>

    fun createOrder(order: OrderEntity): Boolean

    fun updateOrder(order: OrderEntity): Boolean

    fun deleteOrder(order: OrderEntity): Boolean
}
