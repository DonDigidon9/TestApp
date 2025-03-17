package data.database

import org.jetbrains.exposed.sql.Database

object DataBaseFactory {
    fun init() {
        Database.connect(
            url = "jdbc:h2:file:./database.db",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )
    }
}
