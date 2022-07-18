/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 *
 * @author 62499
 */
public class SimpleShapeDemo extends Application {

    private final PerspectiveCamera camera = new PerspectiveCamera();
    private final Group root = new Group();
    private double anchorX, anchorY, anchorAngleX = 0, anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);
    private final Rotate cameraRotateX = new Rotate();
    private final Rotate cameraRotateY = new Rotate();
    private Group connection = new Group();
    private final Group connectionContainer = new Group();
    private final Group boxes = new Group();
    private Sphere light;
    private List<Pair> targetLight = new ArrayList<>();
    private Color universalColor = new Color(1, 0, 0, 1);
    private double cosinusValue;

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

    private final class SimpleBox extends Group {

        public Rectangle rect1, rect2, rect3, rect4, rect5, rect6;
        public Group box;

        private SimpleBox(double s, Color color) {
            rect1 = new Rectangle(-0.5 * s, -0.5 * s, s, s);
            rect2 = new Rectangle(-0.5 * s, -0.5 * s, s, s);
            rect3 = new Rectangle(-0.5 * s, -0.5 * s, s, s);
            rect4 = new Rectangle(-0.5 * s, -0.5 * s, s, s);
            rect5 = new Rectangle(-0.5 * s, -0.5 * s, s, s);
            rect6 = new Rectangle(-0.5 * s, -0.5 * s, s, s);
            rect1.getTransforms().addAll(new Rotate(90, new Point3D(1, 0, 0)), new Translate(0, 0, 0.5 * s));
            rect1.setFill(color);
            rect2.getTransforms().addAll(new Rotate(-90, new Point3D(1, 0, 0)), new Translate(0, 0, 0.5 * s));
            rect2.setFill(color);
            rect3.getTransforms().addAll(new Translate(0, 0, -0.5 * s));
            rect3.setFill(color);
            rect4.getTransforms().addAll(new Translate(0, 0, 0.5 * s));
            rect4.setFill(color);
            rect5.getTransforms().addAll(new Rotate(-90, new Point3D(0, 1, 0)), new Translate(0, 0, 0.5 * s));
            rect5.setFill(color);
            rect6.getTransforms().addAll(new Rotate(90, new Point3D(0, 1, 0)), new Translate(0, 0, 0.5 * s));
            rect6.setFill(color);
            Group group = new Group();
            group.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6);
            this.setBox(group);
        }

        public Group getBox() {
            return box;
        }

        public void setBox(Group box) {
            this.box = box;
        }
    }

    public final class SimpleLight {

        private final double r;
        private final Color color;
        public Sphere light;

        public SimpleLight(double r, Color color) {
            this.r = r;
            this.color = color;

            Sphere sphere = new Sphere(r);
            PhongMaterial fill = new PhongMaterial(color);
            fill.setSpecularColor(Color.TRANSPARENT);
            sphere.setMaterial(fill);
            this.setLight(sphere);
        }

        public Sphere getLight() {
            return light;
        }

        public void setLight(Sphere light) {
            this.light = light;
        }
    }

    public class Pair {

        private Point3D sidePoint;
        private Point3D normalPoint;
        private Shape shape;

        public Pair() {
        }

        public Point3D getSidePoint() {
            return sidePoint;
        }

        public void setSidePoint(Point3D sidePoint) {
            this.sidePoint = sidePoint;
        }

        public Point3D getNormalPoint() {
            return normalPoint;
        }

        public void setNormalPoint(Point3D normalPoint) {
            this.normalPoint = normalPoint;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

        @Override
        public String toString() {
            return "Pair{" + "sidePoint=" + sidePoint + ", normalPoint=" + normalPoint + ", shape=" + shape + '}';
        }

    }

    public Point3D createNormalPoint(Point3D core, Point3D side) {
        double x = side.getX() + (side.getX() - core.getX()) / 2;
        double y = side.getY() + (side.getY() - core.getY()) / 2;
        double z = side.getZ() + (side.getZ() - core.getZ()) / 2;
        return new Point3D(x, y, z);
    }

    public double distance(Point3D a, Point3D b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }

    public double getCosinus(Point3D a, Point3D b, Point3D c) {
        return Math.round(Math.pow(distance(a, b), 2) + Math.pow(distance(a, c), 2) - Math.pow(distance(b, c), 2)) / (2 * distance(a, b) * distance(a, c));
    }

    public void handleShading() {;
        
        targetLight.forEach((shapePair) -> {
            System.out.print(String.format("%.2f|", getCosinus(shapePair.normalPoint, shapePair.sidePoint, getCoordinates(light))));
            cosinusValue = getCosinus(shapePair.normalPoint, shapePair.sidePoint, getCoordinates(light)) + 1;
            shapePair.getShape().setFill(new Color(1 - 1 * cosinusValue / 2, 0, 0, 1));
        });
        System.out.println(targetLight.size());
    }

    public Cylinder createConnection(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.5, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    private void handleConnection(Group g) {
        g.getChildren().stream().map((nodeShape) -> (Group) nodeShape).map((shape) -> {
//            connection.getChildren().addAll(this.createConnection(this.getCoordinates(light), this.getCoordinates(shape)));
            return shape;
        }).map((shape) -> {
//            System.out.println(this.getCoordinates(light));
            Point3D shapeCoord = this.getCoordinates(shape);
            Pair shapePair = new Pair();
            shape.getChildren().forEach((rect) -> {
                connection.getChildren().add(this.createConnection(this.getCoordinates(light), this.getCoordinates(rect)));
                connection.getChildren().add(this.createConnection(this.getCoordinates(shape), this.createNormalPoint(shapeCoord, this.getCoordinates(rect))));
                shapePair.setNormalPoint(this.createNormalPoint(shapeCoord, this.getCoordinates(rect)));
                shapePair.setSidePoint(this.getCoordinates(rect));
                shapePair.setShape((Shape) rect);
                targetLight.add(shapePair);
            });

//            System.out.println(Arrays.toString(targetLight.toArray()));
            return shape;
        }).forEachOrdered((_item) -> {
//            System.out.println("\n");
        });
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
            this.connectionContainer.getChildren().remove(connection);
            this.connection = new Group();
            targetLight.clear();
            this.handleConnection(boxes);
            this.connectionContainer.getChildren().add(connection);
            handleShading();
        });

        scene.setOnMouseReleased(event -> {
//            System.out.println(this.getCoordinates(light));
        });

    }

    private Slider createSlider(double tX, double tY, String axis, Node node) {
        Slider slider = new Slider(0, 360, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMaxWidth(600);

        if (axis.equals("x")) {
            slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cameraRotateX.setAxis(new Point3D(1, 0, 0));
                cameraRotateX.setAngle(newValue.doubleValue());
            });
            node.getTransforms().addAll(cameraRotateX);
        } else {
            slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                cameraRotateY.setAxis(new Point3D(0, 1, 0));
                cameraRotateY.setAngle(newValue.doubleValue());
            });
            node.getTransforms().addAll(cameraRotateY);
        }
        slider.setTranslateX(tX);
        slider.setTranslateY(tY);
        return slider;
    }

    private Label createLabel(String text, double tX, double tY) {
        Label label = new Label(text);
        label.setTranslateX(tX);
        label.setTranslateY(tY);
        return label;
    }

    public Point3D getCoordinates(Node node) {
        Point3D center = node.localToScene(0, 0, 0);
        Scene s = node.getScene();

        return new Point3D(Math.round(center.getX()), Math.round(center.getY()), Math.round(center.getZ()));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.init(primaryStage);

        SimpleBox sb1 = new SimpleBox(100, universalColor); // Red
        Group box1 = sb1.getBox();
        Text text1 = new Text("Box 1");
        box1.setTranslateX(-100);
        box1.setTranslateY(-100);
        text1.setTranslateX(-100);
        text1.setTranslateY(-100);
        text1.setTranslateZ(-100);

        SimpleBox sb2 = new SimpleBox(100, universalColor); /// Green
        Group box2 = sb2.getBox();
        Text text2 = new Text("Box 2");
        box2.setTranslateX(100);
        box2.setTranslateY(-200);
        text2.setTranslateX(100);
        text2.setTranslateY(-200);
        text2.setTranslateZ(-100);

        SimpleBox sb3 = new SimpleBox(100, universalColor); // Blue
        Group box3 = sb3.getBox();
        Text text3 = new Text("Box 3");
        box3.setTranslateX(-100);
        box3.setTranslateY(100);
        text3.setTranslateX(-100);
        text3.setTranslateY(100);
        text3.setTranslateZ(-100);

        SimpleBox sb4 = new SimpleBox(100, universalColor); // Yellow
        Group box4 = sb4.getBox();
        Text text4 = new Text("Box 4");
        box4.setTranslateX(100);
        box4.setTranslateY(200);
        text4.setTranslateX(100);
        text4.setTranslateY(200);
        text4.setTranslateZ(-100);

        // Light
        SimpleLight sl = new SimpleLight(10, Color.YELLOW);
        light = sl.getLight();
        light.setTranslateX(200);
        Group lights = new Group(light);

        // Box or Cube
        boxes.getChildren().addAll(box1, box2, box3, box4);

        this.handleConnection(boxes);
        connectionContainer.getChildren().add(connection);

        Group shapes = new Group(boxes, lights, connectionContainer);
        Slider sliderX = this.createSlider(-200, 200, "x", shapes);
        Label labelX = this.createLabel("Vertical Camera", -300, 200);
        Slider sliderY = this.createSlider(-200, 250, "y", shapes);
        Label labelY = this.createLabel("Horizontal Camera", -300, 250);

        // Text
        Group texts = new Group(text1, text2, text3, text4);

        // controls
        Group controls = new Group(sliderX, sliderY, labelX, labelY);

        // All
        root.getChildren().addAll(shapes, texts, controls);
        Scene scene = new Scene(root, 800, 600, true);

        handleShading();
        scene.setCamera(this.camera);
        this.initMouseControl(box1, scene);
        this.initMouseControl(box2, scene);
        this.initMouseControl(box3, scene);
        this.initMouseControl(box4, scene);
//        this.initMouseControl(root, scene);
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
