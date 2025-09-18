package org.example;

import java.awt.*;

public class Air extends Element{

    public Air(int x, int y) {
        super(x, y, Color.LIGHT_GRAY);
    }

    @Override
    public int collide(Element element){
        if (element instanceof Earth) {
            return 1; //wygrywa z ziemią
        } else if (element instanceof Fire) {
            return 0; //przegrywa z ogniem
        }
        else if(element instanceof Air){
            return 2; //kiedy napotka inne powietrze, zwieksza sie
        }
        return -1;
    }
}
