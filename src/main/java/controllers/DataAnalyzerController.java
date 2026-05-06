package controllers;

import businesslogic.DataAnalyzer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller for the Data Analyzer UI.
 * Handles file selection, analysis, and exporting results.
 * @author Bryan Yerina
 */
public class DataAnalyzerController {

    @FXML private Label statsArea;

    private final DataAnalyzer analyzer = new DataAnalyzer();
    private File currentFile;

    /**
     * Lets you pick a file, and analyzes it
     * @param event Select file button pressed
     */
    @FXML
    private void handleSelectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data File");

        // Only allow text files
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        Stage stage = (Stage) statsArea.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            currentFile = file;

            try {
                analyzer.analyzeFile(file.getAbsolutePath());
                statsArea.setText(analyzer.getFormattedResults());
            } catch (IOException | NumberFormatException e) {
                statsArea.setText("Error reading file: " + e.getMessage());
            }
        }
    }

    /**
     * Exports stats to text file
     * @param event Export stats to .txt button pressed
     */
    @FXML
    private void handleExport(ActionEvent event) {
        if (currentFile == null) {
            statsArea.setText("No file analyzed yet.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Stats");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        fileChooser.setInitialFileName("analysis_results.txt");

        Stage stage = (Stage) statsArea.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(analyzer.getFormattedResults());
                statsArea.setText("Results exported successfully!");
            } catch (IOException e) {
                statsArea.setText("Error writing file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleHelp(ActionEvent event){
        Stage stage = (Stage) statsArea.getScene().getWindow();

        statsArea.setText("""
                1. Press "Select file"
                2. Choose a text file with data from your computer
                3. Press "Export stats to .txt"
                4. Select a location to save the stats file and choose a name
                """);
    }

}