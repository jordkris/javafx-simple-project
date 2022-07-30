/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primitive;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author 62499
 */
public class LineUtility {

    public LineUtility() {
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

    public Point3D getCoordinates(Node node) {
        Point3D center = node.localToScene(0, 0, 0);
        return new Point3D(Math.round(center.getX()), Math.round(center.getY()), Math.round(center.getZ()));
    }

    public Point3D createNormalPoint(Point3D core, Point3D side) {
        double x = side.getX() + (side.getX() - core.getX()) / 2;
        double y = side.getY() + (side.getY() - core.getY()) / 2;
        double z = side.getZ() + (side.getZ() - core.getZ()) / 2;
        return new Point3D(x, y, z);
    }

    public double getCosinus(Point3D a, Point3D b, Point3D c) {
        return Math.round(Math.pow(a.distance(b), 2) + Math.pow(a.distance(c), 2) - Math.pow(b.distance(c), 2)) / (2 * a.distance(b) * a.distance(c));
    }

    public void handleConnection(Group shapes, Group connection, SimpleLight light) {
        double cosinusValue;
        for (int i = 0; i < shapes.getChildren().size(); i++) {
            Group shape = (Group) shapes.getChildren().get(i);
            Point3D shapeCoord = this.getCoordinates(shape);
            Shape subShape;
            for (int j = 0; j < shape.getChildren().size(); j++) {
                subShape = (Shape) shape.getChildren().get(j);
                Point3D normalPoint = this.createNormalPoint(shapeCoord, this.getCoordinates(subShape));
                connection.getChildren().add(this.createConnection(this.getCoordinates(light), this.getCoordinates(subShape)));
                connection.getChildren().add(this.createConnection(this.getCoordinates(shape), normalPoint));
                cosinusValue = getCosinus(normalPoint, this.getCoordinates(subShape), getCoordinates(light)) + 1;
                String color[] = subShape.getAccessibleText().split(",");
                double redValue = Math.round((1 - 1 * cosinusValue / 2) * Float.parseFloat(color[0]) * 100.0) / 100.0;
                double greenValue = Math.round((1 - 1 * cosinusValue / 2) * Float.parseFloat(color[1]) * 100.0) / 100.0;
                double blueValue = Math.round((1 - 1 * cosinusValue / 2) * Float.parseFloat(color[2]) * 100.0) / 100.0;
                subShape.setFill(new Color(redValue, greenValue, blueValue, 1));
            }
        }
    }
}
