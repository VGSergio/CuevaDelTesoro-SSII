package mvc.view;

import mvc.controller.Controller;
import mvc.view.controls.Controls;

import javax.swing.*;

public class View extends javax.swing.JFrame {

    private final int WIDTH = 800;

    private final Controller CONTROLLER;

    private final Controls CONTROLS;
    private final Maze MAZE;

    public View(Controller controller, int mazeSideSquares) {
        this.CONTROLLER = controller;
        this.CONTROLS = new Controls(controller , WIDTH);
        this.MAZE = new Maze(controller, WIDTH, mazeSideSquares);

        configure();
        initComponents();
        showView();
    }

    private void configure(){
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    private void initComponents() {
        add(CONTROLS);
        add(MAZE);
    }

    private void showView(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Cave");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Maze getMaze() {
        return MAZE;
    }

    public void mazeSizeChanged(int mazeSideSquares){
        MAZE.setMazeSide(mazeSideSquares);
    }

}
