package presentation.controllers

import RepositoryDI
import functions.showAlert
import domain.entity.ClientEntity
import javafx.fxml.FXML
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import javafx.stage.Stage

class Controller_clients_add {
    @FXML private lateinit var nameField: TextField
    @FXML private lateinit var ageField: TextField
    @FXML private lateinit var dateField: DatePicker

    private var parentController: Controller_clients? = null
    private lateinit var stage: Stage

    private val clientRepository = RepositoryDI.clientRepository
    private var callback: ((List<ClientEntity>) -> Unit)? = null

    fun setParentController(controller: Controller_clients?, stage: Stage) {
        this.parentController = controller
        this.stage = stage
    }

    fun setCallback(callback: (List<ClientEntity>) -> Unit) {
        this.callback = callback
    }

    @FXML
    fun onAddClick() {
        val name = nameField.text
        val age = ageField.text.toIntOrNull()
        val date = dateField.value

        if (age == null || age <= 0) {
            showAlert("Ошибка", "Возраст должен быть положительным числом")
        }

        clientRepository.createClient(
            ClientEntity(
                name = name,
                age = age!!,
                date = date
            ),
            callback!!
        )

        stage.close()
    }
}