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
    @FXML private lateinit var clientComboBoxForTable: ComboBox<ClientEntity> // Для таблицы
    @FXML private lateinit var clientComboBoxForOrder: ComboBox<ClientEntity> // Для поля client в OrderEntity
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

        clientComboBoxForTable.setButtonCell(
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

        clientComboBoxForOrder.setButtonCell(
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
    fun onAddClick() {
        val selectedClientForOrder = clientComboBoxForOrder.selectionModel.selectedItem
        if (selectedClientForOrder == null) {
            showAlert("Ошибка", "Выберите клиента для заказа!")
            return
        }

        val selectedClientsForTable = clientListView.items
        if (selectedClientsForTable.isEmpty()) {
            showAlert("Ошибка", "Добавьте хотя бы одного клиента в таблицу!")
            return
        }

        // Создаем заказ
        orderRepository.createOrder(
            OrderEntity(
                client = selectedClientForOrder, // Клиент для поля client
                clientList = selectedClientsForTable.toList() // Клиенты для таблицы
            ),
            callback_order!!
        )

        // Закрываем окно
        stage.close()
    }
}