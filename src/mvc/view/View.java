package mvc.view;

import mvc.controller.Controller;
import mvc.view.controls.Controls;

import javax.swing.*;

public class View extends javax.swing.JFrame {

    private final Controls controls;
    private final Maze maze;

    public View(Controller controller, byte mazeSideSquares, byte[] squares) {
        int width = 800;

        controls = new Controls(controller, width);
        maze = new Maze(controller, width, mazeSideSquares, squares);

        configure();
        initComponents();
        showView();
    }

    private void configure() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    private void initComponents() {
        add(controls);
        add(maze);
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
        return maze;
    }

    public void mazeSizeChanged(byte mazeSideSquares, byte[] squares) {
        maze.setMaze(mazeSideSquares, squares);
    }

    public Controls getControls() {
        return controls;
    }

    public void updateMaze() {

    }
}
