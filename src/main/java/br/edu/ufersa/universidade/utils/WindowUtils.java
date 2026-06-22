package br.edu.ufersa.universidade.utils;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class WindowUtils {
    public static <T extends Application> void SwitchToWindow(Class<T> application, ActionEvent event) {
        try {
            T app = application.getDeclaredConstructor().newInstance();
            app.start(WindowUtils.getStage(event));
        } catch (Exception e) {
            System.out.println("[WindowUtils] Tried switching to " + application.getName() + "\n" + e.toString());
        }
    }

    public static <T extends Application> void SwitchToWindow(Class<T> application, Object object) {
        try {
            T app = application.getDeclaredConstructor().newInstance();
            app.start(WindowUtils.getStage(object));
        } catch (Exception e) {
            System.out.println("[WindowUtils] Tried switching to " + application.getName() + "\n" + e.toString());
        }
    }

    public static Stage getStage(Object object) {
        return (Stage)(((Parent)object).getScene().getWindow());
    }

    public static Stage getStage(ActionEvent event) {
        return (Stage)(((Parent)event.getSource()).getScene().getWindow());
    }
}
