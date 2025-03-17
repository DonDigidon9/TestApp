package domain.repository

import domain.entity.ProviderEntity

interface ProviderRepository {
    fun getAllProviders(callback: (List<ProviderEntity>) -> Unit)

    fun createProvider(provider: ProviderEntity, callback: (List<ProviderEntity>) -> Unit): Boolean

    fun updateProvider(provider: ProviderEntity, callback: (List<ProviderEntity>) -> Unit): Boolean

    fun deleteProvider(provider: ProviderEntity, callback: (List<ProviderEntity>) -> Unit): Boolean
}
