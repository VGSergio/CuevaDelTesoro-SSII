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
import java.util.HashMap;
import java.util.Map;

import static mvc.model.Global.*;

public class Maze extends JPanel {

    private final Controller CONTROLLER;

    private final int WINDOW_SIZE;
    private int mazeSide;
    private int squareSize;
    private int[] squares;

    private static final Color SQUARE_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLACK;
    private final Map<Integer, BufferedImage> imageCache = new HashMap<>();

    public Maze(Controller controller, int windowSize, int mazeSide) {
        this.CONTROLLER = controller;
        this.WINDOW_SIZE = windowSize;

        setMazeSide(mazeSide);
        initializeImages();
        configure();
    }

    public void setMazeSide(int mazeSide) {
        this.mazeSide = mazeSide;
        this.squareSize = WINDOW_SIZE / mazeSide;

        this.squares = new int[mazeSide * mazeSide]; // Reinitialize squares array
        Arrays.fill(squares, -1);

        revalidate();
        repaint();
    }

    private void initializeImages() {
        imageCache.put(MONSTER, loadImage(MONSTER_IMAGE));
        imageCache.put(HOLE, loadImage(HOLE_IMAGE));
        imageCache.put(TREASURE, loadImage(TREASURE_IMAGE));
    }

    private void configure() {
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        addMouseListener(new MazeMouseListener());
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + path);
            return null;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < mazeSide; row++) {
            for (int column = 0; column < mazeSide; column++) {
                int x = column * squareSize;
                int y = row * squareSize;
                int element = squares[row * mazeSide + column];

                drawSquare(g, x, y);
                drawElement(g, element, x, y);
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y) {
        g.setColor(SQUARE_COLOR);
        g.fillRect(x, y, squareSize, squareSize);
        g.setColor(BORDER_COLOR);
        g.drawRect(x, y, squareSize, squareSize);
    }

    private void drawElement(Graphics g, int element, int x, int y) {
        if (element != -1) {
            BufferedImage image = imageCache.get(element);
            if (image != null) {
                g.drawImage(image, x, y, squareSize, squareSize, null);
            }
        }
    }

    public void placeElement(int element, int row, int column) {
        squares[row * mazeSide + column] = element;
        repaint(column * squareSize, row * squareSize, squareSize, squareSize); // Repaint the specific square
    }

    private class MazeMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            e.consume();
            int row = e.getY() / squareSize;
            int column = e.getX() / squareSize;
            CONTROLLER.notify(SQUARE_CLICKED, row, column);
        }
    }

}
