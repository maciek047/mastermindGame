package com.kodilla;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class boardViewer {
    public static ImageView pinsViewer (int index){
        String pins[] = new String[2];
        pins[0] = "pin-black.png";
        pins[1] = "pin-white.png";

        ImageView result = new ImageView(new Image("file:resources/" + pins[index]));
        result.setFitHeight(17);
        result.setFitWidth(17);
        return result;
    }
}
