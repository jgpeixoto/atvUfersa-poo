package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.common.CancelStrategy;
import br.edu.ufersa.universidade.common.DeleteStrategy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmarExclusaoController {
    private CancelStrategy cancelStrategy;
    private DeleteStrategy deleteStrategy;
    private String idToDelete;

    public void setCancelStrategy(CancelStrategy onCancel) {
        this.cancelStrategy = onCancel;
    }
    public void setDeleteStrategy(DeleteStrategy onDelete) {
        this.deleteStrategy = onDelete;
    }
    public void setIdToDelete(String toDelete) { this.idToDelete = toDelete; };

    @FXML public void fechar(ActionEvent e) {
        Stage stage = (Stage)((Button)e.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML public void cancelar(ActionEvent e) {
        this.cancelStrategy.onCancel();
        this.fechar(e);
    }
    @FXML public void confirmarExclusao(ActionEvent e) {
        this.deleteStrategy.onDelete(idToDelete);
        this.fechar(e);
    }

}
