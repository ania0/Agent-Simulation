package org.example;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import static org.example.Main.*;
//klasa abstrakcyjna - wzor dla klas air, earth, fire, water
public abstract class Element{
    protected Color color; //kolor obiektu
    protected int x; //wspolrzedna x
    protected int y; //wspolrzedna y
    protected int size;

    private Random random;
    //zmienne wykorzystywane przy ruchu elementów
    private double moveStep = 400.0;
    private double moveStepRandom = 100.0;
    private double rotationStep = 110.0;

    public double moveDirection = 0;
    public int radius = 30;

    public Element(int x, int y, Color color){
        this.x=x;
        this.y=y;
        this.color=color;
        this.random=new Random();
        //losowy kierunek ruchu od 1 do 4
        this.moveDirection = random.nextDouble(4.0);
        this.size = 20;
    }
//rysowanie elementow na planszy
    public void draw(Graphics g) {
    g.setColor(color);
    g.fillOval(x,y,radius,radius);
    }

    public void move(double delta) { //ruch elementow
        moveDirection += (random.nextDouble(rotationStep) - rotationStep * 0.5) * delta;
        double stepX = Math.sin(moveDirection) * moveStep + (random.nextDouble(moveStepRandom) - moveStepRandom * 0.5);
        double stepY = Math.cos(moveDirection) * moveStep + (random.nextDouble(moveStepRandom) - moveStepRandom * 0.5);

        x = (x + (int)(stepX * delta));
        y = (y + (int)(stepY * delta));
        //ograniczenia ruchu do rozmiaru ekranu
        x = Math.min(x, screenSizeX-radius);
        y = Math.min(y, screenSizeY-2*radius);
        x = Math.max(x, 0);
        y = Math.max(y, 0);
    }
//szablon funkcji na kolizje
    public int collide(Element element){
        return 0;
    }
}

