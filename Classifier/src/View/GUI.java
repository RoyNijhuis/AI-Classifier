package View; /**
 * Created by Roy on 6-1-2016.
 */

import Controller.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI implements UI {

    @Override
    public String askForTrainFolder(String category) {
        return null;
    }

    @Override
    public String askForApplyFile() {
        return null;
    }

    @Override
    public int askForMinimumNumberOfOccurrences() {
        return 0;
    }

    @Override
    public String[] askForWordsOnly() {
        return new String[0];
    }

    @Override
    public String[] askClasses() {
        return new String[0];
    }

    @Override
    public void outputDeterminedCategory(String document, String category) {

    }

    @Override
    public String askCorrectClass(String category) {
        return null;
    }

    @Override
    public boolean askLearn() {
        return false;
    }

    @Override
    public void show() {
        Main.showGUI();
    }
}
