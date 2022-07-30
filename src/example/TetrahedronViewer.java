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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

// drag the mouse over the rectangle to rotate it.
public class TetrahedronViewer extends Application {

    double anchorX, anchorY, anchorAngle;
    double anchorAngleX = 0, anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);

    private PerspectiveCamera addCamera(Scene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(false);
        scene.setCamera(perspectiveCamera);
        return perspectiveCamera;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final MeshView rect = new MeshView(
                new Shape3DTetrahedron(200)
        );
        rect.setDrawMode(DrawMode.FILL);
//        rect.setMaterial(new PhongMaterial(Color.GREEN));
        rect.setRotationAxis(Rotate.Y_AXIS);
        rect.setTranslateX(250);
        rect.setTranslateY(250);
// try commenting this line out to see what it's effect is . . .
//        rect.setCullFace(CullFace.NONE);

        final Group root = new Group(rect);
        final Scene scene = new Scene(root, 500, 500, true);

        Rotate xRotate;
        Rotate yRotate;
        rect.getTransforms().addAll(
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

        addCamera(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public class Shape3DTetrahedron extends TriangleMesh {

        public Shape3DTetrahedron(float length) {
            int tc = 1;
            float[] points = {
                -length, 0, -length,
                -length, 0, length,
                length, 0, length,
                length, 0, -length,
                0, length, 0
            };
            float[] texCoords = {
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
            };
            int[] faces = {
                0, tc, 3, tc, 1, tc,
                1, tc, 3, tc, 2, tc,
                0, tc, 1, tc, 4, tc,
                1, tc, 2, tc, 4, tc,
                2, tc, 3, tc, 4, tc,
                3, tc, 0, tc, 4, tc
            };

            this.getPoints().setAll(points);
            this.getTexCoords().setAll(texCoords);
            this.getFaces().setAll(faces);
        }
    }
}
