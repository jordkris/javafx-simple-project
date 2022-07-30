/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author 62499
 */
public class PhongShadingDemo extends Application {
    
    private final PerspectiveCamera camera = new PerspectiveCamera();
    private final Group root = new Group();

    private void init(Stage primaryStage) {
        camera.setTranslateX(-200);
        camera.setTranslateY(-300);
        camera.setTranslateZ(0);
        camera.setRotationAxis(new Point3D(300, 15, 20));
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.init(primaryStage);

        // Phong Shading
        PhongMaterial phongShading = new PhongMaterial(Color.SEAGREEN);
        phongShading.setSpecularColor(Color.WHITE);

        // Phong Shading
        PhongMaterial flatShading = new PhongMaterial(Color.SEAGREEN);
        flatShading.setSpecularColor(Color.TRANSPARENT);

        Box box1 = new Box(100, 100, 100);
        box1.setMaterial(flatShading);
        box1.setDrawMode(DrawMode.FILL);
        Text text1 = new Text("Box 1");

        Box box2 = new Box(100, 100, 100);
        box2.setMaterial(flatShading);
        box2.setTranslateX(200);
        Text text2 = new Text("Box 2");
        text2.setTranslateX(200);

        Box box3 = new Box(100, 100, 100);
        box3.setMaterial(phongShading);
        box3.setTranslateY(-200);
        Text text3 = new Text("Box 3");
        text3.setTranslateY(-200);

        Box box4 = new Box(100, 100, 100);
        box4.setMaterial(phongShading);
        box4.setTranslateX(200);
        box4.setTranslateY(-200);
        Text text4 = new Text("Box 4");
        text4.setTranslateX(200);
        text4.setTranslateY(-200);

        root.getChildren().addAll(box1, box2, box3, box4, text1, text2, text3, text4);

        this.rotate(box1, new Point3D(1, 1, 0));
        this.rotate(box2, new Point3D(1, 0, 1));
        this.rotate(box3, new Point3D(0, 1, 1));
        this.rotate(box4, new Point3D(1, 1, 1));

        this.translate(box1, new Point3D(200, 0, 0));
        this.translate(box2, new Point3D(0, -200, 0));
        this.translate(box3, new Point3D(0, 200, 0));
        this.translate(box4, new Point3D(-200, 0, 0));

        this.translate(text1, new Point3D(200, 0, 0));
        this.translate(text2, new Point3D(0, -200, 0));
        this.translate(text3, new Point3D(0, 200, 0));
        this.translate(text4, new Point3D(-200, 0, 0));

        Scene scene = new Scene(root, 600, 400);
        scene.setCamera(this.camera);
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
