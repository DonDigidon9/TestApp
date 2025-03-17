package domain.entity

data class OrderEntity(
    val id: Long = 0L,
    val client: ClientEntity,
    val providerLint: List<ProviderEntity>
)
