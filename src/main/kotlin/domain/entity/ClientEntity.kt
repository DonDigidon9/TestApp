package domain.entity

import java.time.LocalDate

data class ClientEntity(
    val id: Long = 0L,
    val name: String,
    val age: Int,
    val date: LocalDate
)
