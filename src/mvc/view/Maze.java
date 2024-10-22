package mvc.view;

import mvc.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static mvc.model.Global.*;


public class Maze extends JPanel {

    private final Controller CONTROLLER;

    private final int WINDOW_SIZE;
    private int mazeSide;
    private int squareSize;

    private int[] squares;

    public Maze(Controller controller, int windowSize, int mazeSide) {
        this.CONTROLLER = controller;
        this.WINDOW_SIZE = windowSize;
        this.mazeSide = mazeSide;
        this.squareSize = windowSize / mazeSide;

        squares = new int[mazeSide * mazeSide];
        Arrays.fill(squares, -1);

        configure();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (int row = 0; row < mazeSide; row++) {
            for (int column = 0; column < mazeSide; column++) {
                int x = column * squareSize;
                int y = row * squareSize;

                // Square
                g.setColor(Color.WHITE);
                g.fillRect(x, y, squareSize, squareSize);

                // Border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, squareSize, squareSize);

                // Image
                int element = squares[row * mazeSide + column];
                if (element != -1) {
                    g.drawImage(getSquareImage(element), x, y, squareSize, squareSize, null);
                }
            }
        }
    }

    public void setMazeSide(int mazeSide) {
        this.mazeSide = mazeSide;
        this.squareSize = WINDOW_SIZE / mazeSide;
        this.squares = new int[mazeSide * mazeSide]; // Reinitialize squares array
        Arrays.fill(squares, -1);

        revalidate();
        repaint();
    }

    public void placeElement(int element, int row, int column) {
        squares[row * mazeSide + column] = element;
        revalidate();
        repaint();
    }

    private void configure() {
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        mouseListener();
    }

    private void mouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();

                int row = Math.floorDiv(e.getY(), squareSize);
                int column = Math.floorDiv(e.getX(), squareSize);

                CONTROLLER.notify(SQUARE_CLICKED, row, column);
            }
        });
    }

    private BufferedImage getSquareImage(int element) {
        String path = switch (element) {
            case MONSTER -> MONSTER_IMAGE;
            case HOLE -> HOLE_IMAGE;
            case TREASURE -> TREASURE_IMAGE;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };

        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
