package domain.entity

import java.time.LocalDate

data class ProviderEntity(
    val id: Long = 0L,
    val name: String,
    val date: LocalDate,
)
