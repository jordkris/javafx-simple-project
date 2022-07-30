/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

/**
 *
 * @author 62499
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
 
public class PolygonViewer extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }
 
    @Override
    public void start(Stage stage)
    {
        // Create the Triangle
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(50.0, 0.0,  0.0, 50.0,100.0, 50.0);
        triangle.setFill(Color.WHITE);
        triangle.setStroke(Color.RED);
 
        // Create the Parallelogram
        Polygon parallelogram = new Polygon();
        parallelogram.getPoints().addAll(30.0, 0.0,130.0, 0.0,100.00, 50.0, 0.0, 50.0);
        parallelogram.setFill(Color.YELLOW);
        parallelogram.setStroke(Color.BLACK);
 
        // Create the Hexagon
        Polygon hexagon = new Polygon(100.0, 0.0,120.0, 20.0,120.0,
                40.0,100.0, 60.0,80.0,
                40.0,80.0, 20.0);
        hexagon.setFill(Color.WHITE);
        hexagon.setStroke(Color.BLACK);
 
        // Create the HBox
        HBox root = new HBox();
        // Add the Children to the HBox
        root.getChildren().addAll(triangle, parallelogram, hexagon);
        // Set Spacing of the HBox
        root.setSpacing(10);
 
        // Set the Style
        root.setStyle
        (
            "-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: blue;"
        );
 
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("A JavaFX Polygon Example");
        // Display the Stage
        stage.show();
    }
}
