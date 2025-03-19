package functions

import domain.entity.ClientEntity
import domain.entity.OrderEntity
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import presentation.Classes.OrderSiu
import presentation.controllers.Controller_client_redact
import presentation.controllers.Controller_orders_redact

fun showEditDialogOrder(orderSiu: OrderSiu, callback: (List<OrderEntity>) -> Unit) {
    val resource = object {}.javaClass.getResource("/UI_orders_redact.fxml")


    val loader = FXMLLoader(resource)
    val root: Parent = loader.load()
    val controller: Controller_orders_redact = loader.getController()

    val stage = Stage()
    stage.initModality(Modality.APPLICATION_MODAL)
    stage.title = "Redact"
    stage.scene = Scene(root)
    controller.setOrder(orderSiu, stage)
    controller.setCallback(callback)
    stage.showAndWait()
}