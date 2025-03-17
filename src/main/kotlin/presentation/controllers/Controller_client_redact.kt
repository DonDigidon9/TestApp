package presentation.controllers

import RepositoryDI
import domain.entity.ClientEntity
import functions.showAlert
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import javafx.stage.Stage
import presentation.Classes.ClientSiu

class Controller_client_redact {
    @FXML private lateinit var nameField: TextField
    @FXML private lateinit var ageField: TextField
    @FXML private lateinit var dateField: DatePicker
    @FXML private lateinit var saveButton: Button
    @FXML private lateinit var cancelButton: Button
    @FXML private lateinit var deleteButton: Button

    private lateinit var client: ClientSiu
    private lateinit var stage: Stage

    private val clientRepository = RepositoryDI.clientRepository
    private var callback: ((List<ClientEntity>) -> Unit)? = null

    fun setCallback(callback: (List<ClientEntity>) -> Unit) {
        this.callback = callback
    }

    fun setClient(client: ClientSiu, stage: Stage) {
        this.client = client
        this.stage = stage
        nameField.text = client.getName()
        ageField.text = client.getAge().toString()
        dateField.value = client.getDate()
    }

    @FXML
    fun initialize() {
        saveButton.setOnAction {
            val newName = nameField.text
            val newAge = ageField.text.toIntOrNull()
            val newDate = dateField.value

            if (newName == null || newAge == null || newAge <= 0 || newDate == null) {
                showAlert("Ошибка", "Проверьте корректность заполнения")
                return@setOnAction
            }

            if (callback != null) {
                println("Updating client...")
                val res = clientRepository.updateClient(ClientEntity(
                    id = client.getId(),
                    name = newName,
                    age = newAge,
                    date = newDate
                ), callback!!)
                println(res)
            } else {
                println("Callback is null! Check if it was set.")
            }
            stage.close()
        }

        deleteButton.setOnAction {
            if (callback != null) {
                println("Deleting client...")
                clientRepository.deleteClient(ClientEntity(
                    id = client.getId(),
                    name = client.getName(),
                    age = client.getAge(),
                    date = client.getDate()
                ), callback!!)
            } else {
                println("Callback is null! Check if it was set.")
            }
            stage.close()
        }

        cancelButton.setOnAction {
            stage.close()
        }
    }
}