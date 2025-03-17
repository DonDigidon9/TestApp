package presentation.controllers

import RepositoryDI
import data.repository.ClientRepositoryImpl
import domain.entity.ClientEntity
import domain.repository.ClientRepository
import functions.showEditDialogClient
import presentation.Classes.ClientSiu
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Modality
import javafx.stage.Stage
import java.time.LocalDate

class Controller_clients {
    @FXML private lateinit var tableView: TableView<ClientSiu>
    @FXML private lateinit var idColumn: TableColumn<ClientSiu, Long>
    @FXML private lateinit var nameColumn: TableColumn<ClientSiu, String>
    @FXML private lateinit var ageColumn: TableColumn<ClientSiu, Int>
    @FXML private lateinit var dateColumn: TableColumn<ClientSiu, LocalDate>

    private val data: ObservableList<ClientSiu> = FXCollections.observableArrayList()

    private val clientRepository = RepositoryDI.clientRepository

    private val update = fun(list: List<ClientEntity>) {
        data.clear()
        list.forEach {
            data.add(ClientSiu(
                id = it.id,
                name = it.name,
                age = it.age,
                date = it.date
            ))
        }
    }

    @FXML
    fun initialize() {
        idColumn.setCellValueFactory { cellData -> cellData.value.idProperty.asObject() }
        nameColumn.setCellValueFactory { cellData -> cellData.value.nameProperty }
        ageColumn.setCellValueFactory { cellData -> cellData.value.ageProperty.asObject() }
        dateColumn.setCellValueFactory { cellData -> cellData.value.dateProperty }

        tableView.items = data
        clientRepository.getAllClients(callback = update)

        tableView.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                val selectedClient = tableView.selectionModel.selectedItem
                if (selectedClient != null) {
                    showEditDialogClient(selectedClient, update)
                    return@setOnMouseClicked
                }
            }
        }
    }

    @FXML
    fun onAddClick() {
        val loader = FXMLLoader(javaClass.getResource("/UI_clients_add.fxml")) // Загружаем второй экран
        val root: Parent = loader.load()
        val controller: Controller_clients_add? = loader.getController()

        val stage = Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = "jhsrfi"
        stage.scene = Scene(root)

        controller?.setCallback(update)
        controller?.setParentController(this, stage)
        stage.showAndWait()
    }

    fun goBack(event: ActionEvent) {
        val resource = javaClass.getResource("/UI_start.fxml")
        if (resource == null) {
            println("FXML file not found!")
        } else {
            val fxmlLoader = FXMLLoader(resource)
            val root: Parent = fxmlLoader.load()
            val stage = (event.source as Node).scene.window as Stage
            stage.scene = Scene(root)
            stage.title = "First screen"
        }
    }
}