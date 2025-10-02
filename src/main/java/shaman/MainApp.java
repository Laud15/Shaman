package shaman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import shaman.io.TextReader;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainApp extends Application {

    private final Random random= new Random();
    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {

        ComboBox<Integer> diceSelector=new ComboBox<>();
        textArea=new TextArea();
        Button rollButton = new Button("throw the dice");
        Button btnReadFolder = new Button("Read the folder's files");
        Button btnReadFile = new Button("Read the file");
        Button btnClearTxtArea = new Button("clean txt area");
        Label resultLabel = new Label("Result: ");

        diceSelector.getItems().addAll(4,6,8,10,12,20,100);
        diceSelector.setValue(20);
        textArea.setPrefHeight(600);
        textArea.setWrapText(true);

        btnClearTxtArea.setOnAction(event -> {
            textArea.clear();
        });

        //Dice roll code
        rollButton.setOnAction(event -> {
            int sides = diceSelector.getValue();
            int result = random.nextInt(sides) + 1;
            resultLabel.setText("Result: " + result);
        });

        //read a single file
        btnReadFile.setOnAction(event->{
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select file");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = chooser.showOpenDialog(primaryStage);
            if(file != null){
                try {
                    String text = TextReader.readFile(file);
                    textArea.setText(text);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        //read al file in the selected folder, code in FileReader
        btnReadFolder.setOnAction(event -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Select folder");
            File folder = chooser.showDialog(primaryStage);
            if (folder != null) {
                try {
                    String text = TextReader.readAllTextFilesInFolder(folder);
                    textArea.setText(text);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox root = new VBox(15, diceSelector, rollButton, resultLabel,btnReadFile, btnReadFolder, textArea, btnClearTxtArea);
        root.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(root, 600, 600);

        primaryStage.setTitle("shaman");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
