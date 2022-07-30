/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampah;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author 62499
 */
public final class AutomaticRotation {

    public Node node;
    public Point3D axis;
    public Duration duration;
    public RotateTransition rotation = new RotateTransition();

    public AutomaticRotation(Node node, Point3D axis, Duration duration) {
        this.setNode(node);
        this.setAxis(axis);
        this.setDuration(duration);
        rotation.setAxis(this.axis);
        rotation.setDuration(Duration.seconds(8));
        rotation.setByAngle(360);
        rotation.setNode(this.node);
        rotation.setAutoReverse(true);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.play();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Point3D getAxis() {
        return axis;
    }

    public void setAxis(Point3D axis) {
        this.axis = axis;
    }

    public RotateTransition getRotation() {
        return rotation;
    }

    public void setRotation(RotateTransition rotation) {
        this.rotation = rotation;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
