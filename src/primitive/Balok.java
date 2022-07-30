/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primitive;

import java.util.*;
import javafx.scene.shape.Shape;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author 62499
 */
public class Balok extends Group {

    public List<Shape> shapes = new ArrayList<>();
    public Color color;

    public Balok(double l, double w, double h, Color color) {
        this.color = color;
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    shapes.add(new Rectangle(-0.5 * l, -0.5 * w, l, w));
                    shapes.get(i).getTransforms().addAll(new Rotate(90, new Point3D(1, 0, 0)), new Translate(0, 0, 0.5 * h));
                    break;
                case 1:
                    shapes.add(new Rectangle(-0.5 * l, -0.5 * w, l, w));
                    shapes.get(i).getTransforms().addAll(new Rotate(-90, new Point3D(1, 0, 0)), new Translate(0, 0, 0.5 * h));
                    break;
                case 2:
                    shapes.add(new Rectangle(-0.5 * l, -0.5 * h, l, h));
                    shapes.get(i).getTransforms().addAll(new Translate(0, 0, -0.5 * w));
                    break;
                case 3:
                    shapes.add(new Rectangle(-0.5 * l, -0.5 * h, l, h));
                    shapes.get(i).getTransforms().addAll(new Translate(0, 0, 0.5 * w));
                    break;
                case 4:
                    shapes.add(new Rectangle(-0.5 * w, -0.5 * h, w, h));
                    shapes.get(i).getTransforms().addAll(new Rotate(-90, new Point3D(0, 1, 0)), new Translate(0, 0, 0.5 * l));
                    break;
                case 5:
                    shapes.add(new Rectangle(-0.5 * w, -0.5 * h, w, h));
                    shapes.get(i).getTransforms().addAll(new Rotate(90, new Point3D(0, 1, 0)), new Translate(0, 0, 0.5 * l));
                    break;
            }
            shapes.get(i).setAccessibleText(String.format("%f,%f,%f", color.getRed(), color.getGreen(), color.getBlue()));
            shapes.get(i).setFill(color);
            shapes.get(i).setStroke(Color.BLACK);
//            shapes.add(rect);
            this.getChildren().add(shapes.get(i));
        }
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
