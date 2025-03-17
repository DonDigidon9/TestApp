package functions

import domain.entity.ClientEntity
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import presentation.Classes.ClientSiu
import presentation.controllers.Controller_client_redact

fun showEditDialogClient(clientSiu: ClientSiu, callback: (List<ClientEntity>) -> Unit) {
    val resource = object {}.javaClass.getResource("/UI_clients_redact.fxml")


    val loader = FXMLLoader(resource)
    val root: Parent = loader.load()
    val controller: Controller_client_redact = loader.getController()

    val stage = Stage()
    stage.initModality(Modality.APPLICATION_MODAL)
    stage.title = "Redact"
    stage.scene = Scene(root)
    controller.setClient(clientSiu, stage)
    controller.setCallback(callback)
    stage.showAndWait()
}