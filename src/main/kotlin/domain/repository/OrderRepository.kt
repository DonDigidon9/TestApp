package domain.repository

import domain.entity.OrderEntity

interface OrderRepository {
    fun getAllOrders(callback: (List<OrderEntity>) -> Unit)

    fun createOrder(order: OrderEntity, callback: (List<OrderEntity>) -> Unit): Boolean

    fun updateOrder(order: OrderEntity, callback: (List<OrderEntity>) -> Unit): Boolean

    fun deleteOrder(order: OrderEntity, callback: (List<OrderEntity>) -> Unit): Boolean
}
