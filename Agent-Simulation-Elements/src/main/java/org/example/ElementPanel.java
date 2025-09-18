package org.example;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.example.Main.*;

public class ElementPanel extends JPanel {

    private List<Element> elements; //lista zywiolow
    private Random random;
    private JButton button1;

    //liczby ustawione przez uzytkownika
    public int initialElementCountFire;
    public int initialElementCountWater;
    public int initialElementCountAir;
    public int initialElementCountEarth;

    //zmienne wykorzystywane w schedulerze
    private int framerate = 60;
    private double delta = 1.0 / (double) framerate;
    private int frameTime = 1000 / framerate;

    private JLabel whoWon;

    //do przemieszczania elementow w danym momencie
    private ScheduledExecutorService scheduler;

    public ElementPanel(int waterCount, int airCount, int fireCount, int earthCount) {
        //ustawienie wartosci pozyskanych w poprzednim panelu
        this.initialElementCountWater = waterCount;
        this.initialElementCountAir = airCount;
        this.initialElementCountFire = fireCount;
        this.initialElementCountEarth = earthCount;
        this.whoWon = new JLabel();
        elements = new ArrayList<>();
        random = new Random();
        button1 = new JButton("Start simulation");
        createElements();

        this.add(button1);
        this.add(whoWon);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationStart(); //symulacja sie rozpoczyna
                startSpawningAdditionalElements(); //wywolywana jest metoda, ktora po 10 sekundach tworzy kolejne zywioly
            }
        });
    }
//poczatek symulacji
    public void simulationStart() {
        scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                simulationUpdate();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, frameTime, TimeUnit.MILLISECONDS);
    }

    private void startSpawningAdditionalElements() {
        scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                spawnAdditionalElements();
            }
        };

        scheduler.scheduleAtFixedRate(task, 10, 10, TimeUnit.SECONDS); //wykonywane od pierwszych 10 sekund co 10 sekund

    }

    private void spawnAdditionalElements() {
        //liczymy ile jest obecnie kazdej kategorii
        int waterCount = (int) elements.stream().filter(e -> e instanceof Water).count();
        int airCount = (int) elements.stream().filter(e -> e instanceof Air).count();
        int fireCount = (int) elements.stream().filter(e -> e instanceof Fire).count();
        int earthCount = (int) elements.stream().filter(e -> e instanceof Earth).count();

        //jesli zostala wiecej niz jedna kategoria - symulacja nie zakonczyla sie - beda tworzone kolejne elementy
        if (countRemainingTypes(waterCount, airCount, fireCount, earthCount) > 1) {
            spawnElements(initialElementCountWater, Water.class);
            spawnElements(initialElementCountAir, Air.class);
            spawnElements(initialElementCountFire, Fire.class);
            spawnElements(initialElementCountEarth, Earth.class);
            //repaint();
            //jesli symulacja sie zakonczyla, to sprawdzamy kto jest zwyciezca i pokazujemy to na ekranie
        } else {
            String winner = "";
            if (waterCount > 0) {
                winner = "Water";
            } else if (airCount > 0) {
                winner = "Air";
            } else if (fireCount > 0) {
                winner = "Fire";
            } else if (earthCount > 0) {
                winner = "Earth";
            }
            elements.clear();
            repaint();
            whoWon.setText("Winner: " + winner);
            whoWon.setVisible(true);
            scheduler.shutdown();
        }
    }
    //metoda do obliczania ile typow zostalo
    private int countRemainingTypes(int waterCount, int airCount, int fireCount, int earthCount) {
        int count = 0;
        if (waterCount > 0) count++;
        if (airCount > 0) count++;
        if (fireCount > 0) count++;
        if (earthCount > 0) count++;
        return count;
    }
    //rysowanie kolejnych elementow na ekranie
    private void spawnElements(int initialCount, Class<? extends Element> elementType) {
        int newElements = (int) Math.ceil(initialCount * 0.1);
        for (int i = 0; i < newElements; i++) {
            try {
                Element element = elementType.getDeclaredConstructor(int.class, int.class).newInstance(random.nextInt(screenSizeX), random.nextInt(screenSizeY));
                elements.add(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//poczatkowe stworzenie elementow na ekranie
    private void createElements() {
        for (int i = 0; i < initialElementCountWater; i++) {
            elements.add(new Water(random.nextInt(screenSizeX), random.nextInt(screenSizeY)));
        }
        for (int i = 0; i < initialElementCountEarth; i++) {
            elements.add(new Earth(random.nextInt(screenSizeX), random.nextInt(screenSizeY)));
        }
        for (int i = 0; i < initialElementCountAir; i++) {
            elements.add(new Air(random.nextInt(screenSizeX), random.nextInt(screenSizeY)));
        }
        for (int i = 0; i < initialElementCountFire; i++) {
            elements.add(new Fire(random.nextInt(screenSizeX), random.nextInt(screenSizeY)));
        }
    }
//przesunięcie każdego elementu na liscie za pomoca zmiennej delta
    public void simulationElementsMove() {
        for (Element element : elements) {
            element.move(delta);
        }
    }

    //kolizja elementow
    public void simulationElementsCollide() {
        List<Element> toDelete = new ArrayList<>(); //stworzenie listy elementow do usuniecia
        for (int i = 0; i < elements.size(); i++) { //iteracja przez tablice elementow "dwukrotna", zeby pobrac indeks pierwszego i drugiego elementu ktore sie zderzyly
            for (int j = i + 1; j < elements.size(); j++) {
                Element elementA = elements.get(i);
                Element elementB = elements.get(j);
                double distance = Math.sqrt(Math.pow(elementA.x - elementB.x, 2) + Math.pow(elementA.y - elementB.y, 2));
                //jesli obiekty sie stykają
                if (distance <= elementA.radius) {
                    //jesli obiekt a jest wiekszy od b, to usuwany jest b, jesli b jest wiekszy od a to usuwany jest a
                    if (elementA.radius > elementB.radius) {
                        toDelete.add(elementB);
                    } else if (elementA.radius < elementB.radius) {
                        toDelete.add(elementA);
                    } else {
                        //jesli sa tego samego rozmiaru, to to, ktory znika zalezy od tego, ktory z nich wygrywa (ustwaione w klasie kazdego zywiolu)
                        int collision = elementA.collide(elementB);
                        if (collision == 1) {
                            toDelete.add(elementB);
                        } else if (collision == 0) {
                            toDelete.add(elementA);
                        } else if (collision == 2) {
                            //powiekszenie wiekszego elementu i usuniecie mniejszego
                            elementA.radius = (int)(elementA.radius * 1.5);
                            toDelete.add(elementB);
                        }
                    }
                }
            }
        }
        //usuniecie z tablicy wszystkich elementow tych, ktore "przegraly"
        elements.removeAll(toDelete);
    }
//aktualizacja symulacji
    public void simulationUpdate() {
        simulationElementsMove(); //ruch elementow
        simulationElementsCollide(); //kolizja
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Element element : elements) {
            element.draw(g);
        }
    }
}