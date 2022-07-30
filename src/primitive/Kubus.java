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
public class Kubus extends Group {

    public List<Shape> shapes = new ArrayList<>();
    public Color color;

    public Kubus(double s, Color color) {
        this.setColor(color);
        Rectangle rect;
        this.setAccessibleText("Kubus");
        for (int i = 0; i < 6; i++) {
            rect = new Rectangle(-0.5 * s, -0.5 * s, s, s);
            rect.setFill(color);
            rect.setStroke(Color.BLACK);
            rect.setAccessibleText(String.format("%f,%f,%f", color.getRed(), color.getGreen(), color.getBlue()));
            switch (i) {
                case 0:
                    rect.getTransforms().addAll(new Rotate(90, new Point3D(1, 0, 0)), new Translate(0, 0, 0.5 * s));
                    break;
                case 1:
                    rect.getTransforms().addAll(new Rotate(-90, new Point3D(1, 0, 0)), new Translate(0, 0, 0.5 * s));
                    break;
                case 2:
                    rect.getTransforms().addAll(new Translate(0, 0, -0.5 * s));
                    break;
                case 3:
                    rect.getTransforms().addAll(new Translate(0, 0, 0.5 * s));
                    break;
                case 4:
                    rect.getTransforms().addAll(new Rotate(-90, new Point3D(0, 1, 0)), new Translate(0, 0, 0.5 * s));
                    break;
                case 5:
                    rect.getTransforms().addAll(new Rotate(90, new Point3D(0, 1, 0)), new Translate(0, 0, 0.5 * s));
                    break;
            }
            shapes.add(rect);
            this.getChildren().add(rect);
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
