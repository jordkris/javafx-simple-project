/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primitive;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 *
 * @author 62499
 */
public class SimpleLight extends Sphere {

    private final double r;
    private final Color color;

    public SimpleLight(double r, Color color) {
        this.r = r;
        this.color = color;

        this.setRadius(r);
        PhongMaterial fill = new PhongMaterial(color);
        fill.setSpecularColor(Color.TRANSPARENT);
        this.setMaterial(fill);
        this.setTranslateX(200);
    }
}
