package org.example;

import java.awt.*;

public class Water extends Element{

    public Water(int x, int y) {
        super(x, y, Color.CYAN);
    }

    @Override
    public int collide(Element element){
        if (element instanceof Fire) {
            return 1; //wygrywa z ogniem
        } else if (element instanceof Earth) {
            return 0; //przegrywa z ziemia
        }
        else if(element instanceof Water){
            return 2; //zwieksza sie jesli napotka inna wode
        }
        return -1;
    }
}
