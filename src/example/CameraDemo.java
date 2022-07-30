/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 *
 * @author 62499
 */
public class CameraDemo extends Application {

    private static class RotateCamera extends Group {

        private final Camera camera;
        private final Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        private final Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        private final Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);

        public RotateCamera() {
            buildAxes();
            camera = new PerspectiveCamera(true);
            camera.setFarClip(6000);
            camera.setNearClip(0.01);
            camera.setTranslateZ(-2000);
            camera.getTransforms().addAll(xRotate, yRotate, zRotate);
        }

        private void buildAxes() {
            final Box xAxis = new Box(1200, 10, 10);
            final Box yAxis = new Box(10, 1200, 10);
            final Box zAxis = new Box(10, 10, 1200);

            xAxis.setMaterial(new PhongMaterial(Color.RED));
            yAxis.setMaterial(new PhongMaterial(Color.GREEN));
            zAxis.setMaterial(new PhongMaterial(Color.BLUE));

            Group axisGroup = new Group();
            axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
            this.getChildren().add(axisGroup);
        }
    }

    @Override
    public void start(Stage stage) {
        RotateCamera g = new RotateCamera();
        Scene scene = new Scene(g, 800, 800, Color.BLACK);
        scene.setCamera(g.camera);
        stage.setScene(scene);
        stage.show();
        scene.setOnScroll((final ScrollEvent e) -> {
            g.xRotate.setAngle(g.xRotate.getAngle() + e.getDeltaY() / 10);
            g.yRotate.setAngle(g.yRotate.getAngle() - e.getDeltaX() / 10);
            g.setTranslateX(g.getTranslateX() + e.getDeltaX());
            g.setTranslateY(g.getTranslateY() + e.getDeltaY());
        });
        scene.setOnKeyPressed((KeyEvent e) -> {
            KeyCode code = e.getCode();
            System.out.println(code);
            switch (code) {
                case LEFT:
                    g.zRotate.setAngle(g.zRotate.getAngle() + 10);
                    break;
                case RIGHT:
                    g.zRotate.setAngle(g.zRotate.getAngle() - 10);
                    break;
                case UP:
                    g.setTranslateZ(g.getTranslateZ() - 100);
                    break;
                case DOWN:
                    g.setTranslateZ(g.getTranslateZ() + 100);
                    break;
                case SPACE:
                    g.xRotate.setAngle(0);
                    g.yRotate.setAngle(0);
                    g.zRotate.setAngle(0);
                    g.setTranslateX(0);
                    g.setTranslateY(0);
                    g.setTranslateZ(0);
                    break;
                default:
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
