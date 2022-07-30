/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primitive;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 62499
 */
public class LightToggler extends StackPane {

    private final Rectangle back = new Rectangle(30, 10, Color.RED);
    private final Button button = new Button();
    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private boolean state;

    public LightToggler(Boolean skeletonState, Group connection) {
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
                connectionToggler(skeletonState, connection);
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

    public boolean connectionToggler(boolean skeletonState, Group connection) {
        skeletonState = state;
        PhongMaterial pm = new PhongMaterial();
        for (Node n : connection.getChildren()) {
            Cylinder c = (Cylinder) n;
            if (state) {
                pm.setDiffuseColor(Color.GREY);
            } else {
                pm.setDiffuseColor(Color.TRANSPARENT);
            }
            c.setMaterial(pm);
        }
        return skeletonState;
    }
}
