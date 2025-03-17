import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MainApp : Application() {
    override fun start(primaryStage: Stage) {
        val fxmlLoader = FXMLLoader(javaClass.getResource("UI_start.fxml")) // Загружаем первый экран
        val scene = Scene(fxmlLoader.load()) // Создаём сцену из загруженного FXML
        primaryStage.scene = scene
        primaryStage.title = "Первый экран" // Заголовок окна
        primaryStage.show()
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}
