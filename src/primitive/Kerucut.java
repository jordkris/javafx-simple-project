/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primitive;

import java.util.*;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.shape.Shape;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author 62499
 */
public final class Kerucut extends Group {

    public List<Shape> shapes = new ArrayList<>();
    public Color color;

    public Kerucut(double r, double h, double n, Color color) {
        this.setColor(color);

        // Create triangle 
        double tetha = Math.toDegrees(Math.atan(r / h));
        double fullDegree = (n - 2) * 180;
        double coneCoefficient = Math.tan(Math.toRadians(fullDegree / (2 * n)));
//        System.out.println(coneCoefficient);
//        Point2D orthocenter = this.calculateOrthocenter(
//                Point2D.ZERO,
//                new Point2D(r / coneCoefficient, Math.sqrt(Math.pow(h, 2) + Math.pow(r, 2))),
//                new Point2D(-r / coneCoefficient, Math.sqrt(Math.pow(h, 2) + Math.pow(r, 2)))
//        );
        Point2D orthocenter = this.calculateOrthocenter(
                Point2D.ZERO,
                new Point2D(r, h),
                new Point2D(-r, h)
        );
//        System.out.println(orthocenter);
        List<Double> basePoints = new ArrayList<>();
        Polygon triangle;
        for (int i = 0; i < n; i++) {
            double rotation = ((double) i) / n * 360;
//            triangle = new Polygon(
//                    0, 0,
//                    r / coneCoefficient, Math.sqrt(Math.pow(h, 2) + Math.pow(r, 2)),
//                    -r / coneCoefficient, Math.sqrt(Math.pow(h, 2) + Math.pow(r, 2))
//            );
            triangle = new Polygon(
                    -orthocenter.getX(), -orthocenter.getY(),
                    r / coneCoefficient - orthocenter.getX(), Math.sqrt(Math.pow(h, 2) + Math.pow(r, 2)) - orthocenter.getY(),
                    -r / coneCoefficient - orthocenter.getX(), Math.sqrt(Math.pow(h, 2) + Math.pow(r, 2)) - orthocenter.getY()
            );
            shapes.add(triangle);
            shapes.get(i).getTransforms().addAll(
                    new Rotate(rotation, new Point3D(0, 1, 0)),
                    new Rotate(tetha, 0, -orthocenter.getY(), 0, new Point3D(1, 0, 0))
            );
//            
//            shapes.get(i).getTransforms().addAll(new Rotate(rotation, 0, 0, 0, new Point3D(0, 1, 0)));
            shapes.get(i).setFill(color);
            shapes.get(i).setAccessibleText(String.format("%f,%f,%f", color.getRed(), color.getGreen(), color.getBlue()));
//            shapes.get(i).setStroke(color);
//            shapes.get(i).setStrokeWidth(3);
            this.getChildren().addAll(shapes.get(i));
            basePoints.add(r * Math.cos(Math.toRadians(rotation)) / coneCoefficient - r * Math.sin(Math.toRadians(rotation)));
            basePoints.add(r * Math.sin(Math.toRadians(rotation)) / coneCoefficient + r * Math.cos(Math.toRadians(rotation)));
        }
        // Create polygonal base
        Polygon basePolygon = new Polygon(basePoints.stream().mapToDouble(d -> d).toArray());
        basePolygon.getTransforms().addAll(new Rotate(90, new Point3D(1, 0, 0)), new Translate(0, 0, -(h - orthocenter.getY())));
        basePolygon.setAccessibleText(String.format("%f,%f,%f", color.getRed(), color.getGreen(), color.getBlue()));
        basePolygon.setFill(color);
//        basePolygon.setStroke(color);
//        basePolygon.setStrokeWidth(2);
        this.getChildren().add(basePolygon);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point2D calculateOrthocenter(Point2D point1, Point2D point2, Point2D point3) {
        double x1 = point1.getX(), y1 = point1.getY();
        double x2 = point2.getX(), y2 = point2.getY();
        double x3 = point3.getX(), y3 = point3.getY();
        double x4 = ((y2 - y1) * (x2 * (x3 - x1) + y2 * (y3 - y1)) - (y3 - y1) * (x3 * (x2 - x1) + y3 * (y2 - y1))) / ((y3 - y1) * (x1 - x2) - (y2 - y1) * (x1 - x3));
        double y4 = (y2 - y1 != 0) ? (x1 - x2) / (y2 - y1) * (x4 - x3) + y3 : (x1 - x3) / (y3 - y1) * (x4 - x2) + y2;
//        System.out.println(String.format("(%.2f,%.2f),(%.2f,%.2f),(%.2f,%.2f),(%.2f,%.2f)", x1, y1, x2, y2, x3, y3, x4, y4));
        return new Point2D(x4, y4);
    }
}
