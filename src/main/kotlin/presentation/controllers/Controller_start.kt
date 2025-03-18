package presentation.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.Node
import javafx.event.ActionEvent

class Controller_start {
    @FXML
    fun clientsScreen(event: ActionEvent) {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/UI_clients.fxml")) // Загружаем второй экран
        val root: Parent = fxmlLoader.load()

        val stage = (event.source as Node).scene.window as Stage // Получаем текущее окно
        stage.scene = Scene(root) // Устанавливаем новый экран
        stage.title = "Clients screen" // Меняем заголовок
    }

    @FXML
    fun ordersScreen(event: ActionEvent) {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/UI_orders.fxml")) // Загружаем второй экран
        val root: Parent = fxmlLoader.load()

        val stage = (event.source as Node).scene.window as Stage // Получаем текущее окно
        stage.scene = Scene(root) // Устанавливаем новый экран
        stage.title = "Второй экран" // Меняем заголовок
    }
}
