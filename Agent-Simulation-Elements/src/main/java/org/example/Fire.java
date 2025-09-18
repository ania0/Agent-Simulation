package org.example;

import java.awt.*;

public class Fire extends Element {

    public Fire(int x, int y) {
        super(x, y, Color.RED);
    }

    @Override
    public int collide(Element element){
        if (element instanceof Air){
            return 1; // wygrywa z powietrzem
        }
        else if(element instanceof Water){
            return 0; //przegrywa z wodą
        }
        else if(element instanceof Fire){
            return 2; //powieksza sie gdy spotka inny ogien
        }
        return -1;
    }
}
