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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class RotationSlider extends Application {

    public void start(Stage stage) {
        //Drawing a Sphere
        Sphere sphere = new Sphere();
        sphere.setRadius(75.0);
        sphere.setDrawMode(DrawMode.LINE);
        //Creating a slider for rotation
        Slider slider1 = new Slider(0, 360, 0);
        //Setting its orientation to vertical
        slider1.setOrientation(Orientation.VERTICAL);
        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(90);
        slider1.setBlockIncrement(10);
        Rotate rotate = new Rotate();
        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //Setting the angle for the rotation
                rotate.setAngle((double) newValue);
            }
        });
        //Creating a slider for scaling
        Slider slider2 = new Slider(0.3, 2.1, 0.6);
        //Setting its orientation to Horizontal
        slider2.setOrientation(Orientation.HORIZONTAL);
        slider2.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(0.5);
        slider2.setBlockIncrement(0.1);
        Scale scale = new Scale();
        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scale.setX((double) newValue);
                scale.setY((double) newValue);
            }
        });
        //Adding all the transformations to the node
        sphere.getTransforms().addAll(rotate, scale);
        //Creating the pane
        BorderPane pane = new BorderPane();
        pane.setRight(new VBox(new Label("Rotate"), slider1));
        pane.setCenter(sphere);
        pane.setLeft(new VBox(new Label("Scale"), slider2));
        pane.setPadding(new Insets(10, 10, 10, 10));
        //Preparing the scene
        Scene scene = new Scene(pane, 595, 330);
        stage.setTitle("Slider Orientation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
