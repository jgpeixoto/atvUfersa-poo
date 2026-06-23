package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.common.CancelStrategy;
import br.edu.ufersa.universidade.common.DeleteStrategy;
import br.edu.ufersa.universidade.controller.ConfirmarExclusaoController;
import br.edu.ufersa.universidade.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;

public class ConfirmarExclusaoView extends BaseView {
    private final CancelStrategy onCancel;
    private final DeleteStrategy onDelete;
    private final String toDelete;

    public ConfirmarExclusaoView() {
        this.onCancel = () -> {};
        this.onDelete = (String id) -> {};
        this.toDelete = "";
    }

    public ConfirmarExclusaoView(CancelStrategy onCancel, DeleteStrategy onDelete, String toDelete) {
        this.onCancel = onCancel;
        this.onDelete = onDelete;
        this.toDelete = toDelete;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(baseDir + getResName() + ".fxml"));
        Parent root = loader.load();
        Stage newWindow = new Stage();
        newWindow.setTitle("Confirmar delete");
        newWindow.setScene(new Scene(root));
        this.addLogo(newWindow);
        this.onStart(newWindow, root, loader);
        newWindow.show();
    }

    @Override
    protected void onStart(Stage primaryStage, Parent root, FXMLLoader loader) {
        primaryStage.setTitle("Confirmar exclusão");
        ConfirmarExclusaoController controller = loader.getController();
        controller.setCancelStrategy(this.onCancel);
        controller.setDeleteStrategy(this.onDelete);
        controller.setIdToDelete(this.toDelete);
    }

    @Override
    protected String getResName() {
        return "ConfirmarExclusaoView";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
