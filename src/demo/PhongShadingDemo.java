/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author 62499
 */
public class PhongShadingDemo extends Application {

    private final PerspectiveCamera camera = new PerspectiveCamera();
    private final Group root = new Group();
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    private void init(Stage primaryStage) {
        camera.setTranslateX(-370);
        camera.setTranslateY(-300);
        camera.setTranslateZ(0);
//        camera.setRotationAxis(new Point3D(300, 15, 20));
//        camera.setFarClip(0);
//        camera.setNearClip(0.01);
//        camera.setTranslateZ(-500);
//        camera.setRotate(-20);
    }

    public void rotate(Node node, Point3D axis) {
        RotateTransition rotation = new RotateTransition();
        rotation.setAxis(axis);
        rotation.setDuration(Duration.seconds(8));
        rotation.setByAngle(360);
        rotation.setNode(node);
        rotation.setAutoReverse(true);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.play();
//        return rotation;
    }

    public void translate(Node node, Point3D axis) {
        RotateTransition rotation = new RotateTransition();
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(axis.getX());
        translate.setByY(axis.getY());
        translate.setByZ(axis.getZ());
        translate.setDuration(Duration.seconds(8));
        translate.setNode(node);
        translate.setAutoReverse(true);
        translate.setCycleCount(Animation.INDEFINITE);
        translate.play();
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
        this.init(primaryStage);

        // Phong Shading
        PhongMaterial phongShading = new PhongMaterial(new Color(0.1804, 0.5451, 0.3412, 1));
        phongShading.setSpecularColor(Color.WHITE);

        // Flat Shading
        PhongMaterial flatShading = new PhongMaterial(new Color(0, 0.4118, 0.5804, 1)); //Sea Blue Box
        flatShading.setSpecularColor(Color.TRANSPARENT);

        Box box1 = new Box(100, 100, 100);
        Text text1 = new Text("Box 1");
        box1.setMaterial(flatShading);
        box1.setTranslateX(-100);
        box1.setTranslateY(-100);
        text1.setTranslateX(-100);
        text1.setTranslateY(-100);

        Box box2 = new Box(100, 100, 100);
        Text text2 = new Text("Box 2");
        box2.setMaterial(flatShading);
        box2.setTranslateX(100);
        box2.setTranslateY(-100);
        text2.setTranslateX(100);
        text2.setTranslateY(-100);

        Box box3 = new Box(100, 100, 100);
        Text text3 = new Text("Box 3");
        box3.setMaterial(phongShading);
        box3.setTranslateX(-100);
        box3.setTranslateY(100);
        text3.setTranslateX(-100);
        text3.setTranslateY(100);

        Box box4 = new Box(100, 100, 100);
        Text text4 = new Text("Box 4");
        box4.setMaterial(phongShading);
        box4.setTranslateX(100);
        box4.setTranslateY(100);
        text4.setTranslateX(100);
        text4.setTranslateY(100);

        Sphere point = new Sphere(1);

        root.getChildren().addAll(box1, box2, box3, box4, text1, text2, text3, text4, point);

//        this.rotate(box1, new Point3D(1, 1, 0));
//        this.rotate(box2, new Point3D(1, 0, 1));
//        this.rotate(box3, new Point3D(0, 1, 1));
//        this.rotate(box4, new Point3D(1, 1, 1));
//
//        this.translate(box1, new Point3D(200, 0, 0));
//        this.translate(box2, new Point3D(0, -200, 0));
//        this.translate(box3, new Point3D(0, 200, 0));
//        this.translate(box4, new Point3D(-200, 0, 0));
//
//        this.translate(text1, new Point3D(200, 0, 0));
//        this.translate(text2, new Point3D(0, -200, 0));
//        this.translate(text3, new Point3D(0, 200, 0));
//        this.translate(text4, new Point3D(-200, 0, 0));
        Scene scene = new Scene(root, 800, 600);
        scene.setCamera(this.camera);
        this.initMouseControl(box1, scene);
        this.initMouseControl(box2, scene);
        this.initMouseControl(box3, scene);
        this.initMouseControl(box4, scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Box Example");
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
