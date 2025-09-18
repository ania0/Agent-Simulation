package org.example;

import java.awt.*;

public class Earth extends Element{

    public Earth(int x, int y) {
        super(x, y, Color.GREEN);
    }

    @Override
    public int collide(Element element){
        if (element instanceof Water) {
            return 1; //wygrywa z wodą
        } else if (element instanceof Air) {
            return 0; //przegrywa z powietrzem
        }
        else if(element instanceof Earth){
            return 2; //kiedy napotka inną ziemie, zwieksza sie
        }
        return -1;
    }
}
