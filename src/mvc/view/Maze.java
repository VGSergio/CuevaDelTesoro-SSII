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
    private byte mazeSide;
    private int squareSize;
    private byte[] squares;

    private static final Color SQUARE_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLACK;
    private final Map<Integer, BufferedImage> imageCache = new HashMap<>();

    public Maze(Controller controller, int windowSize, byte mazeSide) {
        this.CONTROLLER = controller;
        this.WINDOW_SIZE = windowSize;

        setMazeSide(mazeSide);
        initializeImages();
        configure();
    }

    public void setMazeSide(byte mazeSide) {
        this.mazeSide = mazeSide;
        this.squareSize = WINDOW_SIZE / mazeSide;

        this.squares = new byte[mazeSide * mazeSide]; // Reinitialize squares array
        Arrays.fill(squares, CLEAN);

        revalidate();
        repaint();
    }

    private void initializeImages() {
        imageCache.put((int) MONSTER, loadImage(MONSTER_IMAGE));
        imageCache.put((int) HOLE, loadImage(HOLE_IMAGE));
        imageCache.put((int) TREASURE, loadImage(TREASURE_IMAGE));
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

        for (byte row = 0; row < mazeSide; row++) {
            for (byte column = 0; column < mazeSide; column++) {
                int x = column * squareSize;
                int y = row * squareSize;
                byte element = squares[row * mazeSide + column];

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

    private void drawElement(Graphics g, byte element, int x, int y) {
        if (element != CLEAN) {
            BufferedImage image = imageCache.get((int) element);
            if (image != null) {
                g.drawImage(image, x, y, squareSize, squareSize, null);
            }
        }
    }

    public void placeElement(byte element, byte row, byte column) {
        squares[row * mazeSide + column] = element;
        repaint(column * squareSize, row * squareSize, squareSize, squareSize); // Repaint the specific square
    }

    private class MazeMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            e.consume();
            byte row = (byte) (e.getY() / squareSize);
            byte column = (byte) (e.getX() / squareSize);
            CONTROLLER.notify(SQUARE_CLICKED, row, column);
        }
    }

}
