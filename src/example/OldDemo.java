/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author 62499
 */
public class OldDemo extends Application {
    private Timeline animation;

    private void init(Stage primaryStage) {
        Group root = new Group();
        root.setDepthTest(DepthTest.ENABLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1024, 200, true)); // 700 Panjang dan 150 lebar dari form
        primaryStage.getScene().setCamera(new PerspectiveCamera());
        root.getTransforms().addAll(
            new Translate(700 / 2, 200 / 2), // letak objek dengan form
            new Rotate(360, Rotate.Y_AXIS) // letak objek dengan objek lain
        );
        root.getChildren().add(create3dContent());
    }

    public Node create3dContent() {
        Delta c = new Delta(90,Color.AQUA,2);//warna yang akan di gunakan dan ukuran objeck
        c.rx.setAngle(60);//pengambilan kamera yang akan di tampilkan
        c.ry.setAngle(60);
        Delta c2 = new Delta(50,Color.GREEN,1);
        c2.setTranslateX(200);//jarak x dengan objek sebelahnya
        c2.rx.setAngle(60);
        c2.ry.setAngle(60);
        Delta c3 = new Delta(50,Color.ORANGE,1);
        c3.setTranslateX(-200);
        c3.rx.setAngle(60);
        c3.ry.setAngle(60);
        
        Delta c4 = new Delta(110,Color.AZURE,1);
        c4.setTranslateX(400);
        c4.rx.setAngle(60);
        c4.ry.setAngle(60);

        animation = new Timeline();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,//durasi perputaran
                        new KeyValue(c.ry.angleProperty(), 0d),
                        new KeyValue(c2.rx.angleProperty(), 0d),
                        new KeyValue(c3.rz.angleProperty(), 0d),
                         new KeyValue(c4.rz.angleProperty(), 0d)
                ),
                new KeyFrame(Duration.seconds(1),//durasi perputaran berdasarkan waktu
                        new KeyValue(c.ry.angleProperty(), 360d),
                        new KeyValue(c2.rx.angleProperty(), 360d),
                        new KeyValue(c3.rz.angleProperty(), 360d),
                         new KeyValue(c4.rz.angleProperty(), 360d)
                
                ));
        animation.setCycleCount(Animation.INDEFINITE);

        return new Group(c,c2,c3,c4);
    }

    public void play() {
        animation.play();
    }

    @Override public void stop() {
        animation.pause();
    }
    public class Delta extends Group { // pembuatan bentuk objek
        final Rotate rx = new Rotate(0,Rotate.X_AXIS);
        final Rotate ry = new Rotate(0,Rotate.Y_AXIS);
        final Rotate rz = new Rotate(0,Rotate.Z_AXIS);
        public Delta(double size, Color color, double shade) {
            getTransforms().addAll(rz, ry, rx);
            getChildren().addAll(
                RectangleBuilder.create() // back face
                    .width(size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.5*shade), 1.0))
                    .translateX(-0.5*size)
                    .translateY(-0.5*size)
                    .translateZ(0.5*size)
                    .build(),
                RectangleBuilder.create() // bottom face
                    .width(size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.4*shade), 1.0))
                    .translateX(-0.5*size)
                    .translateY(0)
                    .rotationAxis(Rotate.X_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // right face
                    .width(size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.3*shade), 1.0))
                    .translateX(-1*size)
                    .translateY(-0.5*size)
                    .rotationAxis(Rotate.Y_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // left face
                    .width(size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.2*shade), 1.0))
                    .translateX(0)
                    .translateY(-0.5*size)
                    .rotationAxis(Rotate.Y_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // top face
                    .width(size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.1*shade), 1.0))
                    .translateX(-0.5*size)
                    .translateY(-1*size)
                    .rotationAxis(Rotate.X_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // top face
                    .width(size).height(size)
                    .fill(color)
                    .translateX(-0.5*size)
                    .translateY(-0.5*size)
                    .translateZ(-0.5*size)
                    .build()
            );
        }
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
        play();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX 
     * application. main() serves only as fallback in case the 
     * application can not be launched through deployment artifacts,
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
