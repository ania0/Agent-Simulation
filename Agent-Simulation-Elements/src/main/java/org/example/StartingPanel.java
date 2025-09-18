package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingPanel extends JPanel{
    //elementy graficzne
    private javax.swing.JPanel JPanel;
    private JLabel howManyUnitsOf;
    private JSlider airSlider;
    private JSlider waterSlider;
    private JSlider earthSlider;
    private JSlider fireSlider;
    private JLabel waterLabel;
    private JLabel earthLabel;
    private JLabel fireLabel;
    private JLabel airLabel;
    private JButton goToSimulation;
    private JLabel fireNoLbl;
    private JLabel waterNoLbl;
    private JLabel earthNoLbl;
    private JLabel airNoLbl;



    public StartingPanel(JFrame frame) {
        setLayout(null);
        goToSimulation = new JButton("Go to simulation");
        goToSimulation.setBounds(350, 600, 150, 60);
        howManyUnitsOf = new JLabel("How many units of each should the simulation have?");
        howManyUnitsOf.setBounds(250, 50, 350,40);
        this.add(howManyUnitsOf);
        waterLabel = new JLabel("Water");
        waterLabel.setBounds(100, 150, 150,40);
        this.add(waterLabel);
        earthLabel = new JLabel("Earth");
        earthLabel.setBounds(600, 150, 150,40);
        this.add(earthLabel);
        fireLabel = new JLabel("Fire");
        fireLabel.setBounds(100, 450, 150,40);
        airLabel = new JLabel("Air");
        airLabel.setBounds(600, 450, 150,40);
        this.add(fireLabel);
        earthSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, 1);
        earthSlider.setBounds(600, 200, 150,40);
        this.add(earthSlider);
        fireSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, 1);
        fireSlider.setBounds(100, 510, 150,40);
        this.add(fireSlider);
        airSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, 1);
        airSlider.setBounds(600, 510, 150,40);
        this.add(airSlider);
        waterSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, 1);
        waterSlider.setBounds(100,200,150,40);
        this.add(waterSlider);
        this.add(airLabel);
        this.add(goToSimulation);
        fireNoLbl = new JLabel(String.valueOf(fireSlider.getValue()));
        fireNoLbl.setBounds(100, 475, 20, 20);
        this.add(fireNoLbl);

        earthNoLbl = new JLabel(String.valueOf(earthSlider.getValue()));
        earthNoLbl.setBounds(600, 175, 20, 20);
        this.add(earthNoLbl);

        waterNoLbl = new JLabel(String.valueOf(waterSlider.getValue()));
        waterNoLbl.setBounds(100, 175, 20, 20);
        this.add(waterNoLbl);

        airNoLbl = new JLabel(String.valueOf(airSlider.getValue()));
        airNoLbl.setBounds(600, 475, 20, 20);
        this.add(airNoLbl);
        //pozyskiwanie wartosci ze sliderow
        earthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                earthNoLbl.setText(String.valueOf(earthSlider.getValue()));
            }
        });

        fireSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fireNoLbl.setText(String.valueOf(fireSlider.getValue()));
            }
        });

        airSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                airNoLbl.setText(String.valueOf(airSlider.getValue()));
            }
        });

        waterSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                waterNoLbl.setText(String.valueOf(waterSlider.getValue()));
            }
        });
        //kiedy kliknie sie przycisk go to simulation, otwierany jest nowy panel
        goToSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int waterno = waterSlider.getValue(); //pozyskiwanie ile ma byc jakiego elementu
              int  airno = airSlider.getValue();
               int fireno = fireSlider.getValue();
               int earthno = earthSlider.getValue();
                ElementPanel elementPanel = new ElementPanel(waterno, airno, fireno, earthno); //przekazywanie tych liczb do nowo otwartegopanelu
                frame.setContentPane(elementPanel);
                frame.revalidate();
            }
        });
    }




}

