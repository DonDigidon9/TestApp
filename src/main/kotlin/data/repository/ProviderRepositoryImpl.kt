package data.repository

import data.database.model.ProviderModel
import domain.entity.ProviderEntity
import domain.repository.ProviderRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class ProviderRepositoryImpl : ProviderRepository {
    init {
        transaction {
            SchemaUtils.create(ProviderModel)
        }
    }

    override fun getAllProviders(callback: (List<ProviderEntity>) -> Unit) {
        try {
            transaction {
                val result = ProviderModel.selectAll().map {
                    ProviderEntity(
                        id = it[ProviderModel.id].value,
                        name = it[ProviderModel.name],
                        date = LocalDate.parse(it[ProviderModel.date])
                    )
                }
                callback(result)
            }
        } catch (e: Exception) {
            callback(emptyList())
        }
    }

    override fun createProvider(provider: ProviderEntity, callback: (List<ProviderEntity>) -> Unit): Boolean {
        try {
            transaction {
                ProviderModel.insert {
                    it[name] = provider.name
                    it[date] = provider.date.toString()
                }
            }
            getAllProviders(callback)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun updateProvider(provider: ProviderEntity, callback: (List<ProviderEntity>) -> Unit): Boolean {
        try {
            transaction {
                ProviderModel.update({ ProviderModel.id eq provider.id }) {
                    it[name] = provider.name
                    it[date] = provider.date.toString()
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun deleteProvider(provider: ProviderEntity, callback: (List<ProviderEntity>) -> Unit): Boolean {
        try {
            transaction {
                ProviderModel.deleteWhere { ProviderModel.id eq provider.id }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
