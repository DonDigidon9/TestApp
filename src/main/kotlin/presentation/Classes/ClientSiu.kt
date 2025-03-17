package presentation.Classes

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.time.LocalDate

class ClientSiu(id: Long, name: String, age: Int, date: LocalDate) {
    val idProperty = SimpleLongProperty(id)
    val nameProperty = SimpleStringProperty(name)
    val ageProperty = SimpleIntegerProperty(age)
    val dateProperty = SimpleObjectProperty(date)

    fun getId() = idProperty.get()
    fun getName() = nameProperty.get()
    fun getAge() = ageProperty.get()
    fun getDate() = dateProperty.get()
}