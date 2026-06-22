package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.utils.DatabaseUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;

/**
 * Ponto de entrada da interface JavaFX (telas visuais).
 *
 * Use esta classe pra abrir as telas — não o Main.java, que só testa a
 * conexão/migração do banco e não desenha nada na tela.
 *
 * Pra testar uma tela diferente, troque o nome do arquivo no getResName()
 * abaixo (ex: "professor_turmas", "professor_notas", etc.)
 */
public class AppUI extends BaseView {
    @Override
    protected String getResName() {
        return "GerenteAdcAlunoView";
    }

    public static void main(String[] args) {
        launch(args);
    }
}