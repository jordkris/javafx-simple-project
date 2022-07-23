/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 *
 * @author 62499
 */
public class Kubus extends Application {

    private final PerspectiveCamera camera = new PerspectiveCamera();
    private final Group root = new Group();
    private double anchorX, anchorY, anchorAngleX = 0, anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);
    private final Group boxes = new Group();

    private void init(Stage primaryStage) {
        camera.setTranslateX(-370);
        camera.setTranslateY(-300);
        camera.setTranslateZ(0);
    }

    public Object[] createBox(double side, Color color, String title, double tX, double tY) {
        PhongMaterial shading = new PhongMaterial(color);
        shading.setSpecularColor(Color.TRANSPARENT);
        Box box = new Box(side, side, side);
        Text text = new Text(title);
        box.setTranslateX(tX);
        box.setTranslateY(tY);
        box.setMaterial(shading);
        box.setRotationAxis(new Point3D(1, 3, 0));
        box.setRotate(30);
        text.setTranslateX(tX);
        text.setTranslateY(tY);
        text.setTranslateZ(-200);
        return new Object[]{box, text};
    }

    private void initMouseControl(Node node, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;
        node.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        double timeA = System.currentTimeMillis();
        this.init(primaryStage);
        Object[] obj;
        obj = this.createBox(100, Color.CORAL, "Box 1", 0, 0);
        Box box1 = (Box) obj[0];
        Text text1 = (Text) obj[1];

        // Box or Cube
        boxes.getChildren().addAll(box1);
        Group shapes = new Group(boxes);

        // Text
        Group texts = new Group(text1);

        // All
        root.getChildren().addAll(shapes, texts);
        Scene scene = new Scene(root, 800, 600, true);

        scene.setCamera(this.camera);
        this.initMouseControl(box1, scene);
//        this.initMouseControl(root, scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Box Example");
        primaryStage.show();
        double timeB = System.currentTimeMillis();
        System.out.println("Performace time: " + (timeB - timeA) / 1000 + " s");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
