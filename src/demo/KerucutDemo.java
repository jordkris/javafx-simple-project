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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import primitive.Kamera;
import primitive.Kerucut;
import primitive.LineUtility;
import primitive.LightToggler;
import primitive.SimpleLight;
import primitive.SimpleSlider;

/**
 *
 * @author 62499
 */
public class KerucutDemo extends Application {

    private final Kamera camera = new Kamera();
    private final Group root = new Group();
    private double anchorX, anchorY, anchorAngleX = 0, anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);
    private final Group shapes = new Group(), texts = new Group(), lights = new Group(), connections = new Group();
    private static Group connection = new Group();
    private final LineUtility lineUtility = new LineUtility();
    private final SimpleLight light = new SimpleLight(10, Color.YELLOW);
    private boolean skeletonState = false;
    private final LightToggler lightToggler = new LightToggler(skeletonState, connection);
    private Translate translate = new Translate();
    private Rotate rotate = new Rotate();

    private void init(Stage primaryStage) {
        camera.setAnchorX(anchorX);
        camera.setAnchorY(anchorY);
        camera.setAngleX(angleX);
        camera.setAngleY(angleY);
        camera.setAnchorAngleX(anchorAngleX);
        camera.setAnchorAngleY(anchorAngleY);
        camera.setConnections(connections);
        camera.setLight(light);
        camera.setLineUtility(lineUtility);
        camera.setLightToggler(lightToggler);
        camera.setShapes(shapes);
    }

    public Object[] createCone(double radius, double height, double nTriangle, Color color, String title, double tX, double tY) {
        Group cone = new Kerucut(radius, height, nTriangle, color);
        Text text = new Text(title);
        cone.setTranslateX(tX);
        cone.setTranslateY(tY);
//        cone.setRotationAxis(new Point3D(1, 3, 0));
//        cone.setRotate(30);
        text.setTranslateX(tX);
        text.setTranslateY(tY);
        text.setTranslateZ(-200);
//        Point2D p1 = new Point2D(-2,0);
//        Point2D p2 = new Point2D(2,0);
//        Point2D p3 = new Point2D(0,4);
//        System.out.println(new Kerucut(radius, height, nTriangle, color).calculateOrthocenter(p1, p2, p3).toString());
        return new Object[]{cone, text};
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        double timeA = System.currentTimeMillis();
        this.init(primaryStage);
        Object[] obj;

        // Light
        lights.getChildren().add(light);
        lights.setTranslateZ(-100);
        // Cone
        
        obj = this.createCone(100, 200, 50, Color.CORAL, "Cone 1", 0, 0);
        Group cone1 = (Group) obj[0];
        Text text1 = (Text) obj[1];
        shapes.getChildren().addAll(cone1);

        // All Shapes
        Group allShapes = new Group(this.shapes, lights);

        // Connection
        lineUtility.handleConnection(this.shapes, connection, light);
        connections.getChildren().add(connection);

        // Controls
        lightToggler.setTranslateX(300);
        skeletonState = lightToggler.connectionToggler(skeletonState, connection);
        SimpleSlider lightSlider = new SimpleSlider(-5, 5, 0, shapes, connections, lineUtility, skeletonState, lightToggler);
        for (int j = 0; j < cone1.getChildren().size(); j++) {
            Shape shape1 = (Shape) cone1.getChildren().get(j);
            lightSlider.initRotate(rotate, "Y", light);
        }
        
        // All Texts
        Group texts = new Group(text1);

        // All
        root.getChildren().addAll(allShapes, texts, connections, lightToggler, lightSlider);
        Scene scene = new Scene(root, 800, 600, true);

        // Main Scene
        scene.setCamera(camera);
        camera.setMainScene(scene);
        camera.initMouseControl(cone1);
//        camera.initMouseControl(lights);
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
