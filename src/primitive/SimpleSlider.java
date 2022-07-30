/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primitive;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author 62499
 */
public class SimpleSlider extends Slider {

    public Group shapes, connections;
    public LineUtility lineUtility;
    public boolean skeletonState;
    public LightToggler lightToggler;

    public SimpleSlider(double min, double max, double value, Group shapes, Group connections, LineUtility lineUtility, boolean skeletonState, LightToggler lightToggler) {
        this.setMin(min);
        this.setMax(max);
        this.setValue(value);
        this.setShowTickLabels(true);
        this.setShowTickMarks(true);
        this.setPrefSize(300, 100);
        this.setTranslateY(200);
        this.shapes = shapes;
        this.connections = connections;
        this.lineUtility = lineUtility;
        this.skeletonState = skeletonState;
        this.lightToggler = lightToggler;
    }

    public void initTranslate(Translate translate, String axis, Node targetNode) {
        this.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            switch (axis) {
                case "X":
                    translate.setX(newValue.doubleValue());
                    break;
                case "Y":
                    translate.setY(newValue.doubleValue());
                    break;
                case "Z":
                    translate.setZ(newValue.doubleValue());
                    break;
            }
            connections.getChildren().clear();
            Group connection = new Group();
            connections.getChildren().add(connection);
            lineUtility.handleConnection(shapes, connection, (SimpleLight) targetNode);
            skeletonState = lightToggler.connectionToggler(skeletonState, connection);
        });

        targetNode.getTransforms().addAll(translate);
    }

    public void initRotate(Rotate rotate, String axis, Node targetNode) {
        this.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            switch (axis) {
                case "X":
                    rotate.setAxis(new Point3D(1, 0, 0));
                    break;
                case "Y":
                    rotate.setAxis(new Point3D(0, 1, 0));
                    break;
                case "Z":
                    rotate.setAxis(new Point3D(0, 0, 1));
                    break;
            }
            rotate.setPivotX(-200);
            rotate.setAngle(newValue.doubleValue());
            connections.getChildren().clear();
            Group connection = new Group();
            connections.getChildren().add(connection);
            lineUtility.handleConnection(shapes, connection, (SimpleLight) targetNode);
            skeletonState = lightToggler.connectionToggler(skeletonState, connection);
        });

        targetNode.getTransforms().addAll(rotate);
    }

}
