package mvc.view;

import mvc.controller.Controller;
import mvc.view.controls.Controls;

import javax.swing.*;

public class View extends javax.swing.JFrame {

    private final Controls controls;
    private final MazeView mazeView;

    public View(Controller controller, byte mazeSideSquares, byte[] squares) {
        int width = 800;

        controls = new Controls(controller, width);
        mazeView = new MazeView(controller, width, mazeSideSquares, squares);

        configure();
        initComponents();
        showView();
    }

    private void configure() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    private void initComponents() {
        add(controls);
        add(mazeView);
    }

    private void showView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Cave of the treasure");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public MazeView getMaze() {
        return mazeView;
    }

    public void mazeSizeChanged(byte mazeSideSquares, byte[] squares) {
        mazeView.setMaze(mazeSideSquares, squares);
    }

    public Controls getControls() {
        return controls;
    }

    public void updateMaze() {

    }
}
