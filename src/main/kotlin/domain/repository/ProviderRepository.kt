package domain.repository

import domain.entity.ProviderEntity

interface ProviderRepository {
    fun getAllProviders(): List<ProviderEntity>

    fun createProvider(provider: ProviderEntity): Boolean

    fun updateProvider(provider: ProviderEntity): Boolean

    fun deleteProvider(provider: ProviderEntity): Boolean
}
