import data.database.DataBaseFactory
import data.repository.ClientRepositoryImpl
import data.repository.OrderRepositoryImpl
import data.repository.ProviderRepositoryImpl
import domain.repository.ClientRepository
import domain.repository.OrderRepository
import domain.repository.ProviderRepository

object RepositoryDI {
    init {
        DataBaseFactory.init()
    }
    val clientRepository: ClientRepository = ClientRepositoryImpl()

    val providerRepository: ProviderRepository = ProviderRepositoryImpl()

    val orderRepository: OrderRepository = OrderRepositoryImpl()
}
