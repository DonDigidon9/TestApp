package presentation.controllers

import RepositoryDI.orderRepository
import domain.entity.ClientEntity
import domain.entity.OrderEntity
import functions.showEditDialogClient
import functions.showEditDialogOrder
import javafx.beans.property.SimpleStringProperty
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
import presentation.Classes.ClientSiu
import presentation.Classes.OrderSiu
import java.time.LocalDate

class Controller_orders {
    @FXML private lateinit var tableView: TableView<OrderSiu>
    @FXML private lateinit var idColumn: TableColumn<OrderSiu, Long>
    @FXML private lateinit var clientColumn: TableColumn<OrderSiu, String>
    @FXML private lateinit var clientListColumn: TableColumn<OrderSiu, String>

    private val data: ObservableList<OrderSiu> = FXCollections.observableArrayList()

    private val clientRepository = RepositoryDI.clientRepository

    private val update = fun(list: List<OrderEntity>) {
        data.clear()
        list.forEach {
            data.add(
                OrderSiu(
                    id = it.id,
                    client = it.client,
                    clientList = it.clientList
            )
            )
        }
    }

    @FXML
    fun initialize() {
        idColumn.setCellValueFactory { cellData -> cellData.value.idProperty.asObject() }
        clientColumn.setCellValueFactory { cellData -> SimpleStringProperty(cellData.value.getClient().name) }
        clientListColumn.setCellValueFactory { cellData ->
            val clientNames = cellData.value.getClientList().joinToString(", ") { it.name }
            SimpleStringProperty(clientNames)
        }

        tableView.items = data
        orderRepository.getAllOrders(callback = update)

        tableView.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                val selectedOrder = tableView.selectionModel.selectedItem
                if (selectedOrder != null) {
                    showEditDialogOrder(selectedOrder, update)
                    return@setOnMouseClicked
                }
            }
        }
    }

    @FXML
    fun onAddClick() {
        val loader = FXMLLoader(javaClass.getResource("/UI_orders_add.fxml")) // Загружаем второй экран
        val root: Parent = loader.load()
        val controller: Controller_orders_add? = loader.getController()

        val stage = Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = "Окно редатирования"
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