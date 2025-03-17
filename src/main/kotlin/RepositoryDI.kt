import data.repository.ClientRepositoryImpl
import domain.repository.ClientRepository

object RepositoryDI {
    var clientRepository: ClientRepository = ClientRepositoryImpl()
}
