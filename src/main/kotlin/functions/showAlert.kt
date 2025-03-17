package functions

import javafx.scene.control.Alert

fun showAlert(title: String, message: String) {
    val alert = Alert(Alert.AlertType.ERROR)
    alert.title = title
    alert.headerText = null
    alert.contentText = message
    alert.showAndWait()
}