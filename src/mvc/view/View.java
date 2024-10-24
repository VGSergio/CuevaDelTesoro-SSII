package mvc.view;

import mvc.controller.Controller;
import mvc.view.controls.Controls;

import javax.swing.*;

public class View extends javax.swing.JFrame {

    private final Controls CONTROLS;
    private final Maze MAZE;

    public View(Controller controller, byte mazeSideSquares, byte[] squares) {
        int width = 800;

        this.CONTROLS = new Controls(controller, width);
        this.MAZE = new Maze(controller, width, mazeSideSquares, squares);

        configure();
        initComponents();
        showView();
    }

    private void configure() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    private void initComponents() {
        add(CONTROLS);
        add(MAZE);
    }

    private void showView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Cave of the treasure");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Maze getMaze() {
        return MAZE;
    }

    public void mazeSizeChanged(byte mazeSideSquares, byte[] squares) {
        MAZE.setMaze(mazeSideSquares, squares);
    }

    public Controls getControls() {
        return CONTROLS;
    }
}
