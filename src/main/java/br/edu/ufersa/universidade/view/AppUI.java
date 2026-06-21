import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ponto de entrada da interface JavaFX (telas visuais).
 *
 * Use esta classe pra abrir as telas — não o Main.java, que só testa a
 * conexão/migração do banco e não desenha nada na tela.
 *
 * Pra testar uma tela diferente, troque o nome do arquivo no getResource()
 * abaixo (ex: "professor_turmas.fxml", "professor_notas.fxml", etc.)
 */
public class AppUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/br/edu/ufersa/universidade/view/aluno_turmas_atuais.fxml"));
        primaryStage.setTitle("Weitinho");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}