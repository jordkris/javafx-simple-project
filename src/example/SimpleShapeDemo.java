/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
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
    private final Rotate cameraRotateX = new Rotate(), cameraRotateY = new Rotate();
    private Group connection = new Group();
    private final Group connectionContainer = new Group();
    private final Group boxes = new Group();
    private Sphere light;
    private boolean skeletonState = true;

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

    public class SimpleButton extends StackPane {

        private final Rectangle back = new Rectangle(30, 10, Color.RED);
        private final Button button = new Button();
        private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
        private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
        private boolean state;

        public SimpleButton() {
            this.getChildren().addAll(back, button);
            this.setMinSize(30, 15);
            back.maxWidth(30);
            back.minWidth(30);
            back.maxHeight(10);
            back.minHeight(10);
            back.setArcHeight(back.getHeight());
            back.setArcWidth(back.getHeight());
            back.setFill(Color.valueOf("#ced5da"));
            Double r = 2.0;
            button.setShape(new Circle(r));
            setAlignment(button, Pos.CENTER_LEFT);
            button.setMaxSize(15, 15);
            button.setMinSize(15, 15);
            button.setStyle(buttonStyleOff);
            EventHandler<Event> click = new EventHandler<Event>() {
                @Override
                public void handle(Event e) {
                    connectionToggler(state);
                    if (state) {
                        button.setStyle(buttonStyleOff);
                        back.setFill(Color.valueOf("#ced5da"));
                        setAlignment(button, Pos.CENTER_LEFT);
                        state = false;
                    } else {
                        button.setStyle(buttonStyleOn);
                        back.setFill(Color.valueOf("#80C49E"));
                        setAlignment(button, Pos.CENTER_RIGHT);
                        state = true;
                    }
                }
            };

            button.setFocusTraversable(false);
            setOnMouseClicked(click);
            button.setOnMouseClicked(click);
        }
    }

    public Point3D createNormalPoint(Point3D core, Point3D side) {
        double x = side.getX() + (side.getX() - core.getX()) / 2;
        double y = side.getY() + (side.getY() - core.getY()) / 2;
        double z = side.getZ() + (side.getZ() - core.getZ()) / 2;
        return new Point3D(x, y, z);
    }

    public Object[] createBox(double side, Color color, String title, double tX, double tY) {
        SimpleBox sb = new SimpleBox(side, color); // Red
        Group box = sb.getBox();
        Text text = new Text(title);
        box.setTranslateX(tX);
        box.setTranslateY(tY);
        text.setTranslateX(tX);
        text.setTranslateY(tY);
        text.setTranslateZ(-100);
        return new Object[]{box, text};
    }

    public double distance(Point3D a, Point3D b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getZ() - b.getZ(), 2));
    }

    public double getCosinus(Point3D a, Point3D b, Point3D c) {
        return Math.round(Math.pow(distance(a, b), 2) + Math.pow(distance(a, c), 2) - Math.pow(distance(b, c), 2)) / (2 * distance(a, b) * distance(a, c));
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
        double cosinusValue;
        for (int i = 0; i < g.getChildren().size(); i++) {
            Group shape = (Group) g.getChildren().get(i);
            Point3D shapeCoord = this.getCoordinates(shape);

            for (int j = 0; j < shape.getChildren().size(); j++) {
                Rectangle rect = (Rectangle) shape.getChildren().get(j);
                rect.setAccessibleText(String.format("Rect %s", j));
                connection.getChildren().add(this.createConnection(this.getCoordinates(light), this.getCoordinates(rect)));
                connection.getChildren().add(this.createConnection(this.getCoordinates(shape), this.createNormalPoint(shapeCoord, this.getCoordinates(rect))));
                cosinusValue = getCosinus(this.createNormalPoint(shapeCoord, this.getCoordinates(rect)), this.getCoordinates(rect), getCoordinates(light)) + 1;
//                Color color = (Color) rect.getFill();
                rect.setFill(new Color((1 - 1 * cosinusValue / 2), (1 - 1 * cosinusValue / 2), 0, 1));
            }
        }
    }

    private void connectionToggler(boolean state) {
        this.skeletonState = state;
        PhongMaterial pm = new PhongMaterial();
        for (Node n : this.connection.getChildren()) {
            Cylinder c = (Cylinder) n;
            if (!state) {
                pm.setDiffuseColor(Color.GREY);
            } else {
                pm.setDiffuseColor(Color.TRANSPARENT);
            }
            c.setMaterial(pm);
        }
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
            this.handleConnection(boxes);
            this.connectionContainer.getChildren().add(connection);
            connectionToggler(skeletonState);
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

        // Light
        SimpleLight sl = new SimpleLight(10, Color.YELLOW);
        light = sl.getLight();
        Group lights = new Group(light);

        Object[] obj;
        obj = this.createBox(100, Color.CORAL, "Box 1", -200, -200);
        Group box1 = (Group) obj[0];
        Text text1 = (Text) obj[1];

        obj = this.createBox(100, Color.CORAL, "Box 2", 0, -200);
        Group box2 = (Group) obj[0];
        Text text2 = (Text) obj[1];

        obj = this.createBox(100, Color.CORAL, "Box 3", 200, -200);
        Group box3 = (Group) obj[0];
        Text text3 = (Text) obj[1];

        obj = this.createBox(100, Color.CORAL, "Box 4", -200, 0);
        Group box4 = (Group) obj[0];
        Text text4 = (Text) obj[1];

        obj = this.createBox(100, Color.CORAL, "Box 5", 200, 0);
        Group box5 = (Group) obj[0];
        Text text5 = (Text) obj[1];

        obj = this.createBox(100, Color.CORAL, "Box 6", -200, 200);
        Group box6 = (Group) obj[0];
        Text text6 = (Text) obj[1];

        obj = this.createBox(100, Color.CORAL, "Box 7", 0, 200);
        Group box7 = (Group) obj[0];
        Text text7 = (Text) obj[1];

        obj = this.createBox(100, Color.CORAL, "Box 8", 200, 200);
        Group box8 = (Group) obj[0];
        Text text8 = (Text) obj[1];

        // Box or Cube
        boxes.getChildren().addAll(box1, box2, box3, box4, box5, box6, box7, box8);

        // Text
        Group texts = new Group(text1, text2, text3, text4, text5, text6, text7, text8);

        this.handleConnection(boxes);
        connectionContainer.getChildren().add(connection);

        Group shapes = new Group(boxes, lights, connectionContainer);
        Slider sliderX = this.createSlider(-200, 200, "x", shapes);
        Label labelX = this.createLabel("Vertical Camera", -300, 200);
        Slider sliderY = this.createSlider(-200, 250, "y", shapes);
        Label labelY = this.createLabel("Horizontal Camera", -300, 250);

        // controls
        Group controls = new Group(sliderX, sliderY, labelX, labelY);

        // Button
        SimpleButton sb = new SimpleButton();
        sb.setTranslateX(300);
        Label labelSb = this.createLabel("Show Skeleton", 300, 30);
        connectionToggler(skeletonState);
        // All
        root.getChildren().addAll(shapes, texts, sb, labelSb);
        Scene scene = new Scene(root, 800, 600, true);

        scene.setCamera(this.camera);
        this.initMouseControl(box1, scene);
        this.initMouseControl(box2, scene);
        this.initMouseControl(box3, scene);
        this.initMouseControl(box4, scene);
        this.initMouseControl(box5, scene);
        this.initMouseControl(box6, scene);
        this.initMouseControl(box7, scene);
        this.initMouseControl(box8, scene);
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
