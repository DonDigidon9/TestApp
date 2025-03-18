package presentation.controllers

import domain.entity.ClientEntity
import domain.entity.OrderEntity
import functions.showAlert
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import presentation.Classes.ClientSiu

class Controller_orders_add {
    @FXML private lateinit var clientComboBox: ComboBox<ClientEntity>
    @FXML private lateinit var clientListView: ListView<ClientEntity>

    private var parentController: Controller_orders? = null
    private lateinit var stage: Stage

    private val orderRepository = RepositoryDI.orderRepository
    private var callback_order: ((List<OrderEntity>) -> Unit)? = null

    private val clientRepository = RepositoryDI.clientRepository
    private var callback_client: ((List<OrderEntity>) -> Unit)? = null

    lateinit var allClients: ObservableList<ClientSiu>

    init {
        allClients = FXCollections.observableArrayList()
    }

    private val update = fun(list: List<ClientEntity>) {
        allClients.clear()
        list.forEach {
            allClients.add(
                ClientSiu(
                    id = it.id,
                    name = it.name,
                    age = it.age,
                    date = it.date
                )
            )
        }
    }

    fun setParentController(controller: Controller_orders?, stage: Stage) {
        this.parentController = controller
        this.stage = stage
    }

    fun setCallback(callback: (List<OrderEntity>) -> Unit) {
        this.callback_order = callback
    }

    @FXML
    fun initialize() {
        if (!::allClients.isInitialized) {
            allClients = FXCollections.observableArrayList()
        }

        clientRepository.getAllClients(callback = update)

        val clientEntities = allClients.map { clientSiu ->
            ClientEntity(
                id = clientSiu.getId(),
                name = clientSiu.getName(),
                age = clientSiu.getAge(),
                date = clientSiu.getDate()
            )
        }

        clientComboBox.items = FXCollections.observableArrayList(clientEntities)
        clientListView.items = FXCollections.observableArrayList(clientEntities)

        clientListView.selectionModel.selectionMode = SelectionMode.MULTIPLE

        clientComboBox.setCellFactory {
            object : ListCell<ClientEntity>() {
                override fun updateItem(item: ClientEntity?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item == null || empty) {
                        text = null
                    } else {
                        text = "${item.id}: ${item.name}" // Показываем ID и имя
                    }
                }
            }
        }

        clientComboBox.setButtonCell(
            object : ListCell<ClientEntity>() {
                override fun updateItem(item: ClientEntity?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item == null || empty) {
                        text = null
                    } else {
                        text = "${item.id}: ${item.name}" // Показываем ID и имя
                    }
                }
            }
        )

        clientListView.setCellFactory {
            object : ListCell<ClientEntity>() {
                override fun updateItem(item: ClientEntity?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item == null || empty) {
                        text = null
                    } else {
                        text = "${item.id}: ${item.name}" // Показываем ID и имя
                    }
                }
            }
        }
    }

    @FXML
    fun onAddClick() {
        val selectedClient = clientComboBox.selectionModel.selectedItem
        if (selectedClient == null) {
            showAlert("Ошибка", "Выберите клиента!")
            return
        }

        // Получаем выбранных клиентов из ListView
        val selectedClients = clientListView.selectionModel.selectedItems
        if (selectedClients.isEmpty()) {
            showAlert("Ошибка", "Выберите хотя бы одного клиента из списка!")
            return
        }

        // Создаем заказ
        orderRepository.createOrder(
            OrderEntity(
                client = selectedClient,
                clientList = selectedClients.toList()
            ),
            callback_order!!
        )



        // Закрываем окно
        stage.close()
    }

    @FXML
    fun onRemoveSelectedClients() {
        val selectedClients = clientListView.selectionModel.selectedItems
        clientListView.items.removeAll(selectedClients)
    }
}