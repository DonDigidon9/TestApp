import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.Node
import javafx.event.ActionEvent

class Controller_1 {
    @FXML
    fun switchToSecond(event: ActionEvent) {
        val fxmlLoader = FXMLLoader(javaClass.getResource("UI_2.fxml")) // Загружаем второй экран
        val root: Parent = fxmlLoader.load()

        val stage = (event.source as Node).scene.window as Stage // Получаем текущее окно
        stage.scene = Scene(root) // Устанавливаем новый экран
        stage.title = "Второй экран" // Меняем заголовок
    }
}
