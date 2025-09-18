package org.example;
import javax.swing.*;

public class Main {

    public static int screenSizeX =  800;
    public static int screenSizeY = 800;

    public static void main(String[] args) {
        //otworzenie JFrame i poczatkowego panelu, zmiana ustawien JFrame
        JFrame frame = new JFrame("Symulacja");
        frame.setResizable(false);
        StartingPanel startingPanel = new StartingPanel(frame);
        frame.add(startingPanel);
        frame.setVisible(true);
        startingPanel.setLayout(null);
        frame.setSize(screenSizeX, screenSizeY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}