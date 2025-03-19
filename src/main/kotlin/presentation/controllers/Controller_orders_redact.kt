package presentation.controllers

import RepositoryDI.clientRepository
import domain.entity.ClientEntity
import domain.entity.OrderEntity
import functions.showAlert
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import presentation.Classes.ClientSiu
import presentation.Classes.OrderSiu

class Controller_orders_redact {
    @FXML private lateinit var clientComboBoxForTable: ComboBox<ClientEntity> // Для таблицы
    @FXML private lateinit var clientComboBoxForOrder: ComboBox<ClientEntity> // Для поля client в OrderEntity
    @FXML private lateinit var clientListView: ListView<ClientEntity>
    @FXML private lateinit var saveButton: Button
    @FXML private lateinit var cancelButton: Button
    @FXML private lateinit var deleteButton: Button

    private lateinit var order: OrderSiu
    private lateinit var stage: Stage

    private val orderRepository = RepositoryDI.orderRepository
    private var callback: ((List<OrderEntity>) -> Unit)? = null

    fun setCallback(callback: (List<OrderEntity>) -> Unit) {
        this.callback = callback
    }

    fun setOrder(order: OrderSiu, stage: Stage) {
        this.order = order
        this.stage = stage
        initializeFields()
    }

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

    private fun initializeFields() {
        // Устанавливаем текущие значения заказа в поля
        clientComboBoxForOrder.value = order.getClient()
        clientListView.items = FXCollections.observableArrayList(order.getClientList())
    }

    @FXML
    fun initialize() {
        if (!::allClients.isInitialized) {
            allClients = FXCollections.observableArrayList()
        }

        // Загружаем клиентов из репозитория
        clientRepository.getAllClients(callback = update)

        val clientEntities = allClients.map { clientSiu ->
            ClientEntity(
                id = clientSiu.getId(),
                name = clientSiu.getName(),
                age = clientSiu.getAge(),
                date = clientSiu.getDate()
            )
        }

        // Заполняем оба ComboBox
        clientComboBoxForTable.items = FXCollections.observableArrayList(clientEntities)
        clientComboBoxForOrder.items = FXCollections.observableArrayList(clientEntities)

        // Инициализация ListView
        clientListView.items = FXCollections.observableArrayList()
        clientListView.selectionModel.selectionMode = SelectionMode.MULTIPLE

        // Настройка отображения для ComboBox
        clientComboBoxForTable.setCellFactory {
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

        clientComboBoxForOrder.setCellFactory {
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

        // Настройка отображения для ListView
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

        saveButton.setOnAction {
            val newClient = clientComboBoxForOrder.value
            val newClientList = clientListView.items

            if (newClient == null) {
                showAlert("Ошибка", "Выберите клиента для заказа!")
                return@setOnAction
            }

            if (newClientList.isEmpty()) {
                showAlert("Ошибка", "Добавьте хотя бы одного клиента в таблицу!")
                return@setOnAction
            }

            if (callback != null) {
                orderRepository.updateOrder(
                    OrderEntity(
                        id = order.getId(),
                        client = newClient,
                        clientList = newClientList.toList()
                    ), callback!!)
            } else {
                println("Callback is null! Check if it was set.")
            }
            stage.close()
        }

        deleteButton.setOnAction {
            if (callback != null) {
                orderRepository.deleteOrder(
                    OrderEntity(
                        id = order.getId(),
                        client = order.getClient(),
                        clientList = order.getClientList()
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

    @FXML
    fun onAddClientToTable() {
        val selectedClient = clientComboBoxForTable.selectionModel.selectedItem
        if (selectedClient == null) {
            showAlert("Ошибка", "Выберите клиента для добавления в таблицу!")
            return
        }

        if (!clientListView.items.contains(selectedClient)) {
            clientListView.items.add(selectedClient)
        } else {
            showAlert("Ошибка", "Этот клиент уже добавлен в таблицу!")
        }
    }

    @FXML
    fun onRemoveSelectedClients() {
        val selectedClients = clientListView.selectionModel.selectedItems
        clientListView.items.removeAll(selectedClients)
    }
}