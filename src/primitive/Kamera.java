/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primitive;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;

/**
 *
 * @author 62499
 */
public class Kamera extends PerspectiveCamera {

    public double anchorX, anchorY, anchorAngleX, anchorAngleY;
    public DoubleProperty angleX = new SimpleDoubleProperty(0), angleY = new SimpleDoubleProperty(0);
    public Group connections,shapes;
    public LineUtility lineUtility;
    public SimpleLight light;
    public boolean skeletonState;
    public LightToggler lightToggler;
    public Scene mainScene;

    public Kamera() {
        this.setTranslateX(-370);
        this.setTranslateY(-300);
    }

    public void initMouseControl(Node node) {
        Rotate xRotate;
        Rotate yRotate;
        node.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        mainScene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        mainScene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
            connections.getChildren().clear();
            Group connection = new Group();
            connections.getChildren().add(connection);
            lineUtility.handleConnection(shapes, connection, light);
            skeletonState = lightToggler.connectionToggler(skeletonState, connection);
        });

    }

    public double getAnchorX() {
        return anchorX;
    }

    public void setAnchorX(double anchorX) {
        this.anchorX = anchorX;
    }

    public double getAnchorY() {
        return anchorY;
    }

    public void setAnchorY(double anchorY) {
        this.anchorY = anchorY;
    }

    public double getAnchorAngleX() {
        return anchorAngleX;
    }

    public void setAnchorAngleX(double anchorAngleX) {
        this.anchorAngleX = anchorAngleX;
    }

    public double getAnchorAngleY() {
        return anchorAngleY;
    }

    public void setAnchorAngleY(double anchorAngleY) {
        this.anchorAngleY = anchorAngleY;
    }

    public DoubleProperty getAngleX() {
        return angleX;
    }

    public void setAngleX(DoubleProperty angleX) {
        this.angleX = angleX;
    }

    public DoubleProperty getAngleY() {
        return angleY;
    }

    public void setAngleY(DoubleProperty angleY) {
        this.angleY = angleY;
    }

    public Group getConnections() {
        return connections;
    }

    public void setConnections(Group connections) {
        this.connections = connections;
    }

    public Group getShapes() {
        return shapes;
    }

    public void setShapes(Group shapes) {
        this.shapes = shapes;
    }

    public LineUtility getLineUtility() {
        return lineUtility;
    }

    public void setLineUtility(LineUtility lineUtility) {
        this.lineUtility = lineUtility;
    }

    public SimpleLight getLight() {
        return light;
    }

    public void setLight(SimpleLight light) {
        this.light = light;
    }

    public boolean isSkeletonState() {
        return skeletonState;
    }

    public void setSkeletonState(boolean skeletonState) {
        this.skeletonState = skeletonState;
    }

    public LightToggler getLightToggler() {
        return lightToggler;
    }

    public void setLightToggler(LightToggler lightToggler) {
        this.lightToggler = lightToggler;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

}
